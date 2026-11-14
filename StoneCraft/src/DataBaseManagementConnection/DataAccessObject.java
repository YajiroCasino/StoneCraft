package DataBaseManagementConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Main.*;
import com.mysql.cj.protocol.Resultset;

public class DataAccessObject {

    static Connection con = DataBaseConnection.getConnection();

    public int insertProduct(Product product) {
        String sqlSyntax = "insert into product (productName, productCategory, productPrice, productQuantity) values (?, ?, ?, ?)";

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

    public static List<Product> getAllProduct() {
        List<Product> product = new ArrayList<>();
    String sqlSyntax = "select productId, productName, productCategory, productPrice, productQuantity from product";

        try (PreparedStatement ps = con.prepareStatement(sqlSyntax);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                product.add(new Product(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("productCategory"),
                        rs.getDouble("productPrice"),
                        rs.getInt("productQuantity")
                ));
            }
        }catch(SQLException e){
                throw new RuntimeException(e);
            }
        return product;
    }

    public static int editProduct(Product product){
        String sqlSyntax = "update product set productName = ?, productCategory = ?, productPrice = ?, productQuantity = ? where productId = ?";
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