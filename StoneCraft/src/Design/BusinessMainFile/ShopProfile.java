package Design.BusinessMainFile;

import Design.BusinessMain;

import javax.swing.*;
import java.awt.*;

public class ShopProfile extends JPanel {

    private final BusinessMain bm;
    public ShopProfile(BusinessMain bm) {
        this.bm = bm;                         // kung may field na bm
        setLayout(new BorderLayout());

        JPanel page = bm.page("Shop Profile", "StoneCraft \u00B7 Cebu City \u00B7 Handmade stone goods since 2019.");
        JButton b = bm.pillButton("Save changes", true);
        b.addActionListener(e -> bm.toast("Profile saved"));
        page.add(bm.toolRow(b), BorderLayout.CENTER);
        add(page, BorderLayout.CENTER);
    }
}
