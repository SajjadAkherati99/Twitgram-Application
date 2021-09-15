package Controllers.Network.Request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.App;
import model.shared.UserInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@JsonTypeName("newInformation")
public class NewUserInformationRequest implements Request{
    private String oldUsername = App.getInstance().getUsername();
    private String newUsername;
    private UserInformation newUserInformation;

    public NewUserInformationRequest(UserInformation newUserInformation) {
        this.newUserInformation = newUserInformation;
        this.newUsername = newUserInformation.getUsername();
    }

    public NewUserInformationRequest() {
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }

    public String getOldUsername() {
        return oldUsername;
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
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        try {
            String s = objectMapper.writeValueAsString(this);
            outputStream.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
