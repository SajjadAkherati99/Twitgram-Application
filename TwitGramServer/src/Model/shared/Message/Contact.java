package Model.shared.Message;

import java.util.ArrayList;

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
