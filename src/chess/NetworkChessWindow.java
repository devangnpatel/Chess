package chess;

import chess.game.Game;
import chess.game.GameProperties.PlayerColor;
import chess.network.NetworkClient;
import chess.network.NetworkServer;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author devang
 */
public class NetworkChessWindow implements ActionListener {
    
    public NetworkClient client;
    
    private JFrame gameFrame;
    private JTextField serverTextField;
    private JTextField portTextField;
    
    private JButton startClientButton;
    private JButton launchServerButton;
    private JButton local2PGameButton;
    private JButton localAIGameButton;
    
    private JLabel serverLabel;
    private JLabel portLabel;
                
    private JLabel titleLabel;
    private String gameName = "Chess [over the Internet]";
    
    private InformationPanel statusPanel;
    private JPanel networkInfoPanel;
    private JPanel networkButtonsPanel;
    
    public static void start()
    {
        new NetworkChessWindow();
    }
    
    private NetworkChessWindow() {
        super();
        init();
    }
    
    public final void init()
    {
        gameFrame = new JFrame("Network Chess");
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                gameFrame.dispose();
                System.exit(0);
            }
        });

        gameFrame.add(gamePanel());
        int xLocation = (int)Math.floor(Math.random()*500);
        int yLocation = (int)Math.floor(Math.random()*400);
        gameFrame.setLocation(xLocation,yLocation);
        gameFrame.setSize(320, 180); 
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }
    
    public JPanel gamePanel()
    {
        JPanel gamePanel = new JPanel();
        GridLayout gamePanelLayout = new GridLayout(3,1);
        gamePanel.setLayout(gamePanelLayout);

        networkInfoPanel = networkInfoPanel();
        networkButtonsPanel = networkButtonsPanel();
        statusPanel = new InformationPanel();
        
        gamePanel.add(networkInfoPanel);
        gamePanel.add(networkButtonsPanel);
        gamePanel.add(statusPanel);

        startClientButton.setEnabled(true);
        launchServerButton.setEnabled(true);
        serverTextField.setEnabled(true);
        portTextField.setEnabled(true);
        serverLabel.setEnabled(true);
        portLabel.setEnabled(true);
        
        return gamePanel;
    }
    
    public JPanel networkInfoPanel()
    {
        JPanel networkInfoPanel = new JPanel();
        GridLayout networkInfoPanelLayout = new GridLayout(2,2);
        networkInfoPanel.setLayout(networkInfoPanelLayout);
        serverLabel = new JLabel("  server:",SwingConstants.LEFT);
        portLabel = new JLabel("  port:",SwingConstants.LEFT);
        serverTextField = new JTextField();
        portTextField = new JTextField();
        serverTextField.setText("127.0.0.1");
        portTextField.setText("8080");
        serverLabel.setVisible(true);
        portLabel.setVisible(true);
        serverTextField.setVisible(true);
        portTextField.setVisible(true);
        networkInfoPanel.add(serverLabel);
        networkInfoPanel.add(portLabel);
        networkInfoPanel.add(serverTextField);
        networkInfoPanel.add(portTextField);
        networkInfoPanel.setVisible(true);

        return networkInfoPanel;
    }
    
    public JPanel networkButtonsPanel()
    {
        JPanel networkButtonsPanel = new JPanel();
        GridLayout networkButtonsPanelLayout = new GridLayout(2,2);
        networkButtonsPanel.setLayout(networkButtonsPanelLayout);
        startClientButton = new JButton("Connect Client");
        launchServerButton = new JButton("Launch Server");
        local2PGameButton = new JButton("Local 2P Chess");
        localAIGameButton = new JButton("Local 1P Chess");
        startClientButton.setVisible(true);
        launchServerButton.setVisible(true);
        local2PGameButton.setVisible(true);
        localAIGameButton.setVisible(true);
        networkButtonsPanel.add(startClientButton);
        networkButtonsPanel.add(launchServerButton);
        networkButtonsPanel.add(local2PGameButton);
        networkButtonsPanel.add(localAIGameButton);
        networkButtonsPanel.setVisible(true);
        
        startClientButton.addActionListener(this);
        launchServerButton.addActionListener(this);
        local2PGameButton.addActionListener(this);
        localAIGameButton.addActionListener(this);
        
        return networkButtonsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
                
        if (s.equals("Connect Client"))
        {
            client = new NetworkClient(this);
            String server = serverTextField.getText();
            int port = Integer.valueOf(portTextField.getText());
            
            client.startClient(server,port);
            
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            
            gameFrame.repaint();
        }
        
        if (s.equals("Launch Server"))
        {
            new NetworkServer(gameName);
            
            startClientButton.setEnabled(true);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(true);
            portTextField.setEnabled(true);
            serverLabel.setEnabled(true);
            portLabel.setEnabled(true);
            
            gameFrame.repaint();

        }
        
        if (s.equals("Local 2P Chess"))
        {
            Game.startLocal2PGame();
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            
            gameFrame.repaint();
        }

        
        if (s.equals("Local AI Chess"))
        {
            Game.startLocalAIGame();
            startClientButton.setEnabled(false);
            launchServerButton.setEnabled(false);
            serverTextField.setEnabled(false);
            portTextField.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            
            gameFrame.repaint();
        }

    }
    
    public void clientConnectedMessage()
    {
        statusPanel.setInformationText("Connected to Server . . . waiting for opponent");
    }
    
    public void gameStartedMessage(PlayerColor color)
    {
        statusPanel.setInformationText("game started . . . good luck!");
        Game.startNetworkGame(color,client);
    }
}
