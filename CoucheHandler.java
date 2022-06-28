import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public interface CoucheHandler {

    public void setNextLayer(CoucheHandler couche);
    public void run(byte[] tableauByte) throws IOException, TransmissionErrorException;


}
