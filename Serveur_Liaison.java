import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Serveur_Liaison {
    protected DatagramSocket socket = null;

    public Serveur_Liaison() throws SocketException {
        socket = new DatagramSocket(3123);

    }
    public void run() throws IOException {

        byte [] buf = new byte[200];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        String test = "Message recu";
        String test2 = "Jai un ptit penis";
        buf=test.getBytes();


            packet = new DatagramPacket(buf,buf.length,address,port);
            socket.send(packet);



        socket.close();
    }
}