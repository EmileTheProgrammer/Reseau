import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Client_Liaison {
    private List<byte[]> paquets;
    private Checksum crc;
    private int nbPaquets;
    private Logger log = Logger.getLogger("Logger");
    private FileHandler fh;
    private SimpleFormatter sf;
    private long checksum;
    public Client_Liaison(List<byte[]> paquets) throws IOException {
        crc = new CRC32();
        this.paquets = paquets;
        nbPaquets = this.paquets.size();
        fh = new FileHandler("liaisonDeDonnes.log", true);
        sf = new SimpleFormatter();
        fh.setFormatter(sf);
        log.addHandler(fh);
        log.setUseParentHandlers(false);
    }

    public void run(){
        for(int i = 0; i < nbPaquets; i++){
            checksum = buildChecksum(paquets.get(i));
            addChecksum(paquets.get(i), ByteBuffer.allocate(8).putLong(checksum).array());
        }

    }

    public long buildChecksum(byte[] b){
        crc.reset();
        crc.update(b);
        writeToFile("CRC updated!");
        return crc.getValue();
    }

    public void addChecksum(byte[] paquet, byte[] checkSum){

    }

    private void writeToFile(String message){
        log.info(message);
    }
}
