package dobble;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    
    private GameDisplay gameDisplay;
    
    public MainFrame(ManoCartas[] jugadores, Carta cartaExtra, Cliente cliente) {
        System.out.println("holaa");
        setTitle("Dobble");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        initializeDisplay(jugadores, cartaExtra, cliente);
        System.out.println("adios");
    }

    private void initializeDisplay(ManoCartas[] jugadores, Carta cartaExtra, Cliente cliente) {
        GameDisplay display = new GameDisplay(cartaExtra, jugadores, cliente);
        gameDisplay = display;
        getContentPane().add((JPanel)display, BorderLayout.CENTER);
    }
    
    public void execute() {
        setVisible(true);
    }
    
    public GameDisplay getGameDisplay() {
        return gameDisplay;
    }
    
}
