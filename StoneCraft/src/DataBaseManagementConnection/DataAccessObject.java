package DataBaseManagementConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Main.*;
import com.mysql.cj.protocol.Resultset;

public class DataAccessObject {

    static Connection con = DataBaseConnection.getConnection();

    public static int insertAccounts(Accounts accounts){
        String sqlSyntax = "insert into Accounts(account_name, account_email_address, account_contact_number, account_password, account_type) values (?,?,?,?,?)";

        try(PreparedStatement ps = con.prepareStatement(sqlSyntax, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, accounts.getName());
            ps.setString(2, accounts.getEmailAddress());
            ps.setString(3, accounts.getContactNumber());
            ps.setString(4, accounts.getPassword());
            ps.setString(5, accounts.getAccountType());

            ps.executeUpdate();

            try(ResultSet keys = ps.getGeneratedKeys()){
                if(keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }


    public static boolean updateAccount(Accounts account){
        String sqlSyntax = "update accounts set account_name = ?, account_email_address = ?, account_contact_number = ? where account_id = ?";
        try(Connection con = DataBaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlSyntax)){

            ps.setString(1, account.getName());
            ps.setString(2,account.getEmailAddress());
            ps.setString(3, account.getContactNumber());
            ps.setInt(4, account.getAccountId());

            return ps.executeUpdate() > 0;

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static Accounts getAccountById(int accountId) {
        String sqlSyntax = "select a.*, b.business_account_type, c.customer_account_type " +
                "from accounts a " +
                "left join business_account b on b.account_id = a.account_id " +
                "left join customer_account c on c.account_id = a.account_id " +
                "where a.account_id = ?";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSyntax)) {

            ps.setInt(1, accountId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Accounts acc;

                    if ("Business".equals(rs.getString("account_type"))) {
                        acc = new BusinessAccount(
                                rs.getString("account_name"),
                                rs.getString("account_email_address"),
                                rs.getString("account_contact_number"),
                                rs.getString("account_password"),
                                rs.getString("business_account_type")
                        );
                    } else {
                        acc = new CustomerAccount(
                                rs.getString("account_name"),
                                rs.getString("account_email_address"),
                                rs.getString("account_contact_number"),
                                rs.getString("account_password"),
                                rs.getString("customer_account_type")
                        );
                    }

                    acc.setAccountId(rs.getInt("account_id"));
                    return acc;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static Accounts getAccountsByName(String name) {
        String sqlSyntax = "select a.*, b.business_account_type, c.customer_account_type " +
                "from accounts a " +
                "left join business_account b on b.account_id = a.account_id " +
                "left join customer_account c on c.account_id = a.account_id " +
                "where a.account_name = ?";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSyntax)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Accounts acc;

                    if ("Business".equals(rs.getString("account_type"))) {
                        acc = new BusinessAccount(
                                rs.getString("account_name"),
                                rs.getString("account_email_address"),
                                rs.getString("account_contact_number"),
                                rs.getString("account_password"),
                                rs.getString("business_account_type")
                        );
                    } else {
                        acc = new CustomerAccount(
                                rs.getString("account_name"),
                                rs.getString("account_email_address"),
                                rs.getString("account_contact_number"),
                                rs.getString("account_password"),
                                rs.getString("customer_account_type")
                        );
                    }

                    acc.setAccountId(rs.getInt("account_id"));
                    return acc;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static int insertBusinessAccount(BusinessAccount ba) {
        String sql = "insert into business_account(account_id, business_account_type) values (?, ?)";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ba.getAccountId());
            ps.setString(2, ba.getRole());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int insertCustomerAccount(CustomerAccount ca) {
        String sql = "insert into customer_account(account_id, customer_account_type) values (?, ?)";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ca.getAccountId());
            ps.setString(2, ca.getCustomerType());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int insertProduct(Product product) {
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

    public Product getProductById(int id) {
        String sql = "SELECT product_id, product_name, product_category, "
                + "product_price, product_quantity FROM product WHERE product_id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product(
                            rs.getString("product_name"),
                            rs.getString("product_category"),
                            rs.getDouble("product_price"),
                            rs.getInt("product_quantity")
                    );
                    p.setProductId(rs.getInt("product_id"));
                    return p;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public static int deleteProduct(int productId) {
        String sqlSyntax = "delete from product where product_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sqlSyntax)) {
            ps.setInt(1, productId);
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int insertCustomProduct(CustomRequest cr) {
        String sql = "insert into custom_products(" +
                "custom_product_name, custom_product_category, custom_product_price, " +
                "custom_product_quantity, custom_product_description, " +
                "custom_product_size, custom_product_preferred_design, " +
                "account_id, custom_product_status) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cr.getProductName());
            ps.setString(2, cr.getProductCategory());
            ps.setDouble(3, cr.getProductPrice());
            ps.setInt(4, cr.getProductQuantity());
            ps.setString(5, cr.getProductDescription());
            ps.setString(6, cr.getProductSize());
            ps.setString(7, cr.getProductPreferredDesign());
            ps.setInt(8, cr.getCustomer().getAccountId());
            ps.setString(9, cr.getStatus());
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

                Accounts customer = null;
                int accountId = rs.getInt("account_id");

                if (!rs.wasNull()) {
                    customer = DataAccessObject.getAccountById(accountId);
                }

                CustomRequest cr = new CustomRequest(
                        customer,
                        rs.getString("custom_product_name"),
                        rs.getString("custom_product_category"),
                        rs.getDouble("custom_product_price"),
                        rs.getInt("custom_product_quantity"),
                        rs.getString("custom_product_description"),
                        rs.getString("custom_product_size"),
                        rs.getString("custom_product_preferred_design")
                );
                cr.setProductId(rs.getInt("custom_product_id"));
                cr.setStatus(rs.getString("custom_product_status"));

                list.add(cr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void updateRequestStatus(CustomRequest request, String status) {

        String sql = "UPDATE custom_products SET custom_product_status = ? WHERE custom_product_id = ?";

        try {Connection con = DataBaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, request.getProductId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertCartItem(Cart cartItem) {
        String sql = "insert into cart(product_id, product_cart_quantity) values(?, ?)";
        try (Connection c = DataBaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, cartItem.getProduct().getProductId());   // product id, hindi cart id
            ps.setInt(2, cartItem.getQuantity());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int newId = keys.getInt(1);
                    cartItem.setCartId(newId);
                    return newId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean updateCartQuantity(Cart cart) {
        String sql = "update cart set product_cart_quantity = ? where cart_id = ?";
        try (Connection c = DataBaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, cart.getQuantity());
            ps.setInt(2, cart.getCartId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCartItem(Cart cart) {
        String sql = "delete from cart where cart_id = ?";
        try (Connection c = DataBaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, cart.getCartId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Cart> getAllCart() {
        List<Cart> list = new ArrayList<>();
        String sql = "select c.cart_id, c.product_cart_quantity, "
                + "p.product_id, p.product_name, p.product_category, "
                + "p.product_price, p.product_quantity "
                + "from cart c join product p on c.product_id = p.product_id";

        try (Connection c = DataBaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getString("product_name"),
                        rs.getString("product_category"),
                        rs.getDouble("product_price"),
                        rs.getInt("product_quantity")
                );
                p.setProductId(rs.getInt("product_id"));

                Cart cart = new Cart(p, rs.getInt("product_cart_quantity"));
                cart.setCartId(rs.getInt("cart_id"));
                list.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }







}