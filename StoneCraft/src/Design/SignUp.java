
package Design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUp extends MainDesign {

<<<<<<< Updated upstream
public class SignUp extends MainDesign {

=======
>>>>>>> Stashed changes
    public JPanel panel1;
    public JButton productButton;
    public JButton messageButton;
    public JPanel productCard;
    public JPanel messageCard;
    public JPanel contentPane;

<<<<<<< Updated upstream
    JPanel LeftPanel, RightPanel;

    JLabel Title, FullName, Email, Contact, Password, ConfirmPassword, Login;

    JLabel Quote1, Quote2;
    JLabel Description1, Description2;
    JLabel Copyright;
=======
    public JPanel LeftPanel, RightPanel;
    public JPanel PasswordPanel, ConfirmPanel;

    public JLabel Title, FullName, Email, Contact;
    public JLabel Password, ConfirmPassword, Login;

    public JLabel Quote1, Quote2;
    public JLabel Description1, Description2;
    public JLabel Copyright;

    public JLabel Logo;
    public JLabel Tagline;
>>>>>>> Stashed changes

    JTextField NameField, EmailField, ContactField;
    JPasswordField PasswordField, ConfirmField;

<<<<<<< Updated upstream
    JButton CreateAccount;

    // JLabel Logo;
    // JLabel Tagline;
=======
    public JButton createAccount;
    public JButton showPassword, showConfirm;
>>>>>>> Stashed changes

    public SignUp() {

        // MAIN WINDOW
        setTitle("StoneCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        // CENTER THE SIGNUP PAGE
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(238, 237, 234));

        // FIXED SIZE SIGNUP CONTAINER
        contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(920, 560));
        contentPane.setBackground(Color.WHITE);


        // =========================
        // LEFT PANEL
<<<<<<< Updated upstream
        LeftPanel = new JPanel();
        LeftPanel.setLayout(null);
        LeftPanel.setBounds(0, 0, 418, 560);
        LeftPanel.setBackground(new Color(43, 42, 40));

        // LOGO AND TAGLINE
        // Logo = new JLabel("StoneCraft");
        // Tagline = new JLabel("Business Management System");

=======
        // =========================

        LeftPanel = new JPanel();
        LeftPanel.setLayout(null);
        LeftPanel.setBounds(0, 0, 418, 560);
        LeftPanel.setBackground(new Color(43, 42, 40));

        // LOGO ICON
        JPanel logoIcon = new JPanel(new BorderLayout());
        logoIcon.setBounds(45, 48, 48, 48);
        logoIcon.setBackground(new Color(181, 101, 29));

        JLabel logoLetter = new JLabel("S");
        logoLetter.setFont(new Font("Arial", Font.BOLD, 27));
        logoLetter.setForeground(Color.WHITE);
        logoLetter.setHorizontalAlignment(SwingConstants.CENTER);

        logoIcon.add(logoLetter, BorderLayout.CENTER);

        // STONECRAFT
        Logo = new JLabel("StoneCraft");
        Logo.setFont(new Font("Arial", Font.BOLD, 27));
        Logo.setForeground(new Color(244, 241, 236));
        Logo.setBounds(107, 48, 260, 35);

        // TAGLINE
        Tagline = new JLabel("Business Management System");
        Tagline.setFont(new Font("Arial", Font.PLAIN, 14));
        Tagline.setForeground(new Color(185, 179, 168));
        Tagline.setBounds(108, 80, 260, 25);

>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
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
=======

        // =========================
        // RIGHT PANEL
        // =========================

        RightPanel = new JPanel();
        RightPanel.setLayout(null);
        RightPanel.setBounds(418, 0, 502, 560);
        RightPanel.setBackground(Color.WHITE);

        // TITLE
        Title = new JLabel("Create an Account");
        Title.setFont(new Font("Arial", Font.BOLD, 25));
        Title.setForeground(new Color(31, 30, 28));
        Title.setBounds(52, 25, 398, 40);

        // FULL NAME
        FullName = new JLabel("Full Name");
        FullName.setBounds(52, 85, 398, 20);

        nameField = new JTextField();
        nameField.setBounds(52, 110, 398, 38);
        addPlaceholder(nameField, "Juan Dela Cruz");

        // EMAIL
        Email = new JLabel("Email Address");
        Email.setBounds(52, 160, 398, 20);

        emailField = new JTextField();
        emailField.setBounds(52, 185, 398, 38);
        addPlaceholder(emailField, "name@email.com");

        // CONTACT
        Contact = new JLabel("Contact Number");
        Contact.setBounds(52, 235, 398, 20);

        contactField = new JTextField();
        contactField.setBounds(52, 260, 398, 38);
        addPlaceholder(contactField, "09XX XXX XXXX");


        // =========================
        // PASSWORD
        // =========================

        Password = new JLabel("Password");
        Password.setBounds(52, 310, 398, 20);

        PasswordPanel = new JPanel(null);
        PasswordPanel.setBounds(52, 335, 330, 38);
        PasswordPanel.setBackground(new Color(250, 249, 247));
        PasswordPanel.setBorder(BorderFactory.createLineBorder(
                new Color(222, 218, 211)
        ));

        passwordField = new JPasswordField();
        passwordField.setBounds(10, 2, 310, 32);
        passwordField.setBorder(null);
        passwordField.setBackground(new Color(250, 249, 247));

        showPassword = new JButton("Show");
        showPassword.setBounds(390, 335, 60, 38);
        showPassword.setForeground(new Color(181, 101, 29));
        showPassword.setBackground(Color.WHITE);
        showPassword.setBorderPainted(false);
        showPassword.setFocusPainted(false);
        showPassword.setOpaque(true);

        showPassword.addActionListener(e -> {
            if (showPassword.getText().equals("Show")) {
                passwordField.setEchoChar((char) 0);
                showPassword.setText("Hide");
            } else {
                passwordField.setEchoChar('•');
                showPassword.setText("Show");
            }
        });

        PasswordPanel.add(passwordField);


        // =========================
        // CONFIRM PASSWORD
        // =========================

        ConfirmPassword = new JLabel("Confirm Password");
        ConfirmPassword.setBounds(52, 385, 398, 20);

        ConfirmPanel = new JPanel(null);
        ConfirmPanel.setBounds(52, 410, 330, 38);
        ConfirmPanel.setBackground(new Color(250, 249, 247));
        ConfirmPanel.setBorder(BorderFactory.createLineBorder(
                new Color(222, 218, 211)
        ));

        confirmField = new JPasswordField();
        confirmField.setBounds(10, 2, 310, 32);
        confirmField.setBorder(null);
        confirmField.setBackground(new Color(250, 249, 247));

        showConfirm = new JButton("Show");
        showConfirm.setBounds(390, 410, 60, 38);
        showConfirm.setForeground(new Color(181, 101, 29));
        showConfirm.setBackground(Color.WHITE);
        showConfirm.setBorderPainted(false);
        showConfirm.setFocusPainted(false);
        showConfirm.setOpaque(true);

        showConfirm.addActionListener(e -> {
            if (showConfirm.getText().equals("Show")) {
                confirmField.setEchoChar((char) 0);
                showConfirm.setText("Hide");
            } else {
                confirmField.setEchoChar('•');
                showConfirm.setText("Show");
            }
        });

        ConfirmPanel.add(confirmField);


        // =========================
        // CREATE ACCOUNT
        // =========================
>>>>>>> Stashed changes

        ConfirmField = new JPasswordField();
        ConfirmField.setBounds(52, 410, 398, 38);

<<<<<<< Updated upstream
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
=======
>>>>>>> Stashed changes

        // =========================
        // LOGIN
        // =========================

        Login = new JLabel(
                "<html><font color='#78746D'>Already have an account? </font>"
                        + "<font color='#B5651D'>Log in</font></html>"
        );

        Login.setBounds(52, 515, 398, 25);
        Login.setHorizontalAlignment(SwingConstants.CENTER);


        // =========================
        // LABEL COLORS
        // =========================

        JLabel[] labels = {
                FullName, Email, Contact, Password, ConfirmPassword
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setForeground(new Color(120, 116, 109));
        }


        // =========================
        // TEXT FIELD COLORS
        // =========================

        JTextField[] fields = {
<<<<<<< Updated upstream
                NameField, EmailField, ContactField,
                PasswordField, ConfirmField
=======
                nameField, emailField, contactField
>>>>>>> Stashed changes
        };

        for (JTextField field : fields) {
            field.setBackground(new Color(250, 249, 247));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(222, 218, 211)),
                    BorderFactory.createEmptyBorder(0, 10, 0, 10)
            ));
            field.setFont(new Font("Arial", Font.PLAIN, 14));
        }

