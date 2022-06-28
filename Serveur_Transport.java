import jdk.swing.interop.SwingInterOpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Serveur_Transport implements CoucheHandler{
    public CoucheHandler couche;
    private ByteArrayOutputStream s = new ByteArrayOutputStream();
    private byte[] fichierComplet;
    private int seq;
    private int prevseq = 0;
    private int errorCount = 0;

    private Log log;

    @Override
    public void setNextLayer(CoucheHandler couche) {

        this.couche=couche;

    }
@Override
    public void run(byte[]paquet) throws IOException, TransmissionErrorException {
        byte [] temp;
        seq = Integer.parseInt(String.valueOf(paquet[3]));
        if(seq != prevseq + 1){
            errorCount++;
        }
        if(errorCount == 3){
            throw new TransmissionErrorException("Erreur");
        }
        temp=removeHeader(paquet);
        addTableau(temp);
        prevseq = seq;
    }
    public void fin() throws IOException, TransmissionErrorException {
        fichierComplet=s.toByteArray();
        couche.run(fichierComplet);
    }
     public byte[] removeHeader(byte [] byt){
        byte[] temp;
        temp = Arrays.copyOfRange(byt,15,215);
        return temp;
    }
    public void addTableau(byte [] byt) throws IOException {
        s.write(byt);
    }
}
