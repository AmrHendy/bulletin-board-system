package message;

import utilities.RequestType;

public class Message {
    private RequestType type;
    private int value;

    public Message(RequestType type, int value) {
        this.type = type;
        this.value = value;
    }

    public RequestType getRequestType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return type.toString() + "," + value;
    }
}
