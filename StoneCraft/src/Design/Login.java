package Design;

import javax.swing.*;
import java.awt.*;

public class Login extends MainDesign{

    // Main container
    JPanel LoginPanel = new JPanel(new BorderLayout());

    // Left and right sections
    JPanel brandPanel = new JPanel();
    JPanel formPanel = new JPanel();

    // Branding components
    JLabel logoLabel = new JLabel("S");
    JLabel BrandName = new JLabel("StoneCraft");

    JLabel sloganLabel = new JLabel(
            "<html>Crafted with care,<br>made to last.</html>");

    JLabel descriptionLabel = new JLabel(
            "<html>Sign in to view your products,<br>" +
                    "orders, and messages.</html>");

    JLabel copyrightLabel = new JLabel("© 2026 StoneCraft");

    JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));

    // Login heading
    JLabel welcomeLabel = new JLabel("Welcome Back!");
    JLabel subtitleLabel = new JLabel("Login to your account");

    // Username field
    JLabel usernameLabel = new JLabel("Name / Username");
    JTextField usernameField = new JTextField(20);

    // Email field
    JLabel emailLabel = new JLabel("Email or Phone Number");
    JTextField emailField = new JTextField(20);

    // Password field
    JLabel passwordLabel = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField(20);

    JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
    JButton showPasswordButton = new JButton("Show");

    // Login options
    JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
    JLabel forgotPasswordLabel = new JLabel("Forgot password?");

    JPanel optionsPanel = new JPanel(new BorderLayout());

    // Login button
    JButton loginButton = new JButton("Login");

    // Sign up section
    JLabel noAccountLabel = new JLabel("Doesn't have an account yet?");
    JLabel signUpLabel = new JLabel("Sign up");

    JPanel signUpPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 5, 0));

    public Login() {

        // Add both sections to the main container
        LoginPanel.add(brandPanel, BorderLayout.WEST);
        LoginPanel.add(formPanel, BorderLayout.CENTER);

        // Left panel background
        brandPanel.setBackground(new Color(25, 25, 25));
        brandPanel.setPreferredSize(new Dimension(430, 0));

        brandPanel.setLayout(
                new BoxLayout(brandPanel, BoxLayout.Y_AXIS));

        brandPanel.setBorder(
                BorderFactory.createEmptyBorder(45, 35, 30, 25));

        // Logo design
        logoLabel.setOpaque(true);
        logoLabel.setBackground(new Color(190, 85, 20));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setPreferredSize(new Dimension(40, 40));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Brand name
        BrandName.setForeground(Color.WHITE);
        BrandName.setFont(new Font("Arial", Font.BOLD, 23));

        // Logo and brand name
        logoRow.setBackground(new Color(25, 25, 25));
        logoRow.add(logoLabel);
        logoRow.add(BrandName);

        // Slogan
        sloganLabel.setForeground(Color.WHITE);
        sloganLabel.setFont(new Font("Arial", Font.BOLD, 27));

        // Description
        descriptionLabel.setForeground(Color.LIGHT_GRAY);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        // Copyright
        copyrightLabel.setForeground(Color.GRAY);
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add components to branding panel
        logoRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        sloganLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        copyrightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        brandPanel.add(logoRow);
        brandPanel.add(Box.createVerticalGlue());
        brandPanel.add(sloganLabel);
        brandPanel.add(Box.createVerticalStrut(15));
        brandPanel.add(descriptionLabel);
        brandPanel.add(Box.createVerticalGlue());
        brandPanel.add(copyrightLabel);

        // Right panel background
        formPanel.setBackground(new Color(250, 250, 248));

        formPanel.setLayout(
                new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(65, 60, 40, 60));

        // Welcome heading
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(new Color(35, 35, 35));

        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setForeground(Color.GRAY);

        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(welcomeLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(subtitleLabel);


        // Username section
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(new Color(130, 130, 130));

        usernameField.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameField.setPreferredSize(new Dimension(450, 45));
        usernameField.setMaximumSize(new Dimension(450, 45));

// Text field design
        usernameField.setBackground(Color.WHITE);
        usernameField.setBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)));

        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(usernameField);

        // Email section
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(new Color(130, 130, 130));

        emailField.setFont(new Font("Arial", Font.PLAIN, 15));
        emailField.setPreferredSize(new Dimension(450, 45));
        emailField.setMaximumSize(new Dimension(450, 45));

// Text field design
        emailField.setBackground(Color.WHITE);
        emailField.setBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)));

        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(22));
        formPanel.add(emailLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(emailField);

        // Password section
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(new Color(130, 130, 130));

        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

// Password panel design
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)));

// Show button design
        showPasswordButton.setBorderPainted(false);
        showPasswordButton.setFocusPainted(false);
        showPasswordButton.setContentAreaFilled(false);
        showPasswordButton.setForeground(new Color(190, 85, 20));
        showPasswordButton.setFont(new Font("Arial", Font.PLAIN, 13));

// Add password components
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showPasswordButton, BorderLayout.EAST);

        passwordPanel.setPreferredSize(new Dimension(450, 45));
        passwordPanel.setMaximumSize(new Dimension(450, 45));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(22));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(passwordPanel);

        // Show and hide password
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

        // Remember me checkbox
        rememberMeCheckBox.setFont(new Font("Arial", Font.PLAIN, 13));
        rememberMeCheckBox.setForeground(new Color(60, 60, 60));
        rememberMeCheckBox.setBackground(new Color(250, 250, 248));
        rememberMeCheckBox.setFocusPainted(false);

        // Forgot password
        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        forgotPasswordLabel.setForeground(new Color(120, 120, 120));

        // Options panel
        optionsPanel.setBackground(new Color(250, 250, 248));
        optionsPanel.setPreferredSize(new Dimension(450, 30));
        optionsPanel.setMaximumSize(new Dimension(450, 30));
        optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        optionsPanel.add(rememberMeCheckBox, BorderLayout.WEST);
        optionsPanel.add(forgotPasswordLabel, BorderLayout.EAST);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(optionsPanel);

        // Login button
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(190, 85, 20));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);

        loginButton.setPreferredSize(new Dimension(450, 50));
        loginButton.setMaximumSize(new Dimension(450, 50));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(loginButton);

        // Sign up section
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        noAccountLabel.setForeground(new Color(120, 120, 120));

        signUpLabel.setFont(new Font("Arial", Font.BOLD, 14));
        signUpLabel.setForeground(new Color(190, 85, 20));

        signUpPanel.setBackground(new Color(250, 250, 248));

        signUpPanel.setPreferredSize(new Dimension(450, 30));
        signUpPanel.setMaximumSize(new Dimension(450, 30));

        signUpPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        signUpPanel.add(noAccountLabel);
        signUpPanel.add(signUpLabel);

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(signUpPanel);

        // Add main container to the window
        add(LoginPanel);
        setVisible(true);
    }
}