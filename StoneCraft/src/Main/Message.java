package Main;

public class Message {

    private Person sender;
    private Person receiver;
    private String messages;

    public Message(Person sender, Person receiver, String messages){
        this.sender = sender;
        this.receiver = receiver;
        this.messages = messages;
    }

    public Person getSender(){
        return sender;
    }

    public void setSender(Person sender){
        this.sender = sender;
    }

    public Person getReceiver(){
        return receiver;
    }
    public void set

}
