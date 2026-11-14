package Design.BusinessMainFile;

import Design.BusinessMain;

import javax.swing.*;
import java.awt.*;

import static Design.BusinessMain.PESO;
import static Design.BusinessMain.TEXT;

public class SaleAnalytics extends JPanel {

    private final BusinessMain bm;

    public SaleAnalytics(BusinessMain bm) {
        this.bm = bm;
        setLayout(new BorderLayout());
        JPanel page = bm.page("Sales Analytics", "Best sellers this month.");
        JPanel c = bm.card(null, null, -1);
        JPanel list = bm.vbox();
        list.add(bm.lowRow("Stone Bird Bath", "18 sold \u00B7 " + PESO + "73,800", TEXT));
        list.add(bm.lowRow("Carved Stone Lamp", "14 sold \u00B7 " + PESO + "44,800", TEXT));
        list.add(bm.lowRow("River Stone Planter", "11 sold \u00B7 " + PESO + "26,400", TEXT));
        c.add(list, BorderLayout.CENTER);
        page.add(bm.stackScroll(c), BorderLayout.CENTER);
        add(page,BorderLayout.CENTER);
    }
}
