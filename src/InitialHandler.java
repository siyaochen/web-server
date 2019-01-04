import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class InitialHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        // Parse request
        String query = he.getRequestURI().getQuery();
        System.out.println("This is the query: " + query + "\n");

        // Read file to send
        File file = new File("website/index.html");
        BufferedInputStream in;
        String response = "";
        try {
            System.out.println("Attempting to read index.html...");
            in = new BufferedInputStream(new FileInputStream(file));
            byte[] arr = new byte[(int) file.length()];
            in.read(arr, 0, (int) file.length());
            response = new String(arr);
        } catch (IOException e) {
            System.out.println("Unable to read index.html");
            e.printStackTrace();
        }

        // Send HTTP response
        System.out.println("Attempting to send index.html...\n");
        he.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
