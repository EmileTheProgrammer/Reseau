import java.io.IOException;
import java.net.*;

public class Physique implements CoucheHandler {
    private CoucheHandler couche;
    public Physique(){

    }

    @Override
    public void setNextLayer(CoucheHandler couche) {
        this.couche =couche;
    }
@Override
    public void run(byte[] paquet) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(paquet, paquet.length, address, 30000);
        socket.send(packet);
    }
}
