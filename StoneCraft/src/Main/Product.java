package Main;

public class Product {
    private String productId;
    private String productName;
    private String productCategory;
    private double productPrice;
    private int productQuantity;

    public Product(String productId, String productName, String productCategory, double productPrice, int productQuantity){
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public String getProductId(){
        return productId;
    }

    public void getProductId(String productId){
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public String getProductCategory(){
        return productCategory;
    }

    public void setProductCategory(String productCategory){
        this.productCategory = productCategory;
    }

    public double getProductPrice(){
        return productPrice;
    }

    public void setProductPrice(double productPrice){
        this.productPrice = productPrice;
    }

    public int getProductQuantity(){
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity){
        this.productQuantity = productQuantity;
    }
}

