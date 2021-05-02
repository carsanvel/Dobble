package dobble;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RepartidorDeCartas {

    public static Reparticion reparte(int jugadores) {
        if (jugadores > 6) {
            return null;
        }
        int numCartas = (57-1)/jugadores;
        Carta[][] cartas = new Carta[jugadores][numCartas];
        int cartaExtra = new Random().nextInt(56);
        int[] reparticion = distribuye(numCartas, jugadores, cartaExtra);
        Map<Integer, Integer> contador = new HashMap<>();
        for (int i = 0; i < jugadores; i++) {
            contador.put(i, 0);
        }
        int count = 0;
        for (int i = cartaExtra + 1; i < reparticion.length; i++) {
            int jugador = reparticion[i];
            int carta = i + 1;
            cartas[jugador][contador.get(jugador)] = new Carta("Carta" + carta);
            contador.put(jugador, contador.get(jugador) + 1);
            count = count + 1;
        }
        int aRepartir = numCartas*jugadores;
        for (int i = 0; i < aRepartir - count; i++) {
            int jugador = reparticion[i];
            int carta = i + 1;
            cartas[jugador][contador.get(jugador)] = new Carta("Carta" + carta);
            contador.put(jugador, contador.get(jugador) + 1);
        }
        cartaExtra =  cartaExtra + 1;
        return new Reparticion(cartas, new Carta("Carta" + cartaExtra));
    }
    
    private static int[] distribuye(int numCartas, int jugadores, int cartaExtra) {
        int aRepartir = numCartas*jugadores;
        int[] reparticion = new int[57];
        Map<Integer, Integer> contador = new HashMap<>();
        for (int i = 0; i < jugadores; i++) {
            contador.put(i, 0);
        }
        Random random = new Random();
        int count = 0;
        for (int i = cartaExtra + 1; i < 57; i++) {
            while(true) {
                int jugador = random.nextInt(jugadores);
                if(contador.get(jugador) < numCartas) {
                    reparticion[i] = jugador;
                    contador.put(jugador, contador.get(jugador)+1);
                    count = count + 1;
                    break;
                }
            }
        }
        for (int i = 0; i < aRepartir - count; i++) {
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
