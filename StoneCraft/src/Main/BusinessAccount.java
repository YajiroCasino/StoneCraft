package Main;

public class BusinessAccount extends Accounts{

    private String role;

    public BusinessAccount(String name, String emailAddress, String contactNumber, String password, String role){
        super(name, emailAddress, contactNumber,password);
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getAccountType(){
        return "Business";
    }

}
