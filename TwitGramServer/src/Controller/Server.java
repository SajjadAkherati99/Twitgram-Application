package Controller;

import Model.OnlineUser;
import Model.Response.Chat.NewMessageReceived;
import Model.Response.Response;
import Model.shared.PublicInformation;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server extends Thread {
    private static Server server = null;
    private ServerSocket serverSocket;
    private ObjectMapper objectMapper;
    private int port;
    private HashMap<Long, OnlineUser> onlineUsers = new HashMap<>();


    public Server(int port) {
        try {
            this.port = port;
            serverSocket = new ServerSocket(port);
            objectMapper = new ObjectMapper();
            server = this;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("server started at port : " + port);
        while (!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static Server getInstance(){
        return server;
    }

    public HashMap<Long, OnlineUser> getOnlineUsers() {
        return onlineUsers;
    }

    public void addToOnlineUsers(long id, OnlineUser user) {
        onlineUsers.put(id, user);
    }

    public void removeFromOnlineUsers(long id, OnlineUser user){
        onlineUsers.remove(id, user);
    }

    public boolean isOnline(String username) {
        for(OnlineUser onlineUser: onlineUsers.values()){
            if(onlineUser.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }



    public synchronized <T extends Response> void sendMessage(PublicInformation info, T object) {
        OnlineUser user;
        for(OnlineUser onlineUser: onlineUsers.values()){
            if(onlineUser.getUsername().equals(info.getUsername())){
                user = onlineUser;
                user.getClientHandler().sendMessage(object);
                break;
            }
        }
    }
}
