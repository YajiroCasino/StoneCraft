package Main;

public class Cart {
    private int cartId;
    private Product product;
    private int quantity;

    public Cart(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public void setCartId(int cartId){
        this.cartId = cartId;
    }

    public int getCartId(){
        return cartId;
    }
    public void setProduct(Product product){
        this.product = product;
    }

    public Product getProduct(){
        return product;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return quantity;
    }
}
