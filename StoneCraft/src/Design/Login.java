package Design;

import javax.swing.*;
import java.awt.*;

public class Login extends MainDesign {

    //main container
    JPanel LoginPanel = new JPanel(new BorderLayout());

    // left and right sections
    JPanel brandPanel = new JPanel();
    JPanel formPanel = new JPanel();

    //branding components
    JLabel logoLabel = new JLabel("S");
    JLabel BrandName = new JLabel("StoneCraft");
    JLabel sloganLabel = new JLabel(
            "<html>Crafted with care,<br>made to last.</html>");

    JLabel descriptionLabel = new JLabel(
            "<html>Mag-sign in para makita ang mga produkto,<br>" +
                    "orders, at messages mo.</html>");
    JLabel copyrightLabel = new JLabel("© 2026 StoneCraft");

    JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));

    JLabel welcomeLabel = new JLabel("Welcome Back!");
    JLabel subtitleLabel = new JLabel("Mag Login sa iyong account");

    JLabel usernameLabel = new JLabel("Name / Username");
    JTextField usernameField = new JTextField(20);

    JLabel emailLabel = new JLabel("Email or Phone Number");
    JTextField emailField = new JTextField(20);

    JLabel passwordLabel = new JLabel("Password");
    JPasswordField  passwordField = new JPasswordField(20);
    JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
    JButton showPasswordButton = new JButton("Show");

    JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
    JLabel forgotPasswordLabel = new JLabel("Forgot password?");
    JPanel optionsPanel = new JPanel(new BorderLayout());

    JButton loginButton = new JButton("Login");

    public Login(){

        // add both sections to the main container
        LoginPanel.add(brandPanel, BorderLayout.WEST);
        LoginPanel.add(formPanel, BorderLayout.CENTER);

        //bg to
        brandPanel.setBackground(new Color(36, 35, 33));
        //panel
        brandPanel.setPreferredSize(new Dimension(430, 0));
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));

        //logo
        logoLabel.setOpaque(true);
        logoLabel.setBackground(new Color(190, 100, 35));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setPreferredSize(new Dimension(40, 40));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // sa name
        BrandName.setForeground(Color.WHITE);
        BrandName.setFont(new Font("Arial", Font.BOLD, 23));

        //dun sa slogan part
        sloganLabel.setForeground(Color.WHITE);
        sloganLabel.setFont(new Font("Arial", Font.BOLD, 27));

        //description
        descriptionLabel.setForeground(Color.LIGHT_GRAY);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        // copyright
        copyrightLabel.setForeground(Color.GRAY);
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        //combination logo and name
        logoRow.setBackground(new Color(36, 35, 33));
        logoRow.add(logoLabel);
        logoRow.add(BrandName);

        // Add components to branding panel
        brandPanel.setBorder(BorderFactory.createEmptyBorder(45, 35, 30, 25));

        brandPanel.add(logoRow);
        brandPanel.add(Box.createVerticalStrut(150));

        brandPanel.add(sloganLabel);
        brandPanel.add(Box.createVerticalStrut(15));

        brandPanel.add(descriptionLabel);

        brandPanel.add(Box.createVerticalGlue());

        brandPanel.add(copyrightLabel);

        logoRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        sloganLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        copyrightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(new Color(40, 40, 40));

        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 30 ));
        subtitleLabel.setForeground(Color.GRAY);

        formPanel.setBackground(new Color(245, 243, 239));

        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(100, 70, 50, 70));

        formPanel.add(welcomeLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(subtitleLabel);

        // username part
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(50, 50 ,50));

        usernameField.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameField.setMaximumSize(new Dimension(450, 45));
        usernameField.setPreferredSize(new Dimension(450, 45));

        formPanel.add(Box.createVerticalStrut(35));
        formPanel.add(usernameLabel);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(usernameField);

        // email part
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(new Color(50, 50 ,50));

        emailField.setFont(new Font("Arial", Font.PLAIN, 15));
        emailField.setMaximumSize(new Dimension(450, 45));
        emailField.setPreferredSize(new Dimension(450, 45));

        formPanel.add(Box.createVerticalStrut(35));
        formPanel.add(emailLabel);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(emailField);

        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(50, 50, 50));

        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setPreferredSize(new Dimension(360, 45));

        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showPasswordButton, BorderLayout.EAST);

        passwordPanel.setMaximumSize(new Dimension(450, 45));
        passwordPanel.setPreferredSize(new Dimension(450, 45));

        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Align all form components to the left
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(35));
        formPanel.add(passwordLabel);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(passwordPanel);

        // Add password components
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showPasswordButton, BorderLayout.EAST);

        passwordPanel.setMaximumSize(new Dimension(450, 45));
        passwordPanel.setPreferredSize(new Dimension(450, 45));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add password panel to form
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(passwordPanel);

        // Show / Hide Password
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

        rememberMeCheckBox.setFont(new Font("Arial", Font.PLAIN, 13));
        rememberMeCheckBox.setForeground(new Color(60, 60, 60));
        rememberMeCheckBox.setBackground(new Color(245, 243, 239));
        rememberMeCheckBox.setFocusPainted(false);

        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        forgotPasswordLabel.setForeground(new Color(120, 120, 120));

        optionsPanel.setBackground(new Color(245, 243, 239));
        optionsPanel.setPreferredSize(new Dimension(450, 30));
        optionsPanel.setMaximumSize(new Dimension(450, 30));
        optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(15));

        optionsPanel.add(rememberMeCheckBox, BorderLayout.WEST);
        optionsPanel.add(forgotPasswordLabel, BorderLayout.EAST);

        formPanel.add(optionsPanel);

        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(190, 100, 35));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(450, 50));
        loginButton.setMaximumSize(new Dimension(450, 50));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(loginButton);

        // Add main container
        add(LoginPanel);
        setVisible(true);
    }
}