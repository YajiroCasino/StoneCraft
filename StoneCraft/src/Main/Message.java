package Main;

public class Message {

    private Accounts sender;
    private Accounts receiver;
    private String messages;

    public Message(Accounts sender, Accounts receiver, String messages){
        this.sender = sender;
        this.receiver = receiver;
        this.messages = messages;
    }
    public Accounts getSender(){
        return sender;
    }

    public void setSender(Accounts sender){
        this.sender = sender;
    }

    public Accounts getReceiver(){
        return receiver;
    }

    public void setReceiver(Accounts receiver){
        this.receiver = receiver;
    }

    public String getMessages(){
        return messages;
    }
    public void Messages(String messages){
        this.messages = messages;
    }



}
