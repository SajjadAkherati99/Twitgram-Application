package Model.Request;

import Controller.ClientHandler;
import Database.Database;
import Database.Reader;
import Model.Response.ObjectResponse;
import Model.shared.Announcements;
import Model.shared.ContactList;
import Model.shared.Message.Chat;
import Model.shared.Message.ChatLists;
import Model.shared.PublicInformation;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;

@JsonTypeName("object")
public class ObjectRequest implements Request {
    private String username;
    private ObjectType objectType;

    public ObjectRequest(String username, ObjectType objectType) {
        this.username = username;
        this.objectType = objectType;
    }

    public ObjectRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id) {
        switch (objectType) {
            case ANNOUNCEMENTS -> {
                String path = username + "/announcements.json";
                String announcements = Database.getInstance().getReader().readFileString(path, Announcements.class);
                new ObjectResponse(objectType, announcements).process(inputStream, outputStream, objectMapper, new Object());
            }
            case LISTS -> {
                String path = username + "/contactList.json";
                String contactList = Database.getInstance().getReader().readFileString(path, ContactList.class);
                new ObjectResponse(objectType, contactList).process(inputStream, outputStream, objectMapper, new Object());
            }
            case CHAT_LIST, CHAT_LIST_SELECTED_USER -> {
                String path = username + "/chatList.json";
                String chatLists = Database.getInstance().getReader().readFileString(path, ChatLists.class);
                new ObjectResponse(objectType, chatLists).process(inputStream, outputStream, objectMapper, new Object());
            }
        }
    }
}
