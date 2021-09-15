package Model.Request;

import Controller.ClientHandler;
import Controller.Server;
import Database.Database;
import Database.Reader;
import Model.OnlineUser;
import Model.Response.SignUpResponse;
import Model.shared.Announcements;
import Model.shared.ContactList;
import Model.shared.Message.ChatLists;
import Model.shared.Message.Contact;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

@JsonTypeName("signUp")
public class SignUpRequest implements Request{
    private String name;
    private String username;
    private String password;
    private String birthday;
    private String email;
    private String phoneNumber;
    private String bio;

    public SignUpRequest(String name, String username, String password, String birthday, String email, String phoneNumber, String bio) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
    }

    public SignUpRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    private void createFiles() {
        String path = username + "/contactList.json";
        Database.getInstance().getWriter().writeFile(new ContactList(), path);
        path = username + "/announcements.json";
        Database.getInstance().getWriter().writeFile(new Announcements(), path);
        path = username + "/chatList.json";
        Database.getInstance().getWriter().writeFile(new ChatLists(), path);
        path = username + "/contacts.json";
        Database.getInstance().getWriter().writeFile(new ArrayList<Contact>(), path);
    }

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id) {
        String response = Database.getInstance().getChecker().checkUserExistence(this);
        if(response.equals("OK")){
            OnlineUser user = Server.getInstance().getOnlineUsers().get(id);
            user.setUsername(username);
            createFiles();
        }
        new SignUpResponse(response).process(inputStream, outputStream, objectMapper, new Object());
    }
}
