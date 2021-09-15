package Controllers.Network.Request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@JsonTypeName("object")
public class ObjectRequest implements Request {
    private String username;
    private ObjectType objectType;

    public ObjectRequest(String username, ObjectType objectType) {
        this.username = username;
        this.objectType = objectType;
    }

    public ObjectRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object) {
        try {
            String s = objectMapper.writeValueAsString(this);
            outputStream.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
