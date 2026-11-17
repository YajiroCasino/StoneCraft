package Design;

import javax.swing.*;
import java.awt.*;

public class Login extends MainDesign {

    JPanel loginPanel;
    JPanel brandPanel, formPanel;
    JPanel contentPane;

    JLabel logoLabel, brandName;
    JLabel sloganLabel, descriptionLabel, copyrightLabel;

    JLabel welcomeLabel, subtitleLabel;
    JLabel usernameLabel, emailLabel, passwordLabel;
    JLabel forgotPasswordLabel;
    JLabel noAccountLabel, signUpLabel;

    public JLabel usernameWarning = new JLabel();
    public JLabel emailWarning = new JLabel();
    public JLabel passwordWarning = new JLabel();

    JTextField usernameField, emailField;
    JPasswordField passwordField;

    JPanel passwordPanel;
    JPanel optionsPanel;
    JPanel signUpPanel;

    JButton showPasswordButton;
    JButton loginButton;

    JCheckBox rememberMeCheckBox;

    public void addPlaceholder(JTextField field, String placeholder) {

        field.setText(placeholder);
        field.setForeground(new Color(120, 116, 109));

        field.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(new Color(31, 30, 28));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(120, 116, 109));
                }
            }
        });
    }

    public Login() {

        // =========================
        // MAIN WINDOW
        // =========================

        setTitle("StoneCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(238, 237, 234));

        // =========================
        // MAIN CONTAINER
        // =========================

        contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(920, 560));
        contentPane.setBackground(Color.WHITE);

        // =========================
        // LEFT PANEL
        // =========================

        brandPanel = new JPanel();
        brandPanel.setLayout(null);
        brandPanel.setBounds(0, 0, 418, 560);
        brandPanel.setBackground(new Color(43, 42, 40));

        // LOGO ICON
        JPanel logoIcon = new JPanel(new BorderLayout());
        logoIcon.setBounds(45, 48, 48, 48);
        logoIcon.setBackground(new Color(181, 101, 29));

        logoLabel = new JLabel("S");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 27));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        logoIcon.add(logoLabel, BorderLayout.CENTER);

        // BRAND NAME
        brandName = new JLabel("StoneCraft");
        brandName.setFont(new Font("Arial", Font.BOLD, 27));
        brandName.setForeground(new Color(244, 241, 236));
        brandName.setBounds(107, 48, 260, 35);

        // SLOGAN
        sloganLabel = new JLabel("<html>Crafted with care,<br>made to last.</html>");
        sloganLabel.setFont(new Font("Arial", Font.BOLD, 30));
        sloganLabel.setForeground(new Color(244, 241, 236));
        sloganLabel.setBounds(45, 290, 350, 80);

        // DESCRIPTION
        descriptionLabel = new JLabel(
                "<html>Quality stone products, carefully crafted<br>" +
                        "to bring lasting beauty and strength.</html>"
        );
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionLabel.setForeground(new Color(185, 179, 168));
        descriptionLabel.setBounds(45, 390, 350, 50);

        // COPYRIGHT
        copyrightLabel = new JLabel("© 2026 StoneCraft");
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        copyrightLabel.setForeground(new Color(138, 133, 123));
        copyrightLabel.setBounds(45, 510, 300, 25);

        // =========================
        // RIGHT PANEL
        // =========================

        formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(418, 0, 502, 560);
        formPanel.setBackground(Color.WHITE);

        // WELCOME TITLE
        welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        welcomeLabel.setForeground(new Color(31, 30, 28));
        welcomeLabel.setBounds(52, 65, 398, 40);

        subtitleLabel = new JLabel("Mag Login sa iyong account");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitleLabel.setForeground(new Color(120, 116, 109));
        subtitleLabel.setBounds(52, 105, 398, 25);

        // USERNAME
        usernameLabel = new JLabel("Name / Username");
        usernameLabel.setBounds(52, 165, 398, 20);

        usernameField = new JTextField();
        usernameField.setBounds(52, 190, 398, 38);
        addPlaceholder(usernameField, "Juan Dela Cruz");

        // EMAIL
        emailLabel = new JLabel("Email Address");
        emailLabel.setBounds(52, 240, 398, 20);

        emailField = new JTextField();
        emailField.setBounds(52, 265, 398, 38);
        addPlaceholder(emailField, "name@email.com");

        // PASSWORD
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(52, 315, 398, 20);

        passwordPanel = new JPanel(null);
        passwordPanel.setBounds(52, 340, 330, 38);
        passwordPanel.setBackground(new Color(250, 249, 247));
        passwordPanel.setBorder(BorderFactory.createLineBorder(
                new Color(222, 218, 211)
        ));

        passwordField = new JPasswordField();
        passwordField.setBounds(10, 2, 310, 32);
        passwordField.setBorder(null);
        passwordField.setBackground(new Color(250, 249, 247));

        showPasswordButton = new JButton("Show");
        showPasswordButton.setBounds(382, 340, 68, 38);
        showPasswordButton.setMargin(new Insets(0, 0, 0, 0));
        showPasswordButton.setFont(new Font("Arial", Font.PLAIN, 12));
        showPasswordButton.setForeground(new Color(181, 101, 29));
        showPasswordButton.setBackground(Color.WHITE);
        showPasswordButton.setBorderPainted(false);
        showPasswordButton.setFocusPainted(false);
        showPasswordButton.setOpaque(true);

        char defaultChar = passwordField.getEchoChar();

        showPasswordButton.addActionListener(e -> {
            if (showPasswordButton.getText().equals("Show")) {
                passwordField.setEchoChar((char) 0);
                showPasswordButton.setText("Hide");
            } else {
                passwordField.setEchoChar(defaultChar);
                showPasswordButton.setText("Show");
            }
        });

        passwordPanel.add(passwordField);

        // =========================
        // REMEMBER ME AND FORGOT PASSWORD
        // =========================

        rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setFont(new Font("Arial", Font.PLAIN, 13));
        rememberMeCheckBox.setForeground(new Color(60, 60, 60));
        rememberMeCheckBox.setBackground(Color.WHITE);
        rememberMeCheckBox.setFocusPainted(false);

        forgotPasswordLabel = new JLabel("Forgot password?");
        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        forgotPasswordLabel.setForeground(new Color(120, 116, 109));

        optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setBounds(52, 395, 398, 30);
        optionsPanel.setBackground(Color.WHITE);

        optionsPanel.add(rememberMeCheckBox, BorderLayout.WEST);
        optionsPanel.add(forgotPasswordLabel, BorderLayout.EAST);

        // =========================
        // LOGIN BUTTON
        // =========================

        loginButton = new JButton("Login");
        loginButton.setBounds(52, 445, 398, 42);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setBackground(new Color(181, 101, 29));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);



        // =========================
        // SIGN UP
        // =========================

        noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        noAccountLabel.setForeground(new Color(120, 116, 109));

        signUpLabel = new JLabel("Sign up");
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 14));
        signUpLabel.setForeground(new Color(181, 101, 29));

        signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        signUpPanel.setBounds(52, 505, 398, 30);
        signUpPanel.setBackground(Color.WHITE);

        signUpPanel.add(noAccountLabel);
        signUpPanel.add(signUpLabel);

        // =========================
        // LABEL STYLING
        // =========================

        JLabel[] labels = {
                usernameLabel, emailLabel, passwordLabel
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setForeground(new Color(120, 116, 109));
        }

        // =========================
        // TEXT FIELD STYLING
        // =========================

        JTextField[] fields = {
                usernameField, emailField
        };

        for (JTextField field : fields) {
            field.setBackground(new Color(250, 249, 247));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                            new Color(222, 218, 211)
                    ),
                    BorderFactory.createEmptyBorder(0, 10, 0, 10)
            ));
            field.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        // =========================
        // ADD COMPONENTS TO LEFT PANEL
        // =========================

        brandPanel.add(logoIcon);
        brandPanel.add(brandName);
        brandPanel.add(sloganLabel);
        brandPanel.add(descriptionLabel);
        brandPanel.add(copyrightLabel);

        // =========================
        // ADD COMPONENTS TO RIGHT PANEL
        // =========================

        formPanel.add(welcomeLabel);
        formPanel.add(subtitleLabel);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordPanel);
        formPanel.add(showPasswordButton);

        formPanel.add(optionsPanel);
        formPanel.add(loginButton);
        formPanel.add(signUpPanel);

        // =========================
        // ADD PANELS TO CONTAINER
        // =========================

        contentPane.add(brandPanel);
        contentPane.add(formPanel);

        // CENTER THE CONTAINER
        getContentPane().add(contentPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
