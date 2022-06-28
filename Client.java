import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {

    public static void main(String[] args) throws IOException{

        Path path = Paths.get(args[0]);
        CoucheHandler Application = new Client_Application(path);
        CoucheHandler Transport = new Client_Transport();
        CoucheHandler Liaison = new Client_Liaison(Integer.parseInt(args[1]));
        CoucheHandler Physique = new Physique();

        Application.setNextLayer(Transport);
        Transport.setNextLayer(Liaison);
        Liaison.setNextLayer(Physique);

        Application.run(null);


    }
}

