package Model.Response;

import Model.Request.ObjectType;
import Model.shared.Announcements;
import Model.shared.ContactList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ObjectResponse implements Response{
    private ObjectType objectType;
    String object;

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
            outputStream.println(objectMapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
