package dobble;

import static dobble.Dobble.rellenaPila;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Stack;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Cliente extends JFrame{
    
    private ClientDisplay clientDisplay;
    private Socket socket;
    private String ipRival;
    private int puertoRival;
    private ButtonGroup buttons;
    private JRadioButton hostButton;
    private JRadioButton clientButton;
    private boolean host;
    private Servidor servidor;
    private boolean[] confirmado;
    private ManoCartas[] manos;
    private Carta cartaExtra;
    
    public Cliente(Servidor servidor) {
        host = false;
        confirmado = new boolean[2];
        confirmado[0] = false;
        confirmado[1] = false;
        manos = null;
        cartaExtra = null;
        this.servidor = servidor;
        setTitle("Dobble");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 200);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(clientDisplay(), BorderLayout.NORTH);
        initializeButtons();
    }
    
    public void execute() {
        setVisible(true);
    }
    
    public JPanel clientDisplay() {
        ClientDisplay display = new ClientDisplay();
        clientDisplay = display;
        return display;
    }
    
    public void initializeButtons() {
        JPanel buttonPanel = new JPanel();
        buttons = new ButtonGroup();
        hostButton = new JRadioButton("Host", true);
        clientButton = new JRadioButton("Client");
        buttons.add(hostButton);
        buttons.add(clientButton);
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.addActionListener(accept());
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(hostButton, BorderLayout.NORTH);
        buttonPanel.add(clientButton, BorderLayout.CENTER);
        JPanel auxPanel = new JPanel();
        auxPanel.add(acceptButton);
        buttonPanel.add(auxPanel, BorderLayout.SOUTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
    }
    
    private ActionListener accept() {
        
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    confirmado[0] = true;
                    ipRival = clientDisplay.getIp();
                    puertoRival = Integer.parseInt(clientDisplay.getPort());
                    socket = new Socket(ipRival, puertoRival);
                    PaqueteEnvio paquete = null;
                    if (hostButton.isSelected()) {
                        host = true;
                        Reparticion reparticion = RepartidorDeCartas.reparte(2);
                        Carta[][] cartas = reparticion.getCartas();
                        Stack<Carta> pila1 = new Stack<>();
                        Stack<Carta> pila2 = new Stack<>();
                        rellenaPila(cartas[0], pila1);
                        rellenaPila(cartas[1], pila2);
                        manos = new ManoCartas[2];
                        manos[0] = new ManoCartas(pila1);
                        manos[1] = new ManoCartas(pila2);
                        cartaExtra = reparticion.getCartaExtra();
                        paquete = new PaqueteEnvio(ipRival, puertoRival, 0, manos, cartaExtra);
                        ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
                        flujoSalida.writeObject(paquete);
                        confirmaInicio(null, null, false);
                    } else {
                        paquete = new PaqueteEnvio(ipRival, puertoRival, 0, null, null);
                        ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
                        flujoSalida.writeObject(paquete);
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
    }
    
    public void notificarRival(int tipoNotificacion) {
        PaqueteEnvio paquete = new PaqueteEnvio(ipRival, puertoRival, tipoNotificacion, null, null);
        try {
            ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            flujoSalida.writeObject(paquete);
            //flujoSalida.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void confirmaInicio(ManoCartas[] manos, Carta carta, boolean cambiar) {
        if(cambiar) {
            if(host == false) {
                confirmado[1] = true;
                this.manos = manos;
                this.cartaExtra = carta;
            }
        }
        if(confirmado[0] && confirmado[1]) {
            setVisible(false);
            MainFrame frame = createMainFrame();
            frame.execute();
            servidor.setGameDisplay(frame.getGameDisplay());
        }
    }
    
    private static void rellenaPila(Carta[] cartas, Stack<Carta> pila) {
        for (int i = 0; i < cartas.length; i++) {
            pila.push(cartas[i]);
        }
    }
    
    private MainFrame createMainFrame() {
        return new MainFrame(manos, cartaExtra, this);
    }
}
