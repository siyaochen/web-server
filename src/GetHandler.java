import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class GetHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        // Parse request
        Map<String, Object> parameters = new HashMap<String, Object>();
        URI requestedUri = he.getRequestURI();
        String query = requestedUri.getRawQuery();
        HTTPServer.parseQuery(query, parameters);

        // Send response
        String response = "";
        for (String key : parameters.keySet()) response += key + " = " + parameters.get(key) + "\n";
        he.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
