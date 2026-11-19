package Main;
import Design.*;
import DataBaseManagementConnection.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.*;


public class Main {

    public static DataAccessObject productDao = new DataAccessObject();

    public static void main(String[] args){

        DataBaseConnection.createDatabase();
        signUpProcess();

    }


    public static void signUpProcess() {

        SignUp su = new SignUp();

        su.createAccount.addActionListener(e -> {

            // Clear all previous errors first
            su.nameWarning.setText("");
            su.emailWarning.setText("");
            su.contactWarning.setText("");
            su.passwordWarning.setText("");

            String name = su.nameField.getText().trim();
            String email = su.emailField.getText().trim();
            String contactNumber = su.contactField.getText().trim();
            String password = su.passwordField.getText().trim();
            String password2 = su.confirmField.getText().trim();

            boolean valid = true;

            if (name.isEmpty()) {
                su.nameWarning.setText("Please enter your name.");
                valid = false;
            }

            if (email.isEmpty()) {
                su.emailWarning.setText("Please enter your email.");
                valid = false;
            } else if (!email.matches("[a-z][a-z0-9._]*@gmail\\.com")) {
                su.emailWarning.setText("This email is invalid.");
                valid = false;
            }

            if (contactNumber.isEmpty()) {
                su.contactWarning.setText("Please enter your contact number.");
                valid = false;
            } else if (!contactNumber.matches("\\d{11}")) {
                su.contactWarning.setText("Contact number must be exactly 11 digits with no spaces.");
                valid = false;
            }


            if (password.isEmpty()) {
                su.passwordWarning.setText("Please enter a password.");
                valid = false;
            } else if (password.length() < 8) {
                su.passwordWarning.setText("Password must be at least 8 characters.");
                valid = false;
            } else if (!password.equals(password2)) {
                su.passwordWarning.setText("Passwords do not match.");
                valid = false;
            }

            if (!valid) {
                return;
            }

            Accounts acc = new Accounts(name, email, contactNumber, password);
            int newId = DataAccessObject.insertAccounts(acc);
            su.dispose();
            customerProcess();
        });

        su.login.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                loginProcess();
                su.dispose();
            }
        });

    }

    public static void loginProcess(){
        Login lg = new Login();


        lg.loginButton.addActionListener(e ->{

            String name = lg.usernameField.getText().trim();
            String email = lg.emailField.getText().trim();
            String password = lg.passwordField.getText().trim();
            boolean valid = true;

            lg.usernameWarning.setText("");
            lg.emailWarning.setText("");
            lg.passwordWarning.setText("");

            Accounts accounts = DataAccessObject.getAccounts(name);

            if(accounts == null){
                lg.usernameWarning.setText("This username doesn't exist.");

            }
            else if(!accounts.getEmailAddress().equals(email)){
                lg.emailWarning.setText("Incorrect email");

            }
            else if (!accounts.getPassword().equals(password)) {
                lg.passwordWarning.setText("Incorrect password.");

            }
            else{
                lg.dispose();
                customerProcess();
            }


        });

        lg.signUpLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                 signUpProcess();
                 lg.dispose();
            }
        });

    }

    public static void customerProcess(){
        CustomerMain cm = new CustomerMain();
    }


    //gmail must not contain spaces and start always at lower case
    //password must contain 8 character
    //number must not have spaces
    //yung sa custom kailangan nakikita kung pending or hindi

    //custom request need ko mamaya


    //error sa log-in di sya nagshoshow ng warning pag mismatch
}