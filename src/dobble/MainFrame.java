package dobble;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    
    private GameDisplay gameDisplay;
    
    public MainFrame(ManoCartas[] jugadores, Carta cartaExtra) {
        setTitle("Dobble");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        initializeDisplay(jugadores, cartaExtra);
    }

    private void initializeDisplay(ManoCartas[] jugadores, Carta cartaExtra) {
        GameDisplay display = new GameDisplay(cartaExtra, jugadores);
        gameDisplay = display;
        getContentPane().add((JPanel)display, BorderLayout.CENTER);
    }
    
    public void execute() {
        setVisible(true);
    }
    
    
}
