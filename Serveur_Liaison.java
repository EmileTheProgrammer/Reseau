import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static java.lang.Integer.parseInt;

public class Serveur_Liaison implements CoucheHandler{
    protected DatagramSocket socket = null;
    private CoucheHandler couche;
    Serveur_Transport T = new Serveur_Transport();
    private byte[] packetByte;
    private byte[] checksum;
    private int compteurErreur = 0;
    private Serveur_Transport T = new Serveur_Transport();
    private Logger log = Logger.getLogger("Logger");
    private FileHandler fh;
    private SimpleFormatter sf;
    public Serveur_Liaison() throws IOException {

    public Serveur_Liaison() throws SocketException {
        socket = new DatagramSocket(30000);
        fh = new FileHandler("liaisonDeDonnes.log", true);
        sf = new SimpleFormatter();
        fh.setFormatter(sf);
        log.addHandler(fh);
        log.setUseParentHandlers(false);
    }
    @Override
    public void run(byte [] byt) {
        try {  byte [] nombreB;
        int nombre;
        int prevSeqNb = 0;
        int seqNb;
        byte [] buf = new byte[223];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        socket.receive(packet);
        nombreB = Arrays.copyOfRange(buf, 8, 13);
        seqNb = nombreB[3];

        if(missingPacket(prevSeqNb, seqNb)){

        }


            socket.receive(packet);

        nombreB = Arrays.copyOfRange(buf,18,23);
        nombre = nombreB[3];
        packetByte = packet.getData();
        packetByte = removeChecksum(packetByte);
        T.run(packetByte);
        couche.run(packetByte);
        //System.out.println(received);
        //String received = new String(packet.getData(), 0,packet.getLength());

       for (int i = 1; i<nombre+1;i++){
           socket.receive(packet);
           nombreB = Arrays.copyOfRange(buf, 8, 13);
           seqNb = nombreB[3];
           if(missingPacket(prevSeqNb, seqNb)){

           }
           packetByte = packet.getData();
           packetByte = removeChecksum(packetByte);
           couche.run(packetByte);
       }


        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        T= (Serveur_Transport) couche;
        T.fin();
        } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }

    /*public byte[] getPacket(byte[] packet){

    }*/

    public byte[] removeChecksum(byte[] b){
        checksum = new byte[8];
        byte[] temp = new byte[b.length - 8];
        long checksumLong;
        System.arraycopy(b, 0, checksum, 0, 8);
        System.arraycopy(b, 8, temp, 0, b.length - 8);
        checksumLong = new BigInteger(checksum).longValue();
        Checksum crc = new CRC32();
        crc.update(temp);
        if(checksumLong % crc.getValue() != 0) {
            compteurErreur += 1;
            writeToFile("Erreur de transmission #" + compteurErreur);
            System.out.println("Erreur de transmission");
        }
        else{
            writeToFile("Transmission réussie");
        }
        return temp;
    }

    public boolean missingPacket(int prevNb, int nb){
        if(nb - prevNb > 1){
            log.info("Paquet manquant!");
            return true;
        }
        else{
            prevNb = nb;
            return false;
        }
    }
    private void writeToFile(String message){
        log.info(message);
    }

    @Override
    public void setNextLayer(CoucheHandler couche) {
        this.couche=couche;
    }
}
