import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Client_Liaison {
    private byte[] paquets;
    private Checksum crc;
    private int nbPaquets;
    private Logger log = Logger.getLogger("Logger");
    private FileHandler fh;
    private SimpleFormatter sf;
    private byte[] checksum;
    public Client_Liaison() throws IOException {
        crc = new CRC32();

        //nbPaquets = this.paquets.length;
        fh = new FileHandler("liaisonDeDonnes.log", true);
        sf = new SimpleFormatter();
        fh.setFormatter(sf);
        log.addHandler(fh);
        log.setUseParentHandlers(false);
    }

    public void run(byte[] paquet){
        this.paquets = paquet;
        checksum = ByteBuffer.allocate(8).putLong(buildChecksum(paquet).getValue()).array();
        addChecksum(checksum);

    }

    public Checksum buildChecksum(byte[] b){
        crc.reset();
        crc.update(b);

        return crc;
    }

    public void addChecksum(byte[] checksum){
        byte[] temp = new byte[checksum.length + paquets.length];
        System.arraycopy(checksum, 0, temp, 0, checksum.length);
        System.arraycopy(paquets, 0, temp, checksum.length, paquets.length);
        paquets = temp;
        writeToFile("Checksum ajouté!");
    }

    private void writeToFile(String message){
        log.info(message);
    }
}
