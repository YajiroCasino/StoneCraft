package Design.BusinessMainFile;

import Design.BusinessMain;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Inventory extends JPanel {

    private final BusinessMain bm;
    public Inventory(BusinessMain bm) {
        this.bm = bm;
        setLayout(new BorderLayout());

        JPanel page = bm.page("Inventory", "I-update ang stock bago maubos.");

        // parehong laman ng My Products, ang pinagkaiba lang may + Add stock dito
        DefaultTableModel model = bm.makeModel(
                new String[]{"Image", "Product", "ID", "Category", "In stock"},
                new Object[0][6], -1);
        bm.inventoryModel = model;
        bm.loadInventory(model);
        JTable table = new JTable(model);
        bm.styleTable(table, 60, 230, 70, 120, 90);

        bm.imageColumn(table, 0);

        page.add(bm.tableCard(table), BorderLayout.CENTER);
        add(page, BorderLayout.CENTER);
    }
}
