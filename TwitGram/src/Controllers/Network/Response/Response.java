package Controllers.Network.Response;

import Controllers.Network.Response.Chat.NewMessageReceived;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "model")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SignInResponse.class, name = "signIn"),
        @JsonSubTypes.Type(value = SignUpResponse.class, name = "signUp"),
        @JsonSubTypes.Type(value = InformationResponse.class, name = "information"),
        @JsonSubTypes.Type(value = NewUserInformationResponse.class, name = "newInformation"),
        @JsonSubTypes.Type(value = SearchResponse.class, name = "search"),
        @JsonSubTypes.Type(value = ObjectResponse.class, name = "object"),
        @JsonSubTypes.Type(value = NewMessageReceived.class, name = "newMessage"),
})
public interface Response {
    public void process(BufferedReader inputStream, PrintWriter outputStream, ObjectMapper objectMapper, Object object);
}
