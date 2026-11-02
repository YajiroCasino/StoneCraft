package Design;
import javax.swing.*;
import java.awt.*;

public class CustomerMain extends MainDesign{

    ImageIcon productImg1 = new ImageIcon(getClass().getResource("../Data/Product_1.jfif"));
    JLabel product1 = new JLabel(productImg1);
    JPanel customerMainPanel = new JPanel();
    JPanel sideBar = new JPanel();
    CardLayout cardLayout = new CardLayout();
    JPanel content = new JPanel(cardLayout);
    JLabel logoName = new JLabel("StoneCraft");

    JPanel homePanel = new JPanel();
    JPanel productPanel = new JPanel();
    JPanel customerOrderRequest = new JPanel();

    JLabel hello = new JLabel("Hello");

    JButton[] buttons ={
            new JButton("Home"),
            new JButton("Products"),
            new JButton("Orders"),
            new JButton("Custom Order Request")
    };


    public CustomerMain(){

        customerMainPanel.setLayout(new BorderLayout());

        add(customerMainPanel);

        customerMainPanel.add(sideBar, BorderLayout.WEST);
        customerMainPanel.add(content, BorderLayout.CENTER);

        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(Color.GREEN);
        sideBar.setPreferredSize(new Dimension(230, 250));
        sideBar.add(logoName);

        for(int i = 0; i < buttons.length; i++){
            JPanel buttonBox = new JPanel(new BorderLayout());

            buttonBox.setBackground(Color.GREEN);
            buttons[i].setMaximumSize(new Dimension(200, 50));
            buttonBox.setBorder(
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            );

            buttonBox.setLayout(new BoxLayout(buttonBox, BoxLayout.Y_AXIS));
            buttonBox.add(buttons[i]);
            sideBar.add(buttonBox);



        }

        homePanel.add(new JLabel("Home"));
        productPanel.add(new JLabel("product"));
        customerOrderRequest.add(new JLabel("Custom order"));


        content.add(homePanel, "Home");
        content.add(productPanel, "Products");
        content.add(customerOrderRequest, "CostumerOrderRequest");

        buttons[0].addActionListener(e -> homePanelContent());
        buttons[1].addActionListener(e -> productPanelContent());
        buttons[2].addActionListener(e -> customerOrderRequestContent());

        setVisible(true);

    }

    public void homePanelContent(){

        cardLayout.show(content, "Home");
        homePanel.add(product1);

    }
    public void productPanelContent(){

        cardLayout.show(content, "Products");

    }
    public void customerOrderRequestContent(){

        cardLayout.show(content, "CostumerOrderRequest");


    }

}
