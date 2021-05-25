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
