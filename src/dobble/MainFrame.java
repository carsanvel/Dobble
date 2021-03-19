package dobble;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    
    private GameDisplay gameDisplay;
    
    public MainFrame() {
        setTitle("Dobble");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        initializeDisplay();
    }

    private void initializeDisplay() {
        GameDisplay display = new GameDisplay(new Carta("carta1"), null);
        gameDisplay = display;
        getContentPane().add((JPanel)display, BorderLayout.CENTER);
    }
    
    public void execute() {
        setVisible(true);
    }
    
    
}
