package Design;

import DataBaseManagementConnection.DataAccessObject;
import Main.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomerMain extends MainDesign {

    private JPanel productsPage;
    private JPanel profilePage;
    private JPanel requestPage;
    private JPanel myRequestsPage;

    private JPanel productGrid;

    // =================================
    // HEADER COMPONENTS
    // =================================

    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private JButton cartButton;

    private final Map<String, JTextField> categorySearchFields =
            new LinkedHashMap<>();

    private JPanel searchHolder;

    // =================================
    // SIDEBAR COMPONENTS
    // =================================

    private JButton profileButton;
    private JButton logoutButton;

    private final Map<String, JButton> menuButtons = new LinkedHashMap<>();
    private final Map<String, JButton> actionMenuButtons = new LinkedHashMap<>();

    private String currentPage = "Products";
    private String currentMenu = "Dashboard";

    // =================================
    // PRODUCT AND CART DATA
    // =================================

    private ArrayList<Product> products;
    private ArrayList<Cart> cartItems;
    private ArrayList<CustomRequest> customRequests;

    private String selectedCategory = "All Products";

    // =================================
    // CART FRAME
    // =================================

    private JFrame cartFrame;
    private JPanel cartContentPanel;
    private JLabel subtotalLabel;

    // =================================
    // CUSTOM REQUEST COMPONENTS
    // =================================

    public JFrame requestFrame = new JFrame("Request");
    public JTextField sculptureNameField;
    public JTextArea descriptionArea;
    public JScrollPane descriptionScroll;
    public JComboBox<String> categoryComboBox;
    public JComboBox<String> sizeComboBox;
    public JSpinner quantitySpinner;
    public JTextField designField;
    public JButton cancelButton  = new JButton("Cancel");
    public JButton submitButton = new JButton("Submit Request");

    private JPanel requestFormPanel;
    private JPanel requestListPanel;

    // =================================
    // PROFILE COMPONENTS
    // =================================

    private JPanel profileDetailsPanel;

    public String profileName;
    public String profileAddress;
    public String profilePhone;
    public String profileEmail;

    public JButton editProfileButton;
    public JButton saveButton = new JButton("Save Changes");

    public JTextField editNameField;
    public JTextField editAddressField;
    public JTextField editPhoneField;
    public JTextField editEmailField;

    public JDialog dialog;

    // =================================
    // CONSTRUCTOR
    // =================================

    public CustomerMain() {

        setTitle("StoneCraft - Customer Main");

        products = new ArrayList<>();
        cartItems = new ArrayList<>();
        customRequests = new ArrayList<>();

        loadProducts();

        createHeader();
        createSidebar();

        createProductContent();
        createProfilePage();
        createCustomRequestPage();
        createMyCustomRequestsPage();

        cardLayout = new CardLayout();

        pagesPanel = new JPanel(cardLayout);
        pagesPanel.setBackground(backgroundColor);

        pagesPanel.add(productsPage, "Products");
        pagesPanel.add(profilePage, "Profile");
        pagesPanel.add(requestPage, "CustomRequest");
        pagesPanel.add(myRequestsPage, "MyCustomRequests");

        loadCart();

        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(backgroundColor);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(backgroundColor);

        bodyPanel.add(sidebarPanel, BorderLayout.WEST);
        bodyPanel.add(pagesPanel, BorderLayout.CENTER);

        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        dashboardPanel.add(bodyPanel, BorderLayout.CENTER);

        setContentPane(dashboardPanel);

        showPage("Products");

        setVisible(true);
    }

    // =================================
    // LOAD DATA
    // =================================

    private void loadProducts() {

        products.clear();
        products.addAll(DataAccessObject.getAllProduct());
    }

    private void loadCart() {

        cartItems.clear();
        cartItems.addAll(DataAccessObject.getAllCart());

        updateCartButton();
    }

    // =================================
    // HEADER
    // =================================

    private void createHeader() {

        headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setPreferredSize(new Dimension(0, 75));
        headerPanel.setBackground(darkColor);

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        );

        JPanel logoPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 4)
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

        JLabel logoTagline = new JLabel("Customer Management System");
        logoTagline.setFont(new Font("Arial", Font.PLAIN, 11));
        logoTagline.setForeground(new Color(185, 179, 168));

        logoTextPanel.add(logoName);
        logoTextPanel.add(logoTagline);

        logoPanel.add(logoIcon);
        logoPanel.add(logoTextPanel);

        JPanel accountPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 5)
        );

        accountPanel.setOpaque(false);

        cartButton = new JButton("Cart (0)");
        styleButton(cartButton, copperColor, Color.WHITE);
        cartButton.addActionListener(e -> openCartFrame());

        accountPanel.add(cartButton);

        headerPanel.add(logoPanel, BorderLayout.WEST);
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

        sidebarPanel.setPreferredSize(new Dimension(215, 0));
        sidebarPanel.setBackground(darkColor);

        sidebarPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 12, 20, 12)
        );

        addSidebarTitle("MENU");

        addMenuButton("Dashboard", "All Products");

        addSidebarTitle("CATEGORIES");

        addMenuButton("Home Decor", "Home Decor");
        addMenuButton("Garden", "Garden");
        addMenuButton("Kitchen", "Kitchen");
        addMenuButton("Sculptures", "Sculptures");

        addSidebarTitle("CUSTOMER SERVICES");

        addActionMenuButton(
                "Request Custom Sculpture",
                e -> openCustomRequestForm()
        );

        addActionMenuButton(
                "My Custom Requests",
                e -> openCustomRequestsFrame()
        );

        sidebarPanel.add(Box.createVerticalGlue());

        addSidebarTitle("ACCOUNT");

        profileButton = new JButton("Profile");
        styleSidebarButton(profileButton);
        profileButton.addActionListener(e -> showProfile());

        sidebarPanel.add(profileButton);

        sidebarPanel.add(Box.createVerticalStrut(8));

        logoutButton = new JButton("Log Out");
        styleSidebarLogoutButton(logoutButton);
        logoutButton.addActionListener(e -> logout());

        sidebarPanel.add(logoutButton);
    }

    private void addMenuButton(String buttonText, String category) {

        JButton menuButton = new JButton(buttonText);

        menuButton.setFont(mainFont);
        menuButton.setForeground(Color.WHITE);
        menuButton.setBackground(darkColor);
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);

        menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        menuButton.setPreferredSize(new Dimension(190, 42));

        menuButton.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 5)
        );

        menuButton.setFocusPainted(false);
        menuButton.setOpaque(true);
        menuButton.setBorderPainted(false);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuButton.addActionListener(e -> {

            selectedCategory = category;
            currentMenu = buttonText;

            updateProductSearchBar();
            showPage("Products");

            displayProducts(
                    selectedCategory,
                    searchField.getText()
            );

            updateSidebarSelection();
        });

        menuButtons.put(buttonText, menuButton);

        sidebarPanel.add(menuButton);
    }

    private void addActionMenuButton(
            String buttonText,
            java.awt.event.ActionListener action
    ) {

        JButton menuButton = new JButton(buttonText);

        menuButton.setFont(mainFont);
        menuButton.setForeground(Color.WHITE);
        menuButton.setBackground(darkColor);
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);

        menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        menuButton.setPreferredSize(new Dimension(190, 42));

        menuButton.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 5)
        );

        menuButton.setFocusPainted(false);
        menuButton.setOpaque(true);
        menuButton.setBorderPainted(false);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuButton.addActionListener(action);

        actionMenuButtons.put(buttonText, menuButton);

        sidebarPanel.add(menuButton);
    }

    private void styleSidebarButton(JButton button) {

        button.setFont(mainFont);
        button.setForeground(Color.WHITE);
        button.setBackground(darkColor);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(190, 42));

        button.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 5)
        );

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleSidebarLogoutButton(JButton button) {

        button.setFont(boldFont);
        button.setForeground(Color.WHITE);
        button.setBackground(deleteColor);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(190, 42));

        button.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 5)
        );

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(145, 49, 44));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(deleteColor);
            }
        });
    }

    private void updateSidebarSelection() {

        for (Map.Entry<String, JButton> entry : menuButtons.entrySet()) {

            boolean selected =
                    currentPage.equals("Products")
                            && entry.getKey().equals(currentMenu);

            JButton menuButton = entry.getValue();

            menuButton.setBackground(
                    selected ? copperColor : darkColor
            );

            menuButton.setForeground(Color.WHITE);
        }

        for (Map.Entry<String, JButton> entry : actionMenuButtons.entrySet()) {

            boolean selected = false;

            if (entry.getKey().equals("Request Custom Sculpture")
                    && currentPage.equals("CustomRequest")) {

                selected = true;

            } else if (entry.getKey().equals("My Custom Requests")
                    && currentPage.equals("MyCustomRequests")) {

                selected = true;
            }

            JButton menuButton = entry.getValue();

            menuButton.setBackground(
                    selected ? copperColor : darkColor
            );

            menuButton.setForeground(Color.WHITE);
        }

        if (profileButton != null) {

            profileButton.setBackground(
                    currentPage.equals("Profile")
                            ? copperColor : darkColor
            );

            profileButton.setForeground(Color.WHITE);
        }
    }

    // =================================
    // PAGES
    // =================================

    private void showPage(String pageName) {

        currentPage = pageName;

        if ("Products".equals(pageName)) {

            displayProducts(
                    selectedCategory,
                    searchField.getText()
            );

        } else if ("Profile".equals(pageName)) {

            refreshProfile();

        } else if ("MyCustomRequests".equals(pageName)) {

            refreshCustomRequests();
        }

        cardLayout.show(pagesPanel, pageName);

        updateSidebarSelection();
    }

    private JPanel createPageTitle(
            String title,
            String description
    ) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(mainTextColor);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(mainFont);
        descriptionLabel.setForeground(labelColor);

        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(descriptionLabel);

        return panel;
    }

    // =================================
    // PRODUCT CONTENT
    // =================================

    private void createProductContent() {

        productsPage = new JPanel(new BorderLayout(15, 15));
        productsPage.setBackground(backgroundColor);

        productsPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(
                titlePanel, BoxLayout.Y_AXIS
        ));

        titlePanel.setBackground(backgroundColor);

        JLabel pageTitle = new JLabel("Discover Products");
        pageTitle.setFont(titleFont);
        pageTitle.setForeground(mainTextColor);

        JLabel pageDescription = new JLabel(
                "Find beautifully crafted stone products."
        );

        pageDescription.setFont(mainFont);
        pageDescription.setForeground(labelColor);

        titlePanel.add(pageTitle);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(pageDescription);

        String[] categories = {
                "All Products",
                "Home Decor",
                "Garden",
                "Kitchen",
                "Sculptures"
        };

        for (String category : categories) {

            JTextField categoryField = new JTextField();
            categoryField.setFont(mainFont);
            categoryField.setBackground(Color.WHITE);

            categoryField.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(borderColor),
                            BorderFactory.createEmptyBorder(7, 10, 7, 10)
                    )
            );

            categoryField.setToolTipText(
                    "Search " + category
            );

            categoryField.addActionListener(e -> {

                if (selectedCategory.equals(category)) {
                    displayProducts(category, categoryField.getText());
                }
            });

            categorySearchFields.put(category, categoryField);
        }

        searchHolder = new JPanel(new BorderLayout(8, 0));
        searchHolder.setBackground(backgroundColor);
        searchHolder.setPreferredSize(new Dimension(0, 40));

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, copperColor, Color.WHITE);

        searchButton.addActionListener(e -> searchProducts());

        searchHolder.add(searchButton, BorderLayout.EAST);

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(backgroundColor);

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
                displayProducts(
                        selectedCategory,
                        searchField.getText()
                )
        );

        filterPanel.add(productCount, BorderLayout.WEST);
        filterPanel.add(sortComboBox, BorderLayout.EAST);

        productGrid = new JPanel(
                new GridLayout(0, 3, 15, 15)
        );

        productGrid.setBackground(backgroundColor);

        JScrollPane scrollPane = new JScrollPane(productGrid);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(backgroundColor);

        JPanel topContent = new JPanel(new BorderLayout(10, 12));
        topContent.setBackground(backgroundColor);

        JPanel topSection = new JPanel(new BorderLayout(0, 12));
        topSection.setBackground(backgroundColor);

        topSection.add(titlePanel, BorderLayout.NORTH);
        topSection.add(searchHolder, BorderLayout.CENTER);

        topContent.add(topSection, BorderLayout.NORTH);
        topContent.add(filterPanel, BorderLayout.SOUTH);

        productsPage.add(topContent, BorderLayout.NORTH);
        productsPage.add(scrollPane, BorderLayout.CENTER);

        updateProductSearchBar();

        displayProducts("All Products", "");
    }

    // =================================
    // CHANGE SEARCH FIELD PER CATEGORY
    // =================================

    private void updateProductSearchBar() {

        if (searchHolder == null) {
            return;
        }

        JTextField selectedField =
                categorySearchFields.get(selectedCategory);

        if (selectedField == null) {
            return;
        }

        searchField = selectedField;

        searchHolder.removeAll();

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, copperColor, Color.WHITE);

        searchButton.addActionListener(e -> searchProducts());

        JPanel searchPanel = new JPanel(new BorderLayout(8, 0));
        searchPanel.setBackground(backgroundColor);
        searchPanel.setPreferredSize(new Dimension(450, 40));

        searchField.setPreferredSize(new Dimension(350, 40));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setBackground(backgroundColor);
        centerPanel.add(searchPanel);

        searchHolder.add(centerPanel, BorderLayout.CENTER);
        searchHolder.revalidate();
        searchHolder.repaint();
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

        if (sortComboBox != null) {

            String selectedSort =
                    (String) sortComboBox.getSelectedItem();

            if ("Price: Low to High".equals(selectedSort)) {

                Collections.sort(
                        filteredProducts,
                        Comparator.comparingDouble(Product::getProductPrice)
                );

            } else if ("Price: High to Low".equals(selectedSort)) {

                Collections.sort(
                        filteredProducts,
                        (p1, p2) -> Double.compare(
                                p2.getProductPrice(),
                                p1.getProductPrice()
                        )
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

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(200, 150));
        imagePanel.setBackground(fieldColor);

        JLabel imageLabel = new JLabel("PRODUCT PHOTO");
        imageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        imageLabel.setForeground(labelColor);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        imagePanel.add(imageLabel, BorderLayout.CENTER);

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

        if (product.getProductQuantity() <= 0) {
            JOptionPane.showMessageDialog(this, "Out of stock!");
            return;
        }

        boolean alreadyAdded = false;

        for (Cart cartItem : cartItems) {

            if (cartItem.getProduct().getProductId() == product.getProductId()) {

                if (cartItem.getQuantity() >= product.getProductQuantity()) {
                    JOptionPane.showMessageDialog(this, "Not enough stock!");
                    return;
                }

                cartItem.setQuantity(cartItem.getQuantity() + 1);
                DataAccessObject.updateCartQuantity(cartItem,Main.currentAccount.getAccountId());

                alreadyAdded = true;
                break;
            }
        }

        if (!alreadyAdded) {

            Cart newItem = new Cart(product, 1);

            if (DataAccessObject.insertCartItem(newItem, Main.currentAccount.getAccountId()) != -1) {
                cartItems.add(newItem);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save to cart.");
                return;
            }
        }

        updateCartButton();
        refreshCart();

        JOptionPane.showMessageDialog(
                this,
                product.getProductName() + " added to cart!",
                "StoneCraft",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =================================
    // UPDATE CART BUTTON
    // =================================

    private void updateCartButton() {

        int totalItems = 0;

        for (Cart item : cartItems) {
            totalItems += item.getQuantity();
        }

        if (cartButton != null) {
            cartButton.setText("Cart (" + totalItems + ")");
        }
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

            for (Cart item : cartItems) {

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

    private JPanel createCartItemPanel(Cart item) {

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

        JLabel nameLabel = new JLabel(item.getProduct().getProductName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(mainTextColor);

        JLabel priceLabel = new JLabel(
                "₱" + String.format("%.2f", item.getProduct().getProductPrice())
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
                String.valueOf(item.getQuantity())
        );

        quantityLabel.setFont(boldFont);
        quantityLabel.setForeground(mainTextColor);

        minusButton.addActionListener(e -> {

            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                DataAccessObject.updateCartQuantity(item, Main.currentAccount.getAccountId());
            }

            refreshCart();
            updateCartButton();
        });

        plusButton.addActionListener(e -> {

            if (item.getQuantity() >= item.getProduct().getProductQuantity()) {
                JOptionPane.showMessageDialog(cartFrame, "Not enough stock!");
                return;
            }

            item.setQuantity(item.getQuantity() + 1);
            DataAccessObject.updateCartQuantity(item, Main.currentAccount.getAccountId());

            refreshCart();
            updateCartButton();
        });

        removeButton.addActionListener(e -> {

            DataAccessObject.deleteCartItem(item,Main.currentAccount.getAccountId());
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

        if (subtotalLabel == null) {
            return;
        }

        double subtotal = 0;

        for (Cart item : cartItems) {
            subtotal += item.getProduct().getProductPrice() * item.getQuantity();
        }

        subtotalLabel.setText(
                "Subtotal: ₱" + String.format("%.2f", subtotal)
        );
    }

    // =================================
    // CUSTOM SCULPTURE REQUEST PAGE
    // =================================

    private void createCustomRequestPage() {

        requestPage = new JPanel(new BorderLayout(0, 20));
        requestPage.setBackground(backgroundColor);

        requestPage.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 25, 40)
        );

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = createPageTitle(
                "Request Custom Sculpture",
                "Share your ideas and tell us about the stone sculpture you want."
        );

        headerPanel.add(titlePanel, BorderLayout.WEST);

        JLabel requestBadge = new JLabel("CUSTOM ORDER");
        requestBadge.setFont(new Font("Arial", Font.BOLD, 10));
        requestBadge.setForeground(copperColor);
        requestBadge.setOpaque(true);
        requestBadge.setBackground(new Color(250, 240, 229));
        requestBadge.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        headerPanel.add(requestBadge, BorderLayout.EAST);

        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setOpaque(false);

        // LEFT INFORMATION PANEL

        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(
                informationPanel, BoxLayout.Y_AXIS
        ));

        informationPanel.setBackground(darkColor);
        informationPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        informationPanel.setPreferredSize(new Dimension(260, 0));

        JLabel informationTitle = new JLabel(
                "<html>Bring Your<br>Ideas to Life</html>"
        );

        informationTitle.setFont(new Font("Arial", Font.BOLD, 23));
        informationTitle.setForeground(Color.WHITE);

        JLabel informationDescription = new JLabel(
                "<html>Tell us what you have in mind. "
                        + "Our team will review your request and "
                        + "work on the details of your custom stone sculpture.</html>"
        );

        informationDescription.setFont(mainFont);
        informationDescription.setForeground(new Color(195, 190, 181));

        JLabel stepTitle = new JLabel("REQUEST GUIDE");
        stepTitle.setFont(new Font("Arial", Font.BOLD, 11));
        stepTitle.setForeground(new Color(210, 160, 111));

        JLabel stepOne = createRequestGuide("01", "Describe your sculpture");
        JLabel stepTwo = createRequestGuide("02", "Choose your preferred size");
        JLabel stepThree = createRequestGuide("03", "Add your design preference");
        JLabel stepFour = createRequestGuide("04", "Submit your request");

        informationPanel.add(informationTitle);
        informationPanel.add(Box.createVerticalStrut(15));
        informationPanel.add(informationDescription);
        informationPanel.add(Box.createVerticalStrut(35));
        informationPanel.add(stepTitle);
        informationPanel.add(Box.createVerticalStrut(18));
        informationPanel.add(stepOne);
        informationPanel.add(Box.createVerticalStrut(18));
        informationPanel.add(stepTwo);
        informationPanel.add(Box.createVerticalStrut(18));
        informationPanel.add(stepThree);
        informationPanel.add(Box.createVerticalStrut(18));
        informationPanel.add(stepFour);

        // FORM CARD

        JPanel formCard = createWhiteCard();
        formCard.setLayout(new BorderLayout(0, 15));

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(25, 30, 20, 30)
                )
        );

        JPanel formHeader = new JPanel();
        formHeader.setLayout(new BoxLayout(
                formHeader, BoxLayout.Y_AXIS
        ));

        formHeader.setOpaque(false);

        JLabel formTitle = new JLabel("Sculpture Details");
        formTitle.setFont(new Font("Arial", Font.BOLD, 19));
        formTitle.setForeground(mainTextColor);

        JLabel formDescription = new JLabel(
                "Please provide the information below."
        );

        formDescription.setFont(mainFont);
        formDescription.setForeground(labelColor);

        formHeader.add(formTitle);
        formHeader.add(Box.createVerticalStrut(5));
        formHeader.add(formDescription);

        // FORM FIELDS

        requestFormPanel = new JPanel(new GridBagLayout());
        requestFormPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 12, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        sculptureNameField = createFormField();

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(mainFont);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(fieldColor);
        descriptionArea.setForeground(mainTextColor);

        descriptionArea.setBorder(
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        );

        descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        categoryComboBox = new JComboBox<>(new String[]{
                "Home Decor", "Garden", "Kitchen", "Sculptures"
        });

        styleComboBox(categoryComboBox);

        sizeComboBox = new JComboBox<>(new String[]{
                "Small", "Medium", "Large", "Extra Large", "Custom Size"
        });

        styleComboBox(sizeComboBox);

        quantitySpinner = new JSpinner(
                new SpinnerNumberModel(1, 1, 100, 1)
        );

        quantitySpinner.setFont(mainFont);
        quantitySpinner.setPreferredSize(new Dimension(100, 38));

        designField = createFormField();

        addRequestFormField(
                requestFormPanel, gbc,
                0, 0, 2,
                "Sculpture Name",
                sculptureNameField
        );

        addRequestFormField(
                requestFormPanel, gbc,
                1, 0, 2,
                "Description / Details",
                descriptionScroll
        );

        addRequestFormField(
                requestFormPanel, gbc,
                2, 0, 1,
                "Category",
                categoryComboBox
        );

        addRequestFormField(
                requestFormPanel, gbc,
                2, 1, 1,
                "Preferred Size",
                sizeComboBox
        );

        addRequestFormField(
                requestFormPanel, gbc,
                3, 0, 1,
                "Quantity",
                quantitySpinner
        );

        addRequestFormField(
                requestFormPanel, gbc,
                3, 1, 1,
                "Preferred Design / Style",
                designField
        );

        JScrollPane formScrollPane = new JScrollPane(requestFormPanel);
        formScrollPane.setBorder(null);
        formScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        formScrollPane.getViewport().setBackground(Color.WHITE);

        // BUTTONS

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );

        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(
                BorderFactory.createMatteBorder(
                        1, 0, 0, 0, borderColor
                )
        );

        styleButton(cancelButton, fieldColor, mainTextColor);

        cancelButton.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        cancelButton.setPreferredSize(new Dimension(110, 40));
        cancelButton.addActionListener(e -> showPage("Products"));

        submitButton = new JButton("Submit Request");
        styleButton(submitButton, copperColor, Color.WHITE);
        submitButton.setPreferredSize(new Dimension(155, 40));

        // CONNECT SUBMIT BUTTON TO DATABASE
        submitButton.addActionListener(e -> submitCustomRequest());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        formCard.add(formHeader, BorderLayout.NORTH);
        formCard.add(formScrollPane, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(informationPanel, BorderLayout.WEST);
        contentPanel.add(formCard, BorderLayout.CENTER);

        requestPage.add(headerPanel, BorderLayout.NORTH);
        requestPage.add(contentPanel, BorderLayout.CENTER);
    }

    // =================================
    // REQUEST GUIDE LABEL
    // =================================

    private JLabel createRequestGuide(
            String number,
            String description
    ) {

        JLabel label = new JLabel(
                "<html><b><font color='#D2A06F'>" + number
                        + "</font></b>&nbsp;&nbsp; "
                        + "<font color='#FFFFFF'>" + description
                        + "</font></html>"
        );

        label.setFont(mainFont);

        return label;
    }

    // =================================
    // REQUEST FORM FIELD HELPER
    // =================================

    private void addRequestFormField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int column,
            int width,
            String labelText,
            JComponent component
    ) {

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(
                fieldPanel, BoxLayout.Y_AXIS
        ));

        fieldPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(boldFont);
        label.setForeground(mainTextColor);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (component instanceof JTextField
                || component instanceof JComboBox
                || component instanceof JSpinner) {

            component.setPreferredSize(new Dimension(200, 38));
            component.setMaximumSize(
                    new Dimension(Integer.MAX_VALUE, 38)
            );
        }

        if (component instanceof JScrollPane) {
            component.setPreferredSize(new Dimension(400, 105));
        }

        fieldPanel.add(label);
        fieldPanel.add(Box.createVerticalStrut(8));
        fieldPanel.add(component);

        gbc.gridx = column;
        gbc.gridy = row;
        gbc.gridwidth = width;
        gbc.weightx = width;
        gbc.insets = new Insets(8, 8, 15, 8);

        panel.add(fieldPanel, gbc);
    }

    // =================================
    // OPEN CUSTOM REQUEST PAGE
    // =================================

    public void openCustomRequestForm() {

        if (sculptureNameField != null) {
            sculptureNameField.setText("");
            descriptionArea.setText("");
            categoryComboBox.setSelectedIndex(0);
            sizeComboBox.setSelectedIndex(0);
            quantitySpinner.setValue(1);
            designField.setText("");
        }

        showPage("CustomRequest");
    }

    // =================================
    // SUBMIT CUSTOM REQUEST
    // =================================

    private void submitCustomRequest() {

        String sculptureName = sculptureNameField.getText().trim();
        String description = descriptionArea.getText().trim();
        String category = (String) categoryComboBox.getSelectedItem();
        String size = (String) sizeComboBox.getSelectedItem();
        int quantity = (Integer) quantitySpinner.getValue();
        String preferredDesign = designField.getText().trim();

        // Check sculpture name
        if (sculptureName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a sculpture name.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            sculptureNameField.requestFocus();
            return;
        }

        // Check description
        if (description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the description of your sculpture.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            descriptionArea.requestFocus();
            return;
        }

        // Check logged-in customer
        Accounts currentAccount = Main.currentAccount;

        if (currentAccount == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please log in first before submitting a request.",
                    "Account Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Create CustomRequest object
        CustomRequest request = new CustomRequest(
                currentAccount,
                sculptureName,
                category,
                0.00,
                quantity,
                description,
                size,
                preferredDesign
        );

        request.setStatus("Pending");

        // Save request to database
        int requestId = DataAccessObject.insertCustomProduct(request);

        if (requestId != -1) {

            // Clear form fields
            sculptureNameField.setText("");
            descriptionArea.setText("");
            categoryComboBox.setSelectedIndex(0);
            sizeComboBox.setSelectedIndex(0);
            quantitySpinner.setValue(1);
            designField.setText("");

            // Refresh and display submitted requests
            showPage("MyCustomRequests");

            JOptionPane.showMessageDialog(
                    this,
                    "Your custom sculpture request has been submitted successfully!",
                    "Request Submitted",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to submit your custom request. Please try again.",
                    "Submission Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =================================
    // FORM DESIGN HELPERS
    // =================================

    private JTextField createFormField() {

        JTextField textField = new JTextField();
        textField.setFont(mainFont);
        textField.setBackground(fieldColor);
        textField.setForeground(mainTextColor);

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



    // =================================
    // MY CUSTOM REQUESTS PAGE
    // =================================

    private void createMyCustomRequestsPage() {

        myRequestsPage = new JPanel(new BorderLayout(0, 20));
        myRequestsPage.setBackground(backgroundColor);

        myRequestsPage.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 25, 40)
        );

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = createPageTitle(
                "My Custom Requests",
                "View and review the custom sculpture requests you have submitted."
        );

        headerPanel.add(titlePanel, BorderLayout.WEST);

        JLabel requestBadge = new JLabel("REQUEST HISTORY");
        requestBadge.setFont(new Font("Arial", Font.BOLD, 10));
        requestBadge.setForeground(copperColor);
        requestBadge.setOpaque(true);
        requestBadge.setBackground(new Color(250, 240, 229));
        requestBadge.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        headerPanel.add(requestBadge, BorderLayout.EAST);

        JPanel contentCard = createWhiteCard();
        contentCard.setLayout(new BorderLayout(0, 15));

        contentCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(22, 25, 22, 25)
                )
        );

        JPanel contentHeader = new JPanel(new BorderLayout());
        contentHeader.setOpaque(false);

        JPanel contentTitlePanel = new JPanel();
        contentTitlePanel.setLayout(new BoxLayout(
                contentTitlePanel, BoxLayout.Y_AXIS
        ));

        contentTitlePanel.setOpaque(false);

        JLabel contentTitle = new JLabel("Submitted Requests");
        contentTitle.setFont(new Font("Arial", Font.BOLD, 18));
        contentTitle.setForeground(mainTextColor);

        JLabel contentDescription = new JLabel(
                "All your custom sculpture requests are listed below."
        );

        contentDescription.setFont(mainFont);
        contentDescription.setForeground(labelColor);

        contentTitlePanel.add(contentTitle);
        contentTitlePanel.add(Box.createVerticalStrut(5));
        contentTitlePanel.add(contentDescription);

        JButton newRequestButton = new JButton("+ New Request");
        styleButton(newRequestButton, copperColor, Color.WHITE);
        newRequestButton.setPreferredSize(new Dimension(140, 38));

        newRequestButton.addActionListener(e -> openCustomRequestForm());

        contentHeader.add(contentTitlePanel, BorderLayout.WEST);
        contentHeader.add(newRequestButton, BorderLayout.EAST);

        requestListPanel = new JPanel();
        requestListPanel.setLayout(new BoxLayout(
                requestListPanel, BoxLayout.Y_AXIS
        ));

        requestListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(requestListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.getViewport().setBackground(Color.WHITE);

        contentCard.add(contentHeader, BorderLayout.NORTH);
        contentCard.add(scrollPane, BorderLayout.CENTER);

        myRequestsPage.add(headerPanel, BorderLayout.NORTH);
        myRequestsPage.add(contentCard, BorderLayout.CENTER);
    }

    // =================================
    // OPEN MY CUSTOM REQUESTS PAGE
    // =================================

    private void openCustomRequestsFrame() {

        showPage("MyCustomRequests");
    }

    // =================================
    // REFRESH CUSTOM REQUESTS
    // =================================

    private void refreshCustomRequests() {

        if (requestListPanel == null) {
            return;
        }

        requestListPanel.removeAll();

        List<CustomRequest> allRequests =
                DataAccessObject.getAllCustomProduct();

        customRequests.clear();

        Accounts currentAccount = Main.currentAccount;

        if (allRequests != null && currentAccount != null) {

            for (CustomRequest request : allRequests) {

                if (request.getCustomer() != null
                        && request.getCustomer().getAccountId()
                        == currentAccount.getAccountId()) {

                    customRequests.add(request);
                }
            }
        }

        if (customRequests.isEmpty()) {

            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BoxLayout(
                    emptyPanel, BoxLayout.Y_AXIS
            ));

            emptyPanel.setBackground(Color.WHITE);
            emptyPanel.setBorder(
                    BorderFactory.createEmptyBorder(55, 10, 55, 10)
            );

            JLabel emptyTitle = new JLabel("No Custom Requests Yet");
            emptyTitle.setFont(new Font("Arial", Font.BOLD, 18));
            emptyTitle.setForeground(mainTextColor);
            emptyTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel emptyDescription = new JLabel(
                    "Your submitted sculpture requests will appear here."
            );

            emptyDescription.setFont(mainFont);
            emptyDescription.setForeground(labelColor);
            emptyDescription.setAlignmentX(Component.CENTER_ALIGNMENT);

            emptyPanel.add(emptyTitle);
            emptyPanel.add(Box.createVerticalStrut(8));
            emptyPanel.add(emptyDescription);

            requestListPanel.add(emptyPanel);

        } else {

            for (CustomRequest customRequest : customRequests) {

                requestListPanel.add(
                        createCustomRequestCard(customRequest)
                );

                requestListPanel.add(Box.createVerticalStrut(12));
            }
        }

        requestListPanel.revalidate();
        requestListPanel.repaint();
    }

    // =================================
    // CUSTOM REQUEST CARD
    // =================================

    private JPanel createCustomRequestCard(
            CustomRequest customRequest
    ) {

        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(18, 20, 18, 20)
                )
        );

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(
                detailsPanel, BoxLayout.Y_AXIS
        ));

        detailsPanel.setBackground(Color.WHITE);

        JLabel categoryLabel = new JLabel(
                customRequest.getProductCategory() != null
                        ? customRequest.getProductCategory().toUpperCase()
                        : "CUSTOM REQUEST"
        );

        categoryLabel.setFont(new Font("Arial", Font.BOLD, 10));
        categoryLabel.setForeground(copperColor);

        JLabel nameLabel = new JLabel(customRequest.getProductName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 17));
        nameLabel.setForeground(mainTextColor);

        JLabel sizeLabel = new JLabel(
                "Preferred Size: " + customRequest.getProductSize()
        );

        sizeLabel.setFont(mainFont);
        sizeLabel.setForeground(labelColor);

        JLabel quantityLabel = new JLabel(
                "Quantity: " + customRequest.getProductQuantity()
        );

        quantityLabel.setFont(mainFont);
        quantityLabel.setForeground(labelColor);

        JLabel designLabel = new JLabel(
                "Design: " + customRequest.getProductPreferredDesign()
        );

        designLabel.setFont(mainFont);
        designLabel.setForeground(labelColor);

        JLabel priceLabel = new JLabel(
                "Price: ₱" + String.format(
                        "%.2f", customRequest.getProductPrice()
                )
        );

        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(copperColor);

        String status = customRequest.getStatus();

        if (status == null || status.trim().isEmpty()) {
            status = "Pending";
        }

        JLabel statusLabel = new JLabel("Status: " + status);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setForeground(getStatusColor(status));

        detailsPanel.add(categoryLabel);
        detailsPanel.add(Box.createVerticalStrut(6));
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(9));
        detailsPanel.add(sizeLabel);
        detailsPanel.add(Box.createVerticalStrut(4));
        detailsPanel.add(quantityLabel);
        detailsPanel.add(Box.createVerticalStrut(4));
        detailsPanel.add(designLabel);
        detailsPanel.add(Box.createVerticalStrut(8));
        detailsPanel.add(priceLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(statusLabel);

        JPanel buttonPanel = new JPanel(
                new GridBagLayout()
        );

        buttonPanel.setOpaque(false);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(
                actionPanel, BoxLayout.Y_AXIS
        ));

        actionPanel.setOpaque(false);

        JButton viewButton = new JButton("View Details");
        styleButton(viewButton, darkColor, Color.WHITE);
        viewButton.setPreferredSize(new Dimension(125, 35));
        viewButton.setMaximumSize(new Dimension(125, 35));

        viewButton.addActionListener(
                e -> showCustomRequestDetails(customRequest)
        );

        JButton editButton = new JButton("Edit");
        styleButton(editButton, copperColor, Color.WHITE);
        editButton.setPreferredSize(new Dimension(125, 35));
        editButton.setMaximumSize(new Dimension(125, 35));

        editButton.addActionListener(
                e -> editCustomRequest(customRequest)
        );

        JButton cancelRequestButton = new JButton("Cancel Request");
        styleButton(cancelRequestButton, deleteColor, Color.WHITE);
        cancelRequestButton.setPreferredSize(new Dimension(125, 35));
        cancelRequestButton.setMaximumSize(new Dimension(125, 35));

        cancelRequestButton.addActionListener( e ->
                cancelCustomRequest(customRequest));

        actionPanel.add(viewButton);
        actionPanel.add(Box.createVerticalStrut(7));
        actionPanel.add(editButton);
        actionPanel.add(Box.createVerticalStrut(7));
        actionPanel.add(cancelRequestButton);

        buttonPanel.add(actionPanel);

        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.EAST);

        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 220)
        );

        return card;
    }

    // =================================
    // REQUEST STATUS COLOR
    // =================================

    private Color getStatusColor(String status) {

        if (status == null) {
            return labelColor;
        }

        String value = status.toLowerCase();

        if (value.contains("cancel")) {
            return deleteColor;
        }

        if (value.contains("complete")
                || value.contains("approved")) {
            return new Color(52, 125, 75);
        }

        if (value.contains("pending")
                || value.contains("review")) {
            return new Color(181, 101, 29);
        }

        return labelColor;
    }

    // =================================
    // EDIT CUSTOM REQUEST
    // =================================

    private void editCustomRequest(CustomRequest request) {

        String status = request.getStatus();

        if (status == null || status.trim().isEmpty()) {
            status = "Pending";
        }

        if ("Cancelled".equalsIgnoreCase(status) || "Completed".equalsIgnoreCase(status) || "Accepted".equalsIgnoreCase(status)
                || "In Progress".equalsIgnoreCase(status) || "Declined".equalsIgnoreCase(status)) {

            JOptionPane.showMessageDialog(
                    this,
                    "This request is already " + status.toLowerCase()
                            + " and can no longer be edited.",
                    "Edit Request",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        JDialog editDialog = new JDialog(
                this,
                "Edit Custom Request",
                true
        );

        editDialog.setSize(500, 550);
        editDialog.setResizable(false);
        editDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(22, 25, 20, 25)
        );

        JLabel titleLabel = new JLabel("Edit Request Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 21));
        titleLabel.setForeground(mainTextColor);

        JLabel descriptionLabel = new JLabel(
                "Update the details of your custom sculpture request."
        );

        descriptionLabel.setFont(mainFont);
        descriptionLabel.setForeground(labelColor);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(
                headerPanel, BoxLayout.Y_AXIS
        ));
        headerPanel.setOpaque(false);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(descriptionLabel);

        JTextField nameField = createFormField();
        nameField.setText(request.getProductName());

        JTextArea detailsArea = new JTextArea(4, 20);
        detailsArea.setFont(mainFont);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setText(request.getProductDescription());
        detailsArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );

        JScrollPane detailsScroll = new JScrollPane(detailsArea);

        JComboBox<String> categoryField = new JComboBox<>(
                new String[]{
                        "Home Decor", "Garden", "Kitchen", "Sculptures"
                }
        );

        categoryField.setSelectedItem(request.getProductCategory());
        styleComboBox(categoryField);

        JComboBox<String> sizeField = new JComboBox<>(
                new String[]{
                        "Small", "Medium", "Large",
                        "Extra Large", "Custom Size"
                }
        );

        sizeField.setSelectedItem(request.getProductSize());
        styleComboBox(sizeField);

        JSpinner quantityField = new JSpinner(
                new SpinnerNumberModel(
                        Math.max(1, request.getProductQuantity()),
                        1, 100, 1
                )
        );

        JTextField designField = createFormField();
        designField.setText(request.getProductPreferredDesign());

        JPanel formPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        formPanel.setBackground(Color.WHITE);

        addEditField(formPanel, "Sculpture Name", nameField);
        addEditField(formPanel, "Description / Details", detailsScroll);
        addEditField(formPanel, "Category", categoryField);
        addEditField(formPanel, "Preferred Size", sizeField);
        addEditField(formPanel, "Quantity", quantityField);
        addEditField(formPanel, "Preferred Design / Style", designField);

        JScrollPane formScroll = new JScrollPane(formPanel);
        formScroll.setBorder(null);
        formScroll.getVerticalScrollBar().setUnitIncrement(15);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );

        buttonPanel.setBackground(Color.WHITE);

        JButton closeButton = new JButton("Cancel");
        styleButton(closeButton, fieldColor, mainTextColor);

        JButton saveEditButton = new JButton("Save Changes");
        styleButton(saveEditButton, copperColor, Color.WHITE);

        closeButton.addActionListener(e -> editDialog.dispose());

        saveEditButton.addActionListener(e -> {

            if (nameField.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        editDialog,
                        "Please enter a sculpture name.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            request.setProductName(nameField.getText().trim());
            request.setProductDescription(detailsArea.getText().trim());
            request.setProductCategory(
                    (String) categoryField.getSelectedItem()
            );
            request.setProductSize(
                    (String) sizeField.getSelectedItem()
            );
            request.setProductQuantity(
                    (Integer) quantityField.getValue()
            );
            request.setProductPreferredDesign(
                    designField.getText().trim()
            );

            // This edit is currently local only.
            // DAO does not yet have an updateCustomProduct method.

            editDialog.dispose();
            refreshCustomRequests();

            JOptionPane.showMessageDialog(
                    this,
                    "Request details updated in this session.\n"
                            + "Database saving is not yet available.",
                    "Request Updated",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        buttonPanel.add(closeButton);
        buttonPanel.add(saveEditButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formScroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        editDialog.setContentPane(mainPanel);
        editDialog.setVisible(true);
    }

    private void addEditField(
            JPanel panel,
            String labelText,
            JComponent component
    ) {

        JLabel label = new JLabel(labelText);
        label.setFont(boldFont);
        label.setForeground(mainTextColor);

        panel.add(label);
        panel.add(component);
    }

    // =================================
    // CANCEL CUSTOM REQUEST
    // =================================

    private void cancelCustomRequest(CustomRequest request) {

        if ("Cancelled".equalsIgnoreCase(request.getStatus())) {

            JOptionPane.showMessageDialog(
                    this,
                    "This cancelled request has been removed.",
                    "Cancel Request",
                    JOptionPane.INFORMATION_MESSAGE
            );

            DataAccessObject.deleteCustomProduct(
                    request.getProductId(),
                    Main.currentAccount.getAccountId()
            );

            refreshCustomRequests();


            return;
        }

        if ("Completed".equalsIgnoreCase(request.getStatus())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Completed requests can no longer be cancelled.",
                    "Cancel Request",
                    JOptionPane.INFORMATION_MESSAGE
            );
            JOptionPane.showMessageDialog(
                    this,
                    "Completed requests can no longer be cancelled.",
                    "Edit",
                    JOptionPane.INFORMATION_MESSAGE
            );



            return;
        }

        if ("Accepted".equalsIgnoreCase(request.getStatus())) {

            JOptionPane.showMessageDialog(
                    this,
                    "This request has already been accepted and can no longer be cancelled.",
                    "Cancel Request",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        if ("In Progress".equalsIgnoreCase(request.getStatus())) {

            JOptionPane.showMessageDialog(
                    this,
                    "This request is already in progress and can no longer be cancelled.",
                    "Cancel Request",
                    JOptionPane.WARNING_MESSAGE
            );

            JOptionPane.showMessageDialog(
                    this,
                    "This request is already in progress and can no longer be cancelled.",
                    "Edit",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if ("Declined".equalsIgnoreCase(request.getStatus())) {

            int declinedChoice = JOptionPane.showConfirmDialog(
                    this,
                    "This request was declined. Do you want to cancel it?",
                    "Edit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (declinedChoice == JOptionPane.YES_OPTION) {

                request.setStatus("Cancelled");

                DataAccessObject.updateRequestStatus(request, "Cancelled");

                refreshCustomRequests();

                JOptionPane.showMessageDialog(
                        this,
                        "Your custom request has been cancelled.",
                        "Request Cancelled",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this custom request?",
                "Cancel Custom Request",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {

            request.setStatus("Cancelled");

            DataAccessObject.updateRequestStatus(request, "Cancelled");

            refreshCustomRequests();

            JOptionPane.showMessageDialog(
                    this,
                    "Your custom request has been cancelled.",
                    "Request Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =================================
    // CUSTOM REQUEST DETAILS
    // =================================

    private void showCustomRequestDetails(
            CustomRequest customRequest
    ) {

        String status = customRequest.getStatus();

        if (status == null || status.trim().isEmpty()) {
            status = "Pending";
        }

        String details =
                "Sculpture Name: " + customRequest.getProductName()
                        + "\n\nCategory: " + customRequest.getProductCategory()
                        + "\n\nDescription: " + customRequest.getProductDescription()
                        + "\n\nPreferred Size: " + customRequest.getProductSize()
                        + "\n\nQuantity: " + customRequest.getProductQuantity()
                        + "\n\nDesign: " + customRequest.getProductPreferredDesign()
                        + "\n\nOwner-Set Price: ₱"
                        + String.format("%.2f", customRequest.getProductPrice())
                        + "\n\nCurrent Status: " + status;

        JOptionPane.showMessageDialog(
                this,
                details,
                "Custom Request Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =================================
    // CUSTOMER PROFILE PAGE
    // =================================

    private void createProfilePage() {

        profilePage = new JPanel(new BorderLayout(20, 20));
        profilePage.setBackground(backgroundColor);

        profilePage.setBorder(
                BorderFactory.createEmptyBorder(30, 35, 25, 35)
        );

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        topPanel.add(
                createPageTitle(
                        "My Profile",
                        "Manage your personal account information."
                ),
                BorderLayout.NORTH
        );

        profileDetailsPanel = new JPanel(new BorderLayout());
        profileDetailsPanel.setOpaque(false);

        profilePage.add(topPanel, BorderLayout.NORTH);
        profilePage.add(profileDetailsPanel, BorderLayout.CENTER);
    }

    public void refreshProfile() {

        Accounts account = Main.currentAccount;

        if (account != null) {

            if (account.getName() != null
                    && !account.getName().trim().isEmpty()) {
                profileName = account.getName();
            }

            if (account.getContactNumber() != null
                    && !account.getContactNumber().trim().isEmpty()) {
                profilePhone = account.getContactNumber();
            }

            if (account.getEmailAddress() != null
                    && !account.getEmailAddress().trim().isEmpty()) {
                profileEmail = account.getEmailAddress();
            }
        }

        profileDetailsPanel.removeAll();

        profileDetailsPanel.add(
                createProfileDetailsPanel(),
                BorderLayout.CENTER
        );

        profileDetailsPanel.revalidate();
        profileDetailsPanel.repaint();
    }

    private JPanel createProfileDetailsPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setOpaque(false);

        JPanel profileBanner = new JPanel(new BorderLayout(20, 0));
        profileBanner.setBackground(darkColor);

        profileBanner.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        profileBanner.setPreferredSize(new Dimension(0, 155));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(
                namePanel, BoxLayout.Y_AXIS
        ));

        namePanel.setOpaque(false);

        JLabel accountTypeLabel = new JLabel("CUSTOMER ACCOUNT");
        accountTypeLabel.setFont(new Font("Arial", Font.BOLD, 11));
        accountTypeLabel.setForeground(new Color(210, 160, 111));

        JLabel nameLabel = new JLabel(profileName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 25));
        nameLabel.setForeground(Color.WHITE);

        JLabel emailLabel = new JLabel(profileEmail);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        emailLabel.setForeground(new Color(195, 190, 181));

        JLabel descriptionLabel = new JLabel(
                "Your personal StoneCraft account"
        );

        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(new Color(160, 155, 147));

        namePanel.add(accountTypeLabel);
        namePanel.add(Box.createVerticalStrut(8));
        namePanel.add(nameLabel);
        namePanel.add(Box.createVerticalStrut(5));
        namePanel.add(emailLabel);
        namePanel.add(Box.createVerticalStrut(8));
        namePanel.add(descriptionLabel);

        profileBanner.add(namePanel, BorderLayout.CENTER);

        JPanel informationPanel = createWhiteCard();

        informationPanel.setLayout(new BorderLayout(0, 18));

        informationPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(22, 25, 22, 25)
                )
        );

        JPanel informationHeader = new JPanel(new BorderLayout());
        informationHeader.setOpaque(false);

        JPanel informationTitlePanel = new JPanel();
        informationTitlePanel.setLayout(new BoxLayout(
                informationTitlePanel, BoxLayout.Y_AXIS
        ));

        informationTitlePanel.setOpaque(false);

        JLabel informationTitle = new JLabel("Account Information");
        informationTitle.setFont(new Font("Arial", Font.BOLD, 19));
        informationTitle.setForeground(mainTextColor);

        JLabel informationDescription = new JLabel(
                "Your registered personal and contact details."
        );

        informationDescription.setFont(mainFont);
        informationDescription.setForeground(labelColor);

        informationTitlePanel.add(informationTitle);
        informationTitlePanel.add(Box.createVerticalStrut(5));
        informationTitlePanel.add(informationDescription);

        JLabel accountBadge = new JLabel("PERSONAL DETAILS");
        accountBadge.setFont(new Font("Arial", Font.BOLD, 10));
        accountBadge.setForeground(copperColor);
        accountBadge.setOpaque(true);
        accountBadge.setBackground(new Color(250, 240, 229));
        accountBadge.setBorder(
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        );

        informationHeader.add(
                informationTitlePanel,
                BorderLayout.WEST
        );

        informationHeader.add(
                accountBadge,
                BorderLayout.EAST
        );

        JPanel rowsPanel = new JPanel(new GridLayout(4, 1, 0, 0));
        rowsPanel.setBackground(Color.WHITE);

        rowsPanel.add(
                createProfileInfoRow(
                        "FULL NAME",
                        profileName,
                        "Your registered name"
                )
        );

        rowsPanel.add(
                createProfileInfoRow(
                        "ADDRESS",
                        profileAddress,
                        "Your residential address"
                )
        );

        rowsPanel.add(
                createProfileInfoRow(
                        "PHONE NUMBER",
                        profilePhone,
                        "Your contact number"
                )
        );

        rowsPanel.add(
                createProfileInfoRow(
                        "EMAIL ADDRESS",
                        profileEmail,
                        "Your registered email"
                )
        );

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);

        JLabel editDescription = new JLabel(
                "Want to change your account details?"
        );

        editDescription.setFont(mainFont);
        editDescription.setForeground(labelColor);

        editProfileButton = new JButton("Edit Profile");
        styleButton(editProfileButton, copperColor, Color.WHITE);

        editProfileButton.setPreferredSize(new Dimension(145, 42));
        editProfileButton.addActionListener(e -> editProfile());

        buttonPanel.add(editDescription, BorderLayout.WEST);
        buttonPanel.add(editProfileButton, BorderLayout.EAST);

        informationPanel.add(
                informationHeader,
                BorderLayout.NORTH
        );

        informationPanel.add(
                rowsPanel,
                BorderLayout.CENTER
        );

        informationPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(profileBanner, BorderLayout.NORTH);
        mainPanel.add(informationPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    // =================================
    // PROFILE INFORMATION ROW
    // =================================

    private JPanel createProfileInfoRow(
            String labelText,
            String valueText,
            String descriptionText
    ) {

        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);

        row.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0, borderColor
                        ),
                        BorderFactory.createEmptyBorder(12, 0, 12, 0)
                )
        );

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(
                textPanel, BoxLayout.Y_AXIS
        ));

        textPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 10));
        label.setForeground(labelColor);

        JLabel description = new JLabel(descriptionText);
        description.setFont(new Font("Arial", Font.PLAIN, 11));
        description.setForeground(labelColor);

        JLabel value = new JLabel(
                valueText != null && !valueText.trim().isEmpty()
                        ? valueText : "Not set"
        );

        value.setFont(new Font("Arial", Font.BOLD, 14));
        value.setForeground(mainTextColor);

        textPanel.add(label);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(value);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setOpaque(false);
        descriptionPanel.add(description, BorderLayout.CENTER);

        row.add(textPanel, BorderLayout.WEST);
        row.add(descriptionPanel, BorderLayout.EAST);

        return row;
    }

    // =================================
    // EDIT PROFILE
    // =================================

    private void editProfile() {

        dialog = new JDialog(this, "Edit Profile", true);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(500, 500);
        dialog.setMinimumSize(new Dimension(500, 500));
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 20, 30)
        );

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Edit Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
        titleLabel.setForeground(mainTextColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(
                "Update your personal and contact information."
        );

        descriptionLabel.setFont(mainFont);
        descriptionLabel.setForeground(labelColor);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(6));
        headerPanel.add(descriptionLabel);

        editNameField = createFormField();
        editAddressField = createFormField();
        editPhoneField = createFormField();
        editEmailField = createFormField();

        editNameField.setText(
                profileName != null && !"Not set".equals(profileName)
                        ? profileName : ""
        );

        editAddressField.setText(
                profileAddress != null && !"Not set".equals(profileAddress)
                        ? profileAddress : ""
        );

        editPhoneField.setText(
                profilePhone != null && !"Not set".equals(profilePhone)
                        ? profilePhone : ""
        );

        editEmailField.setText(
                profileEmail != null && !"Not set".equals(profileEmail)
                        ? profileEmail : ""
        );

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 5, 0);

        JLabel nameLabel = createFormLabel("Full Name");
        JLabel addressLabel = createFormLabel("Address");
        JLabel phoneLabel = createFormLabel("Phone Number");
        JLabel emailLabel = createFormLabel("Email Address");

        editNameField.setPreferredSize(new Dimension(420, 38));
        editAddressField.setPreferredSize(new Dimension(420, 38));
        editPhoneField.setPreferredSize(new Dimension(420, 38));
        editEmailField.setPreferredSize(new Dimension(420, 38));

        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 12, 0);
        formPanel.add(editNameField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(addressLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 12, 0);
        formPanel.add(editAddressField, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(phoneLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 12, 0);
        formPanel.add(editPhoneField, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(emailLabel, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        formPanel.add(editEmailField, gbc);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );

        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");

        styleButton(cancelButton, new Color(235, 233, 229), mainTextColor);
        styleButton(saveButton, copperColor, Color.WHITE);

        cancelButton.setPreferredSize(new Dimension(105, 40));
        saveButton.setPreferredSize(new Dimension(135, 40));

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.getRootPane().setDefaultButton(saveButton);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JLabel createFormLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(boldFont);
        label.setForeground(mainTextColor);

        return label;
    }

    // =================================
    // PROFILE NAVIGATION
    // =================================

    public void showProfile() {
        showPage("Profile");
    }


    private void logout() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Log Out",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            Main.currentAccount = null;
            dispose();
            Main.loginProcess();
        }
    }
}