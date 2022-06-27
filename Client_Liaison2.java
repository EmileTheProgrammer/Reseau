import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class Client_Liaison2 {

    byte[] envoi;
public Client_Liaison2(){


}
    public void run(byte [] buf) throws IOException {
        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();


        // send request
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 3123);
        socket.send(packet);
        // get response
        //packet = new DatagramPacket(buf, buf.length);
        //socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0,packet.getLength());
        System.out.println(received);
       // socket.receive(packet);
    }


    }

