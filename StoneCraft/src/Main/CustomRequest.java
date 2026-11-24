package Main;

public class CustomRequest extends Product {

    private int customRequestId;
    private String description;
    private String size;
    private String preferredDesign;
    private Accounts customer;
    private String status;

    public CustomRequest(Accounts customer, String productName, String productCategory, double productPrice, int productQuantity, String description, String size, String preferredDesign) {
        super(productName, productCategory,productPrice, productQuantity);
        this.customer = customer;
        this.description = description;
        this.size = size;
        this.preferredDesign = preferredDesign;
    }

    public void setCustomRequestId(int customRequestId){
        this.customRequestId = customRequestId;
    }
    public int getCustomRequestId(){
        return customRequestId;
    }
    public void setProductDescription(String description) { this.description = description; }
    public String getProductDescription() { return description; }

    public void setProductSize(String size) { this.size = size; }
    public String getProductSize() { return size; }

    public void setProductPreferredDesign(String preferredDesign) { this.preferredDesign = preferredDesign; }
    public String getProductPreferredDesign() { return preferredDesign; }

    public Accounts getCustomer() {
        return customer;
    }
    public void setCustomer(Accounts customer){
        this.customer = customer;
    }

    public String getCustomerName() {
        return customer == null ? "" : customer.getName();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}