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

        private Socket client;
        private BufferedReader in;
        private InputStreamReader stream;
        private DataOutputStream out;
        private boolean running;

        ConnectionHandler(Socket s) throws IOException {
            client = s;

            // Assign all connections to client
            try {
                out = new DataOutputStream(client.getOutputStream());
                stream = new InputStreamReader(client.getInputStream());
                in = new BufferedReader(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            running = true;

            // SEND INITIAL FILE HERE
        }

        @Override
        public void run() {

        }

    }

    public static void main(String[] args) {
        new WebServer().go();
    }

}
