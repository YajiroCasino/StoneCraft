
package Design;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusinessMain extends MainDesign{

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

<<<<<<< Updated upstream
    // =========================================
    // PUBLIC COMPONENTS
    // =========================================
=======
    // HEADER COMPONENTS
    private JTextField searchField;
    private JButton profileButton;
    private JButton selectedMenuButton;
>>>>>>> Stashed changes

    public JTextField searchField;
    public JButton profileButton;

    public DefaultListModel<String> customerListModel;
    public JList<String> customerList;

    public JPanel chatPanel;
    public JScrollPane chatScrollPane;

    public JTextField messageField;
    public JLabel chatTitle;

    public String selectedCustomer = null;

    public Map<String, ArrayList<ChatMessage>> conversations;

    public ArrayList<Product> products;
    public ArrayList<CustomRequest> customRequests;
    public ArrayList<CustomerReview> customerReviews;

    // =========================================
    // PAGES
    // =========================================

    private JPanel overviewPage;
    private JPanel productsPage;
    private JPanel requestsPage;
    private JPanel messagesPage;
    private JPanel historyPage;
    private JPanel reportsPage;
    private JPanel reviewsPage;

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
    // ORDER HISTORY
    // =========================================

    private DefaultTableModel historyTableModel;
    private JTable historyTable;

    private final ArrayList<CustomRequest> visibleHistory =
            new ArrayList<>();

    // =========================================
    // REPORTS
    // =========================================

    private JPanel reportsContentPanel;

    // =========================================
    // RATINGS AND REVIEWS
    // =========================================

    private JPanel reviewsListPanel;
    private JPanel reviewSummaryPanel;
    private JLabel averageRatingLabel;
    private JLabel totalReviewsLabel;
    private JLabel ratingStarsLabel;

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
        conversations = new LinkedHashMap<>();
        customerReviews = new ArrayList<>();

        addSampleData();

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
    // SAMPLE DATA
    // =========================================

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
                "Maria Santos",
                "Family Crest Plaque",
                "A carved plaque showing our family crest for the front gate.",
                "Medium",
                "Traditional",
                6000.00,
                ""
        ));

        CustomRequest juanRequest = new CustomRequest(
                "Juan Dela Cruz",
                "Eagle Statue",
                "A standing eagle with spread wings for the garden.",
                "Large",
                "Realistic",
                12000.00,
                ""
        );

        juanRequest.status = "In Progress";
        customRequests.add(juanRequest);

        customRequests.add(new CustomRequest(
                "Ana Reyes",
                "Abstract Wave Sculpture",
                "Smooth flowing wave design for a living room shelf.",
                "Small",
                "Abstract",
                3500.00,
                ""
        ));

        CustomRequest completedRequest = new CustomRequest(
                "Pedro Garcia",
                "Stone Garden Fountain",
                "A customized stone fountain for the garden.",
                "Large",
                "Traditional",
                15000.00,
                ""
        );

        completedRequest.status = "Completed";
        customRequests.add(completedRequest);

        CustomRequest declinedRequest = new CustomRequest(
                "Carla Mendoza",
                "Large Lion Sculpture",
                "A large lion sculpture for the entrance.",
                "Large",
                "Realistic",
                18000.00,
                ""
        );

        declinedRequest.status = "Declined";
        customRequests.add(declinedRequest);

        ArrayList<ChatMessage> mariaChat = new ArrayList<>();

        mariaChat.add(new ChatMessage(
                "Hi! Can the family crest plaque be done in two weeks?",
                "Customer"
        ));

        conversations.put("Maria Santos", mariaChat);

        ArrayList<ChatMessage> juanChat = new ArrayList<>();

        juanChat.add(new ChatMessage(
                "Hello, any update on the eagle statue?",
                "Customer"
        ));

        juanChat.add(new ChatMessage(
                "Hi Juan! The carving is about halfway done.",
                "Business"
        ));

        conversations.put("Juan Dela Cruz", juanChat);
        conversations.put("Ana Reyes", new ArrayList<>());

        // SAMPLE CUSTOMER REVIEWS

        customerReviews.add(new CustomerReview(
                "Maria Santos",
                "Stone Vase",
                5,
                "Beautiful craftsmanship! The details are carefully made.",
                "November 10, 2026"
        ));

        customerReviews.add(new CustomerReview(
                "Juan Dela Cruz",
                "Custom Eagle Statue",
                5,
                "The design looks amazing. I am very satisfied with the result.",
                "November 8, 2026"
        ));

        customerReviews.add(new CustomerReview(
                "Ana Reyes",
                "Stone Candle Holder",
                4,
                "Good quality and nice design. The product looks elegant.",
                "November 5, 2026"
        ));

        customerReviews.add(new CustomerReview(
                "Pedro Garcia",
                "Garden Planter",
                4,
                "The item is well-made and looks great in our garden.",
                "November 2, 2026"
        ));
    }

    // =========================================
    // HEADER
    // =========================================

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

        logoTextPanel.setLayout(
                new BoxLayout(logoTextPanel, BoxLayout.Y_AXIS)
        );

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

        searchField = createFormField();
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

    // =========================================
    // SIDEBAR
    // =========================================

    private void createSidebar() {

        sidebarPanel = new JPanel();

<<<<<<< Updated upstream
        sidebarPanel.setLayout(
                new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS)
        );

        sidebarPanel.setPreferredSize(new Dimension(215, 0));
=======
        sidebarPanel.setPreferredSize(new Dimension(210, 0));
>>>>>>> Stashed changes
        sidebarPanel.setBackground(darkColor);

        sidebarPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 12, 20, 12)
        );

