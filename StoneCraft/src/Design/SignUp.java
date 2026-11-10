
package Design;

import javax.swing.*;
import java.awt.*;

public class SignUp extends MainDesign {

    public JPanel panel1;
    public JButton productButton;
    public JButton messageButton;
    public JPanel productCard;
    public JPanel messageCard;
    public JPanel contentPane;

    JPanel LeftPanel, RightPanel;

    JLabel Title, FullName, Email, Contact, Password, ConfirmPassword, Login;

    JLabel Quote1, Quote2;
    JLabel Description1, Description2;
    JLabel Copyright;

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

        // LOGO AND TAGLINE
        // Logo = new JLabel("StoneCraft");
        // Tagline = new JLabel("Business Management System");

        // QUOTE
        Quote1 = new JLabel("Naturally strong.");
        Quote1.setFont(new Font("Arial", Font.BOLD, 30));
        Quote1.setForeground(new Color(244, 241, 236));
        Quote1.setBounds(45, 290, 350, 40);

        Quote2 = new JLabel("Beautifully crafted.");
        Quote2.setFont(new Font("Arial", Font.BOLD, 30));
        Quote2.setForeground(new Color(244, 241, 236));
        Quote2.setBounds(45, 330, 350, 40);

        // DESCRIPTION
        Description1 = new JLabel("Quality stone products, carefully crafted");
        Description1.setFont(new Font("Arial", Font.PLAIN, 16));
        Description1.setForeground(new Color(185, 179, 168));
        Description1.setBounds(45, 390, 350, 25);

        Description2 = new JLabel("to bring lasting beauty and strength.");
        Description2.setFont(new Font("Arial", Font.PLAIN, 16));
        Description2.setForeground(new Color(185, 179, 168));
        Description2.setBounds(45, 415, 350, 25);

        // COPYRIGHT
        Copyright = new JLabel("© 2026 StoneCraft");
        Copyright.setFont(new Font("Arial", Font.PLAIN, 13));
        Copyright.setForeground(new Color(138, 133, 123));
        Copyright.setBounds(45, 510, 300, 25);

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

        // ADD COMPONENTS TO LEFT PANEL
        LeftPanel.add(Quote1);
        LeftPanel.add(Quote2);
        LeftPanel.add(Description1);
        LeftPanel.add(Description2);
        LeftPanel.add(Copyright);

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

    }
}
