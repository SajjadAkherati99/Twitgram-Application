package model.shared.Message;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Message> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
