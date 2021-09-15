package Model.Request;

import Controller.ClientHandler;
import Controller.Server;
import Database.Database;
import Database.Reader;
import Model.Response.NewUserInformationResponse;
import Model.shared.UserInformation;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("newInformation")
public class NewUserInformationRequest implements Request {
    private String oldUsername;
    private String newUsername;
    private UserInformation newUserInformation;

    public NewUserInformationRequest(String oldUsername, String newUsername, UserInformation newUserInformation) {
        this.oldUsername = oldUsername;
        this.newUsername = newUsername;
        this.newUserInformation = newUserInformation;
    }

    public NewUserInformationRequest() {
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }

    public UserInformation getNewUserInformation() {
        return newUserInformation;
    }

    public void setNewUserInformation(UserInformation newUserInformation) {
        this.newUserInformation = newUserInformation;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id) {
        String path = oldUsername + "/info.json";
        String response = Database.getInstance().getWriter().writeNewInformation(path, oldUsername, newUsername, newUserInformation);
        if(response.equals("INFO_CHANGED")){
            Server.getInstance().getOnlineUsers().get(id).setUsername(newUsername);
        }
        new NewUserInformationResponse(response).process(inputStream, outputStream, objectMapper, new Object());
    }
}
