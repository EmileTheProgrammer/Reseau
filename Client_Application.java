import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class Client_Application extends Handler{
    Path path;
    private int errorCode;

    public Client_Application(Path path, int errorCode){
        this.path=path;
        this.errorCode = errorCode;
    }
   public void  TransfererFichier() throws IOException {


               byte [] FichierByte = Files.readAllBytes(path);
               File F = new File(String.valueOf(path));
               byte [] FichierNom = F.getName().getBytes();
               Client_Transport Transport = new Client_Transport(FichierNom, FichierByte, errorCode);

               Transport.run();
    }
}
