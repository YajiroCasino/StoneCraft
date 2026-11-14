package Design.BusinessMainFile;
import Design.BusinessMain;

import javax.swing.*;
import java.awt.*;


public class Dashboard extends JPanel {

    private final BusinessMain bm;

    public Dashboard(BusinessMain bm) {
        this.bm = bm;
        setLayout(new BorderLayout());

        JPanel page = bm.page("Hello User! Welcome to StoneCarft","EHEHEHEHE");

        JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
        stats.setOpaque(false);
        stats.add(bm.statCard("Sales this week", BusinessMain.PESO + "0", "+12% vs last week"));
        stats.add(bm.statCard("New orders", "0", "0 shipping today"));
        stats.add(bm.statCard("Custom requests", "0", "0 waiting for your quote"));
        stats.add(bm.statCard("Shop rating", "5", "from 1 reviews"));

        JPanel chart = bm.card("Daily sales", null, -1);
        chart.add(new BarChart(new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"},
                new int[]{45, 62, 38, 92, 70, 80, 30}), BorderLayout.CENTER);

        JPanel low = bm.card("Low stock", "Restock", 5);
        JPanel lowList = bm.vbox();

        bm.dashModel = bm.makeModel(bm.orderCols, bm.firstRows(bm.orderData, 4), -1);
        JTable dashTable = new JTable(bm.dashModel);
        bm.styleTable(dashTable, 60, 90, 160, 70, 100);
        dashTable.getColumnModel().getColumn(4).setCellRenderer(new BusinessMain.PillRenderer());
        JPanel recent = bm.card("Recent orders", "View all", 3);
        JPanel tp = new JPanel(new BorderLayout());
        tp.setOpaque(false);
        tp.add(dashTable.getTableHeader(), BorderLayout.NORTH);
        tp.add(dashTable, BorderLayout.CENTER);
        recent.add(tp, BorderLayout.CENTER);

        JPanel reqs = bm.card("Custom requests", "View all", 4);
        JPanel reqList = bm.vbox();
        reqList.add(bm.replyRow("Maria D.", null));
        reqList.add(bm.replyRow("Paolo R.", null));
        reqs.add(reqList, BorderLayout.CENTER);

        page.add(bm.stackScroll(stats, bm.twoCols(chart, low), bm.twoCols(recent, reqs)), BorderLayout.CENTER);
        add(page, BorderLayout.CENTER);
    }
}
