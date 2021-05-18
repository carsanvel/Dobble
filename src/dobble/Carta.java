package dobble;

import java.io.Serializable;

public class Carta implements Serializable{

    private String directorio;

    public Carta(String directorio) {
        this.directorio = directorio;
    }

    public String getDirectorio() {
        return directorio;
    }

    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }
    
    
}
