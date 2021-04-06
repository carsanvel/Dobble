package dobble;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
    private int cartaX = (int)(getWidth()/3.2);
    private int cartaY = getHeight()/16 * 11;
    boolean pulsado = false;

    public GameDisplay(Carta prieraCarta, ManoCartas[] jugadores) {
        monton = new Stack<Carta>();
        monton.push(prieraCarta);
        carta = prieraCarta;
        this.jugadores = jugadores;
        CompleteMouseListener mouseListener  = new CompleteMouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        cartaX = (int)(getWidth()/3.2);
        cartaY = getHeight()/16 * 11;
        display(carta);
        repaint();
    }
    
    public void display (Carta carta) {
        this.carta = carta;
        repaint();
    }
    
    public boolean isInRange(int x, int y) {
        int length = (int)(getWidth()/3.5);
        if(x > (int)(getWidth()/3.2) + length || x < (int)(getWidth()/3.2)) {
            return false;
        }
        int centerY = (getHeight()/16 * 11) + (getHeight()/7);
        int centerX = (int)(getWidth()/3.2) + (int) (getWidth()/7);
        int radius = (int) (getHeight() / 7);
        int xDistance =  Math.abs(x - centerX);
        int distance = (int) Math.sqrt(Math.pow(radius, 2) - Math.pow(xDistance, 2));
        int yLimit1 = (int) (centerY + distance);
        int yLimit2 = (int) (centerY - distance);
        if(y < yLimit1 && y > yLimit2) {
            return true;
        }
        return false;
    }
    
    public boolean isInCenter(int x, int y) {
        int length = (int)(getWidth()/2.5);
        if(x > (int)(getWidth()/4) + length || x < (int)(getWidth()/4)) {
            return false;
        }
        int centerY = (int) (getHeight()/3.5) + (getHeight()/5);
        int centerX = getWidth()/4 + (getWidth()/5);
        int radius = (int) (getHeight() / 5);
        int xDistance =  Math.abs(x - centerX);
        int distance = (int) Math.sqrt(Math.pow(radius, 2) - Math.pow(xDistance, 2));
        int yLimit1 = (int) (centerY + distance);
        int yLimit2 = (int) (centerY - distance);
        if (y < yLimit1 && y > yLimit2) {
            return true;
        }
        return false;
    }
    
    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(imageOf(monton.peek().getDirectorio()), getWidth()/4, (int) (getHeight()/3.5), (int)(getWidth()/2.5), (int)(getHeight()/2.5), null);
        g.drawImage(imageOf(jugadores[1].getPrimera().getDirectorio()), (int)(getWidth()/3.2), 0, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
        if (pulsado) {
            g.drawImage(imageOf(jugadores[0].getPrimera().getDirectorio()), cartaX, cartaY, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
        } else {
            g.drawImage(imageOf(jugadores[0].getPrimera().getDirectorio()), (int)(getWidth()/3.2), getHeight()/16 * 11, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
        }
        Graphics2D g2 = (Graphics2D)g;
        g.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(getWidth()/8, 0, getWidth()/8, getHeight());
        g2.drawLine(getWidth()/8 * 7, 0, getWidth()/8 * 7, getHeight());
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
    
    private class CompleteMouseListener implements MouseListener, MouseMotionListener {

        private boolean pressed = false;
        private int mouseXPosition;
        private int mouseYPosition;
        int pressedPointer;
        
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (isInRange(e.getX(), e.getY())) {
                pressed = true;
                pulsado = true;
                mouseXPosition = e.getX();
                mouseYPosition = e.getY();
                cartaX = (int)(getWidth()/3.2);
                cartaY = getHeight()/16 * 11;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(isInCenter(e.getX(), e.getY())) {
                monton.push(jugadores[0].getPrimera());
                jugadores[0].tirarCarta();
            }
            pressed = false;
            cartaX = (int)(getWidth()/3.2);
            cartaY = getHeight()/16 * 11;
            repaint();
            pulsado = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (pressed) {
                cartaX = cartaX + (e.getX() - mouseXPosition);
                cartaY = cartaY + (e.getY() - mouseYPosition);
                mouseXPosition = e.getX();
                mouseYPosition = e.getY();
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }
        
    }
    
}