<<<<<<< Updated upstream
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
=======

        // =========================
        // ADD COMPONENTS TO PANELS
        // =========================

        LeftPanel.add(logoIcon);
        LeftPanel.add(Logo);
        LeftPanel.add(Tagline);
        LeftPanel.add(Quote1);
        LeftPanel.add(Quote2);
        LeftPanel.add(Description1);
        LeftPanel.add(Description2);
        LeftPanel.add(Copyright);

        RightPanel.add(Title);

        RightPanel.add(FullName);
        RightPanel.add(nameField);

        RightPanel.add(Email);
        RightPanel.add(emailField);

        RightPanel.add(Contact);
        RightPanel.add(contactField);

        RightPanel.add(Password);
        RightPanel.add(PasswordPanel);
        RightPanel.add(showPassword);

        RightPanel.add(ConfirmPassword);
        RightPanel.add(ConfirmPanel);
        RightPanel.add(showConfirm);

        RightPanel.add(createAccount);
        RightPanel.add(Login);


        // =========================
        // ADD TO SIGNUP CONTAINER
        // =========================

        contentPane.add(LeftPanel);
        contentPane.add(RightPanel);

        // CENTER THE FIXED SIGNUP CONTAINER
        getContentPane().add(contentPane);

        setVisible(true);
    }


    // =========================
    // PLACEHOLDER METHOD
    // =========================

    public void addPlaceholder(JTextField field, String placeholder) {

        field.setText(placeholder);
        field.setForeground(new Color(120, 116, 109));

        field.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(new Color(31, 30, 28));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(120, 116, 109));
                }
            }
        });
    }
>>>>>>> Stashed changes
}
