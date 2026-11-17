package Design;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusinessMain extends MainDesign {

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

    private static final String[] STATUSES = {
            "Pending", "Accepted", "In Progress", "Completed", "Declined"
    };

    private static final String[] CATEGORIES = {
            "Home Decor", "Garden", "Kitchen", "Sculptures"
    };

    // MAIN PANELS
    private JPanel dashboardPanel;
    private JPanel headerPanel;
    private JPanel sidebarPanel;
    private JPanel pagesPanel;
    private CardLayout cardLayout;

    // HEADER COMPONENTS
    private JTextField searchField;
    private JButton profileButton;

    // PAGES
    private JPanel overviewPage;
    private JPanel productsPage;
    private JPanel requestsPage;
    private JPanel messagesPage;

    // OVERVIEW
    private JPanel statsPanel;
    private JPanel recentPanel;

    // PRODUCTS PAGE
    private DefaultTableModel tableModel;
    private JTable productTable;
    private ArrayList<Product> visibleProducts = new ArrayList<>();

    // REQUESTS PAGE
    private JPanel requestsListPanel;
    private JComboBox<String> statusFilterBox;

    // MESSAGES PAGE
    private DefaultListModel<String> customerListModel;
    private JList<String> customerList;
    private JPanel chatPanel;
    private JScrollPane chatScrollPane;
    private JTextField messageField;
    private JLabel chatTitle;
    private String selectedCustomer = null;

    // DATA
    private ArrayList<Product> products;
    private ArrayList<CustomRequest> customRequests;
    private Map<String, ArrayList<ChatMessage>> conversations;

    public BusinessMain() {

        setTitle("StoneCraft - Business Main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // MAXIMIZED WINDOW
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        products = new ArrayList<>();
        customRequests = new ArrayList<>();
        conversations = new LinkedHashMap<>();

        addSampleData();

        createHeader();
        createSidebar();
        createPages();

        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(Color.WHITE);

        bodyPanel.add(sidebarPanel, BorderLayout.WEST);
        bodyPanel.add(pagesPanel, BorderLayout.CENTER);

        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        dashboardPanel.add(bodyPanel, BorderLayout.CENTER);

        setContentPane(dashboardPanel);

        showPage("Overview");

        setVisible(true);
    }

    // =================================
    // SAMPLE DATA
    // =================================

    private void addSampleData() {

        products.add(new Product("Stone Vase", "Home Decor", 850.00, 12));
        products.add(new Product("Stone Candle Holder", "Home Decor", 450.00, 20));
        products.add(new Product("Garden Planter", "Garden", 1200.00, 8));
        products.add(new Product("Stone Bird Bath", "Garden", 1800.00, 2));
        products.add(new Product("Stone Mortar and Pestle", "Kitchen", 750.00, 15));
        products.add(new Product("Stone Coaster Set", "Kitchen", 350.00, 3));
        products.add(new Product("Stone Sculpture", "Sculptures", 2500.00, 4));
        products.add(new Product("Traditional Stone Carving", "Sculptures", 4200.00, 1));

        customRequests.add(new CustomRequest(
                "Maria Santos", "Family Crest Plaque",
                "A carved plaque showing our family crest for the front gate.",
                "Medium", "Traditional", 6000.00, ""
        ));

        CustomRequest second = new CustomRequest(
                "Juan Dela Cruz", "Eagle Statue",
                "A standing eagle with spread wings for the garden.",
                "Large", "Realistic", 12000.00, ""
        );
        second.status = "In Progress";
        customRequests.add(second);

        customRequests.add(new CustomRequest(
                "Ana Reyes", "Abstract Wave Sculpture",
                "Smooth flowing wave design for a living room shelf.",
                "Small", "Abstract", 3500.00, ""
        ));

        ArrayList<ChatMessage> mariaChat = new ArrayList<>();
        mariaChat.add(new ChatMessage("Hi! Can the family crest plaque be done in two weeks?", "Customer"));
        conversations.put("Maria Santos", mariaChat);

        ArrayList<ChatMessage> juanChat = new ArrayList<>();
        juanChat.add(new ChatMessage("Hello, any update on the eagle statue?", "Customer"));
        juanChat.add(new ChatMessage("Hi Juan! The carving is about halfway done.", "Business"));
        conversations.put("Juan Dela Cruz", juanChat);

        conversations.put("Ana Reyes", new ArrayList<>());
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

        searchField.setToolTipText("Search your products");

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

        // PROFILE
        JPanel accountPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 5)
        );

        accountPanel.setOpaque(false);

        profileButton = new JButton("Profile");
        styleButton(profileButton, Color.WHITE, darkColor);
        profileButton.setBorder(
                BorderFactory.createLineBorder(Color.WHITE)
        );

        profileButton.addActionListener(e -> showProfile());

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

        sidebarPanel.add(menuTitle);
        sidebarPanel.add(Box.createVerticalStrut(15));

        addMenuButton("Dashboard", "Overview");

        JLabel manageTitle = new JLabel("MANAGE");
        manageTitle.setFont(boldFont);
        manageTitle.setForeground(labelColor);
        manageTitle.setBorder(
                BorderFactory.createEmptyBorder(20, 0, 10, 0)
        );

        sidebarPanel.add(manageTitle);

        addMenuButton("My Products", "Products");
        addMenuButton("Custom Requests", "Requests");
        addMenuButton("Messages", "Messages");

        sidebarPanel.add(Box.createVerticalGlue());

        JLabel helpLabel = new JLabel("Need help?");
        helpLabel.setFont(boldFont);
        helpLabel.setForeground(mainTextColor);

        JLabel helpDescription = new JLabel("Contact your team");
        helpDescription.setFont(mainFont);
        helpDescription.setForeground(labelColor);

        sidebarPanel.add(helpLabel);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(helpDescription);
    }

    private void addMenuButton(String buttonText, String pageName) {

        JButton menuButton = new JButton(buttonText);

        menuButton.setFont(mainFont);
        menuButton.setForeground(mainTextColor);
        menuButton.setBackground(backgroundColor);
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);

        menuButton.setMaximumSize(new Dimension(180, 40));
        menuButton.setBorder(
                BorderFactory.createEmptyBorder(8, 10, 8, 5)
        );

        menuButton.setFocusPainted(false);
        menuButton.setOpaque(true);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        menuButton.addActionListener(e -> showPage(pageName));

        sidebarPanel.add(menuButton);
    }

    // =================================
    // PAGES
    // =================================

    private void createPages() {

        cardLayout = new CardLayout();
        pagesPanel = new JPanel(cardLayout);
        pagesPanel.setBackground(Color.WHITE);

        createOverviewPage();
        createProductsPage();
        createRequestsPage();
        createMessagesPage();

        pagesPanel.add(overviewPage, "Overview");
        pagesPanel.add(productsPage, "Products");
        pagesPanel.add(requestsPage, "Requests");
        pagesPanel.add(messagesPage, "Messages");
    }

    private void showPage(String pageName) {

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
                break;
            default:
                break;
        }

        cardLayout.show(pagesPanel, pageName);
    }

    private JPanel createPageTitle(String title, String description) {

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(
                titlePanel, BoxLayout.Y_AXIS
        ));
        titlePanel.setBackground(Color.WHITE);

        JLabel pageTitle = new JLabel(title);
        pageTitle.setFont(new Font("Arial", Font.BOLD, 25));
        pageTitle.setForeground(mainTextColor);

        JLabel pageDescription = new JLabel(description);
        pageDescription.setFont(mainFont);
        pageDescription.setForeground(labelColor);

        titlePanel.add(pageTitle);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(pageDescription);

        return titlePanel;
    }

    // =================================
    // OVERVIEW PAGE
    // =================================

    private void createOverviewPage() {

        overviewPage = new JPanel(new BorderLayout(15, 20));
        overviewPage.setBackground(Color.WHITE);
        overviewPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        JPanel topPanel = new JPanel(new BorderLayout(0, 20));
        topPanel.setBackground(Color.WHITE);

        statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setPreferredSize(new Dimension(0, 100));

        topPanel.add(
                createPageTitle(
                        "Business Dashboard",
                        "A quick look at your StoneCraft shop."
                ),
                BorderLayout.NORTH
        );
        topPanel.add(statsPanel, BorderLayout.CENTER);

        recentPanel = new JPanel();
        recentPanel.setLayout(new BoxLayout(
                recentPanel, BoxLayout.Y_AXIS
        ));
        recentPanel.setBackground(Color.WHITE);

        JScrollPane recentScroll = new JScrollPane(recentPanel);
        recentScroll.setBorder(null);
        recentScroll.getVerticalScrollBar().setUnitIncrement(16);

        overviewPage.add(topPanel, BorderLayout.NORTH);
        overviewPage.add(recentScroll, BorderLayout.CENTER);
    }

    private void refreshOverview() {

        int lowStock = 0;

        for (Product product : products) {
            if (product.stock <= 3) {
                lowStock++;
            }
        }

        int pending = 0;
        int inProgress = 0;

        for (CustomRequest request : customRequests) {
            if ("Pending".equals(request.status)) {
                pending++;
            } else if ("In Progress".equals(request.status)) {
                inProgress++;
            }
        }

        statsPanel.removeAll();
        statsPanel.add(createStatCard("Total Products", String.valueOf(products.size())));
        statsPanel.add(createStatCard("Low Stock", String.valueOf(lowStock)));
        statsPanel.add(createStatCard("Pending Requests", String.valueOf(pending)));
        statsPanel.add(createStatCard("In Progress", String.valueOf(inProgress)));
        statsPanel.revalidate();
        statsPanel.repaint();

        recentPanel.removeAll();

        JLabel recentTitle = new JLabel("Recent Custom Requests");
        recentTitle.setFont(new Font("Arial", Font.BOLD, 16));
        recentTitle.setForeground(mainTextColor);
        recentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        recentPanel.add(recentTitle);
        recentPanel.add(Box.createVerticalStrut(12));

        if (customRequests.isEmpty()) {

            JLabel emptyLabel = new JLabel("No custom requests yet.");
            emptyLabel.setFont(mainFont);
            emptyLabel.setForeground(labelColor);
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            recentPanel.add(emptyLabel);

        } else {

            int start = Math.max(0, customRequests.size() - 3);

            for (int i = customRequests.size() - 1; i >= start; i--) {

                JPanel card = createRequestCard(customRequests.get(i));
                card.setAlignmentX(Component.LEFT_ALIGNMENT);

                recentPanel.add(card);
                recentPanel.add(Box.createVerticalStrut(12));
            }
        }

        recentPanel.revalidate();
        recentPanel.repaint();
    }

    private JPanel createStatCard(String title, String value) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(fieldColor);

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
        valueLabel.setFont(new Font("Arial", Font.BOLD, 30));
        valueLabel.setForeground(copperColor);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(valueLabel);

        return card;
    }

    // =================================
    // PRODUCTS PAGE
    // =================================

    private void createProductsPage() {

        productsPage = new JPanel(new BorderLayout(15, 15));
        productsPage.setBackground(Color.WHITE);
        productsPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add Product");
        styleButton(addButton, copperColor, Color.WHITE);
        addButton.addActionListener(e -> addProduct());

        JPanel addHolder = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        addHolder.setBackground(Color.WHITE);
        addHolder.add(addButton);

        topPanel.add(
                createPageTitle(
                        "My Products",
                        "Add, edit, and manage the products you sell."
                ),
                BorderLayout.WEST
        );
        topPanel.add(addHolder, BorderLayout.EAST);

        tableModel = new DefaultTableModel(
                new String[]{"Name", "Category", "Price", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(tableModel);
        productTable.setFont(mainFont);
        productTable.setRowHeight(34);
        productTable.setForeground(mainTextColor);
        productTable.setGridColor(borderColor);
        productTable.setSelectionBackground(new Color(246, 228, 210));
        productTable.setSelectionForeground(mainTextColor);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader tableHeader = productTable.getTableHeader();
        tableHeader.setFont(boldFont);
        tableHeader.setBackground(darkColor);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(0, 36));

        JScrollPane tableScroll = new JScrollPane(productTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(borderColor));
        tableScroll.getViewport().setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton editButton = new JButton("Edit Selected");
        styleButton(editButton, darkColor, Color.WHITE);
        editButton.addActionListener(e -> editProduct());

        JButton deleteButton = new JButton("Delete Selected");
        styleButton(deleteButton, fieldColor, mainTextColor);
        deleteButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(8, 14, 8, 14)
                )
        );
        deleteButton.addActionListener(e -> deleteProduct());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        productsPage.add(topPanel, BorderLayout.NORTH);
        productsPage.add(tableScroll, BorderLayout.CENTER);
        productsPage.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshProducts() {

        String keyword = searchField.getText().toLowerCase().trim();

        tableModel.setRowCount(0);
        visibleProducts.clear();

        for (Product product : products) {

            if (product.name.toLowerCase().contains(keyword)) {

                visibleProducts.add(product);

                tableModel.addRow(new Object[]{
                        product.name,
                        product.category,
                        "₱" + String.format("%.2f", product.price),
                        product.stock
                });
            }
        }
    }

    private void searchProducts() {

        showPage("Products");
    }

    private void addProduct() {

        Product newProduct = showProductDialog(null);

        if (newProduct != null) {
            products.add(newProduct);
            refreshProducts();
        }
    }

    private void editProduct() {

        int row = productTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to edit.",
                    "StoneCraft",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Product product = visibleProducts.get(row);
        Product edited = showProductDialog(product);

        if (edited != null) {
            product.name = edited.name;
            product.category = edited.category;
            product.price = edited.price;
            product.stock = edited.stock;
            refreshProducts();
        }
    }

    private void deleteProduct() {

        int row = productTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to delete.",
                    "StoneCraft",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Product product = visibleProducts.get(row);

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Delete \"" + product.name + "\"?",
                "Delete Product",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            products.remove(product);
            refreshProducts();
        }
    }

    // Returns a new Product with the entered values, or null if cancelled.
    private Product showProductDialog(Product existing) {

        JTextField nameField = createFormField();
        JComboBox<String> categoryBox = new JComboBox<>(CATEGORIES);
        styleComboBox(categoryBox);
        JTextField priceField = createFormField();
        JTextField stockField = createFormField();

        if (existing != null) {
            nameField.setText(existing.name);
            categoryBox.setSelectedItem(existing.category);
            priceField.setText(String.valueOf(existing.price));
            stockField.setText(String.valueOf(existing.stock));
        }

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 6));
        form.setBackground(Color.WHITE);
        form.setPreferredSize(new Dimension(320, 290));

        form.add(createFormLabel("Product Name"));
        form.add(nameField);
        form.add(createFormLabel("Category"));
        form.add(categoryBox);
        form.add(createFormLabel("Price (₱)"));
        form.add(priceField);
        form.add(createFormLabel("Stock"));
        form.add(stockField);

        while (true) {

            int result = JOptionPane.showConfirmDialog(
                    this,
                    form,
                    existing == null ? "Add Product" : "Edit Product",
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

    private void showWarning(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // =================================
    // CUSTOM REQUESTS PAGE
    // =================================

    private void createRequestsPage() {

        requestsPage = new JPanel(new BorderLayout(15, 15));
        requestsPage.setBackground(Color.WHITE);
        requestsPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        JPanel topPanel = new JPanel(new BorderLayout(10, 15));
        topPanel.setBackground(Color.WHITE);

        topPanel.add(
                createPageTitle(
                        "Custom Requests",
                        "Review and update custom sculpture requests from customers."
                ),
                BorderLayout.NORTH
        );

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(Color.WHITE);

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
        requestsListPanel.setLayout(new BoxLayout(
                requestsListPanel, BoxLayout.Y_AXIS
        ));
        requestsListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(requestsListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        requestsPage.add(topPanel, BorderLayout.NORTH);
        requestsPage.add(scrollPane, BorderLayout.CENTER);
    }

    private void refreshRequests() {

        if (requestsListPanel == null) {
            return;
        }

        String filter = (String) statusFilterBox.getSelectedItem();

        requestsListPanel.removeAll();

        boolean anyShown = false;

        for (CustomRequest request : customRequests) {

            if ("All Statuses".equals(filter)
                    || request.status.equals(filter)) {

                JPanel card = createRequestCard(request);
                card.setAlignmentX(Component.LEFT_ALIGNMENT);

                requestsListPanel.add(card);
                requestsListPanel.add(Box.createVerticalStrut(12));

                anyShown = true;
            }
        }

        if (!anyShown) {

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
        detailsPanel.setLayout(new BoxLayout(
                detailsPanel, BoxLayout.Y_AXIS
        ));
        detailsPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(request.sculptureName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(mainTextColor);

        JLabel customerLabel = new JLabel("Customer: " + request.customerName);
        customerLabel.setFont(mainFont);
        customerLabel.setForeground(labelColor);

        JLabel sizeLabel = new JLabel("Preferred Size: " + request.size);
        sizeLabel.setFont(mainFont);
        sizeLabel.setForeground(labelColor);

        JLabel designLabel = new JLabel("Design: " + request.design);
        designLabel.setFont(mainFont);
        designLabel.setForeground(labelColor);

        JLabel budgetLabel = new JLabel(
                "Budget: ₱" + String.format("%.2f", request.budget)
        );
        budgetLabel.setFont(mainFont);
        budgetLabel.setForeground(copperColor);

        JLabel statusLabel = new JLabel("Status: " + request.status);
        statusLabel.setFont(boldFont);
        statusLabel.setForeground(copperColor);

        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(7));
        detailsPanel.add(customerLabel);
        detailsPanel.add(sizeLabel);
        detailsPanel.add(designLabel);
        detailsPanel.add(budgetLabel);
        detailsPanel.add(Box.createVerticalStrut(7));
        detailsPanel.add(statusLabel);

        // ACTIONS
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(
                actionPanel, BoxLayout.Y_AXIS
        ));
        actionPanel.setBackground(Color.WHITE);

        JComboBox<String> statusBox = new JComboBox<>(STATUSES);
        styleComboBox(statusBox);
        statusBox.setSelectedItem(request.status);
        statusBox.setMaximumSize(new Dimension(150, 34));

        JButton updateButton = new JButton("Update Status");
        styleButton(updateButton, copperColor, Color.WHITE);
        updateButton.setMaximumSize(new Dimension(150, 36));

        JButton viewButton = new JButton("View Details");
        styleButton(viewButton, darkColor, Color.WHITE);
        viewButton.setMaximumSize(new Dimension(150, 36));

        updateButton.addActionListener(e -> {

            request.status = (String) statusBox.getSelectedItem();

            refreshRequests();
            refreshOverview();
        });

        viewButton.addActionListener(e -> showRequestDetails(request));

        actionPanel.add(statusBox);
        actionPanel.add(Box.createVerticalStrut(8));
        actionPanel.add(updateButton);
        actionPanel.add(Box.createVerticalStrut(8));
        actionPanel.add(viewButton);

        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.EAST);

        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 190)
        );

        return card;
    }

    private void showRequestDetails(CustomRequest request) {

        String details =
                "Customer: " + request.customerName
                        + "\n\nSculpture Name: " + request.sculptureName
                        + "\n\nDescription: " + request.description
                        + "\n\nPreferred Size: " + request.size
                        + "\n\nDesign: " + request.design
                        + "\n\nBudget: ₱" + String.format("%.2f", request.budget)
                        + "\n\nStatus: " + request.status
                        + "\n\nReference Image: "
                        + (request.imagePath.isEmpty()
                        ? "No image attached"
                        : new File(request.imagePath).getName());

        JOptionPane.showMessageDialog(
                this,
                details,
                "Custom Request Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =================================
    // MESSAGES PAGE
    // =================================

    private void createMessagesPage() {

        messagesPage = new JPanel(new BorderLayout(15, 15));
        messagesPage.setBackground(Color.WHITE);
        messagesPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        messagesPage.add(
                createPageTitle(
                        "Messages",
                        "Chat with your customers."
                ),
                BorderLayout.NORTH
        );

        // CUSTOMER LIST
        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);
        customerList.setFont(mainFont);
        customerList.setForeground(mainTextColor);
        customerList.setSelectionBackground(new Color(246, 228, 210));
        customerList.setSelectionForeground(mainTextColor);
        customerList.setFixedCellHeight(42);
        customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        customerList.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                selectedCustomer = customerList.getSelectedValue();
                refreshChatMessages();
            }
        });

        JScrollPane listScroll = new JScrollPane(customerList);
        listScroll.setBorder(BorderFactory.createLineBorder(borderColor));
        listScroll.setPreferredSize(new Dimension(220, 0));

        // CHAT SIDE
        JPanel chatSide = new JPanel(new BorderLayout());
        chatSide.setBackground(Color.WHITE);
        chatSide.setBorder(BorderFactory.createLineBorder(borderColor));

        JPanel chatHeader = new JPanel(new BorderLayout());
        chatHeader.setBackground(darkColor);
        chatHeader.setBorder(
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
        );

        chatTitle = new JLabel("Select a customer");
        chatTitle.setFont(new Font("Arial", Font.BOLD, 16));
        chatTitle.setForeground(Color.WHITE);

        chatHeader.add(chatTitle, BorderLayout.WEST);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(
                chatPanel, BoxLayout.Y_AXIS
        ));
        chatPanel.setBackground(backgroundColor);
        chatPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        chatScrollPane = new JScrollPane(chatPanel);
        chatScrollPane.setBorder(null);
        chatScrollPane.getVerticalScrollBar().setUnitIncrement(15);

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

        chatSide.add(chatHeader, BorderLayout.NORTH);
        chatSide.add(chatScrollPane, BorderLayout.CENTER);
        chatSide.add(inputPanel, BorderLayout.SOUTH);

        JPanel bodyPanel = new JPanel(new BorderLayout(15, 0));
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.add(listScroll, BorderLayout.WEST);
        bodyPanel.add(chatSide, BorderLayout.CENTER);

        messagesPage.add(bodyPanel, BorderLayout.CENTER);
    }

    private void refreshCustomerList() {

        String previous = selectedCustomer;

        customerListModel.clear();

        for (String customer : conversations.keySet()) {
            customerListModel.addElement(customer);
        }

        if (previous != null && conversations.containsKey(previous)) {
            customerList.setSelectedValue(previous, true);
        } else {
            selectedCustomer = null;
            refreshChatMessages();
        }
    }

    private void sendMessage() {

        if (selectedCustomer == null) {
            return;
        }

        String message = messageField.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        conversations.get(selectedCustomer).add(
                new ChatMessage(message, "Business")
        );

        messageField.setText("");

        refreshChatMessages();
    }

    private void refreshChatMessages() {

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

            if (messages.isEmpty()) {

                JLabel emptyLabel = new JLabel("No messages yet.");
                emptyLabel.setFont(mainFont);
                emptyLabel.setForeground(labelColor);
                emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                chatPanel.add(Box.createVerticalStrut(30));
                chatPanel.add(emptyLabel);

            } else {

                for (ChatMessage chatMessage : messages) {
                    chatPanel.add(createMessageBubble(chatMessage));
                    chatPanel.add(Box.createVerticalStrut(10));
                }
            }
        }

        chatPanel.revalidate();
        chatPanel.repaint();

        SwingUtilities.invokeLater(() -> {

            JScrollBar verticalBar = chatScrollPane.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
    }

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

        boolean fromBusiness = "Business".equals(chatMessage.sender);

        JLabel senderLabel = new JLabel(
                fromBusiness ? "You" : selectedCustomer
        );
        senderLabel.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel messageLabel = new JLabel(
                "<html><div style='width:250px;'>"
                        + escapeHtml(chatMessage.message)
                        + "</div></html>"
        );

        messageLabel.setFont(mainFont);
        messageLabel.setForeground(mainTextColor);

        if (fromBusiness) {
            bubblePanel.setBackground(new Color(246, 228, 210));
            senderLabel.setForeground(copperColor);
        } else {
            bubblePanel.setBackground(Color.WHITE);
            senderLabel.setForeground(darkColor);
        }

        bubblePanel.add(senderLabel);
        bubblePanel.add(Box.createVerticalStrut(5));
        bubblePanel.add(messageLabel);

        rowPanel.add(
                bubblePanel,
                fromBusiness ? BorderLayout.EAST : BorderLayout.WEST
        );

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

        JMenuItem accountItem = new JMenuItem("Business Account");
        JMenuItem logoutItem = new JMenuItem("Log Out");

        accountItem.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Business Account",
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
        profileMenu.addSeparator();
        profileMenu.add(logoutItem);

        profileMenu.show(
                profileButton,
                0,
                profileButton.getHeight()
        );
    }

    // =================================
    // FORM / BUTTON DESIGN HELPERS
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

        return textField;
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
    // PRODUCT CLASS
    // =================================

    private static class Product {

        String name;
        String category;
        double price;
        int stock;

        Product(String name, String category, double price, int stock) {

            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }
    }

    // =================================
    // CUSTOM REQUEST CLASS
    // =================================

    private static class CustomRequest {

        String customerName;
        String sculptureName;
        String description;
        String size;
        String design;
        double budget;
        String imagePath;
        String status;

        CustomRequest(
                String customerName,
                String sculptureName,
                String description,
                String size,
                String design,
                double budget,
                String imagePath
        ) {

            this.customerName = customerName;
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
