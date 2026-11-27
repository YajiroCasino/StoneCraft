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
                    "product_quantity int not null)";

            String createAccountTable = "create table if not exists accounts(" +
                    "account_id int auto_increment primary key, " +
                    "account_name varchar(100) not null unique, " +
                    "account_email_address varchar(100) not null, " +
                    "account_contact_number varchar(100) not null, " +
                    "account_password varchar(255) not null, " +
                    "account_type enum('Customer','Business') not null default 'Customer')";

            String createCustomerAccountTable = "create table if not exists customer_account(" +
                    "customer_account_id int auto_increment primary key, " +
                    "account_id int not null unique, " +
                    "customer_account_type varchar(100), " +
                    "foreign key (account_id) references accounts(account_id) on delete cascade)";

            String createBusinessAccountTable = "create table if not exists business_account(" +
                    "business_account_id int auto_increment primary key, " +
                    "account_id int not null unique, " +
                    "business_account_type varchar(30) not null default 'Employee', " +
                    "foreign key (account_id) references accounts(account_id) on delete cascade)";

            String createCustomProductTable = "create table if not exists custom_products(" +
                    "custom_product_id int auto_increment primary key, " +
                    "custom_product_name varchar(100) not null, " +
                    "custom_product_category varchar(100) not null, " +
                    "custom_product_price decimal(10,2) not null default 0, " +
                    "custom_product_quantity int not null default 1, " +
                    "custom_product_description text not null, " +
                    "custom_product_size varchar(100) not null, " +
                    "custom_product_preferred_design varchar(100) not null, " +
                    "account_id int null, " +
                    "custom_product_status varchar(30) not null default 'Pending', " +
                    "foreign key (account_id) references accounts(account_id) on delete set null)";

            String createCartTable = "create table if not exists cart(" +
                    "cart_id int auto_increment primary key," +
                    "product_id int not null," +
                    "product_cart_quantity int not null," +
                    "account_id int not null," +
                    "foreign key (product_id) references product(product_id) on delete cascade," +
                    "foreign key (account_id) references accounts(account_id) on delete cascade)";

            st.executeUpdate(createProductTable);
            st.executeUpdate(createAccountTable);
            st.executeUpdate(createCustomerAccountTable);
            st.executeUpdate(createBusinessAccountTable);
            st.executeUpdate(createCustomProductTable);
            st.executeUpdate(createCartTable);

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
            return false;
        }
    }
}
