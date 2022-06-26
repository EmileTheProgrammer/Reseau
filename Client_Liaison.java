import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Client_Liaison {
    private List<byte[]> paquets;
    public Checksum crc;
    int nbPaquets;
    private Logger log = Logger.getLogger("Logger");
    private FileHandler fh;
    private SimpleFormatter sf;
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
            for(int j = 0; j < paquets.get(i).length; j++){
                crc.update(paquets.get(i)[j]);
            }
        }
        writeToFile("CRC updated!");
    }

    private void writeToFile(String message){
        log.info(message);
    }
}
