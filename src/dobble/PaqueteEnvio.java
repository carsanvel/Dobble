package dobble;

import java.io.Serializable;

public class PaqueteEnvio implements Serializable{

    private String ip;
    private int port;
    private int mensaje;
    private ManoCartas[] manos;
    private Carta cartaExtra;
    
    public PaqueteEnvio(String ip, int port, int mensaje, ManoCartas[] manos, Carta cartaExtra) {
        this.ip = ip;
        this.port = port;
        this.mensaje = mensaje;
        this.manos = manos;
        this.cartaExtra = cartaExtra;
    }
    
    public String getIp() {
        return ip;
    }
    
    public int getPort() {
        return port;
    }
    
    public int getMensaje() {
        return mensaje;
    }
    
   public ManoCartas[] getManos() {
       return manos;
   }
   
   public Carta getCartaExtra() {
       return cartaExtra;
   }
    
}
