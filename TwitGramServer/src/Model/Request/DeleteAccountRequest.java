package Model.Request;

import Controller.ClientHandler;
import Database.Database;
import Database.Reader;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("deleteAccount")
public class DeleteAccountRequest implements Request {
    private String username;

    public DeleteAccountRequest(String username) {
        this.username = username;
    }

    public DeleteAccountRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id) {
        Database.getInstance().getWriter().deleteUser(username);
    }
}
