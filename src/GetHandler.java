import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;

public class GetHandler {

    public GetHandler(String header, ArrayList<String> msg, ConnectionHandler ch) {
        // Parse to find requested file
        String fileName = header.substring(5, header.indexOf(" HTTP/"));

        // Send file if it exists
        System.out.println("Requesting " + fileName);
        if ((new File(fileName)).exists()) ch.sendFile(fileName);
        else if (fileName.indexOf(".html") != -1) ch.sendFile("404.html");
        else System.out.println("ERROR: File " + fileName + " cannot be found");
    }

}
