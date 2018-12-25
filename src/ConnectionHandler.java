import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {

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

    public void sendFile(String fileName) {

    }

    @Override
    public void run() {
        // Define variables
        String line, user, pass, desc;
        ArrayList <String> msg = new ArrayList<String>();

        // Receive message from client
        while (running) {
            try {
                // Check for incoming message
                if (in.ready()) {
                    // Read message line
                    line = in.readLine();

                    // Add overall message to process, blank line signals end
                    if (!line.equals("")) {
                        msg.add(line);
                    } else {
                        // Display message
                        System.out.println();
                        for (String s : msg) System.out.println(s);
                        System.out.println();

                        // Process request header
                        String header = "";
                        if (msg.size() > 0) header = msg.get(0);
                        if (header.length() >= 3 && header.substring(0, 3).equals("GET")) {
                            // Process GET request
                            new GetHandler(msg);
                        } else if (header.length() >= 4 && header.substring(0, 4).equals("POST")) {
                            // Process POST request
                            new PostHandler(msg);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to receive message from client.");
                e.printStackTrace();
            }
        }
    }

}
