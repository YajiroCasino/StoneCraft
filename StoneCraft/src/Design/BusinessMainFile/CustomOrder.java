package Design.BusinessMainFile;
import Design.BusinessMain;

import javax.swing.*;
import java.awt.*;

public class CustomOrder extends JPanel {

    private final BusinessMain bm;

    public CustomOrder(BusinessMain bm) {
        this.bm = bm;
        setLayout(new BorderLayout());
        JPanel page = bm.page("Custom Requests", "Mga order na gawa ayon sa gusto ng buyer.");
        JPanel c = bm.card(null, null, -1);
        JPanel list = bm.vbox();
        list.add(bm.replyRow("Maria D.", null));
        list.add(bm.replyRow("Paolo R.", null));
        list.add(bm.replyRow("Ana L.", null));
        c.add(list, BorderLayout.CENTER);
        page.add(bm.stackScroll(c), BorderLayout.CENTER);

        add(page, BorderLayout.CENTER);

    }
}
