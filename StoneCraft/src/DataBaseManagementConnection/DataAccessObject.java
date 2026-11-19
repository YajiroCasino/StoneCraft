package DataBaseManagementConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Main.*;
import com.mysql.cj.protocol.Resultset;

public class DataAccessObject {

    static Connection con = DataBaseConnection.getConnection();

    public static int insertAccounts(Accounts accounts){
        String sqlSyntax = "insert into Accounts(account_name, account_email_address, account_contact_number, account_password) values (?,?,?,?)";

        try(PreparedStatement ps = con.prepareStatement(sqlSyntax, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, accounts.getName());
            ps.setString(2, accounts.getEmailAddress());
            ps.setString(3, accounts.getContactNumber());
            ps.setString(4, accounts.getPassword());

            ps.executeUpdate();

            try(ResultSet keys = ps.getGeneratedKeys()){
                if(keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public static Accounts getAccounts(String name) {
        String sqlSyntax = "Select * from accounts where account_name = ?";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSyntax)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Accounts(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("contact_number"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
        public int insertOrder(Product product){
        String sqlSyntax = "insert into orders (product_name, product_category, product_price, product_quantity) values (?, ?, ?, ?)";

        try(PreparedStatement ps = con.prepareStatement(sqlSyntax, Statement.RETURN_GENERATED_KEYS)) {

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int insertProduct(Product product) {
        String sqlSyntax = "insert into product (product_name, product_category, product_price, product_quantity) values (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sqlSyntax, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductCategory());
            ps.setDouble(3, product.getProductPrice());
            ps.setInt(4, product.getProductQuantity());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static int insertCustomProduct(CustomRequest customRequest) {
        String sqlSyntax = "insert into custom_products(" +
                "custom_product_name, custom_product_category, custom_product_price, " +
                "custom_product_quantity, custom_product_description, " +
                "custom_product_size, custom_product_preferred_design) " +
                "values(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sqlSyntax, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customRequest.getProductName());
            ps.setString(2, customRequest.getProductCategory());
            ps.setDouble(3, customRequest.getProductPrice());
            ps.setInt(4, customRequest.getProductQuantity());
            ps.setString(5, customRequest.getProductDescription());
            ps.setString(6, customRequest.getProductSize());
            ps.setString(7, customRequest.getProductPreferredDesign());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<CustomRequest> getAllCustomProduct() {
        List<CustomRequest> list = new ArrayList<>();
        String sql = "select * from custom_products";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CustomRequest cr = new CustomRequest(
                        rs.getString("custom_product_name"),
                        rs.getString("custom_product_category"),
                        rs.getDouble("custom_product_price"),
                        rs.getInt("custom_product_quantity"),
                        rs.getString("custom_product_description"),
                        rs.getString("custom_product_size"),
                        rs.getString("custom_product_preferred_design")
                );
                list.add(cr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();

        String sql = "select product_id, product_name, product_category, product_price, product_quantity from product";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getString("product_name"),
                        rs.getString("product_category"),
                        rs.getDouble("product_price"),
                        rs.getInt("product_quantity")
                );
                p.setProductId(rs.getInt("product_id"));
                products.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public static int editProduct(Product product){
        String sqlSyntax = "update product set product_name = ?, product_category = ?, product_price = ?, product_quantity = ? where product_id = ?";
        try(PreparedStatement ps = con.prepareStatement(sqlSyntax)){
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductCategory());
            ps.setDouble(3, product.getProductPrice());
            ps.setInt(4, product.getProductQuantity());
            ps.setInt(5, product.getProductId());
            return ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }



}