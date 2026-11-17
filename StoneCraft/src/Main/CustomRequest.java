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

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setSize(String size){
        this.size = size;
    }

    public String getSize(){
        return size;
    }

    public void setPreferredDesign(String preferredDesign){
        this.preferredDesign = preferredDesign;
    }

    public String getPreferredDesign(){
        return preferredDesign;
    }

}
