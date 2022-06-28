import java.io.IOException;
import java.net.SocketException;

public class Serveur {


    public static void main(String[] args) throws IOException, InterruptedException, TransmissionErrorException {

        CoucheHandler Liaison = new Serveur_Liaison();
        CoucheHandler Transport = new Serveur_Transport();
        CoucheHandler Application = new Serveur_Application();

        Liaison.setNextLayer(Transport);
        Transport.setNextLayer(Application);


        Liaison.run(null);
    }

}
