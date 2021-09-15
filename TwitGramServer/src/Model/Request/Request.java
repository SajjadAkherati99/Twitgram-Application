package Model.Request;

import Controller.ClientHandler;
import Model.Request.Chat.ContactsUpdateRequest;
import Model.Request.Chat.MessageSendRequest;
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
        @JsonSubTypes.Type(value = SignInRequest.class, name = "signIn"),
        @JsonSubTypes.Type(value = SignUpRequest.class, name = "signUp"),
        @JsonSubTypes.Type(value = InformationRequest.class, name = "information"),
        @JsonSubTypes.Type(value = NewUserInformationRequest.class, name = "newInformation"),
        @JsonSubTypes.Type(value = SearchRequest.class, name = "search"),
        @JsonSubTypes.Type(value = ToUserRequest.class, name = "toUserRequest"),
        @JsonSubTypes.Type(value = DeleteAccountRequest.class, name = "deleteAccount"),
        @JsonSubTypes.Type(value = ObjectRequest.class, name = "object"),
        @JsonSubTypes.Type(value = MessageSendRequest.class, name = "messageChatSend"),
        @JsonSubTypes.Type(value = ContactsUpdateRequest.class, name = "new contacts"),
})public interface Request {
    void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id);
}
