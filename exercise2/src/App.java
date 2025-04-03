import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

      DataScientist datascientist1 = new DataScientist("John", "", 0);

      System.out.println(datascientist1.name + " works as a " + datascientist1.getWork() + " and he earns " + datascientist1.getSalary());

      Researcher researcher1 = new Researcher("Jane", " ", 0);

      System.out.println(researcher1.name + " works as a " + researcher1.getWork() + " and she earns " + researcher1.getSalary());
      

      car mycar = new car();

      System.out.println("My car has " + mycar.cylinder + " Cylinder and " + mycar.battery + " battery");

    }

  
  }


