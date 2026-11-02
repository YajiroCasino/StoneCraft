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


    JPanel LeftPanel, RightPanel;

    JLabel Title, FullName, Email, Contact, Password, ConfirmPassword, Login;

    JTextField NameField, EmailField, ContactField;
    JPasswordField PasswordField, ConfirmField;

    JButton CreateAccount;

    // JLabel Logo;
    // JLabel Tagline;

    public SignUp() {

        setLayout(null);
        setPreferredSize(new Dimension(920, 560));
        setBackground(Color.WHITE);

        // LEFT PANEL
        LeftPanel = new JPanel();
        LeftPanel.setLayout(null);
        LeftPanel.setBounds(0, 0, 418, 560);
        LeftPanel.setBackground(new Color(43, 42, 40));

        // Logo = new JLabel("StoneCraft");
        // Tagline = new JLabel("Business Management System");

        // RIGHT PANEL
        RightPanel = new JPanel();
        RightPanel.setLayout(null);
        RightPanel.setBounds(418, 0, 502, 560);
        RightPanel.setBackground(Color.WHITE);

        Title = new JLabel("Create an Account");
        Title.setFont(new Font("Arial", Font.BOLD, 25));
        Title.setForeground(new Color(31, 30, 28));
        Title.setBounds(52, 25, 398, 40);

        FullName = new JLabel("Full Name");
        FullName.setBounds(52, 85, 398, 20);

        NameField = new JTextField();
        NameField.setBounds(52, 110, 398, 38);

        Email = new JLabel("Email Address");
        Email.setBounds(52, 160, 398, 20);

        EmailField = new JTextField();
        EmailField.setBounds(52, 185, 398, 38);

        Contact = new JLabel("Contact Number");
        Contact.setBounds(52, 235, 398, 20);

        ContactField = new JTextField();
        ContactField.setBounds(52, 260, 398, 38);

        Password = new JLabel("Password");
        Password.setBounds(52, 310, 398, 20);

        PasswordField = new JPasswordField();
        PasswordField.setBounds(52, 335, 398, 38);

        ConfirmPassword = new JLabel("Confirm Password");
        ConfirmPassword.setBounds(52, 385, 398, 20);

        ConfirmField = new JPasswordField();
        ConfirmField.setBounds(52, 410, 398, 38);

        CreateAccount = new JButton("Create Account");
        CreateAccount.setBounds(52, 465, 398, 42);
        CreateAccount.setBackground(new Color(181, 101, 29));
        CreateAccount.setForeground(Color.WHITE);
        CreateAccount.setFocusPainted(false);
        CreateAccount.setBorderPainted(false);
        CreateAccount.setOpaque(true);

        Login = new JLabel("Already have an account? Log in");
        Login.setBounds(52, 515, 398, 25);
        Login.setHorizontalAlignment(SwingConstants.CENTER);
        Login.setForeground(new Color(181, 101, 29));

        // LABEL COLORS
        JLabel[] labels = {
                FullName, Email, Contact, Password, ConfirmPassword
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setForeground(new Color(120, 116, 109));
        }

        // TEXT FIELD COLORS AND BORDERS
        JTextField[] fields = {
                NameField, EmailField, ContactField,
                PasswordField, ConfirmField
        };

        for (JTextField field : fields) {
            field.setBackground(new Color(250, 249, 247));
            field.setBorder(BorderFactory.createLineBorder(
                    new Color(222, 218, 211)
            ));
            field.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        // ADD COMPONENTS TO RIGHT PANEL
        RightPanel.add(Title);
        RightPanel.add(FullName);
        RightPanel.add(NameField);
        RightPanel.add(Email);
        RightPanel.add(EmailField);
        RightPanel.add(Contact);
        RightPanel.add(ContactField);
        RightPanel.add(Password);
        RightPanel.add(PasswordField);
        RightPanel.add(ConfirmPassword);
        RightPanel.add(ConfirmField);
        RightPanel.add(CreateAccount);
        RightPanel.add(Login);

        // ADD PANELS
        add(LeftPanel);
        add(RightPanel);

        setVisible(true);
        // LOGO AND TAGLINE WILL BE ADDED bukassss
        // LeftPanel.add(Logo);
        // LeftPanel.add(Tagline);
    }
}
