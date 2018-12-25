import java.io.*;
import java.net.*;
import java.util.HashMap;

public class WebServer {

    ServerSocket serverSocket;
    static boolean accepting = true;
    static final int PORT = 5000;

    HashMap<String, String> credentialsList;

    public void go() {
        // Establish connection
        System.out.println("Waiting for a client connection..\n");
        Socket client = null;

        try {
            // Assign port to server
            serverSocket = new ServerSocket(PORT);

            // Loop to accept clients
            while (accepting) {
                client = serverSocket.accept();
                System.out.println("Client connected.\n");

                // Start new thread for client
                Thread t = new Thread(new ConnectionHandler(client));
                t.start();
            }
        } catch (Exception e) {
            // Close all and quit
            try {
                client.close();
            } catch (Exception e1) {
                System.out.println("Failed to close socket.\n");
                e.printStackTrace();
            }
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        new WebServer().go();
    }

}
