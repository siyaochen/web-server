import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HTTPServer {

    static final int PORT = 8000;

    public static void main(String[] args) {
        try {
            // Initialize server
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            System.out.println("Server started at port " + PORT);

            // Add contexts
            server.createContext("/", new InitialHandler());

            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.out.println("Error initializing server.");
            e.printStackTrace();
        }
    }

}
