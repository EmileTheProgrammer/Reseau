import java.io.IOException;
import java.net.SocketException;

public class Serveur {


    public static void main(String[] args) throws IOException {
        Serveur_Liaison Liaison = new Serveur_Liaison();
        Liaison.run();

    }

}
