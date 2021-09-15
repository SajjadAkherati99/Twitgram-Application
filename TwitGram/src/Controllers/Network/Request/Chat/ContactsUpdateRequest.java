package Controllers.Network.Request.Chat;

import Controllers.Network.Request.Request;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.shared.Message.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@JsonTypeName("new contacts")
public class ContactsUpdateRequest implements Request {
    private String username;
    private ArrayList<Contact> contacts;

    public ContactsUpdateRequest() {
    }

    public ContactsUpdateRequest(String username, ArrayList<Contact> contacts) {
        this.username = username;
        this.contacts = contacts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
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
