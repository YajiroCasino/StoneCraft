package Main;

public abstract class Accounts {

    protected int accountId;
    protected String name;
    protected String emailAddress;
    protected String contactNumber;
    protected String password;

    public Accounts(String name, String emailAddress, String contactNumber, String password) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.password = password;
    }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public abstract String getAccountType();
}