package dobble;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Cliente extends JFrame{
    
    private ClientDisplay clientDisplay;
    private Socket socket;
    private String ipRival;
    private int puertoRival;
    
    public Cliente() {
        setTitle("Dobble");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 200);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(clientDisplay(), BorderLayout.NORTH);
        initializeButton();
    }
    
    public void execute() {
        setVisible(true);
    }
    
    public JPanel clientDisplay() {
        ClientDisplay display = new ClientDisplay();
        clientDisplay = display;
        return display;
    }
    
    public void initializeButton() {
        JPanel buttonPanel = new JPanel();
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.addActionListener(accept());
        buttonPanel.add(acceptButton);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
    }
    
    private ActionListener accept() {
        
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ipRival = clientDisplay.getIp();
                    puertoRival = Integer.parseInt(clientDisplay.getPort());
                    socket = new Socket(ipRival, puertoRival);
                    PaqueteEnvio paquete = new PaqueteEnvio(ipRival, puertoRival, 0);
                    ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
                    flujoSalida.writeObject(paquete);
                    flujoSalida.close();
                    setVisible(false);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
    }
    
    public void notificarRival(int tipoNotificacion) {
        PaqueteEnvio paquete = new PaqueteEnvio(ipRival, puertoRival, tipoNotificacion);
        try {
            ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            flujoSalida.writeObject(paquete);
            flujoSalida.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
