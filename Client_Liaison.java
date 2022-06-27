import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Client_Liaison extends Handler{
    private byte[] paquet;
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

    public void run(byte[] paquet) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        this.paquet = paquet;
        checksum = ByteBuffer.allocate(8).putLong(buildChecksum(paquet).getValue()).array();
        //System.out.println(paquet);
        //paquet = addChecksum(checksum);
        //System.out.println(paquet);
        //System.out.println("-----");
        //String received = new String(this.paquet, 0, this.paquet.length);
        //System.out.println(received);
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(paquet, paquet.length, address, 30000);
        socket.send(packet);

    }

    public Checksum buildChecksum(byte[] b){
        crc.reset();
        crc.update(b);

        return crc;
    }

    public byte[] addChecksum(byte[] checksum){
        byte[] temp = new byte[checksum.length + paquet.length];
        System.arraycopy(checksum, 0, temp, 0, checksum.length);
        System.arraycopy(paquet, 0, temp, checksum.length, paquet.length);
        paquet = temp;
        writeToFile("Checksum ajouté!");
        return paquet;
    }

    private void writeToFile(String message){
        log.info(message);
    }
}
