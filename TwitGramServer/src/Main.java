import Controller.Server;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Please enter the port number for server");
        System.out.print("port number: ");

        int port = new Scanner(System.in).nextInt();
        //System.out.println("");

        if (port > 0) {
            new Server(port).start();
        } else {
            new Server(5050).start();
        }
    }
}
