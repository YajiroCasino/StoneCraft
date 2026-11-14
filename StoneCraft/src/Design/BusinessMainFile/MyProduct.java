package Design.BusinessMainFile;

import Design.BusinessMain;
import Main.Main;
import Main.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static Design.BusinessMain.*;


public class MyProduct extends JPanel {

    private final BusinessMain bm;

    public MyProduct(BusinessMain bm) {
        this.bm = bm;
        setLayout(new BorderLayout());

        JPanel page = bm.page("My Products","Your current products.");

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
        search.setBorder(new BusinessMain.RoundBorder(8, 8, 12, 8, 12));
        search.setPreferredSize(new Dimension(320, 38));

        //================================================================
        // rows and column ng product
        //================================================================

        DefaultTableModel model = bm.makeModel(
                new String[]{"Image", "Product", "ID", "Category", "Price", "Stock", ""},
                new Object[0][7], -1);
        bm.productsModel = model;
        bm.loadTable(model);
        JTable table = new JTable(model);
        bm.styleTable(table, 60, 230, 70, 120, 100, 80, 70);

        // product name with color thumbnail nandito rin yung para sa edit

        bm.imageColumn(table, 0);
        bm.actionColumn(table, 6, "Edit", r -> showEditProductDialog(model, r));


        //========================================================
        //add products button
        //========================================================
        JButton add = bm.pillButton("+ Add product", true);
        add.addActionListener(e -> showAddProductDialog(model));

        //=======================================================
        //para sa searchBar to
        //=======================================================

        JButton searchButton = bm.pillButton("Search ", true);
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
        body.add(bm.tableCard(table), BorderLayout.CENTER);
        page.add(body, BorderLayout.CENTER);

        add(page, BorderLayout.CENTER);
    }

    void showAddProductDialog(DefaultTableModel model) {
        Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dlg = new JDialog(owner, "Add product", Dialog.ModalityType.APPLICATION_MODAL);

        //====================================================================
        //variable ko for JForm Field so bala ka jan
        //====================================================================

        JTextField nameProduct =  bm.formField();
        JTextField priceProduct = bm.formField();
        JTextField stockProduct = bm.formField();
        JComboBox<String> categoryProduct;

        categoryProduct = new JComboBox<>(new String[]{"Kitchen", "Decor", "Garden", "Lighting"});
        categoryProduct.setFont(BASE);
        categoryProduct.setBackground(SURFACE);
        categoryProduct.setForeground(TEXT);
        categoryProduct.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryProduct.setPreferredSize(new Dimension(300, 38));
        categoryProduct.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JPanel form = bm.vbox();
        form.add(bm.formRow("Product name", nameProduct));
        form.add(bm.formRow("Category", categoryProduct));
        JPanel priceStock = new JPanel(new GridLayout(1, 2, 12, 0));
        priceStock.setOpaque(false);
        priceStock.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceStock.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        priceStock.add(bm.formRow("Price (" + PESO + ")", priceProduct));
        priceStock.add(bm.formRow("Stock", stockProduct));
        form.add(priceStock);

        JLabel error = bm.errorLabel();
        form.add(error);

        JButton cancel = bm.pillButton("Cancel", false);
        JButton save = bm.pillButton("Save product", true);
        cancel.addActionListener(e -> dlg.dispose());
        save.addActionListener(e -> {

            //yun trim() tinatagal nun yung space sa hulihan at unahan linagay ko na dito baka atakihin ng short term memory eh
            int productId = 1;
            String productName = nameProduct.getText().trim();
            String productPrice = priceProduct.getText().trim();
            String productStock = stockProduct.getText().trim();
            String productCategory = categoryProduct.getSelectedItem().toString().trim();

            if (productName.isEmpty()) {
                error.setText("Invalid name of product!");
                return;
            }
            productName = capitalizeWords(productName);
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

            //====================================================================================
            //imomodify koto depende sa situation pwedeng directed sa main orsa product na gagawing
            //pero mas gusto ko main para magawa sya arraylist
            //=====================================================================================

            Product product = new Product(productId, productName, productCategory, pPrice, pStock);
            int newId = Main.productDao.insertProduct(product);

            if (newId == -1) {
                error.setText("Failed to save product.");
                return;
            }


            bm.refreshTables();
            dlg.dispose();
        });
        BusinessMain.openDialog(dlg, "Add product", "Fill in the details of your new product.", form, cancel, save);
    }

    //edit product
    void showEditProductDialog(DefaultTableModel model, int row) {

        Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dlg = new JDialog(owner, "Edit product", Dialog.ModalityType.APPLICATION_MODAL);

        //dating value
        int productId = Integer.parseInt(model.getValueAt(row, 2).toString().substring(1));
        String curName = model.getValueAt(row, 1).toString();
        String curCategory = model.getValueAt(row, 3).toString();
        String curPrice = model.getValueAt(row, 4).toString();
        String curStock = model.getValueAt(row, 5).toString();

        JTextField nameProduct = bm.formField();
        JTextField priceProduct = bm.formField();
        JTextField stockProduct = bm.formField();
        nameProduct.setText(curName);
        priceProduct.setText(curPrice);
        stockProduct.setText(curStock);

        //for category

        JComboBox<String> categoryProduct = new JComboBox<>(new String[]{"Kitchen", "Decor", "Garden", "Lighting"});
        categoryProduct.setFont(BASE);
        categoryProduct.setBackground(SURFACE);
        categoryProduct.setForeground(TEXT);
        categoryProduct.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryProduct.setPreferredSize(new Dimension(300, 38));
        categoryProduct.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        categoryProduct.setSelectedItem(curCategory);   // naka-select na ang dating category


        JPanel form = bm.vbox();
        form.add(bm.formRow("Product name", nameProduct));
        form.add(bm.formRow("Category", categoryProduct));
        JPanel priceStock = new JPanel(new GridLayout(1, 2, 12, 0));
        priceStock.setOpaque(false);
        priceStock.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceStock.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        priceStock.add(bm.formRow("Price (" + PESO + ")", priceProduct));
        priceStock.add(bm.formRow("Stock", stockProduct));
        form.add(priceStock);

        JLabel error = bm.errorLabel();
        form.add(error);

        JButton cancel = bm.pillButton("Cancel", false);
        JButton save = bm.pillButton("Save changes", true);
        cancel.addActionListener(e -> dlg.dispose());
        save.addActionListener(e -> {
            String name = nameProduct.getText().trim();
            if (name.isEmpty()) {
                error.setText("Invalid name of product!");
                return;
            }
            name = capitalizeWords(name);

            double pPrice;
            int pStock;
            try {
                pPrice = Double.parseDouble(priceProduct.getText().trim());
                pStock = Integer.parseInt(stockProduct.getText().trim());
                if (pPrice < 0 || pStock < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                error.setText("Price and Stock need to be a valid number!");
                return;
            }

            String category = categoryProduct.getSelectedItem().toString().trim();
            Product product = new Product(productId, name, category, pPrice, pStock);

            // isulat sa database, tapos i-reload ang screen
            if (Main.productDao.editProduct(product) <= 0) {
                error.setText("Failed to update product.");
                return;
            }
            bm.refreshTables();
            dlg.dispose();
        });

        BusinessMain.openDialog(dlg, "Edit product", curName + " \u00B7 " + String.format("P%03d", productId), form, cancel, save);
    }
}