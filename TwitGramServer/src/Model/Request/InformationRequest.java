package Model.Request;

import Controller.ClientHandler;
import Database.Database;
import Database.Reader;
import Model.Response.InformationResponse;
import Model.shared.UserInformation;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("information")
public class InformationRequest implements Request {
    private String username;

    public InformationRequest() {
    }

    public InformationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id) {
        String path = username+"/info.json";
        String response = Database.getInstance().getReader().readFileString(path, UserInformation.class);
        new InformationResponse(response).process(inputStream, outputStream, objectMapper, new Object());
    }
}

