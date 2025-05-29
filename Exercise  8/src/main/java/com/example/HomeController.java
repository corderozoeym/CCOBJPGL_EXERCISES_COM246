package com.example;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

 
 
public class HomeController implements Initializable {
    ObservableList<User> mylist = FXCollections.observableArrayList();
 
    @FXML
    private TableView<User> mytable;

    @FXML
    private TableColumn<User, String> usernametable;

    @FXML
    private TableColumn<User, String> passwordtable;

    @FXML
    private TableColumn<User, String> accountcreatedtable;

    @FXML
    private TableColumn<User, String> statustable;

    @FXML
    private TextField usernamebox;

    @FXML
    private PasswordField passwordbox;

    @FXML
    private ChoiceBox<String> choicebox;

    @FXML
    private Label usernameLabel; 
 
    @FXML
    private Label wlcmlabel;

 
 
   @Override
    public void initialize(URL url, ResourceBundle rb){

        String username  = LoginController.user.getUsername();
        usernameLabel.setText(username);

        initializeCol();
        loadData();

        // Add items to the choice box
        choicebox.getItems().addAll("Active", "Inactive");

        // Listener to update text fields when a row is selected
        mytable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            
            if (newSelection != null) {
                usernamebox.setText(newSelection.getUsername());
                passwordbox.setText(newSelection.getPassword());
                choicebox.setValue(newSelection.getAccountstatus());
            }
        });
    }
    private void initializeCol(){
       
 
        usernametable.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordtable.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountcreatedtable.setCellValueFactory(new PropertyValueFactory<>("accountcreated"));
        statustable.setCellValueFactory(new PropertyValueFactory<>("accountstatus"));
    }
    private void loadData(){
        mylist.clear();
 
        try {
            File myFile = new File("accounts.txt");
 
        
            if (myFile.exists()) {
 
                Scanner filescanner = new Scanner(myFile);
 
                while (filescanner.hasNextLine()) {
 
                    String data = filescanner.nextLine();
       
                    String username = data.split(",")[0];
                    String password = data.split(",")[1];
                    String dcreated = data.split(",")[2];
                    String status = data.split(",")[3];
 
                    mylist.add(new User(username, password, dcreated, status));
                }
                mytable.setItems(mylist);
 
                filescanner.close();
            }
            else {
                System.out.println(myFile.getName() + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("There is an error");
        }
    }
      @FXML
    private boolean createuser(ActionEvent event) {

        String username = usernamebox.getText();

        String password = passwordbox.getText();

        String status = choicebox.getValue();

        System.out.println(status);
        username = username.trim();
        password = password.trim();
        status = status.trim();

        if(username.length() == 0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no username provided");
        }

        if(password.length() == 0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no password provided");
        }
        
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = today.format(formatter);

        User user = new User(username, password, formattedDate, status);

        try {

            BufferedWriter myWriter = new BufferedWriter(new FileWriter("accounts.txt", true));
      
            myWriter.newLine(); 
            myWriter.write(user.getUsername() + "," + user.getPassword() + "," + user.getAccountcreated() + "," + user.getAccountstatus());

            myWriter.close();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Account Created");
            alert.setContentText("You have created a new account!");
            alert.showAndWait();
            
            loadData();

        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        return true;
    }

    @FXML  
    public boolean deleteuser(ActionEvent event) {

        User user = mytable.getSelectionModel().getSelectedItem();

        String username = (user.getUsername());

        System.out.println(username);

        String userToDelete = username;

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { 
                    String[] parts = line.split(",");
                    if (!parts[0].equalsIgnoreCase(userToDelete)) {
                        updatedLines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"))) {
            for (int i = 0; i < updatedLines.size(); i++) {
                writer.write(updatedLines.get(i));
                if (i < updatedLines.size() - 1) {
                    writer.newLine(); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("This is the header");
        alert.setContentText("User '" + userToDelete + "' has been deleted (if existed).");
        alert.showAndWait();        
        loadData();

        return true;
    }
    
    @FXML
    public boolean updateuser(ActionEvent event) {

        User user = mytable.getSelectionModel().getSelectedItem();

        String username = usernamebox.getText();

        String password = passwordbox.getText();

        String status = choicebox.getValue();

        username = username.trim();
        password = password.trim();
        status = status.trim();

        if(username.length() == 0)
        {
            System.out.println("No username!");
            return false;
        }

        if(password.length() == 0)
        {
            System.out.println("No password!");
            return false;
        }

        String targetUsername = user.getUsername();
        String newPassword = password;
        String newUsername = username;
        String newStatus = status;

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");

                    if (parts.length == 4 && parts[0].equalsIgnoreCase(targetUsername)) {
                        updatedLines.add(newUsername + "," + newPassword + "," + user.getAccountcreated() + "," + newStatus);
                    } else {
                        updatedLines.add(line);
                    }

                    // if (parts.length == 2 && parts[0].equalsIgnoreCase(targetUsername)) {
                    //     // Update password
                    //     updatedLines.add(parts[0] + "," + newPassword + "," + newStatus);
                    // } else {
                    //     updatedLines.add(line);
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"))) {
            for (int i = 0; i < updatedLines.size(); i++) {
                writer.write(updatedLines.get(i));
                if (i < updatedLines.size() - 1) {
                    writer.newLine(); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("This is the header");
        alert.setContentText("User '" + targetUsername + "' has been updated.");
        alert.showAndWait();
        loadData();
        return true;
    }
}