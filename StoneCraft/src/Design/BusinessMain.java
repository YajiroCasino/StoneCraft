package Design;
import  Main.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class BusinessMain extends MainDesign {

    //=============================================================
    //color ko to para di kayo mahirapan pa magintindi
    //==============================================================
    static final Color SIDE = new Color(0x2B2A28);
    static final Color ACCENT = new Color(0xB5651D);
    static final Color LINE = new Color(0xE6E1DA);
    static final Color SURFACE = new Color(0xFAF8F5);
    static final Color SELECT = new Color(0xF3E4D4);
    static final Color MUTE = new Color(0x7A736B);
    static final Color TEXT = new Color(0x1F1D1B);
    static final Color OK = new Color(0x3F7D4E);
    static final Color WARN = new Color(0xB7791F);
    static final Color BAD = new Color(0xA63A2B);
    static final Color INFO = new Color(0x3B6EA5);

    //===============================================================
    //Ito fonts ko
    //===============================================================

    static final Font BASE = new Font("Segoe UI", Font.PLAIN, 14);
    static final Font BOLD = new Font("Segoe UI", Font.BOLD, 14);
    static final Font SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    static final String PESO = "\u20B1";

    JPanel businessMainPanel = new JPanel();
    JPanel content = new JPanel(new CardLayout());

    String[] pageNames = {"dashboard", "analytics", "products", "orders", "custom",
            "inventory", "payouts", "messages", "shop", "logout"};

    //=======================================
    //nav button lang yan wag ioverthink
    //=========================================
    NavButton[] buttons = {
            new NavButton("Dashboard", null),
            new NavButton("Sales Analytics", null),
            new NavButton("My Products", null),
            new NavButton("Orders", "4"),
            new NavButton("Custom Requests", "3"),
            new NavButton("Inventory", null),
            new NavButton("Payments & Payouts", null),
            new NavButton("Messages", "2"),
            new NavButton("Shop Profile", null),
            new NavButton("Logout", null)
    };

    // ---------- sample data ----------
    String[] orderCols = {"Order", "Buyer", "Item", "Total", "Status"};
    String[] statuses = {"New", "Preparing", "Shipped", "Delivered", "Cancelled"};
    Object[][] orderData = {
            {"#1042", "Maria D.", "Carved Stone Lamp", PESO + "3,200", "New"},
            {"#1041", "Paolo R.", "Marble Coaster Set", PESO + "1,800", "New"},
            {"#1040", "Ana L.", "Stone Bird Bath", PESO + "4,100", "Preparing"},
            {"#1039", "Ken S.", "Granite Mortar & Pestle", PESO + "1,250", "Shipped"},
            {"#1038", "Liza M.", "River Stone Planter", PESO + "2,400", "Delivered"}
    };
    Main main;


    Object[][] productData = {

    };

    Color[] swatches = {
            new Color(0x8C8A82), new Color(0xA9A296), new Color(0x6E7B68), new Color(0xA88A68),
            new Color(0x4A4D55), new Color(0x9C6A4E), new Color(0x7B8A90), new Color(0xC9B99B)
    };

    DefaultTableModel ordersModel, dashModel;
    JWindow toastWin;
    Timer toastTimer;

    java.util.Map<String, Chat> chats = buildChats();
    JPanel threadPage;
    JTextField threadInput;
    JScrollPane threadScroll;

    public BusinessMain() {
        JPanel sideBar = new JPanel();
        JPanel right = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        right.add(topBar(), BorderLayout.NORTH);
        right.add(content, BorderLayout.CENTER);

        businessMainPanel.setLayout(new BorderLayout());
        businessMainPanel.add(sideBar, BorderLayout.WEST);
        businessMainPanel.add(right, BorderLayout.CENTER);

        buildSideBar(sideBar);

        content.add(dashboardPanelContent(), pageNames[0]);
        content.add(saleAnalyticPanelContent(), pageNames[1]);
        content.add(myProductsPanelContent(), pageNames[2]);
        content.add(ordersPanelContent(), pageNames[3]);
        content.add(customRequestPanelContent(), pageNames[4]);
        content.add(inventoryPanelContent(), pageNames[5]);
        content.add(payoutsPanelContent(), pageNames[6]);
        content.add(messagesPanelContent(), pageNames[7]);
        content.add(shopPanelContent(), pageNames[8]);
        content.add(logoutPanelContent(), pageNames[9]);

        // keep dashboard table + Orders badge in sync with the Orders page
        ordersModel.addTableModelListener(e -> syncOrders());
        syncOrders();
        showPage(0);

        add(businessMainPanel);
        setVisible(true);
    }

    void showPage(int index) {
        ((CardLayout) content.getLayout()).show(content, pageNames[index]);
        for (int i = 0; i < buttons.length; i++) buttons[i].setActive(i == index);
    }

    void syncOrders() {
        int n = 0;
        for (int r = 0; r < ordersModel.getRowCount(); r++) {
            if ("New".equals(ordersModel.getValueAt(r, 4))) n++;
            if (r < dashModel.getRowCount())
                for (int c = 0; c < 5; c++) dashModel.setValueAt(ordersModel.getValueAt(r, c), r, c);
        }
        buttons[3].setBadge(String.valueOf(n));
    }

    // =====================================================
    //  SIDEBAR + TOP BAR
    // =====================================================

    void buildSideBar(JPanel sideBar) {
        sideBar.setBackground(SIDE);
        sideBar.setPreferredSize(new Dimension(232, 0));
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBorder(new EmptyBorder(18, 12, 12, 12));

        JPanel logo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = aa(g);
                g2.setColor(ACCENT);
                g2.fillRoundRect(8, 4, 28, 28, 8, 8);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
                g2.drawString("S", 17, 24);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 17));
                g2.drawString("StoneCraft", 46, 17);
                g2.setColor(new Color(0xA9A39B));
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g2.drawString("Seller center", 46, 31);
            }
        };
        logo.setOpaque(false);
        logo.setPreferredSize(new Dimension(208, 38));
        logo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        sideBar.add(logo);

        for (int i = 0; i < buttons.length; i++) {
            String section = i == 0 ? "OVERVIEW" : i == 2 ? "MANAGE" : i == 6 ? "MONEY" : i == 7 ? "ACCOUNT" : null;
            if (section != null) {
                JLabel l = new JLabel(section);
                l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                l.setForeground(new Color(0x8A857E));
                l.setBorder(new EmptyBorder(18, 8, 6, 0));
                l.setAlignmentX(Component.LEFT_ALIGNMENT);
                sideBar.add(l);
            }
            final int idx = i;
            NavButton b = buttons[i];
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            b.setPreferredSize(new Dimension(208, 38));
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
            b.addActionListener(e -> showPage(idx));
            sideBar.add(b);
            sideBar.add(Box.createVerticalStrut(2));
        }
    }

    JPanel topBar() {
        JPanel bar = new JPanel(new BorderLayout(16, 0));
        bar.setBackground(Color.WHITE);
        bar.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, LINE), new EmptyBorder(12, 28, 12, 28)));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);

        JComponent avatar = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = aa(g);
                g2.setColor(ACCENT);
                g2.fillOval(0, 0, 32, 32);
                g2.setColor(Color.WHITE);
                g2.setFont(BOLD);
                g2.drawString("J", 12, 21);
            }
        };
        avatar.setPreferredSize(new Dimension(32, 32));

        bar.add(left, BorderLayout.CENTER);
        bar.add(avatar, BorderLayout.EAST);
        return bar;
    }

    // =====================================================
    //  PAGES
    // =====================================================

    public JPanel dashboardPanelContent() {

        JPanel page = page("Kumusta, Juan", "Ito ang takbo ng shop mo ngayong linggo.");

        JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
        stats.setOpaque(false);
        stats.add(statCard("Sales this week", PESO + "48,350", "+12% vs last week"));
        stats.add(statCard("New orders", "4", "2 need shipping today"));
        stats.add(statCard("Custom requests", "3", "1 waiting for your quote"));
        stats.add(statCard("Shop rating", "4.7", "from 212 reviews"));

        JPanel chart = card("Daily sales", null, -1);
        chart.add(new BarChart(new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"},
                new int[]{45, 62, 38, 92, 70, 80, 30}), BorderLayout.CENTER);

        JPanel low = card("Low stock", "Restock", 5);
        JPanel lowList = vbox();
        for (Object[] p : productData)
            if ((Integer) p[4] < 4) lowList.add(lowRow(p[0].toString(), p[4] + " left", BAD));
        low.add(lowList, BorderLayout.CENTER);

        dashModel = makeModel(orderCols, firstRows(orderData, 4), -1);
        JTable dashTable = new JTable(dashModel);
        styleTable(dashTable, 60, 90, 160, 70, 100);
        dashTable.getColumnModel().getColumn(4).setCellRenderer(new PillRenderer());
        JPanel recent = card("Recent orders", "View all", 3);
        JPanel tp = new JPanel(new BorderLayout());
        tp.setOpaque(false);
        tp.add(dashTable.getTableHeader(), BorderLayout.NORTH);
        tp.add(dashTable, BorderLayout.CENTER);
        recent.add(tp, BorderLayout.CENTER);

        JPanel reqs = card("Custom requests", "View all", 4);
        JPanel reqList = vbox();
        reqList.add(replyRow("Maria D.", null, 7));
        reqList.add(replyRow("Paolo R.", null, 7));
        reqs.add(reqList, BorderLayout.CENTER);

        page.add(stackScroll(stats, twoCols(chart, low), twoCols(recent, reqs)), BorderLayout.CENTER);
        return page;
    }

    public JPanel saleAnalyticPanelContent() {
        JPanel page = page("Sales Analytics", "Best sellers this month.");
        JPanel c = card(null, null, -1);
        JPanel list = vbox();
        list.add(lowRow("Stone Bird Bath", "18 sold \u00B7 " + PESO + "73,800", TEXT));
        list.add(lowRow("Carved Stone Lamp", "14 sold \u00B7 " + PESO + "44,800", TEXT));
        list.add(lowRow("River Stone Planter", "11 sold \u00B7 " + PESO + "26,400", TEXT));
        c.add(list, BorderLayout.CENTER);
        page.add(stackScroll(c), BorderLayout.CENTER);
        return page;
    }

    public JPanel myProductsPanelContent() {
        JPanel page = page("My Products","Your current products.");

        //===================================================
        //searchbar to
        //===================================================

        JTextField search = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2 = aa(g);
                    g2.setColor(MUTE);
                    g2.setFont(BASE);
                    g2.drawString("Search products...", getInsets().left,
                            (getHeight() + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2);
                }
            }
        };
        search.setFont(BASE);
        search.setBackground(SURFACE);
        search.setBorder(new RoundBorder(8, 8, 12, 8, 12));
        search.setPreferredSize(new Dimension(320, 38));

        //================================================================
        // rows and column ng product
        //================================================================

        Object[][] rows = new Object[productData.length][6];
        for (int i = 0; i < productData.length; i++) {
            System.arraycopy(productData[i], 0, rows[i], 0, 5);
            rows[i][5] = "";
        }
        DefaultTableModel model = makeModel(new String[]{"Product", "id", "Category", "Price", "Stock", ""}, rows, -1);
        JTable table = new JTable(model);
        styleTable(table, 260, 70, 120, 100, 80, 70);

        // product name with color thumbnail
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                super.getTableCellRendererComponent(t, v, s, f, r, c);
                setIcon(new Swatch(swatchFor(String.valueOf(v))));
                setIconTextGap(10);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
        actionColumn(table, 5, "Edit", r -> toast("Editing " + model.getValueAt(r, 0)));


        //========================================================
        //add products button
        //========================================================
        JButton add = pillButton("+ Add product", true);
        add.addActionListener(e -> showAddProductDialog(model));

        //=======================================================
        //para sa searchBar to
        //=======================================================

        JButton searchButton = pillButton("Search ", true);
        searchButton.addActionListener(e ->{

        });


        //==========================================================
        //for top left andito yung search bar tapos addproduct
        //==========================================================

        JPanel tools = new JPanel(new BorderLayout());
        tools.setOpaque(false);
        tools.setBorder(new EmptyBorder(0, 0, 14, 0));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setOpaque(false);
        searchPanel.add(search);
        searchPanel.add(searchButton);

        tools.add(searchPanel, BorderLayout.WEST);
        tools.add(add, BorderLayout.EAST);

        //===================================================
        //body ng my product
        //====================================================

        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        body.add(tools, BorderLayout.NORTH);
        body.add(tableCard(table), BorderLayout.CENTER);
        page.add(body, BorderLayout.CENTER);
        return page;
    }


    //====================================================================
    // sa orderpanel panel to
    //====================================================================
    public JPanel ordersPanelContent() {
        JPanel page = page("Orders", "I-update ang status para makita ng buyer sa Track Order.");
        ordersModel = makeModel(orderCols, orderData, 4);
        JTable table = new JTable(ordersModel);
        styleTable(table, 80, 120, 220, 90, 140);
        table.getColumnModel().getColumn(4).setCellRenderer(new PillRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox<>(statuses)));
        page.add(tableCard(table), BorderLayout.CENTER);
        return page;
    }


    //========================================================================
    //sa custom request panel to
    //========================================================================

    public JPanel customRequestPanelContent() {
        JPanel page = page("Custom Requests", "Mga order na gawa ayon sa gusto ng buyer.");
        JPanel c = card(null, null, -1);
        JPanel list = vbox();
        list.add(replyRow("Maria D.", null, 7));
        list.add(replyRow("Paolo R.", null, 7));
        list.add(replyRow("Ana L.", null, 7));
        c.add(list, BorderLayout.CENTER);
        page.add(stackScroll(c), BorderLayout.CENTER);
        return page;
    }

    //=======================================================================
    // inventory panel to
    //=======================================================================

    public JPanel inventoryPanelContent() {
        JPanel page = page("Inventory", "I-update ang stock bago maubos.");
        Object[][] rows = new Object[productData.length][3];
        for (int i = 0; i < productData.length; i++) {
            rows[i][0] = productData[i][0];
            rows[i][1] = productData[i][4];
            rows[i][2] = "";
        }
        DefaultTableModel model = makeModel(new String[]{"Product", "In stock", "Update"}, rows, -1);
        JTable table = new JTable(model);
        styleTable(table, 320, 100, 140);

        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                super.getTableCellRendererComponent(t, v, s, f, r, c);
                boolean lowStock = (Integer) v < 4;
                setForeground(lowStock ? BAD : TEXT);
                setFont(lowStock ? BOLD : BASE);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
        actionColumn(table, 2, "+ Add stock", r -> showAddStockDialog(model, r));
        page.add(tableCard(table), BorderLayout.CENTER);
        return page;
    }

    //=============================================================
    //sa payment panel to
    //=============================================================

    public JPanel payoutsPanelContent() {
        JPanel page = page("Payments & Payouts", "Available balance: " + PESO + "31,200. Next payout on Nov 10 via GCash.");
        JButton b = pillButton("Request payout", true);
        b.addActionListener(e -> toast("Payout requested"));
        page.add(toolRow(b), BorderLayout.CENTER);
        return page;
    }

    //============================================================
    //sa message panel to
    //============================================================

    public JPanel messagesPanelContent() {
        JPanel page = page("Messages", "2 unread messages from buyers.");
        JPanel c = card(null, null, -1);
        JPanel list = vbox();
        list.add(replyRow("Maria D.", null, 7));
        list.add(replyRow("Paolo R.", null, 7));
        c.add(list, BorderLayout.CENTER);
        page.add(stackScroll(c), BorderLayout.CENTER);
        return page;
    }

    //=============================================
    //for shop profie to
    //=============================================

    public JPanel shopPanelContent() {
        JPanel page = page("Shop Profile", "StoneCraft \u00B7 Cebu City \u00B7 Handmade stone goods since 2019.");
        JButton b = pillButton("Save changes", true);
        b.addActionListener(e -> toast("Profile saved"));
        page.add(toolRow(b), BorderLayout.CENTER);
        return page;
    }

    //=============================================
    //sa logout panel
    //=============================================
    public JPanel logoutPanelContent() {

        return page("Logged out", "Close and reopen the app to sign in again.");
    }


    //=================================================
    //for product jpanel to
    //=================================================


    //variable for textField ayoko na kasing ilagay pa sa taas since ang layo na



    void showAddProductDialog(DefaultTableModel model) {
        JDialog dlg = new JDialog(this, "Add product", true);


        //====================================================================
        //variable ko for JForm Field so bala ka jan
        //====================================================================

        JTextField nameProduct = formField();
        JTextField priceProduct = formField();
        JTextField stockProduct = formField();
        JComboBox<String> categoryProduct;

        categoryProduct = new JComboBox<>(new String[]{"Kitchen", "Decor", "Garden", "Lighting"});
        categoryProduct.setFont(BASE);
        categoryProduct.setBackground(SURFACE);
        categoryProduct.setForeground(TEXT);
        categoryProduct.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryProduct.setPreferredSize(new Dimension(300, 38));
        categoryProduct.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JPanel form = vbox();
        form.add(formRow("Product name", nameProduct));
        form.add(formRow("Category", categoryProduct));
        JPanel priceStock = new JPanel(new GridLayout(1, 2, 12, 0));
        priceStock.setOpaque(false);
        priceStock.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceStock.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        priceStock.add(formRow("Price (" + PESO + ")", priceProduct));
        priceStock.add(formRow("Stock", stockProduct));
        form.add(priceStock);

        JLabel error = errorLabel();
        form.add(error);

        JButton cancel = pillButton("Cancel", false);
        JButton save = pillButton("Save product", true);
        cancel.addActionListener(e -> dlg.dispose());
        save.addActionListener(e -> {

            //yun trim() tinatagal nun yung space sa hulihan at unahan linagay ko na dito baka atakihin ng short term memory eh
            String productName = nameProduct.getText().trim();
            String productPrice = priceProduct.getText().trim();
            String productStock = stockProduct.getText().trim();
            String productCategory = categoryProduct.getSelectedItem().toString().trim();

            if (productName.isEmpty()) {
                error.setText("Invalid name of product!");
                return;
            }

            double pPrice;
            int pStock;
            try {
                //same lang sa putting a value pero since dito ko lang naman gagamitin dito ko nalang linagay
                pPrice = Double.parseDouble(priceProduct.getText().trim());
                pStock = Integer.parseInt(stockProduct.getText().trim());

                if (pPrice < 0 || pStock < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                error.setText("Price and Stock need to be a valid number!");
                return;
            }

            //for loop para sa id: hanapin ang pinakamataas na id sa table tapos dagdagan ng 1
            int maxId = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                String existing = String.valueOf(model.getValueAt(i, 1));
                String digits = existing.replaceAll("\\D", "");
                if (!digits.isEmpty()) maxId = Math.max(maxId, Integer.parseInt(digits));
            }
            String productId = String.format("P%03d", maxId + 1);           // P001, P002, P003...




            //====================================================================================
            //imomodify koto depende sa situation pwedeng directed sa main orsa product na gagawing
            //pero mas gusto ko main para magawa sya arraylist
            //=====================================================================================

            Main.productAdd(productId, productName, productPrice, productStock, productCategory);

            model.addRow(new Object[]{productName, productId, productCategory, pPrice, pStock, ""});
            dlg.dispose();
        });
        openDialog(dlg, "Add product", "Fill in the details of your new product.", form, cancel, save);
    }

    //=================================================
    //add stock jpanel to
    //=================================================

    void showAddStockDialog(DefaultTableModel model, int row) {
        JDialog dlg = new JDialog(this, "Add stock", true);
        int current = (Integer) model.getValueAt(row, 1);

        JTextField qtyF = formField();
        qtyF.setText("10");

        JPanel form = vbox();
        form.add(formRow("Quantity to add", qtyF));
        JLabel error = errorLabel();
        form.add(error);

        JButton cancel = pillButton("Cancel", false);
        JButton save = pillButton("Add stock", true);
        cancel.addActionListener(e -> dlg.dispose());
        save.addActionListener(e -> {
            int qty;
            try {
                qty = Integer.parseInt(qtyF.getText().trim());
                if (qty <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                error.setText("Ilagay ang valid na number (higit sa 0).");
                return;
            }
            model.setValueAt(current + qty, row, 1);
            dlg.dispose();
            toast("Stock updated: +" + qty);
        });

        openDialog(dlg, "Add stock", model.getValueAt(row, 0) + " \u00B7 currently " + current + " in stock", form, cancel, save);
    }

    //=================================================
    //reply -> bagong panel na may request at conversation
    //=================================================
    void showThread(Chat chat, int backPage) {
        if (threadPage != null) content.remove(threadPage);
        threadPage = threadPanel(chat, backPage);
        content.add(threadPage, "thread");
        content.revalidate();
        content.repaint();
        ((CardLayout) content.getLayout()).show(content, "thread");
        for (int i = 0; i < buttons.length; i++) buttons[i].setActive(i == backPage);
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = threadScroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
            threadInput.requestFocusInWindow();
        });
    }


    JPanel threadPanel(Chat chat, int backPage) {
        String sub = chat.request == null ? "Buyer conversation" : "Custom request \u00B7 " + chat.request;
        JPanel page = page(chat.buyer, sub);

        JButton back = pillButton("\u2190 Back", false);
        back.addActionListener(e -> showPage(backPage));
        JPanel backRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backRow.setOpaque(false);
        backRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        backRow.add(back);

        JPanel top = vbox();
        top.add(backRow);
        if (chat.request != null) {
            top.add(Box.createVerticalStrut(12));
            JPanel details = card("Request details", null, -1);
            JPanel rows = vbox();
            rows.add(lowRow("Request", chat.request, TEXT));
            rows.add(lowRow("Budget", chat.budget, TEXT));
            rows.add(lowRow("Needed by", chat.needed, TEXT));
            rows.add(lowRow("Status", chat.status, WARN));
            details.add(rows, BorderLayout.CENTER);
            details.setAlignmentX(Component.LEFT_ALIGNMENT);
            top.add(details);
        }

        JPanel thread = vbox();
        for (String[] m : chat.msgs) thread.add(bubbleRow("me".equals(m[0]), m[1], m[2]));
        Wrap wrap = new Wrap();
        wrap.add(thread, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(wrap);
        sp.setBorder(null);
        sp.getViewport().setBackground(Color.WHITE);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        threadScroll = sp;
        JPanel convo = card("Conversation", null, -1);
        convo.add(sp, BorderLayout.CENTER);

        JTextField input = new JTextField();
        input.setFont(BASE);
        input.setForeground(TEXT);
        input.setBackground(SURFACE);
        input.setBorder(new RoundBorder(8, 8, 12, 8, 12));
        input.setPreferredSize(new Dimension(300, 38));
        threadInput = input;
        JButton send = pillButton("Send reply", true);
        Runnable doSend = () -> {
            String text = input.getText().trim();
            if (text.isEmpty()) return;
            chat.say(true, text, "Just now");
            toast("Reply sent to " + chat.buyer);
            showThread(chat, backPage);
        };
        send.addActionListener(e -> doSend.run());
        input.addActionListener(e -> doSend.run());
        JPanel composer = new JPanel(new BorderLayout(8, 0));
        composer.setOpaque(false);
        composer.add(input, BorderLayout.CENTER);
        composer.add(send, BorderLayout.EAST);

        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);
        body.add(top, BorderLayout.NORTH);
        body.add(convo, BorderLayout.CENTER);
        body.add(composer, BorderLayout.SOUTH);
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    JPanel bubbleRow(boolean mine, String text, String time) {
        Bubble b = new Bubble(text, mine);
        JLabel t = new JLabel(time);
        t.setFont(SMALL);
        t.setForeground(MUTE);
        float ax = mine ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT;
        b.setAlignmentX(ax);
        t.setAlignmentX(ax);
        JPanel col = vbox();
        col.add(b);
        col.add(Box.createVerticalStrut(2));
        col.add(t);
        Row r = new Row(new BorderLayout());
        r.setBorder(new EmptyBorder(6, 0, 6, 0));
        r.add(col, mine ? BorderLayout.EAST : BorderLayout.WEST);
        return r;
    }

    java.util.Map<String, Chat> buildChats() {
        java.util.Map<String, Chat> m = new java.util.LinkedHashMap<>();
        m.put("Maria D.", new Chat("Maria D.", "Stone sign, \"Dela Cruz Home\"", PESO + "3,500", "Nov 28", "Waiting for your quote")
                .say(false, "Hello po! Gusto ko po sana ng stone sign na may nakasulat na \"Dela Cruz Home\".", "Nov 6 \u00B7 9:12 AM")
                .say(false, "Pwede po ba gawing mas maliit yung sign?", "Nov 8 \u00B7 2:40 PM"));
        m.put("Paolo R.", new Chat("Paolo R.", "Pair of granite bookends", PESO + "2,000", "Dec 5", "New request")
                .say(false, "Hi! Interested po ako sa pair of granite bookends.", "Nov 7 \u00B7 4:05 PM")
                .say(false, "Magkano po shipping papuntang Davao?", "Nov 8 \u00B7 6:30 PM"));
        m.put("Ana L.", new Chat("Ana L.", "Marble serving tray, 40 cm", PESO + "4,200", "Dec 12", "New request")
                .say(false, "Hello po, pwede po ba 40 cm na marble serving tray?", "Nov 9 \u00B7 8:15 AM"));
        return m;
    }

    void openDialog(JDialog dlg, String title, String subtitle, JComponent center, JButton cancel, JButton primary) {
        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(Color.WHITE);
        root.setBorder(new EmptyBorder(22, 24, 20, 24));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 20));
        t.setForeground(TEXT);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel s = new JLabel("<html><div style='width:340px'>" + subtitle.replace("<", "&lt;") + "</div></html>");
        s.setFont(SMALL);
        s.setForeground(MUTE);
        s.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel head = vbox();
        head.add(t);
        head.add(Box.createVerticalStrut(2));
        head.add(s);
        root.add(head, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);
        btns.add(cancel);
        btns.add(primary);
        root.add(btns, BorderLayout.SOUTH);

        dlg.setContentPane(root);
        dlg.getRootPane().setDefaultButton(primary);
        dlg.setResizable(false);
        dlg.pack();
        dlg.setSize(420, dlg.getHeight());
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    JLabel errorLabel() {
        JLabel error = new JLabel(" ");
        error.setFont(SMALL);
        error.setForeground(BAD);
        error.setAlignmentX(Component.LEFT_ALIGNMENT);
        return error;
    }

    JTextField formField() {
        JTextField f = new JTextField();
        f.setFont(BASE);
        f.setForeground(TEXT);
        f.setBackground(SURFACE);
        f.setBorder(new RoundBorder(8, 8, 12, 8, 12));
        f.setPreferredSize(new Dimension(300, 38));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        return f;
    }

    JPanel formRow(String label, JComponent field) {
        JPanel p = vbox();
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(SMALL);
        l.setForeground(MUTE);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(4));
        p.add(field);
        p.add(Box.createVerticalStrut(12));
        return p;
    }

    // =====================================================
    //  HELPERS
    // =====================================================

    static Graphics2D aa(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }

    static Color statusColor(String s) {
        switch (s) {
            case "New": return INFO;
            case "Preparing": return WARN;
            case "Shipped": return ACCENT;
            case "Delivered": return OK;
            default: return BAD;
        }
    }

    Color swatchFor(String name) {
        for (int i = 0; i < productData.length; i++)
            if (productData[i][0].equals(name)) return swatches[i];
        return LINE;
    }

    Object[][] firstRows(Object[][] src, int n) {
        Object[][] out = new Object[n][];
        for (int i = 0; i < n; i++) out[i] = src[i].clone();
        return out;
    }

    DefaultTableModel makeModel(String[] cols, Object[][] data, int editableCol) {
        Object[][] copy = new Object[data.length][];
        for (int i = 0; i < data.length; i++) copy[i] = data[i].clone();
        return new DefaultTableModel(copy, cols) {
            @Override
            public boolean isCellEditable(int r, int c) { return c == editableCol; }
        };
    }

    JPanel page(String title, String subtitle) {
        JPanel p = new JPanel(new BorderLayout(0, 20));
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(26, 28, 20, 28));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 24));
        t.setForeground(TEXT);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel s = new JLabel(subtitle);
        s.setFont(BASE);
        s.setForeground(MUTE);
        s.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel head = vbox();
        head.add(t);
        head.add(Box.createVerticalStrut(4));
        head.add(s);
        p.add(head, BorderLayout.NORTH);
        return p;
    }

    JPanel vbox() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }

    JPanel toolRow(JComponent... comps) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        p.setOpaque(false);
        for (JComponent c : comps) p.add(c);
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(p, BorderLayout.NORTH);
        return wrap;
    }

    // white card with optional title and optional link on the right (jumps to a page)
    JPanel card(String title, String link, int goPage) {
        JPanel c = new JPanel(new BorderLayout(0, 12));
        c.setBackground(Color.WHITE);
        c.setBorder(new RoundBorder(12, 14, 16, 14, 16));
        if (title != null) {
            JPanel head = new JPanel(new BorderLayout());
            head.setOpaque(false);
            JLabel l = new JLabel(title);
            l.setFont(new Font("Segoe UI", Font.BOLD, 15));
            head.add(l, BorderLayout.WEST);
            if (link != null) {
                JLabel a = new JLabel(link);
                a.setFont(new Font("Segoe UI", Font.BOLD, 12));
                a.setForeground(ACCENT);
                a.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                a.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) { showPage(goPage); }
                });
                head.add(a, BorderLayout.EAST);
            }
            c.add(head, BorderLayout.NORTH);
        }
        return c;
    }

    JPanel statCard(String label, String value, String note) {
        JPanel c = vbox();
        c.setBackground(Color.WHITE);
        c.setOpaque(true);
        c.setBorder(new RoundBorder(12, 14, 16, 14, 16));
        JLabel l = new JLabel(label);
        l.setForeground(MUTE);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 26));
        JLabel n = new JLabel(note);
        n.setForeground(OK);
        n.setFont(SMALL);
        c.add(l);
        c.add(Box.createVerticalStrut(4));
        c.add(v);
        c.add(Box.createVerticalStrut(2));
        c.add(n);
        return c;
    }

    JPanel tableCard(JTable table) {
        JPanel c = card(null, null, -1);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(null);
        sp.getViewport().setBackground(Color.WHITE);
        c.add(sp, BorderLayout.CENTER);
        return c;
    }

    // left : right = 1.6 : 1
    JPanel twoCols(JComponent left, JComponent right) {
        JPanel p = new JPanel(null) {
            @Override
            public void doLayout() {
                int w = getWidth() - 14, lw = (int) (w * 0.615);
                left.setBounds(0, 0, lw, getHeight());
                right.setBounds(lw + 14, 0, w - lw, getHeight());
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, Math.max(left.getPreferredSize().height, right.getPreferredSize().height));
            }
        };
        p.setOpaque(false);
        p.add(left);
        p.add(right);
        return p;
    }

    JScrollPane stackScroll(JComponent... comps) {
        JPanel box = vbox();
        for (int i = 0; i < comps.length; i++) {
            comps[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            box.add(comps[i]);
            if (i < comps.length - 1) box.add(Box.createVerticalStrut(14));
        }
        Wrap wrap = new Wrap();
        wrap.add(box, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(wrap);
        sp.setBorder(null);
        sp.getViewport().setBackground(Color.WHITE);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    // "Name ........ right text" row with bottom line
    JPanel lowRow(String left, String right, Color rightColor) {
        Row r = new Row(new BorderLayout());
        r.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, LINE), new EmptyBorder(9, 0, 9, 0)));
        JLabel l = new JLabel(left);
        l.setFont(BASE);
        JLabel v = new JLabel(right);
        v.setForeground(rightColor);
        v.setFont(rightColor == TEXT ? BASE : BOLD);
        r.add(l, BorderLayout.WEST);
        r.add(v, BorderLayout.EAST);
        return r;
    }

    JPanel replyRow(String buyer, String message, int fromPage) {
        Row r = new Row(new BorderLayout(12, 0));
        r.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, LINE), new EmptyBorder(10, 0, 10, 0)));

        JPanel text = vbox();
        JLabel t = new JLabel(buyer);
        t.setFont(BOLD);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        text.add(t);
        if (message != null) {
            JLabel s = new JLabel(message);
            s.setFont(SMALL);
            s.setForeground(MUTE);
            s.setAlignmentX(Component.LEFT_ALIGNMENT);
            text.add(Box.createVerticalStrut(2));
            text.add(s);
        }

        JButton reply = pillButton("Reply", true);
        reply.addActionListener(e -> showThread(chats.get(buyer), fromPage));
        JPanel right = new JPanel(new GridBagLayout());
        right.setOpaque(false);
        right.add(reply);

        r.add(text, BorderLayout.CENTER);
        r.add(right, BorderLayout.EAST);
        return r;
    }

    JButton pillButton(String text, boolean primary) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBackground(primary ? ACCENT : Color.WHITE);
        b.setForeground(primary ? Color.WHITE : TEXT);
        b.setBorder(new CompoundBorder(new LineBorder(primary ? ACCENT : LINE, 1), new EmptyBorder(7, 14, 7, 14)));
        return b;
    }

    void styleTable(JTable t, int... widths) {
        t.setRowHeight(38);
        t.setFont(BASE);
        t.setGridColor(LINE);
        t.setShowVerticalLines(false);
        t.setShowHorizontalLines(true);
        t.setIntercellSpacing(new Dimension(0, 1));
        t.setSelectionBackground(SELECT);
        t.setSelectionForeground(TEXT);
        t.setFillsViewportHeight(true);
        t.setBackground(Color.WHITE);
        for (int i = 0; i < widths.length; i++) t.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tb, Object v, boolean s, boolean f, int r, int c) {
                super.getTableCellRendererComponent(tb, v, s, false, r, c);
                setForeground(TEXT);
                setFont(BASE);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        });

        JTableHeader h = t.getTableHeader();
        h.setReorderingAllowed(false);
        h.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tb, Object v, boolean s, boolean f, int r, int c) {
                super.getTableCellRendererComponent(tb, v, false, false, r, c);
                setBackground(Color.WHITE);
                setForeground(MUTE);
                setFont(SMALL);
                setHorizontalAlignment(SwingConstants.LEFT);
                setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, LINE), new EmptyBorder(0, 10, 0, 10)));
                return this;
            }
        });
        h.setPreferredSize(new Dimension(h.getPreferredSize().width, 34));
    }

    // clickable text column (like "Edit" / "+ Add stock")
    void actionColumn(JTable t, int col, String text, Consumer<Integer> onClick) {
        t.getColumnModel().getColumn(col).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tb, Object v, boolean s, boolean f, int r, int c) {
                super.getTableCellRendererComponent(tb, text, s, false, r, c);
                setForeground(ACCENT);
                setFont(BOLD);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int vr = t.rowAtPoint(e.getPoint()), vc = t.columnAtPoint(e.getPoint());
                if (vr >= 0 && vc == col) onClick.accept(t.convertRowIndexToModel(vr));
            }
        });
    }

    void toast(String msg) {
        if (toastWin != null) toastWin.dispose();
        JLabel l = new JLabel(msg);
        l.setFont(BASE);
        l.setForeground(Color.WHITE);
        l.setOpaque(true);
        l.setBackground(TEXT);
        l.setBorder(new EmptyBorder(10, 16, 10, 16));
        toastWin = new JWindow(this);
        toastWin.add(l);
        toastWin.pack();
        Point p = getLocationOnScreen();
        toastWin.setLocation(p.x + getWidth() - toastWin.getWidth() - 24, p.y + getHeight() - toastWin.getHeight() - 48);
        toastWin.setVisible(true);
        if (toastTimer != null) toastTimer.stop();
        toastTimer = new Timer(2000, e -> toastWin.dispose());
        toastTimer.setRepeats(false);
        toastTimer.start();
    }

    // =====================================================
    //  SMALL CUSTOM COMPONENTS
    // =====================================================

    // sidebar item with hover, active color and badge
    static class NavButton extends JButton {
        String label, badge;
        boolean active, hover;

        NavButton(String label, String badge) {
            this.label = label;
            this.badge = badge;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { hover = true; repaint(); }

                @Override
                public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            });
        }

        void setActive(boolean a) { active = a; repaint(); }

        void setBadge(String b) { badge = b; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = aa(g);
            int w = getWidth(), h = getHeight();
            if (active) {
                g2.setColor(ACCENT);
                g2.fillRoundRect(0, 0, w, h, 12, 12);
            } else if (hover) {
                g2.setColor(new Color(255, 255, 255, 24));
                g2.fillRoundRect(0, 0, w, h, 12, 12);
            }
            g2.setFont(BASE);
            g2.setColor(active ? Color.WHITE : new Color(0xD8D3CC));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, 14, (h + fm.getAscent() - fm.getDescent()) / 2);
            if (badge != null && !badge.equals("0")) {
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                fm = g2.getFontMetrics();
                int bw = fm.stringWidth(badge) + 14, bx = w - bw - 10, by = (h - 18) / 2;
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fillRoundRect(bx, by, bw, 18, 18, 18);
                g2.setColor(Color.WHITE);
                g2.drawString(badge, bx + 7, by + 13);
            }
        }
    }

    // rounded 1px border
    static class RoundBorder extends AbstractBorder {
        int radius;
        Insets insets;

        RoundBorder(int radius, int top, int left, int bottom, int right) {
            this.radius = radius;
            this.insets = new Insets(top, left, bottom, right);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = aa(g);
            g2.setColor(LINE);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) { return insets; }
    }

    // status pill (Preparing, Shipped, ...)
    static class Pill extends JLabel {
        Pill(String text) {
            super(text);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setForeground(statusColor(text));
            setBorder(new EmptyBorder(3, 10, 3, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = aa(g);
            g2.setColor(SURFACE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
            g2.setColor(LINE);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
            super.paintComponent(g);
        }
    }

    static class PillRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean f, int r, int c) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
            p.setBackground(sel ? SELECT : Color.WHITE);
            p.add(new Pill(String.valueOf(v)));
            return p;
        }
    }

    static class Swatch implements Icon {
        Color color;

        Swatch(Color color) { this.color = color; }

        public int getIconWidth() { return 28; }

        public int getIconHeight() { return 28; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = aa(g);
            g2.setColor(color);
            g2.fillRoundRect(x, y, 28, 28, 8, 8);
        }
    }

    // panel that keeps its own height inside a BoxLayout
    static class Row extends JPanel {
        Row(LayoutManager lm) {
            super(lm);
            setOpaque(false);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
        }
    }

    // scroll content that follows the viewport width (no horizontal scrollbar)
    static class Wrap extends JPanel implements Scrollable {
        Wrap() {
            super(new BorderLayout());
            setOpaque(false);
        }

        public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }

        public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 16; }

        public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 64; }

        public boolean getScrollableTracksViewportWidth() { return true; }

        public boolean getScrollableTracksViewportHeight() { return false; }
    }

    static class Chat {
        String buyer, request, budget, needed, status;
        java.util.List<String[]> msgs = new java.util.ArrayList<>();

        Chat(String buyer, String request, String budget, String needed, String status) {
            this.buyer = buyer;
            this.request = request;
            this.budget = budget;
            this.needed = needed;
            this.status = status;
        }

        Chat say(boolean mine, String text, String time) {
            msgs.add(new String[]{mine ? "me" : "buyer", text, time});
            return this;
        }
    }

    static class Bubble extends JPanel {
        boolean mine;

        Bubble(String text, boolean mine) {
            super(new BorderLayout());
            this.mine = mine;
            setOpaque(false);
            setBorder(new EmptyBorder(9, 14, 9, 14));
            JLabel l = new JLabel();
            l.setFont(BASE);
            l.setForeground(mine ? Color.WHITE : TEXT);
            if (l.getFontMetrics(BASE).stringWidth(text) > 300) {
                String safe = text.replace("&", "&amp;").replace("<", "&lt;");
                l.setText("<html><div style='width:300px'>" + safe + "</div></html>");
            } else {
                l.setText(text);
            }
            add(l, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = aa(g);
            if (mine) {
                g2.setColor(ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            } else {
                g2.setColor(SURFACE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.setColor(LINE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            }
            super.paintComponent(g);
        }

        @Override
        public Dimension getMaximumSize() { return getPreferredSize(); }
    }

    // weekly bar chart, highest day uses the accent color
    static class BarChart extends JPanel {
        String[] labels;
        int[] values;

        BarChart(String[] labels, int[] values) {
            this.labels = labels;
            this.values = values;
            setOpaque(false);
            setPreferredSize(new Dimension(300, 170));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = aa(g);
            int w = getWidth(), h = getHeight() - 24;
            int max = 0, top = 0;
            for (int i = 0; i < values.length; i++) if (values[i] > max) { max = values[i]; top = i; }
            int slot = w / values.length, barW = slot - 16;
            g2.setFont(SMALL);
            FontMetrics fm = g2.getFontMetrics();
            for (int i = 0; i < values.length; i++) {
                int barH = (int) (values[i] / (double) max * (h - 6));
                int x = i * slot + 8;
                g2.setColor(i == top ? ACCENT : LINE);
                g2.fillRoundRect(x, h - barH, barW, barH, 8, 8);
                g2.fillRect(x, h - Math.min(barH, 8), barW, Math.min(barH, 8)); // flat bottom
                g2.setColor(MUTE);
                g2.drawString(labels[i], x + (barW - fm.stringWidth(labels[i])) / 2, h + 17);
            }
        }
    }
}