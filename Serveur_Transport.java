import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Serveur_Transport {
ByteArrayOutputStream s = new ByteArrayOutputStream();
byte[] fichierComplet;
    public Serveur_Transport(){

    }
    public void run(byte[]paquet) throws IOException {
        byte [] temp;
       temp=removeHeader(paquet);
       addTableau(temp);
    }
    public void fin(){
        fichierComplet=s.toByteArray();
        Application_Serveur App = new Application_Serveur();
        App.run(fichierComplet);
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
