package dobble;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    
    private final EscuchaServidor escucha;
    private GameDisplay gameDisplay;
    private Cliente cliente;
    
    public Servidor() {
        escucha = new EscuchaServidor();
        //this.gameDisplay = gameDisplay;
    }
    
    public void setCliente(Cliente cliente) {
       this.cliente = cliente; 
    }
    public void setGameDisplay(GameDisplay gameDisplay) {
        this.gameDisplay = gameDisplay;
    }
    
    private class EscuchaServidor implements Runnable {
        
        public EscuchaServidor() {
            Thread hiloEscucha = new Thread(this);
            hiloEscucha.start();
        }
        
        @Override
        public void run() {
            try {
                PaqueteEnvio paquete;
                ServerSocket socketServidor = new ServerSocket(9999);
                Socket socket = socketServidor.accept();
                while(true) {
                    ObjectInputStream flujoEntrada = new ObjectInputStream(socket.getInputStream());
                    paquete = (PaqueteEnvio)flujoEntrada.readObject();
                    if(paquete.getMensaje() == 0) {
                        ManoCartas[] manos = paquete.getManos();
                        if(manos != null) {
                            ManoCartas aux = manos[0];
                            manos[0] = manos[1];
                            manos[1] = aux;
                        }
                        cliente.confirmaInicio(manos, paquete.getCartaExtra(), true);
                    }
                    if(paquete.getMensaje() == 1) {
                        gameDisplay.notificarInicioRival();
                    }
                    if(paquete.getMensaje() == 2) {
                        gameDisplay.tiradaRival();
                    }
                    if(paquete.getMensaje() == 3) {
                        gameDisplay.cancelarTiradaRival();
                    }
                    if(paquete.getMensaje() == 4) {
                        gameDisplay.finalPartida();
                    }
                    paquete = null;
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            } 
                
        }
        
    }
    
}
