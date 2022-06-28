import jdk.swing.interop.SwingInterOpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Serveur_Transport implements CoucheHandler{
    public CoucheHandler couche;
ByteArrayOutputStream s = new ByteArrayOutputStream();
byte[] fichierComplet;

    @Override
    public void setNextLayer(CoucheHandler couche) {

        this.couche=couche;

    }
@Override
    public void run(byte[]paquet){
        try {   byte [] temp;
       temp=removeHeader(paquet);

            addTableau(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void fin(){

        fichierComplet=s.toByteArray();
        couche.run(fichierComplet);
    }
     public byte[] removeHeader(byte [] byt){
        byte[] temp;
        temp = Arrays.copyOfRange(byt,15,200);
        return temp;
    }
    public void addTableau(byte [] byt) throws IOException {
        s.write(byt);
    }
}
