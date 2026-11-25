package Design;

import DataBaseManagementConnection.DataAccessObject;
import Main.CustomRequest;
import Main.Product;
import Main.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusinessMain extends MainDesign {

    // =========================================
    // COLORS AND FONTS
    // =========================================

    private final Color darkColor = new Color(43, 42, 40);
    private final Color copperColor = new Color(181, 101, 29);
    private final Color hoverColor = new Color(151, 83, 15);
    private final Color mainTextColor = new Color(31, 30, 28);
    private final Color labelColor = new Color(120, 116, 109);
    private final Color fieldColor = new Color(250, 249, 247);
    private final Color borderColor = new Color(222, 218, 211);
    private final Color backgroundColor = new Color(247, 245, 241);
    private final Color selectionColor = new Color(246, 228, 210);
    private final Color deleteColor = new Color(174, 65, 59);

    private final Font mainFont = new Font("Arial", Font.PLAIN, 13);
    private final Font boldFont = new Font("Arial", Font.BOLD, 13);
    private final Font titleFont = new Font("Arial", Font.BOLD, 25);

    private static final String[] STATUSES = {
            "Pending", "Accepted", "In Progress", "Completed", "Declined"
    };

    private static final String[] CATEGORIES = {
            "Home Decor", "Garden", "Kitchen", "Sculptures"
    };

    // =========================================
    // MAIN PANELS
    // =========================================

    private JPanel dashboardPanel;
    private JPanel headerPanel;
    private JPanel sidebarPanel;
    private JPanel pagesPanel;

    private CardLayout cardLayout;

    // =========================================
    // PUBLIC COMPONENTS
    // =========================================

    private JButton selectedMenuButton;

    public JTextField searchField;
    public JButton profileButton;
    public JButton logoutButton;

    public JButton addButton;

    public JTextField nameField, priceField, stockField;
    public JComboBox<String> categoryBox;

    public JMenuItem accountItem, logoutItem;

    public int choice;

    public ArrayList<Product> products;
    public ArrayList<CustomRequest> customRequests;

    // =========================================
    // PAGES
    // =========================================

    private JPanel overviewPage;
    private JPanel productsPage;
    private JPanel requestsPage;
    private JPanel profilePage;

    private final Map<String, JButton> menuButtons = new LinkedHashMap<>();

    private String currentPage = "Overview";

    // =========================================
    // OVERVIEW
    // =========================================

    private JPanel statsPanel;
    private JPanel recentPanel;

    // =========================================
    // PRODUCTS
    // =========================================

    private DefaultTableModel productTableModel;
    private JTable productTable;

    private final ArrayList<Product> visibleProducts = new ArrayList<>();

    // =========================================
    // REQUESTS
    // =========================================

    private JPanel requestsListPanel;
    public JComboBox<String> statusFilterBox;
    public String statusValue;

    // =========================================
    // PROFILE
    // =========================================

    private JPanel profileCardsPanel;
    private JPanel profileDetailsPanel;

    public String profileName;
    public String profilePhone;
    public String profileEmail;

    public JDialog dialog;
    public JTextField editNameField;
    public JTextField editPhoneField;

    public JButton saveButton = new JButton("Save Changes");

    // =========================================
    // CONSTRUCTOR
    // =========================================

    public BusinessMain() {

        setTitle("StoneCraft - Business Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        products = new ArrayList<>();
        customRequests = new ArrayList<>();

        loadProducts();

        createHeader();
        createSidebar();
        createPages();

        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(backgroundColor);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(backgroundColor);

        bodyPanel.add(sidebarPanel, BorderLayout.WEST);
        bodyPanel.add(pagesPanel, BorderLayout.CENTER);

        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        dashboardPanel.add(bodyPanel, BorderLayout.CENTER);

        setContentPane(dashboardPanel);

        showPage("Overview");

        setVisible(true);
    }

    // =========================================
    // DATA LOADING
    // =========================================

    void loadProducts() {
        products.clear();
        products.addAll(DataAccessObject.getAllProduct());
    }

    void loadCustomRequest() {
        customRequests.clear();
        customRequests.addAll(DataAccessObject.getAllCustomProduct());
    }

    // =========================================
    // HEADER
    // =========================================

    private void createHeader() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 75));
        headerPanel.setBackground(darkColor);

        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        logoPanel.setOpaque(false);

        JLabel logoIcon = new JLabel("S");
        logoIcon.setFont(new Font("Arial", Font.BOLD, 24));
        logoIcon.setForeground(Color.WHITE);
        logoIcon.setHorizontalAlignment(SwingConstants.CENTER);
        logoIcon.setOpaque(true);
        logoIcon.setBackground(copperColor);
        logoIcon.setPreferredSize(new Dimension(40, 40));

        JPanel logoTextPanel = new JPanel();
        logoTextPanel.setLayout(new BoxLayout(logoTextPanel, BoxLayout.Y_AXIS));
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

        headerPanel.add(logoPanel, BorderLayout.WEST);
    }

    // =========================================
    // SIDEBAR
    // =========================================

    private void createSidebar() {

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(215, 0));
        sidebarPanel.setBackground(darkColor);
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(25, 12, 20, 12));

        addSidebarTitle("WORKSPACE");
        addMenuButton("Dashboard", "Overview");

        addSidebarTitle("MANAGEMENT");
        addMenuButton("My Products", "Products");
        addMenuButton("Custom Requests", "Requests");

        sidebarPanel.add(Box.createVerticalGlue());

        addSidebarTitle("ACCOUNT");

        profileButton = new JButton("Profile");
        styleSidebarButton(profileButton);
        profileButton.addActionListener(e -> showProfileAccount());

        sidebarPanel.add(profileButton);

        logoutButton = new JButton("Log Out");
        styleSidebarLogoutButton(logoutButton);
        logoutButton.addActionListener(e -> logout());

        sidebarPanel.add(Box.createVerticalStrut(8));
        sidebarPanel.add(logoutButton);
    }

    private void addSidebarTitle(String title) {

        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 11));
        sectionLabel.setForeground(new Color(170, 164, 155));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 0));

        sidebarPanel.add(sectionLabel);
    }

    private void addMenuButton(String buttonText, String pageName) {

        JButton menuButton = new JButton(buttonText);

        menuButton.setFont(mainFont);
        menuButton.setForeground(Color.WHITE);
        menuButton.setBackground(darkColor);
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);
        menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        menuButton.setPreferredSize(new Dimension(190, 42));
        menuButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 5));
        menuButton.setFocusPainted(false);
        menuButton.setOpaque(true);
        menuButton.setBorderPainted(false);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuButton.addActionListener(e -> showPage(pageName));

        menuButtons.put(pageName, menuButton);
        sidebarPanel.add(menuButton);
    }

    private void styleSidebarButton(JButton button) {

        button.setFont(mainFont);
        button.setForeground(Color.WHITE);
        button.setBackground(darkColor);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(190, 42));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 5));
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
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 5));
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

            boolean selected = entry.getKey().equals(currentPage);
            JButton menuButton = entry.getValue();

            menuButton.setBackground(selected ? copperColor : darkColor);
            menuButton.setForeground(Color.WHITE);
        }
    }

    // =========================================
    // PAGES
    // =========================================

    private void createPages() {

        cardLayout = new CardLayout();

        pagesPanel = new JPanel(cardLayout);
        pagesPanel.setBackground(backgroundColor);

        createOverviewPage();
        createProductsPage();
        createRequestsPage();
        createProfilePage();

        pagesPanel.add(overviewPage, "Overview");
        pagesPanel.add(productsPage, "Products");
        pagesPanel.add(requestsPage, "Requests");
        pagesPanel.add(profilePage, "Profile");
    }

    public void showPage(String pageName) {

        currentPage = pageName;

        switch (pageName) {

            case "Overview":
                refreshOverview();
                break;

            case "Products":
                refreshProducts();
                break;

            case "Requests":
                loadCustomRequest();
                refreshRequests();
                break;

            case "Profile":
                refreshProfile();
                break;

            default:
                return;
        }

        cardLayout.show(pagesPanel, pageName);
        updateSidebarSelection();
    }

    private JPanel createPageTitle(String title, String description) {

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

    // =========================================
    // OVERVIEW PAGE
    // =========================================

    private void createOverviewPage() {

        overviewPage = new JPanel(new BorderLayout(15, 20));
        overviewPage.setBackground(backgroundColor);
        overviewPage.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));

        JPanel topPanel = new JPanel(new BorderLayout(0, 20));
        topPanel.setBackground(backgroundColor);

        statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBackground(backgroundColor);
        statsPanel.setPreferredSize(new Dimension(0, 110));

        topPanel.add(
                createPageTitle("Business Dashboard", "A quick look at your StoneCraft shop."),
                BorderLayout.NORTH
        );

        topPanel.add(statsPanel, BorderLayout.CENTER);

        recentPanel = new JPanel(new GridLayout(1, 1, 15, 0));
        recentPanel.setBackground(backgroundColor);

        overviewPage.add(topPanel, BorderLayout.NORTH);
        overviewPage.add(recentPanel, BorderLayout.CENTER);
    }

    public void refreshOverview() {

        int lowStock = 0;
        int pending = 0;
        int inProgress = 0;

        for (Product product : products) {
            if (product.getProductQuantity() <= 3) {
                lowStock++;
            }
        }

        for (CustomRequest request : customRequests) {

            if ("Pending".equals(request.getStatus())) {
                pending++;
            }

            if ("In Progress".equals(request.getStatus())) {
                inProgress++;
            }
        }

        statsPanel.removeAll();

        statsPanel.add(createStatCard("Total Products", String.valueOf(products.size())));
        statsPanel.add(createStatCard("Low Stock", String.valueOf(lowStock)));
        statsPanel.add(createStatCard("Pending Requests", String.valueOf(pending)));
        statsPanel.add(createStatCard("In Progress", String.valueOf(inProgress)));

        recentPanel.removeAll();
        recentPanel.add(createRecentRequestsPanel());

        statsPanel.revalidate();
        recentPanel.revalidate();

        statsPanel.repaint();
        recentPanel.repaint();
    }

    private JPanel createStatCard(String title, String value) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(15, 18, 15, 18)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(mainFont);
        titleLabel.setForeground(labelColor);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(copperColor);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(valueLabel);

        return card;
    }

    private JPanel createRecentRequestsPanel() {

        JPanel panel = createWhiteCard();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Recent Custom Requests");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(mainTextColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        int count = 0;

        for (int i = customRequests.size() - 1; i >= 0 && count < 4; i--) {

            CustomRequest request = customRequests.get(i);

            JLabel nameLabel = new JLabel(request.getProductName());
            nameLabel.setFont(boldFont);
            nameLabel.setForeground(mainTextColor);

            JLabel customerLabel = new JLabel(
                    request.getCustomer() + "  |  " + request.getStatus()
            );
            customerLabel.setFont(mainFont);
            customerLabel.setForeground(labelColor);

            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            customerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            panel.add(nameLabel);
            panel.add(Box.createVerticalStrut(4));
            panel.add(customerLabel);
            panel.add(Box.createVerticalStrut(12));

            count++;
        }

        if (count == 0) {
            panel.add(new JLabel("No requests available."));
        }

        return panel;
    }

    // =========================================
    // MY PRODUCTS PAGE
    // =========================================

    private void createProductsPage() {

        productsPage = new JPanel(new BorderLayout(15, 15));
        productsPage.setBackground(backgroundColor);
        productsPage.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);

        topPanel.add(
                createPageTitle("My Products", "Add, edit, and manage the products you sell."),
                BorderLayout.WEST
        );

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(330, 38));

        searchField = createFormField();
        searchField.setToolTipText("Search products");

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, copperColor, Color.WHITE);

        searchButton.addActionListener(e -> searchProducts());
        searchField.addActionListener(e -> searchProducts());

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel searchHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        searchHolder.setBackground(backgroundColor);
        searchHolder.add(searchPanel);

        topPanel.add(searchHolder, BorderLayout.CENTER);

        addButton = new JButton("Add Product");
        styleButton(addButton, copperColor, Color.WHITE);

        // ADD PRODUCT BUTTON ACTION
        addButton.addActionListener(e -> addProduct());

        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        addButtonPanel.setBackground(backgroundColor);
        addButtonPanel.add(addButton);

        topPanel.add(addButtonPanel, BorderLayout.EAST);

        productTableModel = new DefaultTableModel(
                new String[]{"Product Name", "Category", "Price", "Stock", "Stock Status"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(productTableModel);
        styleTable(productTable);

        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader productHeader = productTable.getTableHeader();
        productHeader.setReorderingAllowed(false);
        productHeader.setResizingAllowed(false);

        productTable.setFillsViewportHeight(true);

        TableColumnModel productColumns = productTable.getColumnModel();
        productColumns.getColumn(0).setPreferredWidth(260);
        productColumns.getColumn(1).setPreferredWidth(180);
        productColumns.getColumn(2).setPreferredWidth(130);
        productColumns.getColumn(3).setPreferredWidth(100);
        productColumns.getColumn(4).setPreferredWidth(150);

        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(borderColor));
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableCard = createWhiteCard();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(tableScrollPane, BorderLayout.CENTER);

        JButton editButton = new JButton("Edit Selected");
        JButton deleteButton = new JButton("Delete Selected");

        styleButton(editButton, darkColor, Color.WHITE);
        styleButton(deleteButton, deleteColor, Color.WHITE);

        editButton.addActionListener(e -> editProduct());
        deleteButton.addActionListener(e -> deleteProduct());

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(backgroundColor);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        productsPage.add(topPanel, BorderLayout.NORTH);
        productsPage.add(tableCard, BorderLayout.CENTER);
        productsPage.add(actionPanel, BorderLayout.SOUTH);
    }

    private void refreshProducts() {

        if (productTableModel == null) {
            return;
        }

        String keyword = "";

        if (searchField != null) {
            keyword = searchField.getText().trim().toLowerCase();
        }

        productTableModel.setRowCount(0);
        visibleProducts.clear();

        for (Product product : products) {

            boolean matchesSearch =
                    product.getProductName().toLowerCase().contains(keyword)
                            || product.getProductCategory().toLowerCase().contains(keyword);

            if (matchesSearch) {

                visibleProducts.add(product);

                String stockStatus =
                        product.getProductQuantity() <= 3 ? "Low Stock" : "Available";

                productTableModel.addRow(new Object[]{
                        product.getProductName(),
                        product.getProductCategory(),
                        "\u20B1" + String.format("%.2f", product.getProductPrice()),
                        product.getProductQuantity(),
                        stockStatus
                });
            }
        }

        productTable.clearSelection();
    }

    private void searchProducts() {
        showPage("Products");
    }

    private Product getSelectedProduct() {

        int selectedRow = productTable.getSelectedRow();

        if (selectedRow < 0) {
            return null;
        }

        int modelRow = productTable.convertRowIndexToModel(selectedRow);

        if (modelRow < 0 || modelRow >= visibleProducts.size()) {
            return null;
        }

        return visibleProducts.get(modelRow);
    }

    public void addProduct() {

        Product newProduct = showProductDialog(null);

        if (newProduct != null) {

            int newId = DataAccessObject.insertProduct(newProduct);

            if (newId <= 0) {
                showDatabaseError("Failed to save the product to the database.");
                return;
            }

            newProduct.setProductId(newId);
            products.add(newProduct);

            searchField.setText("");

            refreshProducts();
            refreshOverview();

            int row = visibleProducts.indexOf(newProduct);

            if (row >= 0) {
                productTable.setRowSelectionInterval(row, row);
                productTable.scrollRectToVisible(productTable.getCellRect(row, 0, true));
            }
        }
    }

    private void editProduct() {

        Product selectedProduct = getSelectedProduct();

        if (selectedProduct == null) {
            showWarning("Please select a product to edit.");
            return;
        }

        Product editedProduct = showProductDialog(selectedProduct);

        if (editedProduct != null) {

            selectedProduct.setProductName(editedProduct.getProductName());
            selectedProduct.setProductCategory(editedProduct.getProductCategory());
            selectedProduct.setProductPrice(editedProduct.getProductPrice());
            selectedProduct.setProductQuantity(editedProduct.getProductQuantity());

            int updated = DataAccessObject.editProduct(selectedProduct);

            if (updated <= 0) {
                showDatabaseError("Failed to update the product in the database.");
                loadProducts();
            }

            refreshProducts();
            refreshOverview();
        }
    }

    private void deleteProduct() {

        Product selectedProduct = getSelectedProduct();

        if (selectedProduct == null) {
            showWarning("Please select a product to delete.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete \""
                        + selectedProduct.getProductName() + "\"?",
                "Delete Product",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {

            if (DataAccessObject.deleteProduct(selectedProduct.getProductId()) <= 0) {
                showDatabaseError("Failed to delete the product from the database.");
                return;
            }

            products.remove(selectedProduct);

            refreshProducts();
            refreshOverview();
        }
    }

    private Product showProductDialog(Product existingProduct) {

        nameField = createFormField();
        priceField = createFormField();
        stockField = createFormField();

        categoryBox = new JComboBox<>(CATEGORIES);
        styleComboBox(categoryBox);

        if (existingProduct != null) {
            nameField.setText(existingProduct.getProductName());
            categoryBox.setSelectedItem(existingProduct.getProductCategory());
            priceField.setText(String.valueOf(existingProduct.getProductPrice()));
            stockField.setText(String.valueOf(existingProduct.getProductQuantity()));
        }

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(340, 290));

        addFormRow(formPanel, "Product Name", nameField);
        addFormRow(formPanel, "Category", categoryBox);
        addFormRow(formPanel, "Price (\u20B1)", priceField);
        addFormRow(formPanel, "Stock", stockField);

        while (true) {

            int result = JOptionPane.showConfirmDialog(
                    this,
                    formPanel,
                    existingProduct == null ? "Add Product" : "Edit Product",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return null;
            }

            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                showWarning("Please enter a product name.");
                continue;
            }

            double price;
            int stock;

            try {
                price = Double.parseDouble(priceField.getText().trim());
                stock = Integer.parseInt(stockField.getText().trim());
            } catch (NumberFormatException ex) {
                showWarning("Price and stock must be valid numbers.");
                continue;
            }

            if (price <= 0 || stock < 0) {
                showWarning("Price must be above 0 and stock cannot be negative.");
                continue;
            }

            return new Product(
                    name,
                    (String) categoryBox.getSelectedItem(),
                    price,
                    stock
            );
        }
    }

    // =========================================
    // CUSTOM REQUESTS PAGE
    // =========================================

    private void createRequestsPage() {

        requestsPage = new JPanel(new BorderLayout(15, 15));
        requestsPage.setBackground(backgroundColor);
        requestsPage.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));

        JPanel topPanel = new JPanel(new BorderLayout(10, 15));
        topPanel.setBackground(backgroundColor);

        topPanel.add(
                createPageTitle("Custom Requests", "Review and update customer sculpture requests."),
                BorderLayout.NORTH
        );

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(backgroundColor);

        JLabel filterLabel = new JLabel("Customer Requests");
        filterLabel.setFont(boldFont);
        filterLabel.setForeground(mainTextColor);

        String[] filterOptions = new String[STATUSES.length + 1];
        filterOptions[0] = "All Statuses";
        System.arraycopy(STATUSES, 0, filterOptions, 1, STATUSES.length);

        statusFilterBox = new JComboBox<>(filterOptions);
        styleComboBox(statusFilterBox);
        statusFilterBox.addActionListener(e -> refreshRequests());

        filterPanel.add(filterLabel, BorderLayout.WEST);
        filterPanel.add(statusFilterBox, BorderLayout.EAST);

        topPanel.add(filterPanel, BorderLayout.SOUTH);

        requestsListPanel = new JPanel();
        requestsListPanel.setLayout(new BoxLayout(requestsListPanel, BoxLayout.Y_AXIS));
        requestsListPanel.setBackground(backgroundColor);

        JScrollPane requestScrollPane = new JScrollPane(requestsListPanel);
        requestScrollPane.setBorder(null);
        requestScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        requestsPage.add(topPanel, BorderLayout.NORTH);
        requestsPage.add(requestScrollPane, BorderLayout.CENTER);
    }

    public void refreshRequests() {

        if (requestsListPanel == null) {
            return;
        }

        String filter = (String) statusFilterBox.getSelectedItem();

        requestsListPanel.removeAll();

        boolean hasRequests = false;

        for (CustomRequest request : customRequests) {

            if ("All Statuses".equals(filter)
                    || request.getStatus().equals(filter)) {

                JPanel requestCard = createRequestCard(request);
                requestCard.setAlignmentX(Component.LEFT_ALIGNMENT);

                requestsListPanel.add(requestCard);
                requestsListPanel.add(Box.createVerticalStrut(12));

                hasRequests = true;
            }
        }

        if (!hasRequests) {

            JLabel emptyLabel = new JLabel("No custom requests found.");
            emptyLabel.setFont(mainFont);
            emptyLabel.setForeground(labelColor);

            requestsListPanel.add(Box.createVerticalStrut(30));
            requestsListPanel.add(emptyLabel);
        }

        requestsListPanel.revalidate();
        requestsListPanel.repaint();
    }

    private JPanel createRequestCard(CustomRequest request) {

        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                )
        );

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(request.getProductName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(mainTextColor);

        JLabel customerLabel = new JLabel("Customer: " + request.getCustomerName());
        JLabel sizeLabel = new JLabel("Preferred Size: " + request.getProductSize());
        JLabel designLabel = new JLabel("Design: " + request.getProductPreferredDesign());
        JLabel statusLabel = new JLabel("Status: " + request.getStatus());

        JLabel[] detailLabels = {customerLabel, sizeLabel, designLabel};

        for (JLabel label : detailLabels) {
            label.setFont(mainFont);
            label.setForeground(labelColor);
        }

        statusLabel.setFont(boldFont);
        statusLabel.setForeground(copperColor);

        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(7));

        for (JLabel label : detailLabels) {
            detailsPanel.add(label);
        }

        detailsPanel.add(Box.createVerticalStrut(7));
        detailsPanel.add(statusLabel);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBackground(Color.WHITE);

        JComboBox<String> requestStatusBox = new JComboBox<>(STATUSES);
        styleComboBox(requestStatusBox);
        requestStatusBox.setSelectedItem(request.getStatus());
        requestStatusBox.setMaximumSize(new Dimension(150, 34));

        JButton updateButton = new JButton("Update Status");

        styleButton(updateButton, copperColor, Color.WHITE);
        updateButton.setMaximumSize(new Dimension(150, 36));

        JButton viewButton = new JButton("View Details");
        styleButton(viewButton, darkColor, Color.WHITE);
        viewButton.setMaximumSize(new Dimension(150, 36));

        updateButton.addActionListener(e -> {
            String newStatus = (String) requestStatusBox.getSelectedItem();

            request.setStatus(newStatus);
            DataAccessObject.updateRequestStatus(request, newStatus);

            refreshRequests();
            refreshOverview();
        });

        viewButton.addActionListener(e -> showRequestDetails(request));

        actionPanel.add(requestStatusBox);
        actionPanel.add(Box.createVerticalStrut(8));
        actionPanel.add(updateButton);
        actionPanel.add(Box.createVerticalStrut(8));
        actionPanel.add(viewButton);

        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.EAST);

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        return card;
    }

    private void showRequestDetails(CustomRequest request) {

        String details =
                "Customer: " + request.getCustomer()
                        + "\n\nSculpture Name: " + request.getProductName()
                        + "\n\nDescription: " + request.getProductDescription()
                        + "\n\nPreferred Size: " + request.getProductSize()
                        + "\n\nDesign: " + request.getProductPreferredDesign()
                        + "\n\nStatus: " + request.getStatus();

        JOptionPane.showMessageDialog(
                this,
                details,
                "Custom Request Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================
    // PROFILE PAGE
    // =========================================

    private void createProfilePage() {

        profilePage = new JPanel(new BorderLayout(20, 20));
        profilePage.setBackground(backgroundColor);
        profilePage.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        JPanel topPanel = new JPanel(new BorderLayout(0, 20));
        topPanel.setOpaque(false);

        topPanel.add(
                createPageTitle(
                        "My Profile",
                        "Manage your personal and StoneCraft business information."
                ),
                BorderLayout.NORTH
        );

        profileCardsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        profileCardsPanel.setOpaque(false);
        profileCardsPanel.setPreferredSize(new Dimension(0, 105));

        topPanel.add(profileCardsPanel, BorderLayout.CENTER);

        profileDetailsPanel = new JPanel(new GridLayout(1, 1));
        profileDetailsPanel.setOpaque(false);

        profilePage.add(topPanel, BorderLayout.NORTH);
        profilePage.add(profileDetailsPanel, BorderLayout.CENTER);
    }

    public void refreshProfile() {

        profileCardsPanel.removeAll();

        profileCardsPanel.add(createProfileCard("FULL NAME", profileName));
        profileCardsPanel.add(createProfileCard("PHONE NUMBER", profilePhone));
        profileCardsPanel.add(createProfileCard("EMAIL ADDRESS", profileEmail));

        profileDetailsPanel.removeAll();
        profileDetailsPanel.add(createProfileDetailsPanel());

        profileCardsPanel.revalidate();
        profileDetailsPanel.revalidate();

        profileCardsPanel.repaint();
        profileDetailsPanel.repaint();
    }

    private JPanel createProfileCard(String title, String value) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(18, 20, 18, 20)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        titleLabel.setForeground(labelColor);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 17));
        valueLabel.setForeground(mainTextColor);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(valueLabel);

        return card;
    }

    private JPanel createProfileDetailsPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
        mainPanel.setOpaque(false);

        // LEFT SIDE - ACCOUNT OVERVIEW

        JPanel accountPanel = createWhiteCard();
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
        accountPanel.setPreferredSize(new Dimension(280, 0));

        JLabel accountHeading = new JLabel("ACCOUNT OVERVIEW");
        accountHeading.setFont(new Font("Arial", Font.BOLD, 11));
        accountHeading.setForeground(labelColor);
        accountHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel accountName = new JLabel(profileName);
        accountName.setFont(new Font("Arial", Font.BOLD, 21));
        accountName.setForeground(mainTextColor);
        accountName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel accountType = new JLabel("Business Account");
        accountType.setFont(mainFont);
        accountType.setForeground(copperColor);
        accountType.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel shopBox = new JPanel();
        shopBox.setLayout(new BoxLayout(shopBox, BoxLayout.Y_AXIS));
        shopBox.setBackground(fieldColor);
        shopBox.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        shopBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        shopBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel shopLabel = new JLabel("SHOP NAME");
        shopLabel.setFont(new Font("Arial", Font.BOLD, 10));
        shopLabel.setForeground(labelColor);

        JLabel shopName = new JLabel("StoneCraft");
        shopName.setFont(new Font("Arial", Font.BOLD, 17));
        shopName.setForeground(mainTextColor);

        shopBox.add(shopLabel);
        shopBox.add(Box.createVerticalStrut(8));
        shopBox.add(shopName);

        JLabel productCount = new JLabel("Products Listed");
        productCount.setFont(mainFont);
        productCount.setForeground(labelColor);
        productCount.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel productValue = new JLabel(String.valueOf(products.size()));
        productValue.setFont(new Font("Arial", Font.BOLD, 25));
        productValue.setForeground(copperColor);
        productValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel requestCount = new JLabel("Custom Requests");
        requestCount.setFont(mainFont);
        requestCount.setForeground(labelColor);
        requestCount.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel requestValue = new JLabel(String.valueOf(customRequests.size()));
        requestValue.setFont(new Font("Arial", Font.BOLD, 25));
        requestValue.setForeground(copperColor);
        requestValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        accountPanel.add(accountHeading);
        accountPanel.add(Box.createVerticalStrut(22));
        accountPanel.add(accountName);
        accountPanel.add(Box.createVerticalStrut(6));
        accountPanel.add(accountType);
        accountPanel.add(Box.createVerticalStrut(25));
        accountPanel.add(shopBox);
        accountPanel.add(Box.createVerticalStrut(25));
        accountPanel.add(productCount);
        accountPanel.add(Box.createVerticalStrut(4));
        accountPanel.add(productValue);
        accountPanel.add(Box.createVerticalStrut(15));
        accountPanel.add(requestCount);
        accountPanel.add(Box.createVerticalStrut(4));
        accountPanel.add(requestValue);
        accountPanel.add(Box.createVerticalGlue());

        // RIGHT SIDE - PERSONAL INFORMATION

        JPanel informationPanel = createWhiteCard();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));

        JLabel informationTitle = new JLabel("Personal Information");
        informationTitle.setFont(new Font("Arial", Font.BOLD, 18));
        informationTitle.setForeground(mainTextColor);
        informationTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel informationDescription = new JLabel(
                "Your registered contact and account details."
        );
        informationDescription.setFont(mainFont);
        informationDescription.setForeground(labelColor);
        informationDescription.setAlignmentX(Component.LEFT_ALIGNMENT);

        informationPanel.add(informationTitle);
        informationPanel.add(Box.createVerticalStrut(5));
        informationPanel.add(informationDescription);
        informationPanel.add(Box.createVerticalStrut(20));

        informationPanel.add(createProfileRow("Full Name", profileName));
        informationPanel.add(createProfileRow("Phone Number", profilePhone));
        informationPanel.add(createProfileRow("Email Address", profileEmail));
        informationPanel.add(createProfileRow("Account Type", "Business Account"));

        informationPanel.add(Box.createVerticalStrut(25));

        JButton editProfileButton = new JButton("Edit Profile");
        styleButton(editProfileButton, copperColor, Color.WHITE);

        editProfileButton.setPreferredSize(new Dimension(130, 40));
        editProfileButton.setMaximumSize(new Dimension(150, 40));
        editProfileButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        editProfileButton.addActionListener(e -> editProfile());

        informationPanel.add(editProfileButton);
        informationPanel.add(Box.createVerticalGlue());

        mainPanel.add(accountPanel, BorderLayout.WEST);
        mainPanel.add(informationPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    // =========================================
    // EDIT PROFILE - WITHOUT EMAIL FIELD
    // =========================================

    private void editProfile() {

        dialog = new JDialog(this, "Edit Profile", true);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(500, 370);
        dialog.setMinimumSize(new Dimension(500, 370));
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        // HEADER

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

        // FORM FIELDS

        editNameField = createFormField();
        editPhoneField = createFormField();

        editNameField.setText("Not set".equals(profileName) ? "" : profileName);
        editPhoneField.setText("Not set".equals(profilePhone) ? "" : profilePhone);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 5, 0);

        JLabel nameLabel = createFormLabel("Full Name");
        JLabel phoneLabel = createFormLabel("Phone Number");

        editNameField.setPreferredSize(new Dimension(420, 40));
        editPhoneField.setPreferredSize(new Dimension(420, 40));

        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        formPanel.add(editNameField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        formPanel.add(phoneLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        formPanel.add(editPhoneField, gbc);

        // FOOTER BUTTONS

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");

        styleButton(cancelButton, new Color(235, 233, 229), mainTextColor);
        styleButton(saveButton, copperColor, Color.WHITE);

        cancelButton.setPreferredSize(new Dimension(105, 40));
        saveButton.setPreferredSize(new Dimension(135, 40));

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        // ADD COMPONENTS

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);

        // CANCEL BUTTON

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.getRootPane().setDefaultButton(saveButton);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JPanel createProfileRow(String labelText, String valueText) {

        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        row.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(1, 0, 0, 0, borderColor),
                        BorderFactory.createEmptyBorder(13, 0, 13, 0)
                )
        );

        JLabel label = new JLabel(labelText);
        label.setFont(mainFont);
        label.setForeground(labelColor);
        label.setPreferredSize(new Dimension(160, 20));

        JLabel value = new JLabel(valueText);
        value.setFont(boldFont);
        value.setForeground(mainTextColor);

        row.add(label, BorderLayout.WEST);
        row.add(value, BorderLayout.CENTER);

        return row;
    }

    // =========================================
    // PROFILE MENU
    // =========================================

    public void showProfile() {
        showPage("Profile");
    }

    public void showProfileAccount() {
        showPage("Profile");
    }

    // =========================================
    // LOG OUT
    // =========================================

    public void logout() {

        choice = JOptionPane.showConfirmDialog(
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

    // =========================================
    // DESIGN HELPERS
    // =========================================

    private JPanel createWhiteCard() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(18, 18, 18, 18)
                )
        );

        return panel;
    }

    private void styleTable(JTable table) {

        table.setFont(mainFont);
        table.setRowHeight(40);
        table.setForeground(mainTextColor);
        table.setBackground(Color.WHITE);
        table.setGridColor(borderColor);
        table.setSelectionBackground(selectionColor);
        table.setSelectionForeground(mainTextColor);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(boldFont);
        tableHeader.setBackground(darkColor);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(0, 42));
        tableHeader.setReorderingAllowed(false);
    }

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

        return textField;
    }

    private void addFormRow(JPanel panel, String labelText, JComponent field) {

        JLabel label = createFormLabel(labelText);

        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setPreferredSize(new Dimension(300, 36));
        field.setMinimumSize(new Dimension(100, 36));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        panel.add(label);
        panel.add(Box.createVerticalStrut(4));
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    private JLabel createFormLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(boldFont);
        label.setForeground(mainTextColor);

        return label;
    }

    private void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setFont(mainFont);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(mainTextColor);
    }

    private void styleButton(JButton button, Color background, Color foreground) {

        button.setFont(boldFont);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(9, 15, 9, 15));

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (background.equals(copperColor)) {
                    button.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(background);
            }
        });
    }

    private void showDatabaseError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void showWarning(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
        );
    }
}