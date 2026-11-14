package Design.BusinessMainFile;

import Design.BusinessMain;

import javax.swing.*;
import java.awt.*;

public class Order extends JPanel {

    private final BusinessMain bm;

    public Order(BusinessMain bm) {
        this.bm = bm;
        setLayout(new BorderLayout());

        JPanel page = bm.page("Orders", "I-update ang status para makita ng buyer sa Track Order.");
        bm.ordersModel = bm.makeModel(bm.orderCols, bm.orderData, 4);
        JTable table = new JTable(bm.ordersModel);
        bm.styleTable(table, 80, 120, 220, 90, 140);
        table.getColumnModel().getColumn(4).setCellRenderer(new BusinessMain.PillRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox<>(bm.statuses)));
        page.add(bm.tableCard(table), BorderLayout.CENTER);

        add(page, BorderLayout.CENTER);
    }

}
