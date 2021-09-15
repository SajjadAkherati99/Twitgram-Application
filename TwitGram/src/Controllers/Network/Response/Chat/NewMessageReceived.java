package Controllers.Network.Response.Chat;
import Controllers.Network.Response.Response;
import Controllers.view.ProgramController;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.App;
import model.shared.Message.Message;
import model.shared.PublicInformation;
import model.shared.UserInformation;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("newMessage")
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
        App.getInstance().getChatLists().update(message, info);
    }
}
