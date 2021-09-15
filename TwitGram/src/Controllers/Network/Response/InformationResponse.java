package Controllers.Network.Response;

import Controllers.view.ProgramController;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.App;
import model.shared.UserInformation;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

@JsonTypeName("information")
public class InformationResponse implements Response{
    private String response;

    public InformationResponse() {
    }

    public InformationResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public void process(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        try {
            UserInformation user = objectMapper.readValue(response, UserInformation.class);
            App.getInstance().setUserInformation(user);
            ProgramController.getInstance().goToAppropriateWindow();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
