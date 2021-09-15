package Model;

import Controller.ClientHandler;
import Model.shared.ContactList;

public class OnlineUser {
    private String username = "";
    private ClientHandler clientHandler;

    public OnlineUser(String username, ClientHandler clientHandler) {
        this.username = username;
        this.clientHandler = clientHandler;
    }

    public OnlineUser(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
}
