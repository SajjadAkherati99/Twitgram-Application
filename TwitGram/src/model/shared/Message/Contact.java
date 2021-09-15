package model.shared.Message;

import model.shared.PublicInformation;

import java.util.ArrayList;
import java.util.HashMap;

public class Contact {
    private String name = "";
    private ArrayList<String> userList = new ArrayList<>();

    public Contact() {
    }

    public Contact(String name, ArrayList<String> userList) {
        this.name = name;
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<String> userList) {
        this.userList = userList;
    }
}
