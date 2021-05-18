package dobble;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

public class Dobble {

    public static void main(String[] args) throws FileNotFoundException {
        
        Servidor servidor = new Servidor();
        Cliente cliente = new Cliente(servidor);
        servidor.setCliente(cliente);
        cliente.execute();
        /*
        Reparticion reparticion = RepartidorDeCartas.reparte(2);
        Carta[][] cartas = reparticion.getCartas();
        Stack<Carta> pila1 = new Stack<>();
        Stack<Carta> pila2 = new Stack<>();
        rellenaPila(cartas[0], pila1);
        rellenaPila(cartas[1], pila2);
        ManoCartas[] manos = new ManoCartas[2];
        manos[0] = new ManoCartas(pila1);
        manos[1] = new ManoCartas(pila2);
                
        MainFrame frame = new MainFrame(manos, reparticion.getCartaExtra(), cliente);
        frame.execute();
        
        cliente.execute();
                */
        //Servidor servidor = new Servidor(frame.getGameDisplay());
                
    }
    
    public static void print(Carta[][] cartas) {
        for (int i = 0; i < cartas.length; i++) {
            System.out.println("Jugador" + i);
            for (int j = 0; j < cartas[i].length; j++) {
                System.out.println(cartas[i][j].getDirectorio());
            }
        }
    }
    
    public static void rellenaPila(Carta[] cartas, Stack<Carta> pila) {
        for (int i = 0; i < cartas.length; i++) {
            pila.push(cartas[i]);
        }
    }
}
