package Controllers.Network.Response;

import Controllers.Network.Request.ObjectType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.shared.Announcements;
import model.shared.ContactList;
import model.shared.Message.ChatLists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@JsonTypeName("object")
public class ObjectResponse implements Response {
    private ObjectType objectType;
    private String object;

    public ObjectResponse(ObjectType objectType, String object) {
        this.objectType = objectType;
        this.object = object;
    }

    public ObjectResponse() {
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public void process(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        try {
            switch (objectType) {
                case ANNOUNCEMENTS -> {
                    Announcements announcements = objectMapper.readValue(this.object, Announcements.class);
                    announcements.goToFrame();
                }
                case LISTS -> {
                    ContactList contactList = objectMapper.readValue(this.object, ContactList.class);
                    contactList.goToFrame();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
