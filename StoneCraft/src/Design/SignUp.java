
package Design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUp extends MainDesign {

    // =========================
    // PUBLIC COMPONENTS
    // =========================

    public JPanel panel1;
    public JButton productButton;
    public JButton messageButton;
    public JPanel productCard;
    public JPanel messageCard;
    public JPanel contentPane;

    public JPanel passwordPanel, confirmPanel;

    public JLabel logo;
    public JLabel tagline;

    public JTextField nameField, emailField, contactField;
    public JPasswordField passwordField, confirmField;

    public JButton createAccount;
    public JButton showPassword, showConfirm;

    // WARNING LABELS
    public JLabel nameWarning = new JLabel();
    public JLabel emailWarning = new JLabel();
    public JLabel contactWarning = new JLabel();
    public JLabel passwordWarning = new JLabel();
    public JLabel confirmWarning = new JLabel();

    // =========================
    // PRIVATE COMPONENTS
    // =========================

    private JPanel leftPanel, rightPanel;

    private JLabel title, fullName, email, contact;
    private JLabel password, confirmPassword, login;

    private JLabel quote1, quote2;
    private JLabel description1, description2;
    private JLabel copyright;

    // =========================
    // COLORS
    // =========================

    private final Color darkColor = new Color(43, 42, 40);
    private final Color copperColor = new Color(181, 101, 29);
    private final Color hoverColor = new Color(151, 83, 15);
    private final Color mainTextColor = new Color(31, 30, 28);
    private final Color labelColor = new Color(120, 116, 109);
    private final Color fieldColor = new Color(250, 249, 247);
    private final Color borderColor = new Color(222, 218, 211);
    private final Color warningColor = new Color(220, 53, 69);

    // =========================
    // CONSTRUCTOR
    // =========================

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
        // =========================

        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, 0, 418, 560);
        leftPanel.setBackground(darkColor);

        // LOGO ICON
        JPanel logoIcon = new JPanel(new BorderLayout());
        logoIcon.setBounds(45, 48, 48, 48);
        logoIcon.setBackground(copperColor);

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

        rightPanel = new JPanel(null);
        rightPanel.setBounds(418, 0, 502, 560);
        rightPanel.setBackground(Color.WHITE);

        // TITLE
        title = new JLabel("Create an Account");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(mainTextColor);
        title.setBounds(52, 15, 398, 40);

        // =========================
        // FULL NAME
        // =========================

        fullName = new JLabel("Full Name");
        fullName.setBounds(52, 65, 398, 20);

        nameField = new JTextField();
        nameField.setBounds(52, 88, 398, 35);
        addPlaceholder(nameField, "Juan Dela Cruz");

        // NAME WARNING DESIGN
        nameWarning.setBounds(52, 124, 398, 13);

        // =========================
        // EMAIL
        // =========================

        email = new JLabel("Email Address");
        email.setBounds(52, 137, 398, 20);

        emailField = new JTextField();
        emailField.setBounds(52, 160, 398, 35);
        addPlaceholder(emailField, "name@email.com");

        // EMAIL WARNING DESIGN
        emailWarning.setBounds(52, 196, 398, 13);

        // =========================
        // CONTACT
        // =========================

        contact = new JLabel("Contact Number");
        contact.setBounds(52, 209, 398, 20);

        contactField = new JTextField();
        contactField.setBounds(52, 232, 398, 35);
        addPlaceholder(contactField, "09XX XXX XXXX");

        // CONTACT WARNING DESIGN
        contactWarning.setBounds(52, 268, 398, 13);

        // =========================
        // PASSWORD
        // =========================

        password = new JLabel("Password");
        password.setBounds(52, 281, 398, 20);

        passwordPanel = new JPanel(null);
        passwordPanel.setBounds(52, 304, 330, 35);
        passwordPanel.setBackground(fieldColor);
        passwordPanel.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        passwordField = new JPasswordField();
        passwordField.setBounds(10, 2, 310, 30);
        passwordField.setBorder(null);
        passwordField.setBackground(fieldColor);

        passwordPanel.add(passwordField);

        // SHOW PASSWORD BUTTON
        showPassword = createShowButton(382, 304);

        showPassword.addActionListener(e -> {

            if (showPassword.getText().equals("Show")) {
                passwordField.setEchoChar((char) 0);
                showPassword.setText("Hide");

            } else {
                passwordField.setEchoChar('•');
                showPassword.setText("Show");
            }
        });

        // PASSWORD WARNING DESIGN
        passwordWarning.setBounds(52, 340, 398, 13);

        // =========================
        // CONFIRM PASSWORD
        // =========================

        confirmPassword = new JLabel("Confirm Password");
        confirmPassword.setBounds(52, 353, 398, 20);

        confirmPanel = new JPanel(null);
        confirmPanel.setBounds(52, 376, 330, 35);
        confirmPanel.setBackground(fieldColor);
        confirmPanel.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        confirmField = new JPasswordField();
        confirmField.setBounds(10, 2, 310, 30);
        confirmField.setBorder(null);
        confirmField.setBackground(fieldColor);

        confirmPanel.add(confirmField);

        // SHOW CONFIRM PASSWORD BUTTON
        showConfirm = createShowButton(382, 376);

        showConfirm.addActionListener(e -> {

            if (showConfirm.getText().equals("Show")) {
                confirmField.setEchoChar((char) 0);
                showConfirm.setText("Hide");

            } else {
                confirmField.setEchoChar('•');
                showConfirm.setText("Show");
            }
        });

        // CONFIRM PASSWORD WARNING DESIGN
        confirmWarning.setBounds(52, 412, 398, 13);

        // =========================
        // CREATE ACCOUNT
        // =========================

        createAccount = new JButton("Create Account");
        createAccount.setBounds(52, 440, 398, 42);
        createAccount.setBackground(copperColor);
        createAccount.setForeground(Color.WHITE);
        createAccount.setFont(new Font("Arial", Font.BOLD, 14));
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

        login.setBounds(52, 490, 398, 25);
        login.setHorizontalAlignment(SwingConstants.CENTER);

        // =========================
        // LABEL COLORS AND FONTS
        // =========================

        JLabel[] labels = {
                fullName,
                email,
                contact,
                password,
                confirmPassword
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setForeground(labelColor);
        }

        // =========================
        // WARNING LABEL DESIGN
        // =========================

        JLabel[] warningLabels = {
                nameWarning,
                emailWarning,
                contactWarning,
                passwordWarning,
                confirmWarning
        };

        for (JLabel warning : warningLabels) {
            warning.setFont(new Font("Arial", Font.PLAIN, 11));
            warning.setForeground(warningColor);
        }

        // =========================
        // TEXT FIELD STYLE
        // =========================

        JTextField[] fields = {
                nameField,
                emailField,
                contactField
        };

        for (JTextField field : fields) {

            field.setBackground(fieldColor);

            field.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(borderColor),
                            BorderFactory.createEmptyBorder(0, 10, 0, 10)
                    )
            );

            field.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        // =========================
        // ADD COMPONENTS TO LEFT PANEL
        // =========================

        leftPanel.add(logoIcon);
        leftPanel.add(logo);
        leftPanel.add(tagline);
        leftPanel.add(quote1);
        leftPanel.add(quote2);
        leftPanel.add(description1);
        leftPanel.add(description2);
        leftPanel.add(copyright);

        // =========================
        // ADD COMPONENTS TO RIGHT PANEL
        // =========================

        rightPanel.add(title);

        rightPanel.add(fullName);
        rightPanel.add(nameField);
        rightPanel.add(nameWarning);

        rightPanel.add(email);
        rightPanel.add(emailField);
        rightPanel.add(emailWarning);

        rightPanel.add(contact);
        rightPanel.add(contactField);
        rightPanel.add(contactWarning);

        rightPanel.add(password);
        rightPanel.add(passwordPanel);
        rightPanel.add(showPassword);
        rightPanel.add(passwordWarning);

        rightPanel.add(confirmPassword);
        rightPanel.add(confirmPanel);
        rightPanel.add(showConfirm);
        rightPanel.add(confirmWarning);

        rightPanel.add(createAccount);
        rightPanel.add(login);

        // =========================
        // ADD PANELS TO CONTAINER
        // =========================

        contentPane.add(leftPanel);
        contentPane.add(rightPanel);

        // CENTER SIGNUP CONTAINER
        getContentPane().add(contentPane);

        setVisible(true);
    }

    // =========================
    // SHOW / HIDE BUTTON DESIGN
    // =========================

    private JButton createShowButton(int x, int y) {

        JButton button = new JButton("Show");

        button.setBounds(x, y, 68, 35);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setForeground(copperColor);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);

        return button;
    }

    // =========================
    // PLACEHOLDER METHOD
    // =========================

    public void addPlaceholder(JTextField field, String placeholder) {

        field.setText(placeholder);
        field.setForeground(labelColor);

        field.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(mainTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(labelColor);
                }
            }
        });
    }
}
