package Controllers.Network.Response;

import Controllers.view.StartUpController;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.App;
import model.shared.ContactList;
import model.shared.Message.ChatLists;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

@JsonTypeName("signUp")
public class SignUpResponse implements Response {
    private String response;

    public SignUpResponse(String response) {
        this.response = response;
    }

    public SignUpResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public void process(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        StartUpController.getInstance().showResponse(response);
        if(response.equals("OK")){
            App.getInstance().setChatLists(new ChatLists());
        }
    }
}
