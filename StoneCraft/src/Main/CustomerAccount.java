package Main;

public class CustomerAccount extends Accounts{

    private int customerAccountId;
    private String customerType;

    public CustomerAccount(String name, String emailAddress, String contactNumber, String password, String customerType){
        super(name, emailAddress, contactNumber,password);
        this.customerType = customerType;
    }


    public int getCustomerAccountId(){
        return customerAccountId;
    }

    public void setCustomerAccountId(int customerAccountId){
        this.customerAccountId = customerAccountId;
    }
    public String getCustomerType(){
        return customerType;
    }

    public void setCustomerType(String customerType){
        this.customerType = customerType;
    }

    public String getAccountType(){
        return "Customer";
    }
}
