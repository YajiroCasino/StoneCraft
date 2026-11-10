package Main;
<<<<<<< Updated upstream
import Design.*;

public class Main {

    static SignUp su = new SignUp();

    public static void main(String[] args){

        su.productButton.addActionListener(e ->{
            su.contentPane.removeAll();
            su.contentPane.add(su.productCard);
            su.contentPane.revalidate();
            su.contentPane.repaint();
        });

        su.messageButton.addActionListener(e ->{
            su.contentPane.removeAll();
            su.contentPane.add(su.messageCard);
            su.contentPane.revalidate();
            su.contentPane.repaint();
        });
        
        su.setContentPane(su.panel1);
        su.setVisible(true);
=======
import Design.CustomerMain;
import Design.Login;

public class Main {

    public static void main(String[] args) {
        new Login();

>>>>>>> Stashed changes
    }
}
