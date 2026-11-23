package Main;
import Design.*;
import DataBaseManagementConnection.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;


public class Main {

    public static DataAccessObject productDao = new DataAccessObject();
    public static Accounts currentAccount;

    public static void main(String[] args){

        DataBaseConnection.createDatabase();
        signUpProcess();
    }

    public static void signUpProcess() {

        SignUp su = new SignUp();

        su.createAccount.addActionListener(e -> {

            su.nameWarning.setText("");
            su.emailWarning.setText("");
            su.contactWarning.setText("");
            su.passwordWarning.setText("");

            String name = su.nameField.getText().trim();
            String email = su.emailField.getText().trim();
            String contactNumber = su.contactField.getText().trim();
            String password = su.passwordField.getText().trim();
            String password2 = su.confirmField.getText().trim();

            if (name.equals("Juan Dela Cruz")) name = "";
            if (email.equals("name@gmail.com")) email = "";
            if (contactNumber.equals("09XX XXX XXXX")) contactNumber = "";

            boolean valid = true;

            if (name.isEmpty()) {
                su.nameWarning.setText("Please enter your name.");
                valid = false;
            } else if (DataAccessObject.getAccountsByName(name) != null) {
                su.nameWarning.setText("This name is already taken.");
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

            Accounts acc;

            if (email.equals("bossbusinessowner@gmail.com")) {
                acc = new BusinessAccount(name, email, contactNumber, password, "Boss");
            } else if (email.equals("employeebusinessowner@gmail.com")) {
                acc = new BusinessAccount(name, email, contactNumber, password, "Employee");
            } else {
                acc = new CustomerAccount(name, email, contactNumber, password, "Standard");
            }

            int newId = DataAccessObject.insertAccounts(acc);

            if (newId <= 0) {
                JOptionPane.showMessageDialog(su, "Failed to create your account.");
                return;
            }

            acc.setAccountId(newId);
            currentAccount = acc;

            if (acc instanceof BusinessAccount b) {
                DataAccessObject.insertBusinessAccount(b);
                su.dispose();
                businessProcess();
            } else if (acc instanceof CustomerAccount c) {
                DataAccessObject.insertCustomerAccount(c);
                su.dispose();
                customerProcess();
            }
        });

        su.login.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                loginProcess();
                su.dispose();
            }
        });
    }

    public static void loginProcess() {

        Login lg = new Login();

        lg.loginButton.addActionListener(e -> {

            String name = lg.usernameField.getText().trim();
            String gmail = lg.emailField.getText().trim();
            String password = lg.passwordField.getText().trim();

            lg.usernameWarning.setText("");
            lg.emailWarning.setText("");
            lg.passwordWarning.setText("");

            if (name.equals("Juan Dela Cruz")) name = "";
            if (gmail.equals("name@gmail.com")) gmail = "";

            boolean valid = true;

            if (name.isEmpty()) {
                lg.usernameWarning.setText("Please enter your name.");
                valid = false;
            }

            if (gmail.isEmpty()) {
                lg.emailWarning.setText("Please enter your gmail.");
                valid = false;
            }

            if (password.isEmpty()) {
                lg.passwordWarning.setText("Please enter your password.");
                valid = false;
            }

            if (!valid) {
                return;
            }

            Accounts accounts = DataAccessObject.getAccountsByName(name);

            if (accounts == null) {
                lg.usernameWarning.setText("This username doesn't exist.");
                return;
            }

            if (!accounts.getEmailAddress().equals(gmail)) {
                lg.emailWarning.setText("Wrong gmail.");
                valid = false;
            }

            if (!accounts.getPassword().equals(password)) {
                lg.passwordWarning.setText("Wrong password.");
                valid = false;
            }

            if (!valid) {
                return;
            }

            currentAccount = accounts;
            lg.dispose();

            if (accounts instanceof BusinessAccount) {
                businessProcess();
            } else {
                customerProcess();
            }
        });

        lg.signUpLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                signUpProcess();
                lg.dispose();
            }
        });
    }
    public static void customerProcess(){
        CustomerMain cm = new CustomerMain();

        cm.submitButton.addActionListener(e ->{
            String name = cm.sculptureNameField.getText().trim();
            String description = cm.descriptionArea.getText().trim();
            String category = (String) cm.categoryComboBox.getSelectedItem();
            String size = (String) cm.sizeComboBox.getSelectedItem();
            int quantity =  ((Number) cm.quantitySpinner.getValue()).intValue();
            String preferredDesign = cm.designField.getText().trim();
            int price = 0;

            CustomRequest cr = new CustomRequest(currentAccount, name, category, price, quantity, description, size, preferredDesign);
            int newId = DataAccessObject.insertCustomProduct(cr);
            cm.requestFrame.dispose();
        });



    }

    public static void businessProcess(){
        BusinessMain bm = new BusinessMain();

        bm.profileButton.addActionListener(e -> {
            bm.showProfile();
        });

        bm.addButton.addActionListener(e -> {
            bm.addProduct();
        });


    }


    //custom request need ko mamaya
    //maya kaipuhan ko yung sa design for Business Main yung sa price
    //yungg sa custom request tapos sa message
    //sa message pa
    //yung sa cart
    //delete message
    //improve design


}