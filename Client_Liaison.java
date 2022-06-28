import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Client_Liaison implements CoucheHandler{
    private byte[] paquet;
    private CoucheHandler couche;
    private Checksum crc;
    private int nbPaquets;
    private Log log;
    private byte[] checksum;
    private int errorCode;
    Physique physique;
    public Client_Liaison(int errorCode) throws IOException {
        physique = new Physique();
        crc = new CRC32();
        log = new Log();
        this.errorCode = errorCode;
    }

    @Override
    public void setNextLayer(CoucheHandler couche) {
        this.couche=couche;
    }

    @Override
    public void run(byte[] paquet) {
    try {
        this.paquet = paquet;

        nbPaquets = (int) paquet[13];

        checksum = ByteBuffer.allocate(8).putLong(buildChecksum(paquet).getValue()).array();
        this.paquet = addChecksum(checksum);
        if(errorCode != 0){
            this.paquet = addErrors(this.paquet);
            errorCode = 0;
        }
      //  String s = new String(paquet, StandardCharsets.UTF_8);
       // System.out.println(s);
        couche.run(this.paquet);

    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    }

    public Checksum buildChecksum(byte[] b){
        crc.reset();
        crc.update(b);
        //System.out.println(crc.getValue());
        return crc;
    }

    public byte[] addChecksum(byte[] checksum){
        byte[] temp = new byte[checksum.length + paquet.length];
        System.arraycopy(checksum, 0, temp, 0, checksum.length);
        System.arraycopy(paquet, 0, temp, checksum.length, paquet.length);
        paquet = temp;
        log.writeLog("Checksum ajouté!");
        return paquet;
    }

    public byte[] addErrors(byte[] paquet){
        if(errorCode == 1)
        {
            paquet[150] = (byte) 45;
        }
        return paquet;
    }

}
