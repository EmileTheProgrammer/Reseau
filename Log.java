import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private Logger log = Logger.getLogger("Logger");
    private FileHandler fh;
    private SimpleFormatter sf;

    public Log() throws IOException {
        fh = new FileHandler("liaisonDeDonnes.log", true);
        sf = new SimpleFormatter();
        fh.setFormatter(sf);
        log.addHandler(fh);
        log.setUseParentHandlers(false);
    }

    public void writeLog(String message){
        log.info(message);
    }
}
