import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class Client_Application implements CoucheHandler {
    Path path;
    CoucheHandler couche;


    public Client_Application(Path path){
        this.path=path;
    }
    @Override
   public void  run(byte[] tableauByte) {

try{
               byte [] FichierByte = Files.readAllBytes(path);
               File F = new File(String.valueOf(path));
               byte [] FichierNom = F.getName().getBytes();
               //Client_Transport Transport = new Client_Transport(FichierByte);
               //Transport.run(FichierByte);
               couche.run(FichierByte);
} catch (IOException e) {
        throw new RuntimeException(e);
    }
   }


    @Override
    public void setNextLayer(CoucheHandler couche) {
this.couche=couche;
    }
}
