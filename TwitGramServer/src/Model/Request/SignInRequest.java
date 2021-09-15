package Model.Request;

import Controller.ClientHandler;
import Controller.Server;
import Database.Database;
import Database.Reader;
import Model.OnlineUser;
import Model.Response.SignInResponse;
import Model.shared.Message.ChatLists;
import Model.shared.Message.Contact;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

@JsonTypeName("signIn")
public class SignInRequest implements Request {
    private String username;
    private String password;

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignInRequest() {
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

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id) {
        String response = Database.getInstance().getChecker().checkSignInRequestInformation(this);
        ChatLists chatLists = new ChatLists();
        ArrayList<Contact> contacts = new ArrayList<>();
        if (response.equals("OK")){
            OnlineUser user = Server.getInstance().getOnlineUsers().get(id);
            user.setUsername(username);
            Database.getInstance().getWriter().updateDeActiveAccounts(username);
            String path = username + "/chatList.json";
            chatLists = Database.getInstance().getReader().readFile(path, ChatLists.class);
            path = username + "/contacts.json";
            contacts = Database.getInstance().getReader().readFile(path, ArrayList.class);
        }
        new SignInResponse(response, chatLists, contacts).process(inputStream, outputStream, objectMapper, new Object());
    }
}