<<<<<<< Updated upstream
        addSidebarTitle("WORKSPACE");
        addMenuButton("Dashboard", "Overview");

        addSidebarTitle("MANAGEMENT");
=======
        JLabel menuTitle = new JLabel("MENU");
        menuTitle.setFont(boldFont);
        menuTitle.setForeground(new Color(185, 179, 168));

        sidebarPanel.add(menuTitle);
        sidebarPanel.add(Box.createVerticalStrut(15));

        addMenuButton("Dashboard", "Overview");

        JLabel manageTitle = new JLabel("MANAGE");
        manageTitle.setFont(boldFont);
        manageTitle.setForeground(new Color(185, 179, 168));
        manageTitle.setBorder(
                BorderFactory.createEmptyBorder(20, 0, 10, 0)
        );

        sidebarPanel.add(manageTitle);

>>>>>>> Stashed changes
        addMenuButton("My Products", "Products");
        addMenuButton("Custom Requests", "Requests");
        addMenuButton("Order History", "History");
        addMenuButton("Messages", "Messages");

        addSidebarTitle("ANALYTICS");
        addMenuButton("Reports", "Reports");
        addMenuButton("Ratings & Reviews", "Reviews");

        sidebarPanel.add(Box.createVerticalGlue());
    }

<<<<<<< Updated upstream
    private void addSidebarTitle(String title) {

        JLabel sectionLabel = new JLabel(title);
=======
        JLabel helpLabel = new JLabel("Need help?");
        helpLabel.setFont(boldFont);
        helpLabel.setForeground(Color.WHITE);

        JLabel helpDescription = new JLabel("Contact your team");
        helpDescription.setFont(mainFont);
        helpDescription.setForeground(new Color(185, 179, 168));
>>>>>>> Stashed changes

        sectionLabel.setFont(new Font("Arial", Font.BOLD, 11));
        sectionLabel.setForeground(new Color(170, 164, 155));

        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sectionLabel.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 10, 0)
        );

        sidebarPanel.add(sectionLabel);
    }

    private void addMenuButton(String buttonText, String pageName) {

        JButton menuButton = new JButton(buttonText);

        menuButton.setFont(mainFont);
        menuButton.setForeground(Color.WHITE);
        menuButton.setBackground(darkColor);
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        menuButton.setHorizontalAlignment(SwingConstants.LEFT);

        menuButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42)
        );

        menuButton.setPreferredSize(new Dimension(190, 42));

        menuButton.setBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 5)
        );

        menuButton.setFocusPainted(false);
        menuButton.setOpaque(true);
        menuButton.setBorderPainted(false);
<<<<<<< Updated upstream

        menuButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
