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

            String createProductTable = ("create table if not exists product( productId auto_increment int, productName varchar(100)" +
                    " productPrice double)");

            st.executeUpdate(createProductTable);


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
}
