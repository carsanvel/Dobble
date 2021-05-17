package dobble;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    
    private final EscuchaServidor escucha;
    private GameDisplay gameDisplay;
    
    public Servidor(GameDisplay gameDisplay) {
        escucha = new EscuchaServidor();
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
                while(true) {
                    Socket socket = socketServidor.accept();
                    ObjectInputStream flujoEntrada = new ObjectInputStream(socket.getInputStream());
                    paquete = (PaqueteEnvio)flujoEntrada.readObject();
                    //socket.close();
                    if(paquete.getMensaje() == 0) {
                        //Notificar al cliente que el rival ha confirmado partida
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
