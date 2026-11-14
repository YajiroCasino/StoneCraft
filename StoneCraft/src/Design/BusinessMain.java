package Design;
import DataBaseManagementConnection.DataAccessObject;
import Main.*;


import Design.BusinessMainFile.Dashboard;
import Design.BusinessMainFile.SaleAnalytics;
import Design.BusinessMainFile.CustomOrder;
import Design.BusinessMainFile.Inventory;
import Design.BusinessMainFile.Logout;
import Design.BusinessMainFile.Message;
import Design.BusinessMainFile.MyProduct;
import Design.BusinessMainFile.Order;
import Design.BusinessMainFile.ShopProfile;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;
import java.util.List;

public class BusinessMain extends MainDesign {
    Main main;

    //=============================================================
    //color ko to para di kayo mahirapan pa magintindi
    //==============================================================
    public static final Color SIDE = new Color(0x2B2A28);
    public static final Color ACCENT = new Color(0xB5651D);
    public static final Color LINE = new Color(0xE6E1DA);
    public static final Color SURFACE = new Color(0xFAF8F5);
    public static final Color SELECT = new Color(0xF3E4D4);
    public static final Color MUTE = new Color(0x7A736B);
    public static final Color TEXT = new Color(0x1F1D1B);
    public static final Color OK = new Color(0x3F7D4E);
    public static final Color WARN = new Color(0xB7791F);
    public static final Color BAD = new Color(0xA63A2B);
    public static final Color INFO = new Color(0x3B6EA5);

    //===============================================================
    //Ito fonts ko
    //===============================================================

    public static final Font BASE = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final String PESO = "\u20B1";

    public JPanel businessMainPanel = new JPanel();
    public JPanel content = new JPanel(new CardLayout());

    String[] pageNames = {"dashboard", "analytics", "products", "orders", "custom",
            "inventory", "messages", "shop", "logout"};

    //=======================================
    //nav button lang yan wag ioverthink
    //=========================================
    public NavButton[] buttons = {
            new NavButton("Dashboard", null),
            new NavButton("Sales Analytics", null),
            new NavButton("My Products", null),
            new NavButton("Orders", null),
            new NavButton("Custom Requests", null),
            new NavButton("Inventory", null),
            new NavButton("Messages", null),
            new NavButton("Shop Profile", null),
            new NavButton("Logout", null)
    };

    // ---------- sample data ----------
    public String[] orderCols = {"Order", "Buyer", "Item", "Total", "Status"};
    public String[] statuses = {"New", "Preparing", "Shipped", "Delivered", "Cancelled"};
    public Object[][] orderData = {
            {"#1042", "Maria D.", "Carved Stone Lamp", PESO + "3,200", "New"},
            {"#1041", "Paolo R.", "Marble Coaster Set", PESO + "1,800", "New"},
            {"#1040", "Ana L.", "Stone Bird Bath", PESO + "4,100", "Preparing"},
            {"#1039", "Ken S.", "Granite Mortar & Pestle", PESO + "1,250", "Shipped"},
            {"#1038", "Liza M.", "River Stone Planter", PESO + "2,400", "Delivered"}
    };


    Color[] swatches = {
            new Color(0x8C8A82), new Color(0xA9A296), new Color(0x6E7B68), new Color(0xA88A68),
            new Color(0x4A4D55), new Color(0x9C6A4E), new Color(0x7B8A90), new Color(0xC9B99B)
    };

    public DefaultTableModel ordersModel, dashModel;
    public DefaultTableModel productsModel, inventoryModel;
    public JWindow toastWin;
    public Timer toastTimer;

    public JPanel threadPage;
    public JTextField threadInput;
    public JScrollPane threadScroll;

