import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Serveur_Liaison {
    protected DatagramSocket socket = null;
    private byte[] packetByte;
    private byte[] checksum;
    public Serveur_Liaison() throws SocketException {
        socket = new DatagramSocket(30000);

    }
    public void run() throws IOException, InterruptedException {
        byte [] buf = new byte[200];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        packetByte = packet.getData();
        //removeChecksum(packetByte);
        String received = new String(packet.getData(), 0,packet.getLength());
        System.out.println(received);
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        String test = "Message recu";

        //buf=test.getBytes();

        //packet = new DatagramPacket(buf,buf.length,address,port);
        //socket.send(packet);

        //socket.close();
    }

    public boolean removeChecksum(byte[] b){
        checksum = new byte[9];
        byte[] temp = new byte[b.length - 9];
        System.arraycopy(b, 0, checksum, 0, 9);
        System.arraycopy(b, 9, temp, 0, b.length - 9);
        System.out.println(checksum);
        Checksum crc = new CRC32();
        crc.update(temp);
        byte[] test = ByteBuffer.allocate(8).putLong(crc.getValue()).array();
        System.out.println(test);
        String received = new String(temp, 0, temp.length);
        System.out.println(received);
        return true;
    }
}
