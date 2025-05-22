package com.example;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController implements Initializable{

    ObservableList<User> mylist = FXCollections.observableArrayList();

    @FXML
    Label usernameLabel;

    @FXML
    private TableColumn<User, String> accountcreatedtable;

    @FXML
    private Button createbutton;

    @FXML
    private Button deletebutton;

    @FXML
    private TableView<User> mytable;

    @FXML
    private TableColumn<User, String> passwordtable;

    @FXML
    private TableColumn<User, String> statustable;

    @FXML
    private Button updatebutton;

    @FXML
    private TableColumn<User, String> usernametable;

    @FXML
    private ChoiceBox<User> choicebox;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeCol();
        loadData();

        String uname = LoginController.user.getUsername(){
        usernameLabel.setText(uname);
    }

    private void loadData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadData'");
    }

    private void initializeCol(){

        usernametable.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordtable.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountcreatedtable.setCellValueFactory(new PropertyValueFactory<>("accountcreated"));
       statustable.setCellValueFactory(new PropertyValueFactory<>("accountstatus"));


    
        try {
            // Create object from File class
            File myFile = new File("accounts.txt");

            // .exists() method checks if a file exists in the pathname
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

 }



