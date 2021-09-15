package Model.Response.Chat;

import Model.Response.Response;
import Model.shared.Message.Message;
import Model.shared.PublicInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class NewMessageReceived implements Response {
    private Message message;
    private PublicInformation info;

    public NewMessageReceived(Message message, PublicInformation info) {
        this.message = message;
        this.info = info;
    }

    public NewMessageReceived() {
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public PublicInformation getInfo() {
        return info;
    }

    public void setInfo(PublicInformation info) {
        this.info = info;
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
