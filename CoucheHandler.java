import java.io.IOException;

public interface CoucheHandler {

    // protected CoucheHandler nextLayer;
  //  protected CoucheHandler previousLayer;
    public void setNextLayer(CoucheHandler couche);
    public void run(byte[] tableauByte);

}
