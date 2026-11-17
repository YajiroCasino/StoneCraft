
package Design;

import DataBaseManagementConnection.DataAccessObject;
import Main.Product;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomerMain extends MainDesign {

    // COLORS
    private final Color darkColor = new Color(43, 42, 40);
    private final Color copperColor = new Color(181, 101, 29);
    private final Color hoverColor = new Color(151, 83, 15);
    private final Color mainTextColor = new Color(31, 30, 28);
    private final Color labelColor = new Color(120, 116, 109);
    private final Color fieldColor = new Color(250, 249, 247);
    private final Color borderColor = new Color(222, 218, 211);
    private final Color backgroundColor = new Color(247, 245, 241);

    private final Font mainFont = new Font("Arial", Font.PLAIN, 13);
    private final Font boldFont = new Font("Arial", Font.BOLD, 13);

    // MAIN PANELS
    private JPanel dashboardPanel;
    private JPanel headerPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JPanel productGrid;

    // HEADER COMPONENTS
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private JButton cartButton;
    private JButton profileButton;

    // PRODUCT AND CART DATA
    private ArrayList<Product> products;
    private ArrayList<CartItem> cartItems;

    // CUSTOM REQUEST AND MESSAGE DATA
    private ArrayList<CustomRequest> customRequests;
    private ArrayList<ChatMessage> chatMessages;

    private String selectedCategory = "All Products";
    private String selectedImagePath = "";

    // CART FRAME
    private JFrame cartFrame;
    private JPanel cartContentPanel;
    private JLabel subtotalLabel;

    // MESSAGE FRAME
    private JFrame messageFrame;
    private JPanel chatPanel;
    private JTextField messageField;

    public CustomerMain() {

        setTitle("StoneCraft - Customer Main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // MAXIMIZED WINDOW
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        products = new ArrayList<>();
        cartItems = new ArrayList<>();
        customRequests = new ArrayList<>();
        chatMessages = new ArrayList<>();

        loadProducts();

        createHeader();
        createSidebar();
        createProductContent();

        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(Color.WHITE);

        bodyPanel.add(sidebarPanel, BorderLayout.WEST);
        bodyPanel.add(contentPanel, BorderLayout.CENTER);

        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        dashboardPanel.add(bodyPanel, BorderLayout.CENTER);

        setContentPane(dashboardPanel);
        setVisible(true);
    }

    // =================================
    // SAMPLE PRODUCTS
    // =================================

    private void loadProducts(){
        products.clear();
        products.addAll(DataAccessObject.getAllProduct());
    }

    // =================================
    // HEADER
    // =================================

    private void createHeader() {

        headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setBackground(darkColor);

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        );

        // LOGO
        JPanel logoPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        logoPanel.setOpaque(false);

        JLabel logoIcon = new JLabel("S");
        logoIcon.setFont(new Font("Arial", Font.BOLD, 24));
        logoIcon.setForeground(Color.WHITE);
        logoIcon.setHorizontalAlignment(SwingConstants.CENTER);
        logoIcon.setOpaque(true);
        logoIcon.setBackground(copperColor);
        logoIcon.setPreferredSize(new Dimension(40, 40));

        JPanel logoTextPanel = new JPanel();
        logoTextPanel.setLayout(new BoxLayout(
                logoTextPanel, BoxLayout.Y_AXIS
        ));
        logoTextPanel.setOpaque(false);

        JLabel logoName = new JLabel("StoneCraft");
        logoName.setFont(new Font("Arial", Font.BOLD, 21));
        logoName.setForeground(Color.WHITE);

        JLabel logoTagline = new JLabel("Business Management System");
        logoTagline.setFont(new Font("Arial", Font.PLAIN, 11));
        logoTagline.setForeground(new Color(185, 179, 168));

        logoTextPanel.add(logoName);
        logoTextPanel.add(logoTagline);

        logoPanel.add(logoIcon);
        logoPanel.add(logoTextPanel);

        // SEARCH BAR
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(320, 36));

        searchField = new JTextField();
        searchField.setFont(mainFont);
        searchField.setBackground(fieldColor);

        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        searchField.setToolTipText("Search products");

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, copperColor, Color.WHITE);

        searchButton.addActionListener(e -> searchProducts());
        searchField.addActionListener(e -> searchProducts());

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel searchHolder = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 0, 7)
        );

        searchHolder.setOpaque(false);
        searchHolder.add(searchPanel);

        // CART AND PROFILE
        JPanel accountPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 5)
        );

        accountPanel.setOpaque(false);

        cartButton = new JButton("Cart (0)");
        styleButton(cartButton, copperColor, Color.WHITE);
        cartButton.addActionListener(e -> openCartFrame());

        profileButton = new JButton("Profile");
        styleButton(profileButton, Color.WHITE, darkColor);
        profileButton.setBorder(
                BorderFactory.createLineBorder(Color.WHITE)
        );

        profileButton.addActionListener(e -> showProfile());

        accountPanel.add(cartButton);
        accountPanel.add(profileButton);

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(searchHolder, BorderLayout.CENTER);
        headerPanel.add(accountPanel, BorderLayout.EAST);
    }

    // =================================
    // SIDEBAR
    // =================================

    private void createSidebar() {

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(
                sidebarPanel, BoxLayout.Y_AXIS
        ));

        sidebarPanel.setPreferredSize(new Dimension(210, 0));
        sidebarPanel.setBackground(backgroundColor);

        sidebarPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 15, 20, 15)
        );

        JLabel menuTitle = new JLabel("MENU");
        menuTitle.setFont(boldFont);
        menuTitle.setForeground(labelColor);

        JLabel categoryTitle = new JLabel("CATEGORIES");
        categoryTitle.setFont(boldFont);
        categoryTitle.setForeground(labelColor);

        categoryTitle.setBorder(
                BorderFactory.createEmptyBorder(20, 0, 10, 0)
        );

        sidebarPanel.add(menuTitle);
        sidebarPanel.add(Box.createVerticalStrut(15));

        addMenuButton("Dashboard", "All Products");
        addMenuButton("All Products", "All Products");

        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(categoryTitle);

        addMenuButton("Home Decor", "Home Decor");
        addMenuButton("Garden", "Garden");
        addMenuButton("Kitchen", "Kitchen");
        addMenuButton("Sculptures", "Sculptures");

        // CUSTOM SCULPTURE SECTION
        JLabel customTitle = new JLabel("CUSTOMER SERVICES");
        customTitle.setFont(boldFont);
        customTitle.setForeground(labelColor);
        customTitle.setBorder(
                BorderFactory.createEmptyBorder(20, 0, 10, 0)
        );

        sidebarPanel.add(customTitle);

        addActionMenuButton(
                "Request Custom Sculpture",
                e -> openCustomRequestForm()
        );

        addActionMenuButton(
                "My Custom Requests",
                e -> openCustomRequestsFrame()
        );

        addActionMenuButton(
                "Messages",
                e -> openMessageFrame()
        );

        sidebarPanel.add(Box.createVerticalGlue());

        JLabel helpLabel = new JLabel("Need help?");
        helpLabel.setFont(boldFont);
        helpLabel.setForeground(mainTextColor);

        JLabel helpDescription = new JLabel("Contact StoneCraft");
        helpDescription.setFont(mainFont);
        helpDescription.setForeground(labelColor);

        sidebarPanel.add(helpLabel);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(helpDescription);
    }

    private void addMenuButton(String buttonText, String category) {

        JButton menuButton = new JButton(buttonText);

        menuButton.setFont(mainFont);
        menuButton.setForeground(mainTextColor);
        menuButton.setBackground(backgroundColor);
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);

        menuButton.setMaximumSize(new Dimension(180, 38));
        menuButton.setBorder(
                BorderFactory.createEmptyBorder(8, 10, 8, 5)
        );

        menuButton.setFocusPainted(false);
        menuButton.setOpaque(true);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        menuButton.addActionListener(e -> {
            selectedCategory = category;
            displayProducts(selectedCategory, searchField.getText());
        });

        sidebarPanel.add(menuButton);
    }

    private void addActionMenuButton(
            String buttonText,
            java.awt.event.ActionListener action
    ) {

        JButton menuButton = new JButton(buttonText);

        menuButton.setFont(mainFont);
        menuButton.setForeground(mainTextColor);
        menuButton.setBackground(backgroundColor);
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);

        menuButton.setMaximumSize(new Dimension(180, 42));
        menuButton.setBorder(
                BorderFactory.createEmptyBorder(8, 10, 8, 5)
        );

        menuButton.setFocusPainted(false);
        menuButton.setOpaque(true);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        menuButton.addActionListener(action);

        sidebarPanel.add(menuButton);
    }

    // =================================
    // PRODUCT CONTENT
    // =================================

    private void createProductContent() {

        contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(Color.WHITE);

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(
                titlePanel, BoxLayout.Y_AXIS
        ));

        titlePanel.setBackground(Color.WHITE);

        JLabel pageTitle = new JLabel("Discover Products");
        pageTitle.setFont(new Font("Arial", Font.BOLD, 25));
        pageTitle.setForeground(mainTextColor);

        JLabel pageDescription = new JLabel(
                "Find beautifully crafted stone products."
        );

        pageDescription.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );
        pageDescription.setForeground(labelColor);

        titlePanel.add(pageTitle);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(pageDescription);

        // FILTER SECTION
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(Color.WHITE);

        JLabel productCount = new JLabel("Available Products");
        productCount.setFont(boldFont);
        productCount.setForeground(mainTextColor);

        sortComboBox = new JComboBox<>(new String[]{
                "Featured",
                "Price: Low to High",
                "Price: High to Low"
        });

        sortComboBox.setFont(mainFont);
        sortComboBox.setBackground(Color.WHITE);

        sortComboBox.addActionListener(e ->
                displayProducts(selectedCategory, searchField.getText())
        );

        filterPanel.add(productCount, BorderLayout.WEST);
        filterPanel.add(sortComboBox, BorderLayout.EAST);

        // PRODUCT GRID
        productGrid = new JPanel(
                new GridLayout(0, 3, 15, 15)
        );
        productGrid.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(productGrid);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel topContent = new JPanel(new BorderLayout(10, 15));
        topContent.setBackground(Color.WHITE);

        topContent.add(titlePanel, BorderLayout.NORTH);
        topContent.add(filterPanel, BorderLayout.SOUTH);

        contentPanel.add(topContent, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        displayProducts("All Products", "");
    }

    // =================================
    // DISPLAY PRODUCTS
    // =================================

    private void displayProducts(String category, String keyword) {

        productGrid.removeAll();

        ArrayList<Product> filteredProducts = new ArrayList<>();

        for (Product product : products) {

            boolean categoryMatch =
                    category.equals("All Products")
                            || product.getProductCategory().equals(category);

            boolean searchMatch =
                    product.getProductName().toLowerCase().contains(
                            keyword.toLowerCase().trim()
                    );

            if (categoryMatch && searchMatch) {
                filteredProducts.add(product);
            }
        }

        // PRICE SORTING
        if (sortComboBox != null) {

            String selectedSort =
                    (String) sortComboBox.getSelectedItem();

            if ("Price: Low to High".equals(selectedSort)) {

                Collections.sort(
                        filteredProducts,
                        Comparator.comparingDouble(p -> p.getProductPrice())
                );

            } else if ("Price: High to Low".equals(selectedSort)) {

                Collections.sort(
                        filteredProducts,
                        (p1, p2) -> Double.compare(p2.getProductPrice(), p1.getProductPrice())
                );
            }
        }

        if (filteredProducts.isEmpty()) {

            JLabel noProducts = new JLabel("No products found.");
            noProducts.setFont(mainFont);
            noProducts.setForeground(labelColor);

            productGrid.add(noProducts);

        } else {

            for (Product product : filteredProducts) {
                productGrid.add(createProductCard(product));
            }
        }

        productGrid.revalidate();
        productGrid.repaint();
    }

    // =================================
    // PRODUCT CARD
    // =================================

    private JPanel createProductCard(Product product) {

        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        );

        // PRODUCT IMAGE PLACEHOLDER
        // Add your actual product image here.
        // Example:
        // ImageIcon image = new ImageIcon("images/stone-vase.jpg");
        // JLabel imageLabel = new JLabel(image);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(200, 150));
        imagePanel.setBackground(fieldColor);

        JLabel imageLabel = new JLabel("PRODUCT PHOTO");
        imageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        imageLabel.setForeground(labelColor);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // PRODUCT DETAILS
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(
                informationPanel, BoxLayout.Y_AXIS
        ));

        informationPanel.setBackground(Color.WHITE);

        JLabel productName = new JLabel(product.getProductName());
        productName.setFont(new Font("Arial", Font.BOLD, 15));
        productName.setForeground(mainTextColor);

        JLabel productCategory = new JLabel(product.getProductCategory());
        productCategory.setFont(mainFont);
        productCategory.setForeground(labelColor);

        JLabel productPrice = new JLabel(
                "₱" + String.format("%.2f", product.getProductPrice())
        );

        productPrice.setFont(new Font("Arial", Font.BOLD, 16));
        productPrice.setForeground(copperColor);

        informationPanel.add(productName);
        informationPanel.add(Box.createVerticalStrut(4));
        informationPanel.add(productCategory);
        informationPanel.add(Box.createVerticalStrut(7));
        informationPanel.add(productPrice);

        // ADD TO CART
        JButton addButton = new JButton("Add to Cart");
        styleButton(addButton, copperColor, Color.WHITE);

        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addButton.addActionListener(e -> addToCart(product));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton, BorderLayout.CENTER);

        card.add(imagePanel, BorderLayout.NORTH);
        card.add(informationPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    // =================================
    // SEARCH
    // =================================

    private void searchProducts() {

        displayProducts(
                selectedCategory,
                searchField.getText()
        );
    }

    // =================================
    // ADD TO CART
    // =================================

    private void addToCart(Product product) {

        boolean alreadyAdded = false;

        for (CartItem item : cartItems) {

            if (item.product == product) {
                item.quantity++;
                alreadyAdded = true;
                break;
            }
        }

        if (!alreadyAdded) {
            cartItems.add(new CartItem(product, 1));
        }

        updateCartButton();

        JOptionPane.showMessageDialog(
                this,
                product.getProductName() + " added to cart!",
                "StoneCraft",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateCartButton() {

        int totalQuantity = 0;

        for (CartItem item : cartItems) {
            totalQuantity += item.quantity;
        }

        cartButton.setText("Cart (" + totalQuantity + ")");
    }

    // =================================
    // CART FRAME
    // =================================

    private void openCartFrame() {

        if (cartFrame != null && cartFrame.isDisplayable()) {
            cartFrame.dispose();
        }

        cartFrame = new JFrame("StoneCraft - My Cart");
        cartFrame.setSize(650, 550);
        cartFrame.setLocationRelativeTo(this);
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cartFrame.setLayout(new BorderLayout());
        cartFrame.getContentPane().setBackground(Color.WHITE);

        JPanel cartHeader = new JPanel(new BorderLayout());
        cartHeader.setBackground(darkColor);

        cartHeader.setBorder(
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        );

        JLabel cartTitle = new JLabel("My Cart");
        cartTitle.setFont(new Font("Arial", Font.BOLD, 21));
        cartTitle.setForeground(Color.WHITE);

        cartHeader.add(cartTitle, BorderLayout.WEST);

        cartContentPanel = new JPanel();
        cartContentPanel.setLayout(new BoxLayout(
                cartContentPanel, BoxLayout.Y_AXIS
        ));
        cartContentPanel.setBackground(Color.WHITE);

        cartContentPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JScrollPane cartScrollPane = new JScrollPane(cartContentPanel);
        cartScrollPane.setBorder(null);
        cartScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 12));
        bottomPanel.setBackground(backgroundColor);

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        subtotalLabel = new JLabel("Subtotal: ₱0.00");
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 17));
        subtotalLabel.setForeground(mainTextColor);

        JButton checkoutButton = new JButton("Proceed to Checkout");
        styleButton(checkoutButton, copperColor, Color.WHITE);

        checkoutButton.addActionListener(e -> {

            if (cartItems.isEmpty()) {

                JOptionPane.showMessageDialog(
                        cartFrame,
                        "Your cart is empty.",
                        "StoneCraft",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        cartFrame,
                        "Checkout feature will be connected soon.",
                        "StoneCraft",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        bottomPanel.add(subtotalLabel, BorderLayout.NORTH);
        bottomPanel.add(checkoutButton, BorderLayout.SOUTH);

        cartFrame.add(cartHeader, BorderLayout.NORTH);
        cartFrame.add(cartScrollPane, BorderLayout.CENTER);
        cartFrame.add(bottomPanel, BorderLayout.SOUTH);

        refreshCart();

        cartFrame.setVisible(true);
    }

    // =================================
    // REFRESH CART
    // =================================

    private void refreshCart() {

        if (cartContentPanel == null) {
            return;
        }

        cartContentPanel.removeAll();

        if (cartItems.isEmpty()) {

            JLabel emptyLabel = new JLabel("Your cart is currently empty.");
            emptyLabel.setFont(mainFont);
            emptyLabel.setForeground(labelColor);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            cartContentPanel.add(Box.createVerticalStrut(30));
            cartContentPanel.add(emptyLabel);

        } else {

            for (CartItem item : cartItems) {

                cartContentPanel.add(createCartItemPanel(item));
                cartContentPanel.add(Box.createVerticalStrut(10));
            }
        }

        updateSubtotal();

        cartContentPanel.revalidate();
        cartContentPanel.repaint();
    }

    // =================================
    // CART ITEM PANEL
    // =================================

    private JPanel createCartItemPanel(CartItem item) {

        JPanel itemPanel = new JPanel(new BorderLayout(10, 5));
        itemPanel.setBackground(Color.WHITE);

        itemPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(12, 12, 12, 12)
                )
        );

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(
                detailsPanel, BoxLayout.Y_AXIS
        ));
        detailsPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(item.product.getProductName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(mainTextColor);

        JLabel priceLabel = new JLabel(
                "₱" + String.format("%.2f", item.product.getProductPrice())
        );

        priceLabel.setFont(mainFont);
        priceLabel.setForeground(labelColor);

        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(priceLabel);

        JPanel quantityPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 5, 5)
        );
        quantityPanel.setBackground(Color.WHITE);

        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");
        JButton removeButton = new JButton("Remove");

        styleSmallButton(minusButton);
        styleSmallButton(plusButton);

        removeButton.setFont(new Font("Arial", Font.PLAIN, 11));
        removeButton.setForeground(copperColor);
        removeButton.setBackground(Color.WHITE);
        removeButton.setBorderPainted(false);
        removeButton.setFocusPainted(false);

        JLabel quantityLabel = new JLabel(
                String.valueOf(item.quantity)
        );

        quantityLabel.setFont(boldFont);
        quantityLabel.setForeground(mainTextColor);

        minusButton.addActionListener(e -> {

            if (item.quantity > 1) {
                item.quantity--;
            }

            refreshCart();
            updateCartButton();
        });

        plusButton.addActionListener(e -> {

            item.quantity++;

            refreshCart();
            updateCartButton();
        });

        removeButton.addActionListener(e -> {

            cartItems.remove(item);

            refreshCart();
            updateCartButton();
        });

        quantityPanel.add(minusButton);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(plusButton);
        quantityPanel.add(removeButton);

        itemPanel.add(detailsPanel, BorderLayout.CENTER);
        itemPanel.add(quantityPanel, BorderLayout.EAST);

        itemPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 90)
        );

        return itemPanel;
    }

    private void styleSmallButton(JButton button) {

        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(mainTextColor);
        button.setBackground(fieldColor);
        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        button.setPreferredSize(new Dimension(30, 28));
    }

    // =================================
    // UPDATE SUBTOTAL
    // =================================

    private void updateSubtotal() {

        double subtotal = 0;

        for (CartItem item : cartItems) {
            subtotal += item.product.getProductPrice() * item.quantity;
        }

        subtotalLabel.setText(
                "Subtotal: ₱" + String.format("%.2f", subtotal)
        );
    }

    // =================================
    // CUSTOM SCULPTURE REQUEST FORM
    // =================================
    public JFrame requestFrame;
    public JTextField sculptureNameField;
    public JTextArea descriptionArea;
    public JScrollPane descriptionScroll;
    public JComboBox<String> categoryComboBox;
    public JComboBox<String> sizeComboBox;
    public JSpinner quantitySpinner;
    public JTextField designField;
    public JButton cancelButton;
    public JButton submitButton;

    public void openCustomRequestForm() {

        if (requestFrame != null && requestFrame.isDisplayable()) {
            requestFrame.setState(JFrame.NORMAL);
            requestFrame.toFront();
            requestFrame.requestFocus();
            return;
        }

        requestFrame = new JFrame("StoneCraft - Custom Sculpture Request");
        requestFrame.setSize(650, 700);
        requestFrame.setLocationRelativeTo(this);
        requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // FORM HEADER
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Request Custom Sculpture");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
        titleLabel.setForeground(mainTextColor);

        JLabel descriptionLabel = new JLabel("Tell us about the stone sculpture you want.");
        descriptionLabel.setFont(mainFont);
        descriptionLabel.setForeground(labelColor);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(descriptionLabel);

        // FORM FIELDS
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        sculptureNameField = createFormField();

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(mainFont);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(fieldColor);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setBorder(BorderFactory.createLineBorder(borderColor));

        categoryComboBox = new JComboBox<>(new String[]{
                "Home Decor", "Garden", "Kitchen", "Sculptures"
        });
        styleComboBox(categoryComboBox);

        sizeComboBox = new JComboBox<>(new String[]{
                "Small", "Medium", "Large", "Extra Large", "Custom Size"
        });
        styleComboBox(sizeComboBox);

        // QUANTITY
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // default, min, max, step
        quantitySpinner.setFont(mainFont);
        quantitySpinner.setPreferredSize(new Dimension(100, 35));
        quantitySpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        designField = createFormField();

        addFormSection(formPanel, "Sculpture Name", sculptureNameField);
        addFormSection(formPanel, "Description / Details", descriptionScroll);
        addFormSection(formPanel, "Category", categoryComboBox);
        addFormSection(formPanel, "Preferred Size", sizeComboBox);
        addFormSection(formPanel, "Quantity", quantitySpinner);
        addFormSection(formPanel, "Preferred Design / Style", designField);

        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setBorder(null);
        formScrollPane.getVerticalScrollBar().setUnitIncrement(14);

        // BUTTONS
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        cancelButton = new JButton("Cancel");
        styleButton(cancelButton, fieldColor, mainTextColor);
        cancelButton.setBorder(BorderFactory.createLineBorder(borderColor));
        cancelButton.addActionListener(e -> requestFrame.dispose());

        submitButton = new JButton("Submit Request");
        styleButton(submitButton, copperColor, Color.WHITE);
        submitButton.addActionListener(e -> submitCustomRequest());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        requestFrame.add(mainPanel);
        requestFrame.setVisible(true);
    }

    private void submitCustomRequest() {
        String name = sculptureNameField.getText().trim();
        String description = descriptionArea.getText().trim();
        String category = (String) categoryComboBox.getSelectedItem();
        String size = (String) sizeComboBox.getSelectedItem();
        int quantity = ((Number) quantitySpinner.getValue()).intValue();
        String design = designField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(requestFrame,
                    "Please enter a sculpture name.", "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            sculptureNameField.requestFocus();
            return;
        }
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(requestFrame,
                    "Please describe the sculpture you want.", "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            descriptionArea.requestFocus();
            return;
        }


        JOptionPane.showMessageDialog(requestFrame,
                "Your request has been submitted!\n\n"
                        + "Name: " + name + "\n"
                        + "Category: " + category + "\n"
                        + "Size: " + size + "\n"
                        + "Quantity: " + quantity,
                "Request Submitted", JOptionPane.INFORMATION_MESSAGE);

        requestFrame.dispose();
    }

    // =================================
    // FORM DESIGN HELPERS
    // =================================

    private JTextField createFormField() {

        JTextField textField = new JTextField();
        textField.setFont(mainFont);
        textField.setBackground(fieldColor);

        textField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );

        textField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38)
        );

        return textField;
    }

    private void addFormSection(
            JPanel formPanel,
            String labelText,
            JComponent component
    ) {

        JLabel sectionLabel = new JLabel(labelText);
        sectionLabel.setFont(boldFont);
        sectionLabel.setForeground(mainTextColor);
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (component instanceof JTextField
                || component instanceof JComboBox) {

            component.setMaximumSize(
                    new Dimension(Integer.MAX_VALUE, 38)
            );
        }

        if (component instanceof JScrollPane) {
            component.setPreferredSize(new Dimension(500, 95));
        }

        formPanel.add(sectionLabel);
        formPanel.add(Box.createVerticalStrut(6));
        formPanel.add(component);
        formPanel.add(Box.createVerticalStrut(15));
    }

    private void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setFont(mainFont);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(mainTextColor);
    }

    // =================================
    // MY CUSTOM REQUESTS
    // =================================

    private void openCustomRequestsFrame() {

        JFrame requestListFrame = new JFrame(
                "StoneCraft - My Custom Requests"
        );

        requestListFrame.setSize(750, 550);
        requestListFrame.setLocationRelativeTo(this);
        requestListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requestListFrame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(darkColor);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        );

        JLabel titleLabel = new JLabel("My Custom Requests");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 21));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel requestListPanel = new JPanel();
        requestListPanel.setLayout(new BoxLayout(
                requestListPanel, BoxLayout.Y_AXIS
        ));
        requestListPanel.setBackground(Color.WHITE);
        requestListPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        if (customRequests.isEmpty()) {

            JLabel emptyLabel = new JLabel(
                    "You haven't submitted any custom requests yet."
            );

            emptyLabel.setFont(mainFont);
            emptyLabel.setForeground(labelColor);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            requestListPanel.add(Box.createVerticalStrut(40));
            requestListPanel.add(emptyLabel);

        } else {

            for (CustomRequest customRequest : customRequests) {

                requestListPanel.add(
                        createCustomRequestCard(customRequest)
                );

                requestListPanel.add(Box.createVerticalStrut(12));
            }
        }

        JScrollPane scrollPane = new JScrollPane(requestListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        requestListFrame.add(headerPanel, BorderLayout.NORTH);
        requestListFrame.add(scrollPane, BorderLayout.CENTER);

        requestListFrame.setVisible(true);
    }

    // =================================
    // CUSTOM REQUEST CARD
    // =================================

    private JPanel createCustomRequestCard(CustomRequest customRequest) {

        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                )
        );

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(
                detailsPanel, BoxLayout.Y_AXIS
        ));
        detailsPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(customRequest.sculptureName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(mainTextColor);

        JLabel sizeLabel = new JLabel(
                "Preferred Size: " + customRequest.size
        );
        sizeLabel.setFont(mainFont);
        sizeLabel.setForeground(labelColor);

        JLabel designLabel = new JLabel(
                "Design: " + customRequest.design
        );
        designLabel.setFont(mainFont);
        designLabel.setForeground(labelColor);

        JLabel budgetLabel = new JLabel(
                "Budget: ₱" + String.format("%.2f", customRequest.budget)
        );
        budgetLabel.setFont(mainFont);
        budgetLabel.setForeground(copperColor);

        JLabel statusLabel = new JLabel(
                "Status: " + customRequest.status
        );
        statusLabel.setFont(boldFont);
        statusLabel.setForeground(copperColor);

        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(7));
        detailsPanel.add(sizeLabel);
        detailsPanel.add(designLabel);
        detailsPanel.add(budgetLabel);
        detailsPanel.add(Box.createVerticalStrut(7));
        detailsPanel.add(statusLabel);

        JButton viewButton = new JButton("View Details");
        styleButton(viewButton, darkColor, Color.WHITE);

        viewButton.addActionListener(e ->
                showCustomRequestDetails(customRequest)
        );

        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(viewButton, BorderLayout.EAST);

        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 160)
        );

        return card;
    }

    // =================================
    // CUSTOM REQUEST DETAILS
    // =================================

    private void showCustomRequestDetails(CustomRequest customRequest) {

        String details =
                "Sculpture Name: " + customRequest.sculptureName
                        + "\n\nDescription: " + customRequest.description
                        + "\n\nPreferred Size: " + customRequest.size
                        + "\n\nDesign: " + customRequest.design
                        + "\n\nBudget: ₱" + String.format("%.2f", customRequest.budget)
                        + "\n\nStatus: " + customRequest.status
                        + "\n\nReference Image: "
                        + (customRequest.imagePath.isEmpty()
                        ? "No image attached"
                        : new File(customRequest.imagePath).getName());

        JOptionPane.showMessageDialog(
                this,
                details,
                "Custom Request Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =================================
    // MESSAGES / CHAT FRAME
    // =================================

    private void openMessageFrame() {

        if (messageFrame != null && messageFrame.isDisplayable()) {
            messageFrame.toFront();
            return;
        }

        messageFrame = new JFrame("StoneCraft - Messages");
        messageFrame.setSize(750, 600);
        messageFrame.setLocationRelativeTo(this);
        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        messageFrame.setLayout(new BorderLayout());

        // CHAT HEADER
        JPanel chatHeader = new JPanel(new BorderLayout());
        chatHeader.setBackground(darkColor);
        chatHeader.setBorder(
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        );

        JPanel sellerInfoPanel = new JPanel();
        sellerInfoPanel.setLayout(new BoxLayout(
                sellerInfoPanel, BoxLayout.Y_AXIS
        ));
        sellerInfoPanel.setOpaque(false);

        JLabel sellerName = new JLabel("StoneCraft Seller");
        sellerName.setFont(new Font("Arial", Font.BOLD, 18));
        sellerName.setForeground(Color.WHITE);

        JLabel sellerStatus = new JLabel("Customer Support");
        sellerStatus.setFont(mainFont);
        sellerStatus.setForeground(new Color(185, 179, 168));

        sellerInfoPanel.add(sellerName);
        sellerInfoPanel.add(Box.createVerticalStrut(4));
        sellerInfoPanel.add(sellerStatus);

        chatHeader.add(sellerInfoPanel, BorderLayout.WEST);

        // CHAT AREA
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(
                chatPanel, BoxLayout.Y_AXIS
        ));
        chatPanel.setBackground(backgroundColor);
        chatPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JScrollPane chatScrollPane = new JScrollPane(chatPanel);
        chatScrollPane.setBorder(null);
        chatScrollPane.getVerticalScrollBar().setUnitIncrement(15);

        // MESSAGE INPUT
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        );

        messageField = new JTextField();
        messageField.setFont(mainFont);
        messageField.setBackground(fieldColor);

        messageField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );

        JButton sendButton = new JButton("Send");
        styleButton(sendButton, copperColor, Color.WHITE);

        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        messageFrame.add(chatHeader, BorderLayout.NORTH);
        messageFrame.add(chatScrollPane, BorderLayout.CENTER);
        messageFrame.add(inputPanel, BorderLayout.SOUTH);

        refreshChatMessages();

        messageFrame.setVisible(true);
    }

    // =================================
    // SEND MESSAGE
    // =================================

    private void sendMessage() {

        if (messageField == null) {
            return;
        }

        String message = messageField.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        // SAVE CUSTOMER MESSAGE LOCALLY
        chatMessages.add(new ChatMessage(message, "Customer"));

        messageField.setText("");

        refreshChatMessages();
    }

    // =================================
    // REFRESH CHAT MESSAGES
    // =================================

    private void refreshChatMessages() {

        if (chatPanel == null) {
            return;
        }

        chatPanel.removeAll();

        if (chatMessages.isEmpty()) {

            JLabel welcomeLabel = new JLabel(
                    "Start a conversation with the StoneCraft seller."
            );

            welcomeLabel.setFont(mainFont);
            welcomeLabel.setForeground(labelColor);
            welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            chatPanel.add(Box.createVerticalStrut(30));
            chatPanel.add(welcomeLabel);

        } else {

            for (ChatMessage chatMessage : chatMessages) {

                chatPanel.add(createMessageBubble(chatMessage));
                chatPanel.add(Box.createVerticalStrut(10));
            }
        }

        chatPanel.revalidate();
        chatPanel.repaint();

        SwingUtilities.invokeLater(() -> {

            JScrollBar verticalBar =
                    ((JScrollPane) chatPanel.getParent().getParent())
                            .getVerticalScrollBar();

            verticalBar.setValue(verticalBar.getMaximum());
        });
    }

    // =================================
    // MESSAGE BUBBLE
    // =================================

    private JPanel createMessageBubble(ChatMessage chatMessage) {

        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setOpaque(false);

        JPanel bubblePanel = new JPanel();
        bubblePanel.setLayout(new BoxLayout(
                bubblePanel, BoxLayout.Y_AXIS
        ));

        bubblePanel.setBorder(
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        );

        JLabel senderLabel = new JLabel(chatMessage.sender);
        senderLabel.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel messageLabel = new JLabel(
                "<html><div style='width:250px;'>"
                        + escapeHtml(chatMessage.message)
                        + "</div></html>"
        );

        messageLabel.setFont(mainFont);

        if ("Customer".equals(chatMessage.sender)) {

            bubblePanel.setBackground(new Color(246, 228, 210));
            senderLabel.setForeground(copperColor);
            messageLabel.setForeground(mainTextColor);

            bubblePanel.add(senderLabel);
            bubblePanel.add(Box.createVerticalStrut(5));
            bubblePanel.add(messageLabel);

            rowPanel.add(bubblePanel, BorderLayout.EAST);

        } else {

            bubblePanel.setBackground(Color.WHITE);
            senderLabel.setForeground(darkColor);
            messageLabel.setForeground(mainTextColor);

            bubblePanel.add(senderLabel);
            bubblePanel.add(Box.createVerticalStrut(5));
            bubblePanel.add(messageLabel);

            rowPanel.add(bubblePanel, BorderLayout.WEST);
        }

        return rowPanel;
    }

    private String escapeHtml(String text) {

        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
    }

    // =================================
    // PROFILE MENU
    // =================================

    private void showProfile() {

        JPopupMenu profileMenu = new JPopupMenu();

        JMenuItem accountItem = new JMenuItem("My Account");
        JMenuItem ordersItem = new JMenuItem("My Orders");
        JMenuItem logoutItem = new JMenuItem("Log Out");

        accountItem.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "My Account",
                        "StoneCraft",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        ordersItem.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "My Orders",
                        "StoneCraft",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        logoutItem.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Log Out",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        profileMenu.add(accountItem);
        profileMenu.add(ordersItem);
        profileMenu.addSeparator();
        profileMenu.add(logoutItem);

        profileMenu.show(
                profileButton,
                0,
                profileButton.getHeight()
        );
    }

    // =================================
    // BUTTON DESIGN
    // =================================

    private void styleButton(
            JButton button,
            Color background,
            Color foreground
    ) {

        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(9, 15, 9, 15)
        );

        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

                if (background == copperColor) {
                    button.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                button.setBackground(background);
            }
        });
    }


    // =================================
    // CART ITEM CLASS
    // =================================

    private static class CartItem {

        Product product;
        int quantity;

        CartItem(Product product, int quantity) {

            this.product = product;
            this.quantity = quantity;
        }
    }

    // =================================
    // CUSTOM REQUEST CLASS
    // =================================

    private static class CustomRequest {

        String sculptureName;
        String description;
        String size;
        String design;
        double budget;
        String imagePath;
        String status;

        CustomRequest(
                String sculptureName,
                String description,
                String size,
                String design,
                double budget,
                String imagePath
        ) {

            this.sculptureName = sculptureName;
            this.description = description;
            this.size = size;
            this.design = design;
            this.budget = budget;
            this.imagePath = imagePath;
            this.status = "Pending";
        }
    }

    // =================================
    // CHAT MESSAGE CLASS
    // =================================

    private static class ChatMessage {

        String message;
        String sender;

        ChatMessage(String message, String sender) {

            this.message = message;
            this.sender = sender;
        }
    }
}
