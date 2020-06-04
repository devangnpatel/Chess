package chess.game;

import chess.game.GameProperties.PlayerColor;
import static chess.game.GameProperties.PlayerColor.BLACK;
import static chess.game.GameProperties.PlayerColor.WHITE;
import chess.graphics.GraphicsBoard;
import chess.network.NetworkClient;
import chess.players.Player;
import chess.players.PlayerHuman;

/**
 *
 * @author devang
 */
public class PlayerManager {
    private PlayerColor activePlayer;
    private Player[] players;
    
    public PlayerManager()
    {
        players = new Player[2];
        activePlayer = WHITE;
    }
    
    public void togglePlayer()
    {
        if (activePlayer == BLACK) activePlayer = WHITE;
        else activePlayer = BLACK;
    }
    
    public Player getActivePlayer()
    {
        if (players[0].getColor() == activePlayer)
            return players[0];
        return players[1];
    }
    
    public Player[] getPlayers()
    {
        return players;
    }
    
    public PlayerColor getActivePlayerColor()
    {
        return activePlayer;
    }
        
    protected void initializePlayersLocalGame()
    {
        players[0]         = Player.newHumanPlayer(WHITE);
        players[1]         = Player.newHumanPlayer(BLACK);
        GraphicsBoard gui = Game.getGraphicsBoard();
        ((PlayerHuman)(players[0])).setGui(gui);
        ((PlayerHuman)(players[1])).setGui(gui);
    }
    
    protected void initializePlayersAIGame()
    {
        PlayerColor humanPlayerColor;
        PlayerColor CPUPlayerColor;
        if (Math.random() > 0.5) humanPlayerColor = WHITE;
        else humanPlayerColor = BLACK;
        CPUPlayerColor     = GameProperties.getOpponentColor(humanPlayerColor);
        
        players[0]         = Player.newHumanPlayer(humanPlayerColor);
        GraphicsBoard gui = Game.getGraphicsBoard();
        ((PlayerHuman)(players[1])).setGui(gui);
        players[1]         = Player.newCPUPlayer(CPUPlayerColor);
    }
    
    protected void initializePlayersNetworkGame(PlayerColor color, NetworkClient client)
    {
        
        PlayerColor localPlayerColor  = color;
        PlayerColor remotePlayerColor = GameProperties.getOpponentColor(color);
        
        players = new Player[2];
        players[0] = Player.newHumanPlayer(localPlayerColor);
        GraphicsBoard gui = Game.getGraphicsBoard();
        ((PlayerHuman)(players[1])).setGui(gui);
        players[1] = Player.newNetworkPlayer(remotePlayerColor,client);
    }

}
