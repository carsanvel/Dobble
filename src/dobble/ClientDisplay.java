package dobble;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientDisplay extends JPanel{
    
    private JTextField ipText;
    private JTextField portText;
    
    public ClientDisplay() {
        setLayout(new BorderLayout());
        initializeIpPanel();
        initializePortPanel();
    }

    private void initializeIpPanel() {
        JPanel ipPanel = new JPanel();
        ipText = new JTextField(20);
        ipPanel.add(new JLabel("IP: "));
        ipPanel.add(ipText);
        add(ipPanel, BorderLayout.NORTH);
    }
    
    private void initializePortPanel() {
        JPanel portPanel = new JPanel();
        portText = new JTextField(10);
        portPanel.add(new JLabel("Puerto: "));
        portPanel.add(portText);
        add(portPanel, BorderLayout.CENTER);
    }
    
    public String getIp() {
        return ipText.getText();
    }
        
    public String getPort() {
        return portText.getText();
    }
    

    
}
