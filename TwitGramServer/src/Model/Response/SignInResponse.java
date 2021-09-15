package Model.Response;

import Model.shared.ContactList;
import Model.shared.Message.ChatLists;
import Model.shared.Message.Contact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SignInResponse implements Response {

    private String response;
    private ChatLists chatLists;
    private ArrayList<Contact> contacts;

    public SignInResponse() {
    }

    public SignInResponse(String response, ChatLists chatLists, ArrayList<Contact> contacts) {
        this.response = response;
        this.chatLists = chatLists;
        this.contacts = contacts;
    }



    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ChatLists getChatLists() {
        return chatLists;
    }

    public void setChatLists(ChatLists chatLists) {
        this.chatLists = chatLists;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
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
