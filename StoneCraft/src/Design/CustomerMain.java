package Design;
import javax.swing.*;
import java.awt.*;

public class CustomerMain extends MainDesign{

    //sa image
    ImageIcon[] productImg ={
            new ImageIcon(getClass().getResource("../Data/product_1.jfif")),
            new ImageIcon(getClass().getResource("../Data/product_2.jfif")),
            new ImageIcon(getClass().getResource("../Data/product_3.jfif")),
            new ImageIcon(getClass().getResource("../Data/product_4.jfif")),
            new ImageIcon(getClass().getResource("../Data/product_5.jfif")),
            new ImageIcon(getClass().getResource("../Data/product_6.jfif")),
            new ImageIcon(getClass().getResource("../Data/product_7.jfif")),
            new ImageIcon(getClass().getResource("../Data/product_8.jfif"))

    };
    JLabel[] product = new JLabel[productImg.length];

    //panel main
    JPanel customerMainPanel = new JPanel();
    JPanel sideBar = new JPanel();

    //card panel
    CardLayout cardLayout = new CardLayout();
    JPanel content = new JPanel(cardLayout);

    JLabel logoName = new JLabel("StoneCraft");

    // panel sa card
    JPanel homePanel = new JPanel();
    JPanel productPanel = new JPanel();
    JPanel customerOrderRequest = new JPanel();

    //buttons sa cards
    JButton[] buttons ={
            new JButton("Home"),
            new JButton("Products"),
            new JButton("Orders"),
            new JButton("Custom Order Request")
    };


    public CustomerMain(){

        //Main layout
        customerMainPanel.setLayout(new BorderLayout());

        customerMainPanel.add(sideBar, BorderLayout.WEST);
        customerMainPanel.add(content, BorderLayout.CENTER);

        //sidebar
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(Color.BLACK);
        sideBar.setPreferredSize(new Dimension(230, 0));
        sideBar.add(logoName);

        //button loop para sararuan
        for(int i = 0; i < buttons.length; i++){
            JPanel buttonBox = new JPanel(new BorderLayout());

            buttonBox.setBackground(Color.BLACK);

            buttonBox.setBorder(
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            );

            buttonBox.setLayout(new BoxLayout(buttonBox, BoxLayout.Y_AXIS));
            buttonBox.add(buttons[i]);
            sideBar.add(buttonBox);

            buttons[i].setMaximumSize(new Dimension(200, 50));
            buttons[i].setBackground(Color.BLACK);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorderPainted(false);
            buttons[i].setFocusPainted(false);
        }

        //card
        content.add(homePanel, "Home");
        content.add(productPanel, "Products");
        content.add(customerOrderRequest, "CostumerOrderRequest");

        // img and boxes
        for(int i = 0; i < product.length; i++){
            Image img = productImg[i].getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
            product[i] = new JLabel(new ImageIcon(img));
        }

        //listener lang
        buttons[0].addActionListener(e -> homePanelContent());
        buttons[1].addActionListener(e -> productPanelContent());
        buttons[2].addActionListener(e -> customerOrderRequestContent());

        add(customerMainPanel);
        setVisible(true);

    }

    public void homePanelContent(){

        //Button Colors
        buttons[0].setBackground(new Color(139, 69, 19));
        buttons[1].setBackground(Color.BLACK);
        buttons[2].setBackground(Color.BLACK);

        //panel layout
        homePanel.setLayout(new BorderLayout());

        //Panel for Content
        JPanel upperContentPanel = new JPanel(),
                centerContentPanel = new JPanel(),
                lowerContentPanel = new JPanel();

        // center content layer panel
        JPanel[] centerContentPanelLayer = new JPanel[4];

        //card ng product
        JPanel[] centerpanelLayerCard = new JPanel[5];

        //center content panel for card
        JPanel productCardBg = new JPanel();

        upperContentPanel.setBackground(Color.WHITE);
        upperContentPanel.setLayout(new BorderLayout());
        upperContentPanel.setBorder( BorderFactory.createEmptyBorder(30,30,30,30));
        upperContentPanel.setPreferredSize(new Dimension(0, 100));

        centerContentPanel.setBackground(Color.WHITE);
        centerContentPanel.setLayout(new GridLayout(5,0));
        centerContentPanel.setBorder( BorderFactory.createMatteBorder(1,0,0,0, Color.GRAY));


        lowerContentPanel.setBackground(Color.GREEN);


        //Search Bar

        JTextField searchBar = new JTextField("Search Product");
        searchBar.setPreferredSize(new Dimension(300, 1));

        //Content
        cardLayout.show(content, "Home");
        homePanel.removeAll();



        for(int i = 0; i < product.length; i++){



            productCardBg.setLayout(
                    new BoxLayout(productCardBg, BoxLayout.X_AXIS)
            );

            productCardBg.setBackground(Color.BLUE);
            productCardBg.setPreferredSize(new Dimension(200, 200));

            productCardBg.add(product[i]);

        }
        homePanel.add(upperContentPanel, BorderLayout.NORTH);
        homePanel.add(centerContentPanel, BorderLayout.CENTER);
        homePanel.add(lowerContentPanel, BorderLayout.SOUTH);

        //upperPanel content
        upperContentPanel.add(searchBar, BorderLayout.WEST);

        //centerPanel content

        for(int i = 0; i < centerContentPanelLayer.length; i++){
            centerContentPanelLayer[i] = new JPanel();
            switch(i)
            {
                case 0:
                    for(int j = 0; j < centerContentPanelLayer.length; j++) {
                        centerContentPanelLayer[i].add(new JLabel("hello"));
                    }
                    break;
                case 1:
                    for(int k = 0; k < centerContentPanelLayer.length; k++){
                        centerContentPanelLayer[i].add(new JLabel("bye"));
                }

            }

            centerContentPanel.add(centerContentPanelLayer[i]);
        }


        homePanel.revalidate();
        homePanel.repaint();
    }


    public void productPanelContent(){
        buttons[0].setBackground(Color.BLACK);
        buttons[1].setBackground(new Color(139, 69, 19));
        buttons[2].setBackground(Color.BLACK);
        cardLayout.show(content, "Products");

    }
    public void customerOrderRequestContent(){
        buttons[0].setBackground(Color.BLACK);
        buttons[1].setBackground(Color.BLACK);
        buttons[2].setBackground(new Color(139, 69, 19));
        cardLayout.show(content, "CostumerOrderRequest");


    }


    /*Yung sa content maglagay na ng card na nag lalaman sya ng picture, price, name, tapos button na okay na*/


}
