import java.nio.charset.StandardCharsets;

public class Serveur_Application implements CoucheHandler {
    private CoucheHandler couche;
    @Override
    public void setNextLayer(CoucheHandler couche) {
        this.couche=couche;
    }
    @Override
    public void run(byte[] byt){
        String str = new String(byt, StandardCharsets.UTF_8);
        System.out.println(str);
    }
}
