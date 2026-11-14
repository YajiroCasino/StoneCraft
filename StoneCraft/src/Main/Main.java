package Main;
import Design.*;
import DataBaseManagementConnection.*;
import javax.swing.*;
import java.util.List;


public class Main {

    public static DataAccessObject productDao = new DataAccessObject();

    public static void main(String[] args){
        DataBaseConnection.createDatabase();
        new BusinessMain();


    }

    public static void businessMainProcess(){

    }

    public static void signUpProcess(){
        SignUp su = new SignUp();

        su.createAccount.addActionListener(e ->{
            String name = su.nameField.getText();
            String email = su.emailField.getText();
            String contactNumber = su.contactField.getText();
            String password = su.passwordField.getText();
            String password2 = su.confirmField.getText();

            if(password.equals(password2)){
            }
            else{
                System.out.println("Error");
            }

        });

    }
    //may error sa dashboard and yung sa inventory di ko pa nalalagay pero okay naman na sa part ng product
}