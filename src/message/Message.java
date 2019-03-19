package message;

enum Type{}

// TODO ENUM
public class Message {
    private Type type;
    private String value;

    public Message(Type type, String value){
        this.type = type;
        this.value = value;
    }

    public Message(String message){
        parse(message);
    }

    @Override
    public String toString() {
        return type + "," + value;
    }

    private void parse(String message){
        String[] splits = message.split(",");
        // TODO change this bug 
        // this.type = splits[0];
        this.value = splits[1];
    }
}
