import javax.swing.*;

public class Main {
<<<<<<< Updated upstream

    static SignUp su = new SignUp();

    public static void main(String[] args){
<<<<<<< HEAD

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
    public static void main(String[] args) {

        JFrame f = new JFrame("StoneCraft Sign Up");

        SignUp su = new SignUp();

        f.add(su);
        f.setSize(920, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
>>>>>>> Stashed changes
=======
        CustomerMain  cm = new CustomerMain();
>>>>>>> origin/main
    }
}