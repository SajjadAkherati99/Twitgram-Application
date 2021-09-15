package Model.Request.Chat;

import Controller.ClientHandler;
import Controller.Server;
import Database.Database;
import Model.Request.Request;
import Model.Response.Chat.NewMessageReceived;
import Model.shared.Message.ChatLists;
import Model.shared.Message.Message;
import Model.shared.PublicInformation;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("messageChatSend")
public class MessageSendRequest implements Request {
    private String senderUsername;
    private String receiverUsername;
    private Message message;

    public MessageSendRequest(String senderUsername, String receiverUsername, Message message) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.message = message;
    }

    public MessageSendRequest() {
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
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id) {
        String pathSender = senderUsername + "/chatList.json";
        String pathReceiver = receiverUsername + "/chatList.json";

        PublicInformation infoSender = Database.getInstance().getReader().readPublicInformation(senderUsername);
        PublicInformation infoReceiver = Database.getInstance().getReader().readPublicInformation(receiverUsername);

        ChatLists chatListsSender = Database.getInstance().getReader().readFile(pathSender, ChatLists.class);
        ChatLists chatListsReceiver = Database.getInstance().getReader().readFile(pathReceiver, ChatLists.class);

        chatListsSender.update(message, infoReceiver);
        chatListsReceiver.update(message, infoSender);

        Database.getInstance().getWriter().writeFile(chatListsSender, pathSender);
        Database.getInstance().getWriter().writeFile(chatListsReceiver, pathReceiver);

        if(Server.getInstance().isOnline(receiverUsername)){
            NewMessageReceived messageReceived = new NewMessageReceived(message, infoSender);
            Server.getInstance().sendMessage(infoReceiver, messageReceived);
        }
    }
}
