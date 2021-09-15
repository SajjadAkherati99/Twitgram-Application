package Controllers.Network.Request.Chat;

import Controllers.Network.Request.Request;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.shared.Message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@JsonTypeName("messageChatSend")
public class MessageChatSendRequest implements Request {
    private String senderUsername;
    private String receiverUsername;
    private Message message;

    public MessageChatSendRequest(String senderUsername, String receiverUsername, Message message) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.message = message;
    }

    public MessageChatSendRequest() {
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        try {
            String s = objectMapper.writeValueAsString(this);
            outputStream.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
