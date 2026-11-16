package Design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUp extends MainDesign {

    public JPanel panel1;
    public JButton productButton;
    public JButton messageButton;
    public JPanel productCard;
    public JPanel messageCard;
    public JPanel contentPane;

    JPanel leftPanel, rightPanel;

    JLabel title, fullName, email, contact, password, confirmPassword, login;
    JLabel quote1, quote2;
    JLabel description1, description2;
    JLabel copyright;

    public JPanel passwordPanel, confirmPanel;

    public JLabel logo;
    public JLabel tagline;

    public JTextField nameField, emailField, contactField;
    public JPasswordField passwordField, confirmField;

    public JButton createAccount;
    public JButton showPassword, showConfirm;


    public SignUp() {

        // =========================
        // MAIN WINDOW
        // =========================
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
        // =========================
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 418, 560);
        leftPanel.setBackground(new Color(43, 42, 40));

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
        logo = new JLabel("StoneCraft");
        logo.setFont(new Font("Arial", Font.BOLD, 27));
        logo.setForeground(new Color(244, 241, 236));
        logo.setBounds(107, 48, 260, 35);

        // TAGLINE
        tagline = new JLabel("Business Management System");
        tagline.setFont(new Font("Arial", Font.PLAIN, 14));
        tagline.setForeground(new Color(185, 179, 168));
        tagline.setBounds(108, 80, 260, 25);

        // QUOTE
        quote1 = new JLabel("Naturally strong.");
        quote1.setFont(new Font("Arial", Font.BOLD, 30));
        quote1.setForeground(new Color(244, 241, 236));
        quote1.setBounds(45, 290, 350, 40);

        quote2 = new JLabel("Beautifully crafted.");
        quote2.setFont(new Font("Arial", Font.BOLD, 30));
        quote2.setForeground(new Color(244, 241, 236));
        quote2.setBounds(45, 330, 350, 40);

        // DESCRIPTION
        description1 = new JLabel("Quality stone products, carefully crafted");
        description1.setFont(new Font("Arial", Font.PLAIN, 16));
        description1.setForeground(new Color(185, 179, 168));
        description1.setBounds(45, 390, 350, 25);

        description2 = new JLabel("to bring lasting beauty and strength.");
        description2.setFont(new Font("Arial", Font.PLAIN, 16));
        description2.setForeground(new Color(185, 179, 168));
        description2.setBounds(45, 415, 350, 25);

        // COPYRIGHT
        copyright = new JLabel("© 2026 StoneCraft");
        copyright.setFont(new Font("Arial", Font.PLAIN, 13));
        copyright.setForeground(new Color(138, 133, 123));
        copyright.setBounds(45, 510, 300, 25);


        // =========================
        // RIGHT PANEL
        // =========================
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(418, 0, 502, 560);
        rightPanel.setBackground(Color.WHITE);

        // TITLE
        title = new JLabel("Create an Account");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(new Color(31, 30, 28));
        title.setBounds(52, 25, 398, 40);

        // FULL NAME
        fullName = new JLabel("Full Name");
        fullName.setBounds(52, 85, 398, 20);

        nameField = new JTextField();
        nameField.setBounds(52, 110, 398, 38);
        addPlaceholder(nameField, "Juan Dela Cruz");

        // EMAIL
        email = new JLabel("Email Address");
        email.setBounds(52, 160, 398, 20);

        emailField = new JTextField();
        emailField.setBounds(52, 185, 398, 38);
        addPlaceholder(emailField, "name@email.com");

        // CONTACT
        contact = new JLabel("Contact Number");
        contact.setBounds(52, 235, 398, 20);

        contactField = new JTextField();
        contactField.setBounds(52, 260, 398, 38);
        addPlaceholder(contactField, "09XX XXX XXXX");


        // =========================
        // PASSWORD
        // =========================
        password = new JLabel("Password");
        password.setBounds(52, 310, 398, 20);

        passwordPanel = new JPanel(null);
        passwordPanel.setBounds(52, 335, 330, 38);
        passwordPanel.setBackground(new Color(250, 249, 247));
        passwordPanel.setBorder(BorderFactory.createLineBorder(
                new Color(222, 218, 211)
        ));

        passwordField = new JPasswordField();
        passwordField.setBounds(10, 2, 310, 32);
        passwordField.setBorder(null);
        passwordField.setBackground(new Color(250, 249, 247));

        showPassword = new JButton("Show");
        showPassword.setBounds(382, 335, 68, 38);
        showPassword.setMargin(new Insets(0, 0, 0, 0));
        showPassword.setFont(new Font("Arial", Font.PLAIN, 12));
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

        passwordPanel.add(passwordField);


        // =========================
        // CONFIRM PASSWORD
        // =========================
        confirmPassword = new JLabel("Confirm Password");
        confirmPassword.setBounds(52, 385, 398, 20);

        confirmPanel = new JPanel(null);
        confirmPanel.setBounds(52, 410, 330, 38);
        confirmPanel.setBackground(new Color(250, 249, 247));
        confirmPanel.setBorder(BorderFactory.createLineBorder(
                new Color(222, 218, 211)
        ));

        confirmField = new JPasswordField();
        confirmField.setBounds(10, 2, 310, 32);
        confirmField.setBorder(null);
        confirmField.setBackground(new Color(250, 249, 247));

        showConfirm = new JButton("Show");
        showConfirm.setBounds(382, 410, 68, 38);
        showConfirm.setMargin(new Insets(0, 0, 0, 0));
        showConfirm.setFont(new Font("Arial", Font.PLAIN, 12));
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

        confirmPanel.add(confirmField);


        // =========================
        // CREATE ACCOUNT
        // =========================
        createAccount = new JButton("Create Account");
        createAccount.setBounds(52, 465, 398, 42);
        createAccount.setBackground(new Color(181, 101, 29));
        createAccount.setForeground(Color.WHITE);
        createAccount.setFocusPainted(false);
        createAccount.setBorderPainted(false);
        createAccount.setOpaque(true);


        // =========================
        // LOGIN
        // =========================
        login = new JLabel(
                "<html><font color='#78746D'>Already have an account? </font>"
                        + "<font color='#B5651D'>Log in</font></html>"
        );
        login.setBounds(52, 515, 398, 25);
        login.setHorizontalAlignment(SwingConstants.CENTER);


        // =========================
        // LABEL COLORS
        // =========================
        JLabel[] labels = {
                fullName, email, contact, password, confirmPassword
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setForeground(new Color(120, 116, 109));
        }


        // =========================
        // TEXT FIELD STYLE
        // =========================
        JTextField[] fields = {
                nameField, emailField, contactField
        };

        for (JTextField field : fields) {
            field.setBackground(new Color(250, 249, 247));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(222, 218, 211)),
                    BorderFactory.createEmptyBorder(0, 10, 0, 10)
            ));
            field.setFont(new Font("Arial", Font.PLAIN, 14));
        }


        // =========================
        // ADD COMPONENTS TO PANELS
        // =========================
        leftPanel.add(logoIcon);
        leftPanel.add(logo);
        leftPanel.add(tagline);
        leftPanel.add(quote1);
        leftPanel.add(quote2);
        leftPanel.add(description1);
        leftPanel.add(description2);
        leftPanel.add(copyright);

        rightPanel.add(title);

        rightPanel.add(fullName);
        rightPanel.add(nameField);

        rightPanel.add(email);
        rightPanel.add(emailField);

        rightPanel.add(contact);
        rightPanel.add(contactField);

        rightPanel.add(password);
        rightPanel.add(passwordPanel);
        rightPanel.add(showPassword);

        rightPanel.add(confirmPassword);
        rightPanel.add(confirmPanel);
        rightPanel.add(showConfirm);

        rightPanel.add(createAccount);
        rightPanel.add(login);


        // =========================
        // ADD TO SIGNUP CONTAINER
        // =========================
        contentPane.add(leftPanel);
        contentPane.add(rightPanel);

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

}