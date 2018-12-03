import java.io.*;
import java.net.*;
import java.util.HashMap;

public class WebServer {

    ServerSocket serverSocket;
    static boolean accepting = true;

    HashMap<String, String> credentialsList;

    public void go() {
        // Establish connection
        System.out.println("Waiting for a client connection..\n");
        Socket client = null;

        try {
            // Assign port to server
            serverSocket = new ServerSocket(5000);

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

    // INNER CLASS - Thread for client connection
    class ConnectionHandler implements Runnable {

        ConnectionHandler(Socket s) throws IOException {

        }

        public void run() {

        }

    }

    public static void main(String[] args) {
        new WebServer().go();
    }

}
