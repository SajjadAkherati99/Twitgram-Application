package Model.Response;

import Model.shared.PublicInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class SearchResponse implements Response{
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
        try {
            outputStream.println(objectMapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
