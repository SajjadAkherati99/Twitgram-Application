package model;

import model.shared.Message.Contact;
import model.shared.Message.ChatLists;
import model.shared.UserInformation;

import java.util.ArrayList;

public class App {
    private static App application = null;
    private String username = null;
    private UserInformation userInformation;
    private ChatLists chatLists;
    private ArrayList<Contact> contacts = new ArrayList<>();

    public App() {
    }

    public static App getInstance(){
        if (application == null)
            application = new App();
        return application;
    }

    public static void setInstance(){
        application = new App();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public static App getApplication() {
        return application;
    }

    public static void setApplication(App application) {
        App.application = application;
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
        if(contacts != null) {
            this.contacts = contacts;
        }
    }
}
