import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {

    public static void main(String[] args) throws IOException{
        if(args.length == 0){
            args = new String[1];
            args[0] = "0";
        }
        Path path = Paths.get("one-liners.txt");
        CoucheHandler Application = new Client_Application(path);
        CoucheHandler Transport = new Client_Transport();
        CoucheHandler Liaison = new Client_Liaison(Integer.parseInt(args[0]));

        Application.setNextLayer(Transport);
        Transport.setNextLayer(Liaison);


        Application.run(null);


    }
}

