package Main;

public class CustomerAccount {
    private String name;
    private String emailAddress;
    private String contactNumber;
    private String password;
    private String password2;

    public CustomerAccount(String name, String emailAddress, String contactNumber,String password, String password2){
        this.name = name;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.password = password;
        this.password2 = password2;
    }

    public void showAccount(){
        System.out.println(name + emailAddress + contactNumber + password + password2);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getEmailAddress(){
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public String getContactNumber(){
        return contactNumber;
    }
    public void setContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword2(){
        return password2;
    }
    public void setPassword2(String password2){
        this.password2 = password2;
    }

}
