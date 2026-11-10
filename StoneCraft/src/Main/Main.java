package Main;
import DataBaseManagementConnection.DataBaseConnection;
import Design.*;
import javax.swing.*;
import java.util.ArrayList;

public class Main {

    static BusinessMain bs;
    static SignUp ca;
    static ArrayList <Product> kitchenCategory = new ArrayList<>();

    public static void main(String[] args){

        new BusinessMain();

    }

    public static void businessMainProcess(){

    }

    public static void signUpProcess(){
        SignUp su = new SignUp();

        su.createAccount.addActionListener(e ->{
            String name = su.nameField.getText();
            String email = su.emailField.getText();
            String contactNumber = su.contactField.getText();
            String password = su.passwordField.getText();
            String password2 = su.confirmField.getText();

            if(password.equals(password2)){
            }
            else{
                System.out.println("Error");
            }

        });

    }

    //===================================
    //for product add
    //===================================

    public static void productAdd(String productId, String productName, String productPrice, String productQuantity,String productCategory){

        double pPrice = Double.parseDouble(productPrice);
        int pCategory = Integer.parseInt(productQuantity);

        kitchenCategory.add(new Product(productId, productName, productCategory, pPrice, pCategory));

        for(Product pr: kitchenCategory){
            System.out.println(pr.getProductId());
            System.out.println(pr.getProductName());
            System.out.println(pr.getProductCategory());
            System.out.println(pr.getProductPrice());
            System.out.println(pr.getProductCategory());
            System.out.println("===========================");
        }

    }

    //yung sa id ng product need ko yun i modify to forloop para magsunod sunod without entering the value
    //yung sa productId siguro soon na yun heheh
    //gagawin ko pa is yung iuupdate ko yung inventory
}