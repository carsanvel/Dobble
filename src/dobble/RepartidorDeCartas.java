package dobble;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RepartidorDeCartas {

    public static Carta[][] reparte(int jugadores) {
        if (jugadores > 6) {
            return null;
        }
        int numCartas = (57-1)/jugadores;
        System.out.println(numCartas);
        Carta[][] cartas = new Carta[jugadores][numCartas];
        int[] reparticion = distribuye(numCartas, jugadores);
        Map<Integer, Integer> contador = new HashMap<>();
        for (int i = 0; i < jugadores; i++) {
            contador.put(i, 0);
        }
        for (int i = 0; i < reparticion.length; i++) {
            int jugador = reparticion[i];
            cartas[jugador][contador.get(jugador)] = new Carta("Carta" + i);
            contador.put(jugador, contador.get(jugador) + 1);
        }
        return cartas;
    }
    
    private static int[] distribuye(int numCartas, int jugadores) {
        int[] reparticion = new int[numCartas*jugadores];
        Map<Integer, Integer> contador = new HashMap<>();
        for (int i = 0; i < jugadores; i++) {
            contador.put(i, 0);
        }
        Random random = new Random();
        for (int i = 0; i < reparticion.length; i++) {
            while(true) {
                int jugador = random.nextInt(jugadores);
                if(contador.get(jugador) < numCartas) {
                    reparticion[i] = jugador;
                    contador.put(jugador, contador.get(jugador)+1);
                    break;
                }
            }
        }
        return reparticion;
    }
}
