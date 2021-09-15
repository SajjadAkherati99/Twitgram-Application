package Controller;

import Database.DateAndTimeHandler;
import Model.OnlineUser;
import Model.Request.Request;
import Model.Response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    public final Server server;
    public final Socket socket;
    public PrintWriter output;
    public BufferedReader input;
    public final ObjectMapper objectMapper;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        objectMapper = new ObjectMapper();
        Server.getInstance().getOnlineUsers().put(this.getId(), new OnlineUser(this));
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            String msg = "";
            while ((msg = input.readLine()) != null) {
                executeReq(msg, this.getId());
            }
        } catch (IOException e) {
            closeSocket();
        }
        closeSocket();
    }

    private void closeSocket() {
        try {
            if(this.socket != null && !this.socket.isClosed())
            {
                this.socket.close();
                System.out.println("socket is closed");
                OnlineUser user = Server.getInstance().getOnlineUsers().remove(this.getId());
                if (!user.getUsername().equals("")) {
                    DateAndTimeHandler.updateLastSeen(user.getUsername());
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while closing the socket");
        }
    }

    public synchronized void executeReq(String string, long id) {
        new Thread(() -> {
            try {
                Request request = objectMapper.readValue(string, Request.class);
                request.execute(input, output, this, objectMapper, id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public <T extends Response> void sendMessage(T object){
        object.process(input, output, objectMapper, new Object());
    }
}
