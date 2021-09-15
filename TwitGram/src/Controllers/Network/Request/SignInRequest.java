package Controllers.Network.Request;


import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@JsonTypeName("signIn")
public class SignInRequest implements Request {
    private String username;
    private String password;

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignInRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
