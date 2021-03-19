package dobble;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameDisplay extends JPanel {
    
    private Stack<Carta> monton;
    private ManoCartas[] jugadores;
    private Carta carta;

    public GameDisplay(Carta prieraCarta, ManoCartas[] jugadores) {
        monton = new Stack<Carta>();
        monton.push(prieraCarta);
        carta = prieraCarta;
        this.jugadores = jugadores;
        display(carta);
    }
    
    public void display (Carta carta) {
        this.carta = carta;
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(imageOf(carta.getDirectorio()), 0, 0, getWidth(), getHeight(), null);
    }
    
    private BufferedImage imageOf(String carta) {
        ByteArrayInputStream is = new ByteArrayInputStream(ImageLoader.getImage(carta));
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    
}
