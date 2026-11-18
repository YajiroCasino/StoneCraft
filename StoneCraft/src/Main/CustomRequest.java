package Main;

public class CustomRequest extends Product{

    private String description;
    private String size;
    private String preferredDesign;

    public CustomRequest(String productName, String productCategory, double productPrice, int productQuantity, String description,
                         String size,String preferredDesign) {

        super(productName, productCategory, productPrice, productQuantity);
        this.description = description;
        this.size = size;
        this.preferredDesign = preferredDesign;
    }

    public void setProductDescription(String description){
        this.description = description;
    }

    public String getProductDescription(){
        return description;
    }

    public void setProductSize(String size){
        this.size = size;
    }

    public String getProductSize(){
        return size;
    }

    public void setProductPreferredDesign(String preferredDesign){
        this.preferredDesign = preferredDesign;
    }

    public String getProductPreferredDesign(){
        return preferredDesign;
    }

}
