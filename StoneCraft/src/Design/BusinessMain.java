package Design;

import DataBaseManagementConnection.DataAccessObject;
import Main.ChatMessage;
import Main.CustomRequest;
import Main.Product;

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

    public DefaultListModel<String> customerListModel;
    public JList<String> customerList;

    public JButton addButton;

    public JTextField nameField, priceField, stockField;
    public JComboBox<String> categoryBox;

    public JMenuItem accountItem, logoutItem;

    public int choice;

    public JPanel chatPanel;
    public JScrollPane chatScrollPane;

    public JTextField messageField;
    public JLabel chatTitle;

    public String selectedCustomer = null;

    public Map<String, ArrayList<ChatMessage>> conversations = new LinkedHashMap<>();

    public ArrayList<Product> products;
    public ArrayList<CustomRequest> customRequests;

    // =========================================
    // PAGES
    // =========================================

    private JPanel overviewPage;
    private JPanel productsPage;
    private JPanel requestsPage;
    private JPanel messagesPage;

    // =========================================
    // SIDEBAR
    // =========================================

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
    private JComboBox<String> statusFilterBox;

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

    // =========================================
    // HEADER
    // =========================================

    private void createHeader() {

        headerPanel = new JPanel(new BorderLayout(20, 0));
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

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(330, 36));

        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        accountPanel.setOpaque(false);

        profileButton = new JButton("Profile");
        styleButton(profileButton, Color.WHITE, darkColor);
        profileButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));


        accountPanel.add(profileButton);

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(accountPanel, BorderLayout.EAST);
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
        addMenuButton("Messages", "Messages");

        sidebarPanel.add(Box.createVerticalGlue());
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

        menuButton.addActionListener(e -> {
            selectMenuButton(menuButton);
            showPage(pageName);
        });

        menuButtons.put(pageName, menuButton);
        sidebarPanel.add(menuButton);

        if (pageName.equals("Overview")) {
            selectMenuButton(menuButton);
        }
    }

    private void selectMenuButton(JButton button) {

        if (selectedMenuButton != null) {
            selectedMenuButton.setBackground(darkColor);
            selectedMenuButton.setForeground(Color.WHITE);
        }

        selectedMenuButton = button;
        selectedMenuButton.setBackground(copperColor);
        selectedMenuButton.setForeground(Color.WHITE);
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
        createMessagesPage();

        pagesPanel.add(overviewPage, "Overview");
        pagesPanel.add(productsPage, "Products");
        pagesPanel.add(requestsPage, "Requests");
        pagesPanel.add(messagesPage, "Messages");
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
                refreshRequests();
                break;

            case "Messages":
                refreshCustomerList();
                refreshChatMessages();
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
        panel.setBackground(Color.WHITE);

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

        recentPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        recentPanel.setBackground(backgroundColor);

        overviewPage.add(topPanel, BorderLayout.NORTH);
        overviewPage.add(recentPanel, BorderLayout.CENTER);
    }

    private void refreshOverview() {

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
        recentPanel.add(createRecentMessagesPanel());

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
                    request.getCustomerName() + "  |  " + request.getStatus()
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

    private JPanel createRecentMessagesPanel() {

        JPanel panel = createWhiteCard();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Recent Messages");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(mainTextColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        int count = 0;

        for (String customer : conversations.keySet()) {

            ArrayList<ChatMessage> messages = conversations.get(customer);

            String preview = "No messages yet.";

            if (!messages.isEmpty()) {
                preview = messages.get(messages.size() - 1).getMessage();
            }

            JLabel customerLabel = new JLabel(customer);
            customerLabel.setFont(boldFont);
            customerLabel.setForeground(mainTextColor);
            customerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel previewLabel = new JLabel(shortenText(preview, 35));
            previewLabel.setFont(mainFont);
            previewLabel.setForeground(labelColor);
            previewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            panel.add(customerLabel);
            panel.add(Box.createVerticalStrut(4));
            panel.add(previewLabel);
            panel.add(Box.createVerticalStrut(12));

            count++;

            if (count >= 4) {
                break;
            }
        }

        if (count == 0) {
            panel.add(new JLabel("No messages yet."));
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
        styleButton(deleteButton, fieldColor, mainTextColor);
        deleteButton.setBorder(BorderFactory.createLineBorder(borderColor));

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
                loadProducts();   // restore the list from the database
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


        //sa adding button wag na sa main kasi di complited
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

    private void refreshRequests() {

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

        JLabel budgetLabel = new JLabel(
                "Budget: \u20B1" + String.format("%.2f", request.getProductPrice())
        );

        JLabel statusLabel = new JLabel("Status: " + request.getStatus());

        JLabel[] detailLabels = {customerLabel, sizeLabel, designLabel};

        for (JLabel label : detailLabels) {
            label.setFont(mainFont);
            label.setForeground(labelColor);
        }

        budgetLabel.setFont(mainFont);
        budgetLabel.setForeground(copperColor);

        statusLabel.setFont(boldFont);
        statusLabel.setForeground(copperColor);

        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(7));

        for (JLabel label : detailLabels) {
            detailsPanel.add(label);
        }

        detailsPanel.add(budgetLabel);
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

            request.setStatus((String) requestStatusBox.getSelectedItem());

            // TODO: save to database, e.g. DataAccessObject.updateRequestStatus(request);

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
                "Customer: " + request.getCustomerName()
                        + "\n\nSculpture Name: " + request.getProductName()
                        + "\n\nDescription: " + request.getProductDescription()
                        + "\n\nPreferred Size: " + request.getProductSize()
                        + "\n\nDesign: " + request.getProductPreferredDesign()
                        + "\n\nBudget: \u20B1" + String.format("%.2f", request.getProductPrice())
                        + "\n\nStatus: " + request.getStatus();

        JOptionPane.showMessageDialog(
                this,
                details,
                "Custom Request Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================
    // MESSAGES PAGE
    // =========================================

    private void createMessagesPage() {

        messagesPage = new JPanel(new BorderLayout(15, 15));
        messagesPage.setBackground(backgroundColor);
        messagesPage.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));

        messagesPage.add(
                createPageTitle("Messages", "Chat with your customers and manage conversations."),
                BorderLayout.NORTH
        );

        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);

        customerList.setFont(mainFont);
        customerList.setForeground(mainTextColor);
        customerList.setSelectionBackground(selectionColor);
        customerList.setSelectionForeground(mainTextColor);
        customerList.setFixedCellHeight(55);
        customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerList.setCellRenderer(new CustomerListRenderer());

        customerList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedCustomer = customerList.getSelectedValue();
                refreshChatMessages();
            }
        });

        JScrollPane listScrollPane = new JScrollPane(customerList);
        listScrollPane.setBorder(BorderFactory.createLineBorder(borderColor));
        listScrollPane.setPreferredSize(new Dimension(230, 0));

        JPanel chatSide = new JPanel(new BorderLayout());
        chatSide.setBackground(Color.WHITE);
        chatSide.setBorder(BorderFactory.createLineBorder(borderColor));

        JPanel chatHeader = new JPanel(new BorderLayout());
        chatHeader.setBackground(darkColor);
        chatHeader.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        chatTitle = new JLabel("Select a customer");
        chatTitle.setFont(new Font("Arial", Font.BOLD, 16));
        chatTitle.setForeground(Color.WHITE);

        chatHeader.add(chatTitle, BorderLayout.WEST);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(backgroundColor);
        chatPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        chatScrollPane = new JScrollPane(chatPanel);
        chatScrollPane.setBorder(null);
        chatScrollPane.getVerticalScrollBar().setUnitIncrement(15);

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        messageField = createFormField();

        JButton sendButton = new JButton("Send");
        styleButton(sendButton, copperColor, Color.WHITE);

        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatSide.add(chatHeader, BorderLayout.NORTH);
        chatSide.add(chatScrollPane, BorderLayout.CENTER);
        chatSide.add(inputPanel, BorderLayout.SOUTH);

        JPanel bodyPanel = new JPanel(new BorderLayout(15, 0));
        bodyPanel.setBackground(backgroundColor);

        bodyPanel.add(listScrollPane, BorderLayout.WEST);
        bodyPanel.add(chatSide, BorderLayout.CENTER);

        messagesPage.add(bodyPanel, BorderLayout.CENTER);
    }

    // =========================================
    // CUSTOMER LIST RENDERER
    // =========================================

    private class CustomerListRenderer implements ListCellRenderer<String> {

        @Override
        public Component getListCellRendererComponent(
                JList<? extends String> list,
                String customer,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            panel.setBackground(isSelected ? selectionColor : Color.WHITE);

            JLabel nameLabel = new JLabel(customer);
            nameLabel.setFont(mainFont);
            nameLabel.setForeground(mainTextColor);

            panel.add(nameLabel, BorderLayout.CENTER);

            return panel;
        }
    }

    // =========================================
    // MESSAGE METHODS
    // =========================================

    public void refreshCustomerList() {

        if (customerListModel == null) {
            return;
        }

        String previousCustomer = selectedCustomer;

        customerListModel.clear();

        for (String customer : conversations.keySet()) {
            customerListModel.addElement(customer);
        }

        if (previousCustomer != null
                && conversations.containsKey(previousCustomer)) {

            customerList.setSelectedValue(previousCustomer, true);

        } else if (!customerListModel.isEmpty()) {

            customerList.setSelectedIndex(0);

        } else {

            selectedCustomer = null;
        }

        customerList.repaint();
    }

    private void sendMessage() {

        if (selectedCustomer == null) {
            showWarning("Please select a customer first.");
            return;
        }

        String message = messageField.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        ArrayList<ChatMessage> messages = conversations.get(selectedCustomer);

        if (messages == null) {
            return;
        }

        messages.add(new ChatMessage(message, "Business"));

        messageField.setText("");

        refreshChatMessages();
    }

    public void receiveCustomerMessage(String customerName, String message) {

        if (customerName == null || customerName.trim().isEmpty()
                || message == null || message.trim().isEmpty()) {
            return;
        }

        String name = customerName.trim();

        conversations.putIfAbsent(name, new ArrayList<>());

        conversations.get(name).add(new ChatMessage(message.trim(), "Customer"));

        refreshCustomerList();

        if (name.equals(selectedCustomer) && currentPage.equals("Messages")) {
            refreshChatMessages();
        }

        if (currentPage.equals("Overview")) {
            refreshOverview();
        }
    }

    private void refreshChatMessages() {

        if (chatPanel == null) {
            return;
        }

        chatPanel.removeAll();

        if (selectedCustomer == null) {

            chatTitle.setText("Select a customer");

            JLabel hintLabel = new JLabel(
                    "Choose a customer on the left to see the conversation."
            );
            hintLabel.setFont(mainFont);
            hintLabel.setForeground(labelColor);
            hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            chatPanel.add(Box.createVerticalStrut(30));
            chatPanel.add(hintLabel);

        } else {

            chatTitle.setText(selectedCustomer);

            ArrayList<ChatMessage> messages = conversations.get(selectedCustomer);

            if (messages == null || messages.isEmpty()) {

                JLabel emptyLabel = new JLabel("No messages yet.");
                emptyLabel.setFont(mainFont);
                emptyLabel.setForeground(labelColor);
                emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                chatPanel.add(Box.createVerticalStrut(30));
                chatPanel.add(emptyLabel);

            } else {

                for (ChatMessage chatMessage : messages) {
                    chatPanel.add(createMessageBubble(chatMessage));
                    chatPanel.add(Box.createVerticalStrut(8));
                }
            }
        }

        chatPanel.revalidate();
        chatPanel.repaint();

        SwingUtilities.invokeLater(() -> {

            if (chatScrollPane != null) {
                JScrollBar scrollBar = chatScrollPane.getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getMaximum());
            }
        });
    }

    // =========================================
    // MESSAGE BUBBLES
    // =========================================

    private JPanel createMessageBubble(ChatMessage chatMessage) {

        boolean fromBusiness = "Business".equals(chatMessage.getSender());

        JPanel rowPanel = new JPanel(
                new FlowLayout(fromBusiness ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 0)
        );

        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bubblePanel = new JPanel();
        bubblePanel.setLayout(new BoxLayout(bubblePanel, BoxLayout.Y_AXIS));

        bubblePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(7, 9, 7, 9)
                )
        );

        JLabel senderLabel = new JLabel(fromBusiness ? "You" : selectedCustomer);
        senderLabel.setFont(new Font("Arial", Font.BOLD, 10));

        JLabel messageLabel = new JLabel(
                "<html><div style='width:155px;'>"
                        + escapeHtml(chatMessage.getMessage())
                        + "</div></html>"
        );

        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(mainTextColor);

        if (fromBusiness) {
            bubblePanel.setBackground(selectionColor);
            senderLabel.setForeground(copperColor);
        } else {
            bubblePanel.setBackground(Color.WHITE);
            senderLabel.setForeground(darkColor);
        }

        bubblePanel.add(senderLabel);
        bubblePanel.add(Box.createVerticalStrut(3));
        bubblePanel.add(messageLabel);

        bubblePanel.setMaximumSize(new Dimension(180, Integer.MAX_VALUE));

        rowPanel.add(bubblePanel);

        return rowPanel;
    }

    private String escapeHtml(String text) {

        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
    }

    // =========================================
    // PROFILE MENU
    // =========================================

    public void showProfile() {

        JPopupMenu profileMenu = new JPopupMenu();

        accountItem = new JMenuItem("Business Account");
        logoutItem = new JMenuItem("Log Out");





        profileMenu.add(accountItem);
        profileMenu.addSeparator();
        profileMenu.add(logoutItem);

        profileMenu.show(profileButton, 0, profileButton.getHeight());
    }

    public void showProfileAccount(){

    }

    public void logout() {

        choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Log Out",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );


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

    private void showWarning(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private String shortenText(String text, int maxLength) {

        if (text == null) {
            return "";
        }

        if (text.length() <= maxLength) {
            return text;
        }

        return text.substring(0, maxLength) + "...";
    }
}