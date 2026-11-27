package Design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;


public class MainDesign extends JFrame {

    public  final Color darkColor = new Color(43, 42, 40);
    public final Color copperColor = new Color(181, 101, 29);
    public final Color hoverColor = new Color(151, 83, 15);
    public final Color mainTextColor = new Color(31, 30, 28);
    public final Color labelColor = new Color(120, 116, 109);
    public final Color fieldColor = new Color(250, 249, 247);
    public final Color borderColor = new Color(222, 218, 211);
    public final Color backgroundColor = new Color(247, 245, 241);
    public final Color selectionColor = new Color(246, 228, 210);
    public  final Color deleteColor = new Color(174, 65, 59);

    public final Font mainFont = new Font("Arial", Font.PLAIN, 13);
    public final Font boldFont = new Font("Arial", Font.BOLD, 13);
    public final Font titleFont = new Font("Arial", Font.BOLD, 25);

    protected JPanel dashboardPanel;
    protected JPanel headerPanel;
    protected JPanel sidebarPanel;
    protected JPanel pagesPanel;
    protected CardLayout cardLayout;

    public MainDesign(){
        setTitle("StoneCraft");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }


    protected void addSidebarTitle(String title) {

        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 11));
        sectionLabel.setForeground(new Color(170, 164, 155));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 0));

        sidebarPanel.add(sectionLabel);
    }

    public void showWarning(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
        );
    }

    protected void styleButton(JButton button, Color background, Color foreground) {

        button.setFont(boldFont);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(9, 15, 9, 15));

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (background.equals(copperColor)) {
                    button.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(background);
            }
        });
    }

    public JPanel createWhiteCard() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor),
                        BorderFactory.createEmptyBorder(18, 18, 18, 18)
                )
        );

        return panel;
    }

    protected void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setFont(mainFont);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(mainTextColor);
    }

}