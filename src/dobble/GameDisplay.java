package dobble;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
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
    private boolean pulsado = false;
    private Carta icono;
    private Carta atras;
    private boolean primeraPintada = true;
    private Cliente cliente;
    private boolean[] inicio;

    public GameDisplay(Carta prieraCarta, ManoCartas[] jugadores, Cliente cliente) {
        this.cliente = cliente;
        inicio = new boolean[2];
        inicio[0] = false;
        inicio[1] = false;
        icono = new Carta("cartaPortada");
        atras = new Carta("cartaAtras");
        monton = new Stack<>();
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
    
    public Cliente getCliente() {
        return cliente;
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
        int yLimit1 = (int) (centerY + distance - (int)(getHeight()/4));
        int yLimit2 = (int) (centerY - distance);
        if (y < yLimit1 && y > yLimit2) {
            return true;
        }
        return false;
    }
    
    public boolean isInButton(int x, int y) {
        if (x < getWidth()/8 *2 && x > getWidth()/8 && y > getHeight()/8 *7) {
            return true;
        }
        return false;
    }
    
    public boolean isInCancel(int x, int y) {
        if (x < getWidth()/8 *7 && x > getWidth()/8 * 6 && y > getHeight()/8 *7) {
            return true;
        }
        return false;
    }
    
    @Override
    public void paint(Graphics g) {
        if (primeraPintada) {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(getWidth()/8, 0, getWidth()/8 * 7 - (getWidth()/8), getHeight());
            g.setColor(new Color(245,80,80));
            g.fillRect(getWidth()/8, getHeight()/8 *7, getWidth()/8, getHeight());
            
            
            g.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(getWidth()/8, getHeight()/8 *7, getWidth()/8 *2, getHeight()/8 *7);
            g2.drawLine(getWidth()/8 *2, getHeight()/8 *7, getWidth()/8 *2, getHeight());
            Font font = new Font(null, Font.PLAIN, 20);
            g2.setFont(font);
            g2.drawString("Empezar",getWidth()/8*6/5, getHeight()/16 *15);
            
            
            
            g.drawImage(imageOf(icono.getDirectorio()), 0, 0, getWidth()/9, getWidth()/9, null);
            g.drawImage(imageOf(icono.getDirectorio()), 0, getHeight() - getWidth()/9, getWidth()/9, getWidth()/9, null);
            g.drawImage(imageOf(icono.getDirectorio()), getWidth()/8 * 7, 0, getWidth()/9, getWidth()/9, null);
            g.drawImage(imageOf(icono.getDirectorio()), getWidth()/8 * 7, getHeight() - getWidth()/9, getWidth()/9, getWidth()/9, null);
            g.drawImage(imageOf(atras.getDirectorio()), getWidth()/4, (int) (getHeight()/3.5), (int)(getWidth()/2.5), (int)(getHeight()/2.5), null);
            g.drawImage(imageOf(jugadores[1].getPrimera().getDirectorio()), (int)(getWidth()/3.2), 0, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
            if (pulsado) {
                g.drawImage(imageOf(jugadores[0].getPrimera().getDirectorio()), cartaX, cartaY, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
            } else {
                g.drawImage(imageOf(jugadores[0].getPrimera().getDirectorio()), (int)(getWidth()/3.2), getHeight()/16 * 11, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
            }
            
            g.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(getWidth()/8, 0, getWidth()/8, getHeight());
            g2.drawLine(getWidth()/8 * 7, 0, getWidth()/8 * 7, getHeight());
            font = new Font(null, Font.PLAIN, 40);
            g2.setFont(font);
            g2.translate((float) getWidth()/8*7 ,(float)getHeight()/8*7);
            g2.rotate(Math.toRadians(270));
            //g2.drawString("Dobble Carlos & Annia",55,80);
            g2.drawString("Dobble Carlos & Annia",(int)(getWidth()/17.89),(int)(661/8.26));
            g2.drawString("Dobble Carlos & Annia",55,-790);
            g2.translate(-(float)getWidth()/8*7,-(float)(float)getHeight()/8*7);
        } else {
            g.clearRect(getWidth()/8, 0, getWidth()/8 * 7 - (getWidth()/8), getHeight());
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(getWidth()/8, 0, getWidth()/8 * 7 - (getWidth()/8), getHeight());
            g.drawImage(imageOf(monton.peek().getDirectorio()), getWidth()/4, (int) (getHeight()/3.5), (int)(getWidth()/2.5), (int)(getHeight()/2.5), null);
            g.drawImage(imageOf(jugadores[1].getPrimera().getDirectorio()), (int)(getWidth()/3.2), 0, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
            g.setColor(new Color(245,80,80));
            g.fillRect(getWidth()/8, getHeight()/8 *7, getWidth()/8, getHeight());
            g.fillRect(getWidth()/8 * 6, getHeight()/8 *7, getWidth()/8, getHeight());
            
            
            g.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(getWidth()/8, getHeight()/8 *7, getWidth()/8 *2, getHeight()/8 *7);
            g2.drawLine(getWidth()/8 *2, getHeight()/8 *7, getWidth()/8 *2, getHeight());
            g2.drawLine(getWidth()/8 *6, getHeight()/8 *7, getWidth()/8 *7, getHeight()/8 *7);
            g2.drawLine(getWidth()/8 *6, getHeight()/8 *7, getWidth()/8 *6, getHeight());
            Font font = new Font(null, Font.PLAIN, 20);
            g2.setFont(font);
            g2.drawString(jugadores[0].getCartasPendientes() + " Cartas",getWidth()/8*6/5, getHeight()/16 *15);
            g2.drawString("Cancelar" ,getWidth()/8*6, getHeight()/16 *15);
            if (pulsado) {
                g.drawImage(imageOf(jugadores[0].getPrimera().getDirectorio()), cartaX, cartaY, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
            } else {
                g.drawImage(imageOf(jugadores[0].getPrimera().getDirectorio()), (int)(getWidth()/3.2), getHeight()/16 * 11, (int) (getWidth()/3.5), (int)(getHeight()/3.5), null);
            }
        }
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
    
    public void tiradaRival() {
        monton.push(jugadores[1].getPrimera());
        jugadores[1].tirarCarta();
        repaint();
    }
    
    public void cancelarTiradaRival() {
        if (monton.size() > 1) {
            monton.pop();
            jugadores[1].cancelarTirada();
            repaint();
        }
    }
    
    public void cancelarTiradaJugador() {
        if(monton.size() > 1) {
            monton.pop();
            jugadores[0].cancelarTirada();
            repaint();
        }
    }
    
    public void notificarInicioRival() {
        inicio[1] = true;
        if(inicio[0]) {
            primeraPintada = false;
            repaint();
        }
    }
    
    public void finalPartida() {
        setVisible(false);
        EndDisplay endDisplay = new EndDisplay(false);
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
            if (primeraPintada == false && isInRange(e.getX(), e.getY())) {
                pressed = true;
                pulsado = true;
                mouseXPosition = e.getX();
                mouseYPosition = e.getY();
                cartaX = (int)(getWidth()/3.2);
                cartaY = getHeight()/16 * 11;
            }
            if (primeraPintada && isInButton(e.getX(), e.getY())) {
                if(inicio[1]) {
                    primeraPintada = false;
                }
                //primeraPintada = false;
                inicio[0] = true;
                cliente.notificarRival(1);
            }
            if (primeraPintada == false && isInCancel(e.getX(), e.getY())) {
                cliente.notificarRival(3);
                cancelarTiradaJugador();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(pressed && isInCenter(cartaX + (int) (getWidth()/7), cartaY)) {
                monton.push(jugadores[0].getPrimera());
                if(jugadores[0].getCartasPendientes() == 1) {
                    System.out.println("holaa");
                    setVisible(false);
                    cliente.notificarRival(4);
                    EndDisplay endDisplay = new EndDisplay(true);
                } else {
                    jugadores[0].tirarCarta();
                }
                cliente.notificarRival(2);
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
                int xProv = cartaX + (e.getX() - mouseXPosition);
                cartaY = cartaY + (e.getY() - mouseYPosition);
                mouseXPosition = e.getX();
                mouseYPosition = e.getY();
                if (xProv >= getWidth()/8 && xProv + (int) (getWidth()/3.5) <= getWidth()/8 * 7) {
                    cartaX = xProv;
                }
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }
        
    }
    
}
