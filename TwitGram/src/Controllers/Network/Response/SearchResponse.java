package Controllers.Network.Response;

import Controllers.view.ProgramController;
import Controllers.view.program.explorer.ExplorerController;
import Controllers.view.program.profile.ProfileController;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.shared.PublicInformation;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("search")
public class SearchResponse implements Response {
    private String responseHeader;
    private PublicInformation information;

    public SearchResponse() {
    }

    public SearchResponse(String responseHeader, PublicInformation information) {
        this.responseHeader = responseHeader;
        this.information = information;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public PublicInformation getInformation() {
        return information;
    }

    public void setInformation(PublicInformation information) {
        this.information = information;
    }

    @Override
    public void process(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        ExplorerController.getInstance().showResponse(responseHeader, information);
    }
}
