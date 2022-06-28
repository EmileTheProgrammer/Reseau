import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Client_Transport extends Handler{
    byte [] FichierByte;
    Client_Liaison Liaison;
    byte [] FichierNom;
    byte [] temp;
    List<byte[]> paquets = new ArrayList<>();
    int compteur = 1;
    final int taille = 200;
    public Client_Transport(byte [] NomByte, byte [] FichierByte, int errorCode) throws IOException {
        this.FichierByte=FichierByte;
        this.FichierNom = NomByte;
        Liaison = new Client_Liaison(errorCode);
    }
    public void run() throws IOException {
        int dernierPaquet = FichierByte.length/200;
        byte [] compteurB = ByteBuffer.allocate(5).putInt(compteur).array();
        byte [] premier =ByteBuffer.allocate(5).putInt(0).array();
        byte [] dernier = ByteBuffer.allocate(5).putInt(dernierPaquet).array();

        ByteArrayOutputStream header = new ByteArrayOutputStream();
        //header.write(nom);
        header.write(compteurB);
        header.write(premier);
        header.write(dernier);
        header.close();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        temp= Arrays.copyOfRange(FichierByte,0,taille);
        out.write(header.toByteArray());
        out.write(temp);
        out.close();
        Liaison.run(out.toByteArray());

        for(int i=200;i<FichierByte.length;i=i+taille){

            ByteArrayOutputStream head = new ByteArrayOutputStream();
            head.write(compteurB);
            head.write(premier);
            head.write(dernier);
            head.close();

            temp = Arrays.copyOfRange(FichierByte,i,taille+i);
            head.write(temp);

            Liaison.run(head.toByteArray());
            compteur++;
        }
    }
}
