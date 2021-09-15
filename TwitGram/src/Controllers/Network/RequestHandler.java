package Controllers.Network;

import Controllers.Network.Request.Request;
import Controllers.view.StartUpController;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler {
    private static RequestHandler requestHandler;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter printWriter;
    private ObjectMapper objectMapper;
    private ResponseHandler responseHandler;
    private final Object object = new Object();

    public RequestHandler(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            objectMapper = new ObjectMapper();
            responseHandler = new ResponseHandler(reader, printWriter, objectMapper, object);
            responseHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createInstance(Socket socket) {
        requestHandler = new RequestHandler(socket);
    }

    public static RequestHandler getInstance() {
        return requestHandler;
    }

    public synchronized void sendRequest(Request request) {
        if (socket.isConnected()) {
            request.execute(reader, printWriter, objectMapper, object);
        } else {
            StartUpController.getInstance().popupAlert(Alert.AlertType.ERROR, "You are offline!", "Info");
        }
    }

    public static void Connect(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            createInstance(socket);
        } catch (IOException e) {
            StartUpController.getInstance().popupAlert(Alert.AlertType.ERROR, "Connection Error!!!", "Error");
        }
    }

    public void closeSocket() {
        try {
            if(this.socket != null && !this.socket.isClosed())
            {
                this.socket.close();
                System.out.println("socket is closed");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while closing the socket");
        }
    }
}
