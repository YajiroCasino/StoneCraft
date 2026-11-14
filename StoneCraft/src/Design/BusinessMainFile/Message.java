package Design.BusinessMainFile;

import Design.BusinessMain;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static Design.BusinessMain.*;

public class Message extends JPanel {
    private final BusinessMain bm;

    public Message(BusinessMain bm) {
        this.bm = bm;
        setLayout(new BorderLayout());

        JPanel page = bm.page("Messages", "2 unread messages from buyers.");
        JPanel c = bm.card(null, null, -1);
        JPanel list = bm.vbox();
        list.add(bm.replyRow("Maria D.", null));
        list.add(bm.replyRow("Paolo R.", null));
        c.add(list, BorderLayout.CENTER);
        page.add(bm.stackScroll(c), BorderLayout.CENTER);

        add(page, BorderLayout.CENTER);
    }
    public void showThread(BusinessMain.Chat chat, int backPage) {


        if (bm.threadPage != null) bm.content.remove(bm.threadPage);
        bm.threadPage = threadPanel(chat, backPage);
        bm.content.add(bm.threadPage, "thread");
        bm.content.revalidate();
        bm.content.repaint();
        ((CardLayout) bm.content.getLayout()).show(bm.content, "thread");
        for (int i = 0; i < bm.buttons.length; i++) bm.buttons[i].setActive(i == backPage);
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = bm.threadScroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
            bm.threadInput.requestFocusInWindow();
        });
    }


    JPanel threadPanel(BusinessMain.Chat chat, int backPage) {

        String sub = chat.request == null ? "Buyer conversation" : "Custom request \u00B7 " + chat.request;
        JPanel page = bm.page(chat.buyer, sub);

        JButton back = bm.pillButton("\u2190 Back", false);
        back.addActionListener(e -> bm.showPage(backPage));
        JPanel backRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backRow.setOpaque(false);
        backRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        backRow.add(back);

        JPanel top = bm.vbox();
        top.add(backRow);
        if (chat.request != null) {
            top.add(Box.createVerticalStrut(12));
            JPanel details = bm.card("Request details", null, -1);
            JPanel rows = bm.vbox();
            rows.add(bm.lowRow("Request", chat.request, TEXT));
            rows.add(bm.lowRow("Budget", chat.budget, TEXT));
            rows.add(bm.lowRow("Needed by", chat.needed, TEXT));
            rows.add(bm.lowRow("Status", chat.status, WARN));
            details.add(rows, BorderLayout.CENTER);
            details.setAlignmentX(Component.LEFT_ALIGNMENT);
            top.add(details);
        }

        JPanel thread = bm.vbox();
        for (String[] m : chat.msgs) thread.add(bubbleRow("me".equals(m[0]), m[1], m[2]));
        BusinessMain.Wrap wrap = new BusinessMain.Wrap();
        wrap.add(thread, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(wrap);
        sp.setBorder(null);
        sp.getViewport().setBackground(Color.WHITE);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        bm.threadScroll = sp;
        JPanel convo = bm.card("Conversation", null, -1);
        convo.add(sp, BorderLayout.CENTER);

        JTextField input = new JTextField();
        input.setFont(BASE);
        input.setForeground(TEXT);
        input.setBackground(SURFACE);
        input.setBorder(new BusinessMain.RoundBorder(8, 8, 12, 8, 12));
        input.setPreferredSize(new Dimension(300, 38));
        bm.threadInput = input;
        JButton send = bm.pillButton("Send reply", true);
        Runnable doSend = () -> {
            String text = input.getText().trim();
            if (text.isEmpty()) return;
            chat.say(true, text, "Just now");
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
        BusinessMain.Bubble b = new BusinessMain.Bubble(text, mine);
        JLabel t = new JLabel(time);
        t.setFont(SMALL);
        t.setForeground(MUTE);
        float ax = mine ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT;
        b.setAlignmentX(ax);
        t.setAlignmentX(ax);
        JPanel col = bm.vbox();
        col.add(b);
        col.add(Box.createVerticalStrut(2));
        col.add(t);
        BusinessMain.Row r = new BusinessMain.Row(new BorderLayout());
        r.setBorder(new EmptyBorder(6, 0, 6, 0));
        r.add(col, mine ? BorderLayout.EAST : BorderLayout.WEST);
        return r;
    }

}