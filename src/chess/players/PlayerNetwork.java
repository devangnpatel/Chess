package chess.players;

import chess.game.GameProperties.PlayerColor;
import chess.network.NetworkClient;

/**
 *
 * @author devang
 */
public class PlayerNetwork extends Player {
    private NetworkClient client;
    public PlayerNetwork(PlayerColor color, NetworkClient client)
    {
        super(color);
        this.client = client;
    }
    
    @Override
    public void terminate()
    {
        if (client != null) client.endClientListener();
    }
}
