import java.nio.charset.StandardCharsets;

public class Application_Serveur {
    public void run(byte[] byt){
        String str = new String(byt, StandardCharsets.UTF_8);
        System.out.println(str);
    }
}
