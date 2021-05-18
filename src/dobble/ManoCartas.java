package dobble;

import java.io.Serializable;
import java.util.Stack;

public class ManoCartas implements Serializable{

    private Stack<Carta> cartas;
    private int cartasPendientes;
    private Carta ultimaTirada;
    private Carta primeraCarta;
    private int totalCartas;

    public ManoCartas(Stack<Carta> cartas) {
        this.cartas = cartas;
        totalCartas = cartas.size();
        cartasPendientes = cartas.size();
        primeraCarta = cartas.pop();
        ultimaTirada = null;
    }
    
    public Carta getPrimera() {
        return primeraCarta;
    }
    
    public void tirarCarta() {
        ultimaTirada = primeraCarta;
        primeraCarta = cartas.pop();
        cartasPendientes = cartasPendientes - 1;
    }
    
    public void cancelarTirada() {
        if(ultimaTirada != null) {
            cartas.push(primeraCarta);
            primeraCarta = ultimaTirada;
            cartasPendientes =  cartasPendientes + 1;
            ultimaTirada = null;
        }
    }

    public Stack<Carta> getCartas() {
        return cartas;
    }

    public int getCartasPendientes() {
        return cartasPendientes;
    }

    public Carta getUltimaTirada() {
        return ultimaTirada;
    }

    public int getTotalCartas() {
        return totalCartas;
    }
        
    
    
}
