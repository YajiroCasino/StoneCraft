package Design.BusinessMainFile;

import javax.swing.*;
import java.awt.*;

import static Design.BusinessMain.*;   // aa, SMALL, ACCENT, LINE, MUTE

public class BarChart extends JPanel {

    private final String[] labels;
    private final int[] values;

    public BarChart(String[] labels, int[] values) {
        this.labels = labels;
        this.values = values;
        setOpaque(false);
        setPreferredSize(new Dimension(300, 170));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (values.length == 0) return;

        Graphics2D g2 = aa(g);
        int w = getWidth(), h = getHeight() - 24;
        int max = 0, top = 0;
        for (int i = 0; i < values.length; i++) if (values[i] > max) { max = values[i]; top = i; }
        if (max == 0) max = 1;

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
        g2.dispose();
    }
}