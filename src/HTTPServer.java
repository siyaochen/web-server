import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Map;

public class HTTPServer {

    static final int PORT = 8000;

    public static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {
        if (query != null) { }
    }

    public static void main(String[] args) {
        try {
            // Initialize server
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            System.out.println("Server started at port " + PORT);

            // Add contexts
            server.createContext("/", new InitialHandler());
            server.createContext("/header", new HeaderHandler());
            server.createContext("/get", new GetHandler());

            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.out.println("Error initializing server.");
            e.printStackTrace();
        }
    }

}