    public BusinessMain() {

        ordersModel    = makeModel(orderCols, new Object[0][5], 4);
        dashModel      = makeModel(orderCols, new Object[0][5], -1);
        productsModel  = makeModel(new String[]{"", "Product", "ID", "Category", "Price", "Stock", ""}, new Object[0][7], -1);
        inventoryModel = makeModel(new String[]{"", "Product", "ID", "Category", "In stock", ""}, new Object[0][6], -1);

        JPanel sideBar = new JPanel();
        JPanel right = new JPanel(new BorderLayout());

        content.setBackground(Color.WHITE);

        right.add(topBar(), BorderLayout.NORTH);
        right.add(content, BorderLayout.CENTER);

        businessMainPanel.setLayout(new BorderLayout());
        businessMainPanel.add(sideBar, BorderLayout.WEST);
        businessMainPanel.add(right, BorderLayout.CENTER);

        buildSideBar(sideBar);

        //==================================================
        //content add ko to
        //==================================================

        content.add(new Dashboard(this), pageNames[0]);
        content.add(new SaleAnalytics(this), pageNames[1]);
        content.add(new MyProduct(this), pageNames[2]);
        content.add(new Order(this), pageNames[3]);
        content.add(new CustomOrder(this), pageNames[4]);
        content.add(new Inventory(this), pageNames[5]);
        content.add(new Message(this), pageNames[6]);
        content.add(new ShopProfile(this), pageNames[7]);
        content.add(new Logout(this), pageNames[8]);

        // keep dashboard table + Orders badge in sync with the Orders pag
        showPage(0);

        add(businessMainPanel);
        setVisible(true);
    }

    public void showPage(int index) {
        ((CardLayout) content.getLayout()).show(content, pageNames[index]);
        for (int i = 0; i < buttons.length; i++) buttons[i].setActive(i == index);
    }


    // =====================================================
    //  SIDEBAR + TOP BAR
    // =====================================================

