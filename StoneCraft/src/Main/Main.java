package Main;
import Design.*;
import DataBaseManagementConnection.*;
import javax.swing.*;
import java.util.List;


public class Main {

    public static DataAccessObject productDao = new DataAccessObject();

    public static void main(String[] args){

        DataBaseConnection.createDatabase();

        new CustomerMain();

    }


    public static void signUpProcess(){

        SignUp su = new SignUp();

        su.createAccount.addActionListener(e ->{
            String name = su.nameField.getText().trim();
            String email = su.emailField.getText().trim();
            String contactNumber = su.contactField.getText().trim();
            String password = su.passwordField.getText().trim();
            String password2 = su.confirmField.getText().trim();

            if(name.isEmpty() || email.isEmpty() || contactNumber.isEmpty() || password.isEmpty() || password2.isEmpty()){
                System.out.println("Error");
            }
            else{
                if(email.endsWith("@gmail.com")){
                    if(contactNumber.matches("\\d{11}")){
                        if(password.equals(password2)) {

                            Accounts acc = new Accounts(name, email, contactNumber, password);
                            int newId = DataAccessObject.insertAccounts(acc);

                            CustomerMainProcess();
                        }
                        else{
                            System.out.println("password not match");
                        }
                    }
                    else{
                        System.out.println("number must contain only number and it will not exceed to 11 digits");
                    }

                }
                else{
                    System.out.println("email must be valid!");
                }
            }
        });

    }

    public static void CustomerMainProcess(){

         CustomerMain cm = new CustomerMain();

         cm.submitButton.addActionListener(e->{

             String name = cm.sculptureNameField.getText().trim();
             String description = cm.descriptionArea.getText().trim();
             String category = (String) cm.categoryComboBox.getSelectedItem();
             String size = (String) cm.sizeComboBox.getSelectedItem();
             int quantity =  ((Number) cm.quantitySpinner.getValue()).intValue();
             String preferredDesign = cm.designField.getText().trim();
             int price = 0;

             CustomRequest cr = new CustomRequest(name, category, price, quantity, description, size, preferredDesign);
         });

    }


    //gmail must not contain spaces and start always at lower case
    //password must contain 8 character
    //number must not have spaces

    //custom request need ko mamaya
}