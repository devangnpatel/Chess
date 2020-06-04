package chess.players;

import chess.board.BoardState;
import chess.game.Game;
import chess.game.GameManager;
import chess.game.GameProperties.PlayerColor;
import chess.moves.Move;
import chess.network.NetworkClient;
import chess.pieces.Piece;
import chess.utility.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Player class(es) using Bridge Pattern: to handle CPU,Human,Network separately
 * 
 * @author devang
 */
public abstract class Player {
    protected PlayerColor color;
    
    protected Player(PlayerColor color)
    {
        this.color = color;
    }
    
    public PlayerColor getColor()
    {
        return color;
    }
    
    public static Player newNetworkPlayer(PlayerColor color, NetworkClient client)
    {
        return new PlayerNetwork(color,client);
    }
    
    public static Player newCPUPlayer(PlayerColor color)
    {
        return new PlayerCPU(color);
    }
    
    public static Player newHumanPlayer(PlayerColor color)
    {
        return new PlayerHuman(color);
    }
    
    public abstract void terminate();
    
    public void commitMove(Move move)
    {
        GameManager gameManager = Game.getGameManager();
        gameManager.commitMove(this, move);
    }
        
    protected static List<Move> getValidMoves(Location location)
    {
        BoardState boardState = Game.getBoardState();
        Piece      piece      = boardState.getPiece(location);
        return piece.getValidMoves(location, boardState);
    }
    
    protected static Map<Location,Move> getValidMoves(List<Move> moves)
    {
        Map<Location,Move> moveLocations = new HashMap<>();
        
        for (Move move : moves)
        {
            Location toLocation = Location.copyOf(move.getToLocation());
            moveLocations.put(toLocation,move);
        }
        
        return moveLocations;
    }
}
