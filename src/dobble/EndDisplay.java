package dobble;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class EndDisplay extends JFrame{
    
    private JTextArea area;
    
    public EndDisplay(boolean win) {
        if(win) {
            area.setText("Has gando!!!!");
        } else {
            area.setText("Has perdido!!!!");
        }
        getContentPane().add(area);
        setVisible(true);
    }
}
