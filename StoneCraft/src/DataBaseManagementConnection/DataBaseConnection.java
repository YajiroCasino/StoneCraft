package DataBaseManagementConnection;

import java.sql.*;

public class DataBaseConnection {

    public static final String urlStart = "jdbc:mysql://localhost:3306/";
    public static final String urlContinue = "jdbc:mysql://localhost:3306/stoneCraft_db";
    public static final String user = "root";
    public static final String pass = "";

    public static void createDatabase(){
        try(Connection con = DriverManager.getConnection(urlStart, user, pass);
            Statement st = con.createStatement()){

            st.executeUpdate("create database if not exists stoneCraft_db");

            st.executeUpdate("use stoneCraft_db");

            String createProductTable = "create table if not exists product (" +
                    "product_id int auto_increment primary key, " +
                    "product_name varchar(100) not null, " +
                    "product_category varchar(50) not null, " +
                    "product_price decimal(10,2) not null, " +
                    "product_quantity int not null) auto_increment = 1";

            String createAccountTable = "create table if not exists accounts(" +
                    "account_id int auto_increment primary key," +
                    "account_name varchar(100) not null," +
                    "account_email_address varchar(100) not null unique," +
                    "account_contact_number varchar(100) not null," +
                    "account_password varchar(100) not null) auto_increment = 0001";

            String createCustomProductTable = "create table if not exists custom_products(" +
                    "custom_product_id int auto_increment primary key, " +
                    "custom_product_name varchar(100) not null, " +
                    "custom_product_category varchar(100) not null, " +
                    "custom_product_price decimal(10,2) not null default 0, " +
                    "custom_product_quantity int not null default 1, " +
                    "custom_product_description text not null, " +
                    "custom_product_size varchar(100) not null, " +
                    "custom_product_preferred_design varchar(100) not null" +
                    ") auto_increment = 1";

            String createOrderTable = "create table if not exists orders(" +
                    "order_id int auto_increment primary key, " +
                    "order_product_name varchar(100) not null, " +
                    "order_product_category varchar(100) not null, " +
                    "order_product_price decimal(10,2) not null, " +
                    "order_product_quantity int not null) auto_increment = 1";

            st.executeUpdate(createProductTable);
            st.executeUpdate(createAccountTable);
            st.executeUpdate(createCustomProductTable);
            st.executeUpdate(createOrderTable);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(urlContinue, user, pass);
        }catch(SQLException e){
            System.out.println("Database error!");
            return null;
        }
    }

    public static boolean isConnected() {
        try (Connection con = DriverManager.getConnection(urlContinue, user, pass)) {
            return con != null && con.isValid(2);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return false;   // huwag kalimutan ito
        }
    }
}
