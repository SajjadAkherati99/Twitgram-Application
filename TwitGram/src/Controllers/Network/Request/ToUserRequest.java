package Controllers.Network.Request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@JsonTypeName("toUserRequest")
public class ToUserRequest implements Request {
    private String username;
    private UserTypeRequest userTypeRequest;

    public ToUserRequest(String username, UserTypeRequest userTypeRequest) {
        this.username = username;
        this.userTypeRequest = userTypeRequest;
    }

    public ToUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserTypeRequest getUserTypeRequest() {
        return userTypeRequest;
    }

    public void setUserTypeRequest(UserTypeRequest userTypeRequest) {
        this.userTypeRequest = userTypeRequest;
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
