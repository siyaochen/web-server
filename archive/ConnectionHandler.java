import java.io.*;
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

        sendFile("index.html");
    }

    public void sendFile(String fileName) {
        File file = new File("website/" + fileName);
        BufferedInputStream input;

        try {
            System.out.println("Attempting to send " + fileName + "\n");

            input = new BufferedInputStream(new FileInputStream(file));

            // Convert file to byte array to send
            System.out.println("File size: " + file.length());
            byte[] arr = new byte[(int) file.length()];

            // Send file header info
            out.writeBytes("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + file.length() + "\r\n");
            out.flush();
            /*out.writeBytes("Content-Type: text/html" + "\n");
            out.flush();
            out.writeBytes("Contentc-Length: " + file.length() + "\n\n");
            out.flush();*/

            // Read data into byte array and send
            input.read(arr, 0, (int) file.length());
            System.out.println("Read: " + file.length() + " bytes");
            out.write(arr, 0, arr.length);
            System.out.println("Sent: " + file.length() + " bytes\n");
            out.flush();
            input.close();
        } catch (IOException e) {
            System.out.println("Unable to find " + fileName + "\n");
            e.printStackTrace();
        }
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
                            new GetHandler(header, msg, this);
                        } else if (header.length() >= 4 && header.substring(0, 4).equals("POST")) {
                            // Process POST request
                            new PostHandler(header, msg, this);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to receive message from client.");
                e.printStackTrace();
            }
        }

        // Close socket
        try {
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            System.out.println("Failed to close socket");
        }
    }

}
