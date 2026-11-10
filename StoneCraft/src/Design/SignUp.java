package Design;
import javax.swing.*;
import java.awt.*;

public class SignUp extends  MainDesign{
    public JPanel panel1;
    public JButton productButton;
    public JButton messageButton;
    public JPanel productCard;
    public JPanel messageCard;
    public JPanel contentPane;


    JPanel leftPanel, rightPanel;

    JLabel title, fullName, email, contact, password, confirmPassword, login;

    public JTextField nameField, emailField, contactField;
    public JPasswordField passwordField, confirmField;

    public JButton createAccount;

    // JLabel Logo;
    // JLabel Tagline;

    public SignUp() {

        setLayout(null);
        setPreferredSize(new Dimension(920, 560));
        setBackground(Color.WHITE);

        // LEFT PANEL
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 418, 560);
        leftPanel.setBackground(new Color(43, 42, 40));

        // Logo = new JLabel("StoneCraft");
        // Tagline = new JLabel("Business Management System");

        // RIGHT PANEL
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(418, 0, 502, 560);
        rightPanel.setBackground(Color.WHITE);

        title = new JLabel("Create an Account");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(new Color(31, 30, 28));
        title.setBounds(52, 25, 398, 40);

        fullName = new JLabel("Full Name");
        fullName.setBounds(52, 85, 398, 20);

        nameField = new JTextField();
        nameField.setBounds(52, 110, 398, 38);

        email = new JLabel("Email Address");
        email.setBounds(52, 160, 398, 20);

        emailField = new JTextField();
        emailField.setBounds(52, 185, 398, 38);

        contact = new JLabel("Contact Number");
        contact.setBounds(52, 235, 398, 20);

        contactField = new JTextField();
        contactField.setBounds(52, 260, 398, 38);
        password = new JLabel("Password");
        password.setBounds(52, 310, 398, 20);

        passwordField = new JPasswordField();
        passwordField.setBounds(52, 335, 398, 38);

        confirmPassword = new JLabel("Confirm Password");
        confirmPassword.setBounds(52, 385, 398, 20);

        confirmField = new JPasswordField();
        confirmField.setBounds(52, 410, 398, 38);

        createAccount = new JButton("Create Account");
        createAccount.setBounds(52, 465, 398, 42);
        createAccount.setBackground(new Color(181, 101, 29));
        createAccount.setForeground(Color.WHITE);
        createAccount.setFocusPainted(false);
        createAccount.setBorderPainted(false);
        createAccount.setOpaque(true);

        login = new JLabel("Already have an account? Log in");
        login.setBounds(52, 515, 398, 25);
        login.setHorizontalAlignment(SwingConstants.CENTER);
        login.setForeground(new Color(181, 101, 29));

        // LABEL COLORS
        JLabel[] labels = {
                fullName, email, contact, password, confirmPassword
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setForeground(new Color(120, 116, 109));
        }

        // TEXT FIELD COLORS AND BORDERS
        JTextField[] fields = {
                nameField, emailField, contactField,
                passwordField, confirmField
        };

        for (JTextField field : fields) {
            field.setBackground(new Color(250, 249, 247));
            field.setBorder(BorderFactory.createLineBorder(
                    new Color(222, 218, 211)
            ));
            field.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        // ADD COMPONENTS TO RIGHT PANEL
        rightPanel.add(title);
        rightPanel.add(fullName);
        rightPanel.add(nameField);
        rightPanel.add(email);
        rightPanel.add(emailField);
        rightPanel.add(contact);
        rightPanel.add(contactField);
        rightPanel.add(password);
        rightPanel.add(passwordField);
        rightPanel.add(confirmPassword);
        rightPanel.add(confirmField);
        rightPanel.add(createAccount);
        rightPanel.add(login);

        // ADD PANELS
        add(leftPanel);
        add(rightPanel);

        setVisible(true);
        // LOGO AND TAGLINE WILL BE ADDED bukassss
        // LeftPanel.add(Logo);
        // LeftPanel.add(Tagline);
    }


}
