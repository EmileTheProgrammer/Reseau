import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static java.lang.Integer.parseInt;

public class Serveur_Liaison {
    protected DatagramSocket socket = null;
    private byte[] packetByte;
    private byte[] checksum;
    Serveur_Transport T = new Serveur_Transport();
    public Serveur_Liaison() throws SocketException {
        socket = new DatagramSocket(30000);
    }

    public void run() throws IOException, InterruptedException {
        byte [] nombreB;
        int nombre;
        byte [] buf = new byte[223];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        socket.receive(packet);
        nombreB = Arrays.copyOfRange(buf,18,23);
        nombre = nombreB[3];
        packetByte = packet.getData();
        packetByte = removeChecksum(packetByte);
        T.run(packetByte);
        //System.out.println(received);
        //String received = new String(packet.getData(), 0,packet.getLength());

       for (int i = 1; i<nombre+1;i++){
           socket.receive(packet);
           packetByte = packet.getData();
           packetByte = removeChecksum(packetByte);
           T.run(packetByte);
       }


        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        T.fin();
    }

    public byte[] removeChecksum(byte[] b){
        checksum = new byte[8];
        byte[] temp = new byte[b.length - 8];
        long checksumLong;
        boolean error = false;
        System.arraycopy(b, 0, checksum, 0, 8);
        System.arraycopy(b, 8, temp, 0, b.length - 8);
        checksumLong = new BigInteger(checksum).longValue();
        Checksum crc = new CRC32();
        crc.update(temp);
        if(checksumLong % crc.getValue() != 0) {
            error = true;
        }
        return temp;
    }
}