    public void buildSideBar(JPanel sideBar) {
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
            String section = i == 0 ? "OVERVIEW" : i == 2 ? "MANAGE" : i == 6 ? "ACCOUNT" : null;
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

    public JPanel topBar() {
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

    //=================================
    // for pop-up dialog
    //=================================

    public static void openDialog(JDialog dlg, String title, String subtitle, JComponent center, JButton cancel, JButton primary) {
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
        dlg.setLocationRelativeTo(dlg.getOwner());
        dlg.setVisible(true);
    }

    public JLabel errorLabel() {
        JLabel error = new JLabel(" ");
        error.setFont(SMALL);
        error.setForeground(BAD);
        error.setAlignmentX(Component.LEFT_ALIGNMENT);
        return error;
    }

    public JTextField formField() {
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

    public JPanel formRow(String label, JComponent field) {
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

    public static Graphics2D aa(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }

    public static Color statusColor(String s) {
        switch (s) {
            case "New": return INFO;
            case "Preparing": return WARN;
            case "Shipped": return ACCENT;
            case "Delivered": return OK;
            default: return BAD;
        }
    }

    public Color swatchFor(String name) {
        if (name == null) return LINE;
        return swatches[Math.floorMod(name.hashCode(), swatches.length)];
    }

    public Object[][] firstRows(Object[][] src, int n) {
        Object[][] out = new Object[n][];
        for (int i = 0; i < n; i++) out[i] = src[i].clone();
        return out;
    }

    public DefaultTableModel makeModel(String[] cols, Object[][] data, int editableCol) {
        Object[][] copy = new Object[data.length][];
        for (int i = 0; i < data.length; i++) copy[i] = data[i].clone();
        return new DefaultTableModel(copy, cols) {
            @Override
            public boolean isCellEditable(int r, int c) { return c == editableCol; }
        };
    }

    //=========================================================
    //page
    public JPanel page(String title, String subtitle) {
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

    public static JPanel vbox() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }

    public JPanel toolRow(JComponent... comps) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        p.setOpaque(false);
        for (JComponent c : comps) p.add(c);
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(p, BorderLayout.NORTH);
        return wrap;
    }

    // white card with optional title and optional link on the right (jumps to a page)
    public JPanel card(String title, String link, int goPage) {
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

    public JPanel statCard(String label, String value, String note) {
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

    public  JPanel tableCard(JTable table) {
        JPanel c = card(null, null, -1);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(null);
        sp.getViewport().setBackground(Color.WHITE);
        c.add(sp, BorderLayout.CENTER);
        return c;
    }

    // left : right = 1.6 : 1
    public JPanel twoCols(JComponent left, JComponent right) {
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

    public JScrollPane stackScroll(JComponent... comps) {
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
    public JPanel lowRow(String left, String right, Color rightColor) {
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

    public JPanel replyRow(String buyer, String message) {
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
        reply.addActionListener(e -> toast("Reply to " + buyer));
        JPanel right = new JPanel(new GridBagLayout());
        right.setOpaque(false);
        right.add(reply);

        r.add(text, BorderLayout.CENTER);
        r.add(right, BorderLayout.EAST);
        return r;
    }

    //fot button accept or not ba
    public JButton pillButton(String text, boolean primary) {
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

    public void styleTable(JTable t, int... widths) {
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
    public void actionColumn(JTable t, int col, String text, Consumer<Integer> onClick) {
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

    // image-only column (kulay na swatch base sa pangalan sa column 1)
    public void imageColumn(JTable t, int col) {
        t.getColumnModel().getColumn(col).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tb, Object v, boolean s, boolean f, int r, int c) {
                super.getTableCellRendererComponent(tb, "", s, f, r, c);
                setIcon(new Swatch(swatchFor(String.valueOf(tb.getValueAt(r, 1)))));
                setHorizontalAlignment(SwingConstants.LEFT);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
    }

    public void toast(String msg) {
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
    public static class NavButton extends JButton {
        String label, badge;
        boolean active, hover;

        public NavButton(String label, String badge) {
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

        public void setActive(boolean a) { active = a; repaint(); }

        public void setBadge(String b) { badge = b; repaint(); }

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
    public static class RoundBorder extends AbstractBorder {
        int radius;
        Insets insets;

        public RoundBorder(int radius, int top, int left, int bottom, int right) {
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
    public static class Pill extends JLabel {
        public Pill(String text) {
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

    public static class PillRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean f, int r, int c) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
            p.setBackground(sel ? SELECT : Color.WHITE);
            p.add(new Pill(String.valueOf(v)));
            return p;
        }
    }

    public static class Swatch implements Icon {
        Color color;

        public Swatch(Color color) { this.color = color; }

        public int getIconWidth() { return 28; }

        public int getIconHeight() { return 28; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = aa(g);
            g2.setColor(color);
            g2.fillRoundRect(x, y, 28, 28, 8, 8);
        }
    }

    // panel that keeps its own height inside a BoxLayout
    public static class Row extends JPanel {
        public Row(LayoutManager lm) {
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
    public static class Wrap extends JPanel implements Scrollable {
        public Wrap() {
            super(new BorderLayout());
            setOpaque(false);
        }

        public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }

        public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 16; }

        public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 64; }

        public boolean getScrollableTracksViewportWidth() { return true; }

        public boolean getScrollableTracksViewportHeight() { return false; }
    }

    public static class Chat {
        public String buyer;
        public String request;
        public String budget;
        public String needed;
        public String status;
        public java.util.List<String[]> msgs = new java.util.ArrayList<>();

        public Chat(String buyer, String request, String budget, String needed, String status) {
            this.buyer = buyer;
            this.request = request;
            this.budget = budget;
            this.needed = needed;
            this.status = status;
        }

        public Chat say(boolean mine, String text, String time) {
            msgs.add(new String[]{mine ? "me" : "buyer", text, time});
            return this;
        }
    }

    public static class Bubble extends JPanel {
        boolean mine;

        public Bubble(String text, boolean mine) {
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

    public static String capitalizeFirstLetter(String word){
        if(word == null) {
            return "";
        }
        word = word.trim();
        if(word.isEmpty()){
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    public static String capitalizeWords(String text) {
        if (text == null) {
            return "";
        }
        String[] words = text.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String w : words) {
            if (w.isEmpty()) continue;
            sb.append(Character.toUpperCase(w.charAt(0)))
                    .append(w.substring(1))
                    .append(" ");
        }
        return sb.toString().trim();
    }

    public void loadTable(DefaultTableModel model) {
        model.setRowCount(0);

        List<Product> products = DataAccessObject.getAllProduct();

        for (Product p : products) {
            model.addRow(new Object[]{
                    "",
                    p.getProductName(),
                    String.format("P%03d", p.getProductId()),
                    p.getProductCategory(),
                    p.getProductPrice(),
                    p.getProductQuantity(),
                    ""
            });
        }
    }

    public void loadInventory(DefaultTableModel model) {
        model.setRowCount(0);

        List<Product> products = DataAccessObject.getAllProduct();

        for (Product p : products) {
            model.addRow(new Object[]{
                    "",
                    p.getProductName(),
                    String.format("P%03d", p.getProductId()),
                    p.getProductCategory(),
                    p.getProductQuantity(),
                    ""
            });
        }
    }


    public void refreshTables() {
        if (productsModel != null) loadTable(productsModel);
        if (inventoryModel != null) loadInventory(inventoryModel);
    }
}