=======
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
>>>>>>> Stashed changes

        menuButton.addActionListener(e -> {

            selectMenuButton(menuButton);
            showPage(pageName);

        });

        menuButtons.put(pageName, menuButton);

        sidebarPanel.add(menuButton);

        // DASHBOARD IS SELECTED BY DEFAULT
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

            menuButton.setBackground(
                    selected ? copperColor : darkColor
            );

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
        createHistoryPage();
        createReportsPage();
        createReviewsPage();

        pagesPanel.add(overviewPage, "Overview");
        pagesPanel.add(productsPage, "Products");
        pagesPanel.add(requestsPage, "Requests");
        pagesPanel.add(messagesPage, "Messages");
        pagesPanel.add(historyPage, "History");
        pagesPanel.add(reportsPage, "Reports");
        pagesPanel.add(reviewsPage, "Reviews");
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

            case "History":
                refreshHistory();
                break;

            case "Reports":
                refreshReports();
                break;

            case "Reviews":
                refreshReviews();
                break;

            default:
                return;
        }

        cardLayout.show(pagesPanel, pageName);

        updateSidebarSelection();
    }

    private JPanel createPageTitle(String title, String description) {

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

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

        overviewPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        JPanel topPanel = new JPanel(new BorderLayout(0, 20));

        topPanel.setBackground(backgroundColor);

        statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));

        statsPanel.setBackground(backgroundColor);
        statsPanel.setPreferredSize(new Dimension(0, 110));

        topPanel.add(
                createPageTitle(
                        "Business Dashboard",
                        "A quick look at your StoneCraft shop."
                ),
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

            if (product.stock <= 3) {
                lowStock++;
            }
        }

        for (CustomRequest request : customRequests) {

            if (request.status.equals("Pending")) {
                pending++;
            }

            if (request.status.equals("In Progress")) {
                inProgress++;
            }
        }

        statsPanel.removeAll();

        statsPanel.add(createStatCard(
                "Total Products",
                String.valueOf(products.size())
        ));

        statsPanel.add(createStatCard(
                "Low Stock",
                String.valueOf(lowStock)
        ));

        statsPanel.add(createStatCard(
                "Pending Requests",
                String.valueOf(pending)
        ));

        statsPanel.add(createStatCard(
                "In Progress",
                String.valueOf(inProgress)
        ));

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

        card.setLayout(
                new BoxLayout(card, BoxLayout.Y_AXIS)
        );

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

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        JLabel titleLabel = new JLabel("Recent Custom Requests");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(mainTextColor);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        int count = 0;

        for (int i = customRequests.size() - 1;
             i >= 0 && count < 4; i--) {

            CustomRequest request = customRequests.get(i);

            JLabel nameLabel = new JLabel(request.sculptureName);

            nameLabel.setFont(boldFont);
            nameLabel.setForeground(mainTextColor);

            JLabel customerLabel = new JLabel(
                    request.customerName + "  |  " + request.status
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

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        JLabel titleLabel = new JLabel("Recent Messages");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(mainTextColor);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        int count = 0;

        for (String customer : conversations.keySet()) {

            ArrayList<ChatMessage> messages = conversations.get(customer);

            String preview = "No messages yet.";

            if (!messages.isEmpty()) {
                preview = messages.get(messages.size() - 1).message;
            }

            JLabel customerLabel = new JLabel(customer);

            customerLabel.setFont(boldFont);
            customerLabel.setForeground(mainTextColor);

            JLabel previewLabel = new JLabel(
                    shortenText(preview, 35)
            );

            previewLabel.setFont(mainFont);
            previewLabel.setForeground(labelColor);

            panel.add(customerLabel);
            panel.add(Box.createVerticalStrut(4));
            panel.add(previewLabel);
            panel.add(Box.createVerticalStrut(12));

            count++;

            if (count >= 4) {
                break;
            }
        }

        return panel;
    }

    // =========================================
    // MY PRODUCTS PAGE
    // =========================================

    private void createProductsPage() {

        productsPage = new JPanel(new BorderLayout(15, 15));

        productsPage.setBackground(backgroundColor);

        productsPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.setBackground(backgroundColor);

        topPanel.add(
                createPageTitle(
                        "My Products",
                        "Add, edit, and manage the products you sell."
                ),
                BorderLayout.WEST
        );

        JButton addButton = new JButton("Add Product");

        styleButton(addButton, copperColor, Color.WHITE);

        addButton.addActionListener(e -> addProduct());

        JPanel addButtonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 0, 10)
        );

        addButtonPanel.setBackground(backgroundColor);
        addButtonPanel.add(addButton);

        topPanel.add(addButtonPanel, BorderLayout.EAST);

        productTableModel = new DefaultTableModel(
                new String[]{
                        "Product Name",
                        "Category",
                        "Price",
                        "Stock",
                        "Stock Status"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(productTableModel);

        styleTable(productTable);

        // Automatically fill the available table width.

        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader productHeader = productTable.getTableHeader();

        productHeader.setReorderingAllowed(false);
        productHeader.setResizingAllowed(false);

        productTable.setFillsViewportHeight(true);

        TableColumnModel productColumns =
                productTable.getColumnModel();

        productColumns.getColumn(0).setPreferredWidth(260);
        productColumns.getColumn(1).setPreferredWidth(180);
        productColumns.getColumn(2).setPreferredWidth(130);
        productColumns.getColumn(3).setPreferredWidth(100);
        productColumns.getColumn(4).setPreferredWidth(150);

        JScrollPane tableScrollPane = new JScrollPane(productTable);

        tableScrollPane.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        tableScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        tableScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        tableScrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableCard = createWhiteCard();

        tableCard.setLayout(new BorderLayout());
        tableCard.add(tableScrollPane, BorderLayout.CENTER);

        JButton editButton = new JButton("Edit Selected");
        JButton deleteButton = new JButton("Delete Selected");

        styleButton(editButton, darkColor, Color.WHITE);
        styleButton(deleteButton, fieldColor, mainTextColor);

        deleteButton.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        editButton.addActionListener(e -> editProduct());
        deleteButton.addActionListener(e -> deleteProduct());

        JPanel actionPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );

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
                    product.name.toLowerCase().contains(keyword)
                            || product.category.toLowerCase().contains(keyword);

            if (matchesSearch) {

                visibleProducts.add(product);

                String stockStatus =
                        product.stock <= 3 ? "Low Stock" : "Available";

                productTableModel.addRow(new Object[]{
                        product.name,
                        product.category,
                        "₱" + String.format("%.2f", product.price),
                        product.stock,
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

    private void addProduct() {

        Product newProduct = showProductDialog(null);

        if (newProduct != null) {

            products.add(newProduct);

            refreshProducts();
            refreshOverview();
            refreshReports();
        }
    }

    private void editProduct() {

        Product selectedProduct = getSelectedProduct();

<<<<<<< Updated upstream
        if (selectedProduct == null) {

            showWarning("Please select a product to edit.");
=======
        if (row < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to edit.",
                    "StoneCraft",
                    JOptionPane.WARNING_MESSAGE);
>>>>>>> Stashed changes
            return;
        }

        Product editedProduct = showProductDialog(selectedProduct);

        if (editedProduct != null) {

            selectedProduct.name = editedProduct.name;
            selectedProduct.category = editedProduct.category;
            selectedProduct.price = editedProduct.price;
            selectedProduct.stock = editedProduct.stock;

            refreshProducts();
            refreshOverview();
            refreshReports();
        }
    }

    private void deleteProduct() {

        Product selectedProduct = getSelectedProduct();

<<<<<<< Updated upstream
        if (selectedProduct == null) {

            showWarning("Please select a product to delete.");
=======
        if (row < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to delete.",
                    "StoneCraft",
                    JOptionPane.WARNING_MESSAGE);
>>>>>>> Stashed changes
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete \""
                        + selectedProduct.name + "\"?",
                "Delete Product",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {

            products.remove(selectedProduct);

            refreshProducts();
            refreshOverview();
            refreshReports();
        }
    }

    private Product showProductDialog(Product existingProduct) {

        JTextField nameField = createFormField();

        JComboBox<String> categoryBox =
                new JComboBox<>(CATEGORIES);

        styleComboBox(categoryBox);

        JTextField priceField = createFormField();
        JTextField stockField = createFormField();

        if (existingProduct != null) {

            nameField.setText(existingProduct.name);
            categoryBox.setSelectedItem(existingProduct.category);
            priceField.setText(String.valueOf(existingProduct.price));
            stockField.setText(String.valueOf(existingProduct.stock));
        }

        JPanel formPanel = new JPanel(new GridLayout(0, 1, 0, 7));

        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(320, 280));

        formPanel.add(createFormLabel("Product Name"));
        formPanel.add(nameField);

        formPanel.add(createFormLabel("Category"));
        formPanel.add(categoryBox);

        formPanel.add(createFormLabel("Price (₱)"));
        formPanel.add(priceField);

        formPanel.add(createFormLabel("Stock"));
        formPanel.add(stockField);

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

                showWarning(
                        "Price must be above 0 and stock cannot be negative."
                );

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

        requestsPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        JPanel topPanel = new JPanel(new BorderLayout(10, 15));

        topPanel.setBackground(backgroundColor);

        topPanel.add(
                createPageTitle(
                        "Custom Requests",
                        "Review and update customer sculpture requests."
                ),
                BorderLayout.NORTH
        );

        JPanel filterPanel = new JPanel(new BorderLayout());

        filterPanel.setBackground(backgroundColor);

        JLabel filterLabel = new JLabel("Customer Requests");

        filterLabel.setFont(boldFont);
        filterLabel.setForeground(mainTextColor);

        String[] filterOptions = new String[STATUSES.length + 1];

        filterOptions[0] = "All Statuses";

        System.arraycopy(
                STATUSES, 0, filterOptions, 1, STATUSES.length
        );

        statusFilterBox = new JComboBox<>(filterOptions);

        styleComboBox(statusFilterBox);

        statusFilterBox.addActionListener(e -> refreshRequests());

        filterPanel.add(filterLabel, BorderLayout.WEST);
        filterPanel.add(statusFilterBox, BorderLayout.EAST);

        topPanel.add(filterPanel, BorderLayout.SOUTH);

        requestsListPanel = new JPanel();

        requestsListPanel.setLayout(
                new BoxLayout(requestsListPanel, BoxLayout.Y_AXIS)
        );

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
                    || request.status.equals(filter)) {

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

        detailsPanel.setLayout(
                new BoxLayout(detailsPanel, BoxLayout.Y_AXIS)
        );

        detailsPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(request.sculptureName);

        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(mainTextColor);

        JLabel customerLabel = new JLabel(
                "Customer: " + request.customerName
        );

        JLabel sizeLabel = new JLabel(
                "Preferred Size: " + request.size
        );

        JLabel designLabel = new JLabel(
                "Design: " + request.design
        );

        JLabel budgetLabel = new JLabel(
                "Budget: ₱" + String.format("%.2f", request.budget)
        );

        JLabel statusLabel = new JLabel(
                "Status: " + request.status
        );

        JLabel[] detailLabels = {
                customerLabel, sizeLabel, designLabel
        };

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

        actionPanel.setLayout(
                new BoxLayout(actionPanel, BoxLayout.Y_AXIS)
        );

        actionPanel.setBackground(Color.WHITE);

        JComboBox<String> requestStatusBox =
                new JComboBox<>(STATUSES);

        styleComboBox(requestStatusBox);

        requestStatusBox.setSelectedItem(request.status);
        requestStatusBox.setMaximumSize(new Dimension(150, 34));

        JButton updateButton = new JButton("Update Status");

        styleButton(updateButton, copperColor, Color.WHITE);

        updateButton.setMaximumSize(new Dimension(150, 36));

        JButton viewButton = new JButton("View Details");

        styleButton(viewButton, darkColor, Color.WHITE);

        viewButton.setMaximumSize(new Dimension(150, 36));

        updateButton.addActionListener(e -> {

            request.status =
                    (String) requestStatusBox.getSelectedItem();

            refreshRequests();
            refreshOverview();
            refreshHistory();
            refreshReports();
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

    // =========================================
    // MESSAGES PAGE
    // =========================================

    private void createMessagesPage() {

        messagesPage = new JPanel(new BorderLayout(15, 15));

        messagesPage.setBackground(backgroundColor);

        messagesPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        messagesPage.add(
                createPageTitle(
                        "Messages",
                        "Chat with your customers and manage conversations."
                ),
                BorderLayout.NORTH
        );

        // CUSTOMER LIST

        customerListModel = new DefaultListModel<>();

        customerList = new JList<>(customerListModel);

        customerList.setFont(mainFont);
        customerList.setForeground(mainTextColor);

        customerList.setSelectionBackground(selectionColor);
        customerList.setSelectionForeground(mainTextColor);

        customerList.setFixedCellHeight(55);

        customerList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        customerList.setCellRenderer(new CustomerListRenderer());

        customerList.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                selectedCustomer = customerList.getSelectedValue();

                refreshChatMessages();
            }
        });

        JScrollPane listScrollPane = new JScrollPane(customerList);

        listScrollPane.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        listScrollPane.setPreferredSize(new Dimension(230, 0));

        // CHAT SIDE

        JPanel chatSide = new JPanel(new BorderLayout());

        chatSide.setBackground(Color.WHITE);

        chatSide.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

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

        chatPanel.setLayout(
                new BoxLayout(chatPanel, BoxLayout.Y_AXIS)
        );

        chatPanel.setBackground(backgroundColor);

        chatPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        chatScrollPane = new JScrollPane(chatPanel);

        chatScrollPane.setBorder(null);

        chatScrollPane.getVerticalScrollBar().setUnitIncrement(15);

        // MESSAGE INPUT

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));

        inputPanel.setBackground(Color.WHITE);

        inputPanel.setBorder(
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        );

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

    private class CustomerListRenderer
            implements ListCellRenderer<String> {

        @Override
        public Component getListCellRendererComponent(
                JList<? extends String> list,
                String customer,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {

            JPanel panel = new JPanel(new BorderLayout());

            panel.setBorder(
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            );

            panel.setBackground(
                    isSelected ? selectionColor : Color.WHITE
            );

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

        ArrayList<ChatMessage> messages =
                conversations.get(selectedCustomer);

        if (messages == null) {
            return;
        }

        messages.add(new ChatMessage(message, "Business"));

        messageField.setText("");

        refreshChatMessages();
    }

    public void receiveCustomerMessage(
            String customerName,
            String message
    ) {

        if (customerName == null || customerName.trim().isEmpty()
                || message == null || message.trim().isEmpty()) {
            return;
        }

        String name = customerName.trim();

        conversations.putIfAbsent(name, new ArrayList<>());

        conversations.get(name).add(
                new ChatMessage(message.trim(), "Customer")
        );

        refreshCustomerList();

        if (name.equals(selectedCustomer)
                && currentPage.equals("Messages")) {

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

            ArrayList<ChatMessage> messages =
                    conversations.get(selectedCustomer);

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

                JScrollBar scrollBar =
                        chatScrollPane.getVerticalScrollBar();

                scrollBar.setValue(scrollBar.getMaximum());
            }
        });
    }

<<<<<<< Updated upstream
    // =========================================
    // COMPACT MESSAGE BUBBLES
    // =========================================

=======
    // CREATE MESSAGE BUBBLE
>>>>>>> Stashed changes
    private JPanel createMessageBubble(ChatMessage chatMessage) {

        boolean fromBusiness =
                "Business".equals(chatMessage.sender);

        JPanel rowPanel = new JPanel(
                new FlowLayout(
                        fromBusiness ? FlowLayout.RIGHT : FlowLayout.LEFT,
                        0,
                        0
                )
        );

        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bubblePanel = new JPanel();

        bubblePanel.setLayout(
                new BoxLayout(bubblePanel, BoxLayout.Y_AXIS)
        );

        bubblePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(7, 9, 7, 9)
                )
        );

        JLabel senderLabel = new JLabel(
                fromBusiness ? "You" : selectedCustomer
        );

        senderLabel.setFont(new Font("Arial", Font.BOLD, 10));

        // ADJUST MESSAGE WIDTH BASED ON CONTENT
        FontMetrics metrics = getFontMetrics(mainFont);
        String wrappedMessage = wrapMessage(
                chatMessage.message, metrics, 220
        );

        JLabel messageLabel = new JLabel(
<<<<<<< Updated upstream
                "<html><div style='width:155px;'>"
                        + escapeHtml(chatMessage.message)
                        + "</div></html>"
=======
                "<html>" + escapeHtml(wrappedMessage) + "</html>"
>>>>>>> Stashed changes
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

        // Limit the bubble width so it does not become too long.

        bubblePanel.setMaximumSize(
                new Dimension(180, Integer.MAX_VALUE)
        );

        rowPanel.add(bubblePanel);

        return rowPanel;
    }

    // WRAP LONG MESSAGES
    private String wrapMessage(
            String message, FontMetrics metrics, int maxWidth
    ) {

        String[] words = message.split("\\s+");

        StringBuilder result = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {

            String testLine = line.length() == 0
                    ? word
                    : line + " " + word;

            if (metrics.stringWidth(testLine) > maxWidth
                    && line.length() > 0) {

                result.append(line).append("\n");
                line = new StringBuilder(word);

            } else {

                line = new StringBuilder(testLine);
            }
        }

        result.append(line);

        return result.toString();
    }

    private String escapeHtml(String text) {

        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
    }

    // =========================================
    // ORDER HISTORY PAGE
    // =========================================

    private void createHistoryPage() {

        historyPage = new JPanel(new BorderLayout(15, 15));

        historyPage.setBackground(backgroundColor);

        historyPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        historyPage.add(
                createPageTitle(
                        "Order History",
                        "View and manage completed and declined custom requests."
                ),
                BorderLayout.NORTH
        );

        historyTableModel = new DefaultTableModel(
                new String[]{
                        "Customer",
                        "Sculpture",
                        "Budget",
                        "Status"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(historyTableModel);

        styleTable(historyTable);

        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.getTableHeader().setResizingAllowed(false);

        historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        historyTable.setFillsViewportHeight(true);

        TableColumnModel historyColumns =
                historyTable.getColumnModel();

        historyColumns.getColumn(0).setPreferredWidth(200);
        historyColumns.getColumn(1).setPreferredWidth(260);
        historyColumns.getColumn(2).setPreferredWidth(150);
        historyColumns.getColumn(3).setPreferredWidth(140);

        JScrollPane historyScrollPane = new JScrollPane(historyTable);

        historyScrollPane.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        historyScrollPane.getViewport().setBackground(Color.WHITE);

        JPanel historyCard = createWhiteCard();

        historyCard.setLayout(new BorderLayout());
        historyCard.add(historyScrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Selected");

        styleButton(deleteButton, fieldColor, mainTextColor);

        deleteButton.setBorder(
                BorderFactory.createLineBorder(borderColor)
        );

        deleteButton.addActionListener(e -> deleteHistoryRecord());

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 0, 0)
        );

        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(deleteButton);

        historyPage.add(historyCard, BorderLayout.CENTER);
        historyPage.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshHistory() {

        if (historyTableModel == null) {
            return;
        }

        historyTableModel.setRowCount(0);
        visibleHistory.clear();

        for (CustomRequest request : customRequests) {

            if ("Completed".equals(request.status)
                    || "Declined".equals(request.status)) {

                visibleHistory.add(request);

                historyTableModel.addRow(new Object[]{
                        request.customerName,
                        request.sculptureName,
                        "₱" + String.format("%.2f", request.budget),
                        request.status
                });
            }
        }

        historyTable.clearSelection();
    }

    private void deleteHistoryRecord() {

        int selectedRow = historyTable.getSelectedRow();

        if (selectedRow < 0) {

            showWarning("Please select a history record to delete.");
            return;
        }

        int modelRow = historyTable.convertRowIndexToModel(selectedRow);

        if (modelRow < 0 || modelRow >= visibleHistory.size()) {

            showWarning("The selected record could not be found.");
            return;
        }

        CustomRequest selectedRequest = visibleHistory.get(modelRow);

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this history record?\n\n"
                        + selectedRequest.sculptureName
                        + "\nCustomer: " + selectedRequest.customerName,
                "Delete History Record",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {

            customRequests.remove(selectedRequest);

            refreshHistory();
            refreshOverview();
            refreshReports();
            refreshRequests();

            JOptionPane.showMessageDialog(
                    this,
                    "History record deleted successfully.",
                    "Order History",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =========================================
    // REPORTS PAGE
    // =========================================

    private void createReportsPage() {

        reportsPage = new JPanel(new BorderLayout(15, 20));

        reportsPage.setBackground(backgroundColor);

        reportsPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        reportsPage.add(
                createPageTitle(
                        "Reports",
                        "A simple summary of your StoneCraft business."
                ),
                BorderLayout.NORTH
        );

        reportsContentPanel = new JPanel(
                new GridLayout(0, 2, 15, 15)
        );

        reportsContentPanel.setBackground(backgroundColor);

        reportsPage.add(
                reportsContentPanel,
                BorderLayout.CENTER
        );
    }

    private void refreshReports() {

        if (reportsContentPanel == null) {
            return;
        }

        int pending = 0;
        int accepted = 0;
        int inProgress = 0;
        int completed = 0;
        int declined = 0;

        double completedBudget = 0;

        for (CustomRequest request : customRequests) {

            switch (request.status) {

                case "Pending":
                    pending++;
                    break;

                case "Accepted":
                    accepted++;
                    break;

                case "In Progress":
                    inProgress++;
                    break;

                case "Completed":
                    completed++;
                    completedBudget += request.budget;
                    break;

                case "Declined":
                    declined++;
                    break;
            }
        }

        int totalStock = 0;

        for (Product product : products) {
            totalStock += product.stock;
        }

        reportsContentPanel.removeAll();

        reportsContentPanel.add(
                createStatCard("Total Products", String.valueOf(products.size()))
        );

        reportsContentPanel.add(
                createStatCard("Total Stock", String.valueOf(totalStock))
        );

        reportsContentPanel.add(
                createStatCard("Pending Requests", String.valueOf(pending))
        );

        reportsContentPanel.add(
                createStatCard("Accepted Requests", String.valueOf(accepted))
        );

        reportsContentPanel.add(
                createStatCard("In Progress", String.valueOf(inProgress))
        );

        reportsContentPanel.add(
                createStatCard("Completed Orders", String.valueOf(completed))
        );

        reportsContentPanel.add(
                createStatCard("Declined Requests", String.valueOf(declined))
        );

        reportsContentPanel.add(
                createStatCard(
                        "Completed Order Budget",
                        "₱" + String.format("%.2f", completedBudget)
                )
        );

        reportsContentPanel.revalidate();
        reportsContentPanel.repaint();
    }

    // =========================================
    // RATINGS AND REVIEWS PAGE
    // =========================================

    private void createReviewsPage() {

        reviewsPage = new JPanel(new BorderLayout(15, 18));

        reviewsPage.setBackground(backgroundColor);

        reviewsPage.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        reviewsPage.add(
                createPageTitle(
                        "Ratings & Reviews",
                        "See what your customers say about StoneCraft products and services."
                ),
                BorderLayout.NORTH
        );

        // MAIN CONTENT

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        mainPanel.setBackground(backgroundColor);

        // REVIEW SUMMARY

        reviewSummaryPanel = new JPanel(new GridLayout(1, 3, 15, 0));

        reviewSummaryPanel.setBackground(backgroundColor);
        reviewSummaryPanel.setPreferredSize(new Dimension(0, 125));

        // REVIEW LIST

        reviewsListPanel = new JPanel();

        reviewsListPanel.setLayout(
                new BoxLayout(reviewsListPanel, BoxLayout.Y_AXIS)
        );

        reviewsListPanel.setBackground(backgroundColor);

        JScrollPane reviewScrollPane = new JScrollPane(reviewsListPanel);

        reviewScrollPane.setBorder(null);

        reviewScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel reviewListHolder = new JPanel(new BorderLayout(0, 10));

        reviewListHolder.setBackground(backgroundColor);

        JLabel reviewsHeading = new JLabel("Customer Feedback");

        reviewsHeading.setFont(new Font("Arial", Font.BOLD, 17));
        reviewsHeading.setForeground(mainTextColor);

        reviewListHolder.add(reviewsHeading, BorderLayout.NORTH);
        reviewListHolder.add(reviewScrollPane, BorderLayout.CENTER);

        mainPanel.add(reviewSummaryPanel, BorderLayout.NORTH);
        mainPanel.add(reviewListHolder, BorderLayout.CENTER);

        reviewsPage.add(mainPanel, BorderLayout.CENTER);
    }

    private void refreshReviews() {

        if (reviewsListPanel == null || reviewSummaryPanel == null) {
            return;
        }

        reviewSummaryPanel.removeAll();
        reviewsListPanel.removeAll();

        int totalReviews = customerReviews.size();

        double totalRating = 0;

        int[] ratingCounts = new int[6];

        for (CustomerReview review : customerReviews) {

            totalRating += review.rating;

            if (review.rating >= 1 && review.rating <= 5) {
                ratingCounts[review.rating]++;
            }
        }

        double averageRating = 0;

        if (totalReviews > 0) {
            averageRating = totalRating / totalReviews;
        }

        // AVERAGE RATING CARD

        JPanel averageCard = createWhiteCard();

        averageCard.setLayout(
                new BoxLayout(averageCard, BoxLayout.Y_AXIS)
        );

        JLabel averageTitle = new JLabel("Average Rating");

        averageTitle.setFont(boldFont);
        averageTitle.setForeground(labelColor);

        averageRatingLabel = new JLabel(
                String.format("%.1f / 5.0", averageRating)
        );

        averageRatingLabel.setFont(new Font("Arial", Font.BOLD, 27));
        averageRatingLabel.setForeground(copperColor);

        ratingStarsLabel = new JLabel(
                getStars((int) Math.round(averageRating))
        );

        ratingStarsLabel.setFont(new Font("Arial", Font.PLAIN, 19));
        ratingStarsLabel.setForeground(copperColor);

        averageCard.add(averageTitle);
        averageCard.add(Box.createVerticalStrut(8));
        averageCard.add(averageRatingLabel);
        averageCard.add(ratingStarsLabel);

        // TOTAL REVIEWS CARD

        JPanel totalCard = createWhiteCard();

        totalCard.setLayout(
                new BoxLayout(totalCard, BoxLayout.Y_AXIS)
        );

        JLabel totalTitle = new JLabel("Total Reviews");

        totalTitle.setFont(boldFont);
        totalTitle.setForeground(labelColor);

        totalReviewsLabel = new JLabel(String.valueOf(totalReviews));

        totalReviewsLabel.setFont(new Font("Arial", Font.BOLD, 27));
        totalReviewsLabel.setForeground(copperColor);

        JLabel totalDescription = new JLabel("Customer feedback received");

        totalDescription.setFont(mainFont);
        totalDescription.setForeground(labelColor);

        totalCard.add(totalTitle);
        totalCard.add(Box.createVerticalStrut(8));
        totalCard.add(totalReviewsLabel);
        totalCard.add(totalDescription);

        // RATING BREAKDOWN CARD

        JPanel breakdownCard = createWhiteCard();

        breakdownCard.setLayout(
                new BoxLayout(breakdownCard, BoxLayout.Y_AXIS)
        );

        JLabel breakdownTitle = new JLabel("Rating Breakdown");

        breakdownTitle.setFont(boldFont);
        breakdownTitle.setForeground(labelColor);

        breakdownCard.add(breakdownTitle);
        breakdownCard.add(Box.createVerticalStrut(6));

        for (int star = 5; star >= 1; star--) {

            JPanel ratingRow = new JPanel(new BorderLayout(8, 0));

            ratingRow.setOpaque(false);

            JLabel starLabel = new JLabel(star + " ★");

            starLabel.setFont(new Font("Arial", Font.BOLD, 11));
            starLabel.setForeground(copperColor);

            JProgressBar ratingBar = new JProgressBar(0, 100);

            int percentage = 0;

            if (totalReviews > 0) {

                percentage = (int) (
                        ratingCounts[star] * 100.0 / totalReviews
                );
            }

            ratingBar.setValue(percentage);
            ratingBar.setStringPainted(false);
            ratingBar.setForeground(copperColor);
            ratingBar.setBackground(fieldColor);
            ratingBar.setPreferredSize(new Dimension(100, 9));

            JLabel countLabel = new JLabel(
                    String.valueOf(ratingCounts[star])
            );

            countLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            countLabel.setForeground(labelColor);

            ratingRow.add(starLabel, BorderLayout.WEST);
            ratingRow.add(ratingBar, BorderLayout.CENTER);
            ratingRow.add(countLabel, BorderLayout.EAST);

            breakdownCard.add(ratingRow);
            breakdownCard.add(Box.createVerticalStrut(3));
        }

        reviewSummaryPanel.add(averageCard);
        reviewSummaryPanel.add(totalCard);
        reviewSummaryPanel.add(breakdownCard);

        // CUSTOMER REVIEW CARDS

        if (customerReviews.isEmpty()) {

            JLabel emptyLabel = new JLabel("No customer reviews yet.");

            emptyLabel.setFont(mainFont);
            emptyLabel.setForeground(labelColor);

            reviewsListPanel.add(Box.createVerticalStrut(20));
            reviewsListPanel.add(emptyLabel);

        } else {

            for (int i = customerReviews.size() - 1; i >= 0; i--) {

                CustomerReview review = customerReviews.get(i);

                JPanel reviewCard = createReviewCard(review);

                reviewCard.setAlignmentX(Component.LEFT_ALIGNMENT);

                reviewsListPanel.add(reviewCard);
                reviewsListPanel.add(Box.createVerticalStrut(12));
            }
        }

        reviewSummaryPanel.revalidate();
        reviewSummaryPanel.repaint();

        reviewsListPanel.revalidate();
        reviewsListPanel.repaint();
    }

    private JPanel createReviewCard(CustomerReview review) {

        JPanel card = new JPanel(new BorderLayout(10, 8));

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(15, 18, 15, 18)
                )
        );

        // TOP INFORMATION

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.setBackground(Color.WHITE);

        JPanel customerPanel = new JPanel();

        customerPanel.setLayout(
                new BoxLayout(customerPanel, BoxLayout.Y_AXIS)
        );

        customerPanel.setBackground(Color.WHITE);

        JLabel customerLabel = new JLabel(review.customerName);

        customerLabel.setFont(boldFont);
        customerLabel.setForeground(mainTextColor);

        JLabel productLabel = new JLabel(
                "Product: " + review.productName
        );

        productLabel.setFont(mainFont);
        productLabel.setForeground(labelColor);

        customerPanel.add(customerLabel);
        customerPanel.add(Box.createVerticalStrut(4));
        customerPanel.add(productLabel);

        JPanel ratingPanel = new JPanel();

        ratingPanel.setLayout(
                new BoxLayout(ratingPanel, BoxLayout.Y_AXIS)
        );

        ratingPanel.setBackground(Color.WHITE);

        JLabel starsLabel = new JLabel(getStars(review.rating));

        starsLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        starsLabel.setForeground(copperColor);

        JLabel dateLabel = new JLabel(review.date);

        dateLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        dateLabel.setForeground(labelColor);

        ratingPanel.add(starsLabel);
        ratingPanel.add(dateLabel);

        topPanel.add(customerPanel, BorderLayout.WEST);
        topPanel.add(ratingPanel, BorderLayout.EAST);

        // REVIEW COMMENT

        JTextArea commentArea = new JTextArea(review.comment);

        commentArea.setFont(mainFont);
        commentArea.setForeground(mainTextColor);
        commentArea.setBackground(Color.WHITE);

        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setEditable(false);
        commentArea.setFocusable(false);

        commentArea.setBorder(
                BorderFactory.createEmptyBorder(8, 0, 0, 0)
        );

        card.add(topPanel, BorderLayout.NORTH);
        card.add(commentArea, BorderLayout.CENTER);

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        return card;
    }

    private String getStars(int rating) {

        StringBuilder stars = new StringBuilder();

        for (int i = 1; i <= 5; i++) {

            if (i <= rating) {
                stars.append("★");
            } else {
                stars.append("☆");
            }
        }

        return stars.toString();
    }

    // =========================================
    // RECEIVE CUSTOMER REVIEW
    // =========================================

    public void receiveCustomerReview(
            String customerName,
            String productName,
            int rating,
            String comment,
            String date
    ) {

        if (customerName == null || customerName.trim().isEmpty()
                || productName == null || productName.trim().isEmpty()
                || comment == null || comment.trim().isEmpty()) {

            return;
        }

        if (rating < 1 || rating > 5) {
            return;
        }

        CustomerReview newReview = new CustomerReview(
                customerName.trim(),
                productName.trim(),
                rating,
                comment.trim(),
                date == null || date.trim().isEmpty()
                        ? "Date not provided"
                        : date.trim()
        );

        customerReviews.add(newReview);

        if (currentPage.equals("Reviews")) {
            refreshReviews();
        }

        if (currentPage.equals("Reports")) {
            refreshReports();
        }
    }

    // =========================================
    // PROFILE MENU
    // =========================================

    private void showProfile() {

        JPopupMenu profileMenu = new JPopupMenu();

        JMenuItem accountItem = new JMenuItem("Business Account");
        JMenuItem logoutItem = new JMenuItem("Log Out");

        accountItem.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
<<<<<<< Updated upstream
                        "StoneCraft Business Account",
                        "StoneCraft",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );
=======
                        "Business Account",
                        "StoneCraft", JOptionPane.INFORMATION_MESSAGE));
>>>>>>> Stashed changes

        logoutItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Log Out",
                    JOptionPane.YES_NO_OPTION);

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

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

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

        button.setFont(boldFont);

        button.setBackground(background);
        button.setForeground(foreground);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(9, 15, 9, 15)
        );

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

    // =========================================
    // PRODUCT CLASS
    // =========================================

    public static class Product {

        public String name;
        public String category;
        public double price;
        public int stock;

        public Product(
                String name,
                String category,
                double price,
                int stock
        ) {

            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }
    }

    // =========================================
    // CUSTOM REQUEST CLASS
    // =========================================

    public static class CustomRequest {

        public String customerName;
        public String sculptureName;
        public String description;
        public String size;
        public String design;
        public double budget;
        public String imagePath;
        public String status;

        public CustomRequest(
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

    // =========================================
    // CHAT MESSAGE CLASS
    // =========================================

<<<<<<< Updated upstream
    public static class ChatMessage {

        public String message;
        public String sender;

        public ChatMessage(String message, String sender) {

=======
    private static class ChatMessage {
        String message;
        String sender;

        ChatMessage(String message, String sender) {
>>>>>>> Stashed changes
            this.message = message;
            this.sender = sender;
        }
    }

    // =========================================
    // CUSTOMER REVIEW CLASS
    // =========================================

    public static class CustomerReview {

        public String customerName;
        public String productName;
        public int rating;
        public String comment;
        public String date;

        public CustomerReview(
                String customerName,
                String productName,
                int rating,
                String comment,
                String date
        ) {

            this.customerName = customerName;
            this.productName = productName;
            this.rating = rating;
            this.comment = comment;
            this.date = date;
        }
    }
}
