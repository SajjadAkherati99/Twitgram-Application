package Controllers.Network.Response;

import Controllers.view.PopAlert;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("newInformation")
public class NewUserInformationResponse implements Response {
    private String response;

    public NewUserInformationResponse(String response) {
        this.response = response;
    }

    public NewUserInformationResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public void process(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        PopAlert.getInstance().showResponse(response);
    }
}
