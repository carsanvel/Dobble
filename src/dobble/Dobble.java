package dobble;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Dobble {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("Cartas\\carta1.png");
        Carta[][] cartas = RepartidorDeCartas.reparte(4);
        MainFrame frame = new MainFrame();
        frame.execute();
    }
    
    public static void print(Carta[][] cartas) {
        for (int i = 0; i < cartas.length; i++) {
            System.out.println("Jugador" + i);
            for (int j = 0; j < cartas[i].length; j++) {
                System.out.println(cartas[i][j].getDirectorio());
            }
        }
    }
    
}
