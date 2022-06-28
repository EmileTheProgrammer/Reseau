import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Client_Transport implements CoucheHandler{
    byte [] FichierByte;
    private CoucheHandler couche;
   // Client_Liaison Liaison = new Client_Liaison();
    byte [] FichierNom;
    byte [] temp;
    List<byte[]> paquets = new ArrayList<>();
    int compteur = 1;
    final int taille = 200;
    public Client_Transport(byte [] NomByte, byte [] FichierByte, int errorCode) throws IOException {
        this.FichierByte=FichierByte;
        this.FichierNom = NomByte;
    }
    @Override
    public void run(byte[] FichierByte) {
        int dernierPaquet = FichierByte.length/200;
//        byte[] nom = ByteBuffer.allocate(15).put(FichierNom).array();
       byte [] compteurB = ByteBuffer.allocate(5).putInt(compteur).array();
       byte [] premier =ByteBuffer.allocate(5).putInt(0).array();
       byte [] dernier = ByteBuffer.allocate(5).putInt(dernierPaquet).array();
        try {
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
        //String str = new String(out.toByteArray(), StandardCharsets.UTF_8);
      //  System.out.println("akkkkkkkkkkkk"+str);
        couche.run(out.toByteArray());

        for(int i=200;i<FichierByte.length;i=i+taille){

            ByteArrayOutputStream head = new ByteArrayOutputStream();
            head.write(compteurB);
            head.write(premier);
            head.write(dernier);
            head.close();

            temp = Arrays.copyOfRange(FichierByte,i,taille+i);
            head.write(temp);

 //  String str = new String(temp, StandardCharsets.UTF_8);
  // System.out.println("\n\n\n\n de "+ i +"aaaaaaaaaa"+(taille+i)+str);
   couche.run(head.toByteArray());
    compteur++;

}
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public void setNextLayer(CoucheHandler couche) {
        this.couche=couche;
    }
}
