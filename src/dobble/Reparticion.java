package dobble;

public class Reparticion {

    private Carta[][] cartas;
    private Carta cartaExtra;

    public Reparticion(Carta[][] cartas, Carta cartaExtra) {
        this.cartas = cartas;
        this.cartaExtra = cartaExtra;
    }

    public Carta[][] getCartas() {
        return cartas;
    }

    public Carta getCartaExtra() {
        return cartaExtra;
    }
    
}
