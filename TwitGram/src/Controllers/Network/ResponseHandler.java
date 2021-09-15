package Controllers.Network;

import Controllers.Network.Response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ResponseHandler extends Thread{

    private BufferedReader input;
    private PrintWriter output;
    private ObjectMapper objectMapper;
    private Object object;

    public ResponseHandler(BufferedReader input, PrintWriter output, ObjectMapper objectMapper, Object object) {
        this.input = input;
        this.output = output;
        this.objectMapper = objectMapper;
        this.object = object;
    }

    @Override
    public void run() {
        String msg = "";
        try {
            while ((msg = input.readLine()) != null) {
                processRes(msg);
            }
        } catch (IOException e){
        }
    }

    public void processRes(String resString) {
        new Thread(() -> {
            try {
                Response response = objectMapper.readValue(resString, Response.class);
                response.process(input, output, objectMapper, object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
