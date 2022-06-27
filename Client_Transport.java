import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Client_Transport extends Handler{
    byte [] FichierByte;
    Client_Liaison Liaison = new Client_Liaison();
    byte [] FichierNom;
    byte [] temp;
    List<byte[]> paquets = new ArrayList<>();
    int compteur = 1;
    final int taille = 200;
    public Client_Transport(byte [] NomByte, byte [] FichierByte) throws IOException {
        this.FichierByte=FichierByte;
        this.FichierNom = NomByte;
    }
    public void run() throws IOException {
        ByteArrayOutputStream header = new ByteArrayOutputStream();
        //header.write((byte)compteur);
        //header.write(FichierByte);
        //header.write(FichierByte.length/200);
        header.close();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        temp= Arrays.copyOfRange(FichierNom,0,taille);
        out.write(header.toByteArray());
        out.write(temp);

    Liaison.run(out.toByteArray());

/*for(int i=0;i<FichierByte.length;i=i+taille){

    ByteArrayOutputStream head = new ByteArrayOutputStream();
    head.write((byte)compteur);
    head.write(FichierByte);
    head.write(FichierByte.length/200);
    temp = Arrays.copyOfRange(FichierByte,i,taille+i);
    head.write(temp);
    Liaison.run(head.toByteArray());
    compteur++;

}*/

    }
}
