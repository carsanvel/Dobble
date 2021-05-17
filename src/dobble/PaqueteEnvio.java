package dobble;

import java.io.Serializable;

public class PaqueteEnvio implements Serializable{

    private String ip;
    private int port;
    private int mensaje;
    
    public PaqueteEnvio(String ip, int port, int mensaje) {
        this.ip = ip;
        this.port = port;
        this.mensaje = mensaje;
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
    
}
