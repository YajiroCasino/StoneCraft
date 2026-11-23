package Main;

public class CustomRequest extends Product {

    private String description;
    private String size;
    private String preferredDesign;
    private String customerName = "";
    private String status = "Pending";

    public CustomRequest(String customerName, String productName, String productCategory, double productPrice, int productQuantity, String description, String size, String preferredDesign) {
        super(productName, productCategory,productPrice, productQuantity);
        this.customerName = customerName;
        this.description = description;
        this.size = size;
        this.preferredDesign = preferredDesign;
    }

    public void setProductDescription(String description) { this.description = description; }
    public String getProductDescription() { return description; }

    public void setProductSize(String size) { this.size = size; }
    public String getProductSize() { return size; }

    public void setProductPreferredDesign(String preferredDesign) { this.preferredDesign = preferredDesign; }
    public String getProductPreferredDesign() { return preferredDesign; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}