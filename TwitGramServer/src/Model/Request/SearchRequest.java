package Model.Request;

import Controller.ClientHandler;
import Database.Database;
import Database.Reader;
import Model.Response.SearchResponse;
import Model.shared.PublicInformation;
import Model.shared.UserInformation;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("search")
public class SearchRequest implements Request {
    private String username;

    public SearchRequest() {
    }

    public SearchRequest(String username) {
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
        String header = (Database.getInstance().getChecker().searchUser(username)).toString();
        PublicInformation info;
        if (header == "OK"){
            String path = username + "/info.json";
            UserInformation user = Database.getInstance().getReader().readFile(path, UserInformation.class);
            info = new PublicInformation(user);
        }
        else {
            info = new PublicInformation();
        }
        new SearchResponse(header, info).process(inputStream, outputStream, objectMapper, new Object());
    }
}
