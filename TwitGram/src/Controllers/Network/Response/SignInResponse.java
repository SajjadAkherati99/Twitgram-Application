package Controllers.Network.Response;

import Controllers.view.StartUpController;
import model.shared.Message.Contact;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.App;
import model.shared.Message.ChatLists;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

@JsonTypeName("signIn")
public class SignInResponse implements Response {
    private String response;
    private ChatLists chatLists;
    private ArrayList<Contact> contacts;

    public SignInResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ChatLists getChatLists() {
        return chatLists;
    }

    public void setChatLists(ChatLists chatLists) {
        this.chatLists = chatLists;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void process(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        if(response.equals("OK")){
            App.getInstance().setChatLists(chatLists);
            App.getInstance().setContacts(contacts);
        }
        StartUpController.getInstance().showResponse(response);
    }
}
