import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("one-liners.txt");
        CoucheHandler Application = new Client_Application(path);
        CoucheHandler Transport = new Client_Transport();
        CoucheHandler Liaison = new Client_Liaison();

        Application.setNextLayer(Transport);
        Transport.setNextLayer(Liaison);


        Application.run(null);


    }
}

