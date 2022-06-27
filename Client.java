import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("one-liners.txt");
        Client_Application Application = new Client_Application(path);
        Application.TransfererFichier();

    }
}

