import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            args = new String[1];
            args[0] = "1";
        }
        Path path = Paths.get("one-liners.txt");
        Client_Application Application = new Client_Application(path, Integer.parseInt(args[0]));
        Application.TransfererFichier();
    }
}

