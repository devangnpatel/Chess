package chess.pieces;

import chess.board.BoardState;
import chess.books.MoveHistory;
import chess.game.GameProperties;
import chess.game.GameProperties.PlayerColor;
import chess.moves.Move;
import chess.utility.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devang
 */
public class PieceKnight extends Piece {
    
    private PieceKnight(PlayerColor color)
    {
        super(color);
    }
    
    

    /**
     * gets a list of valid moves for the piece at the given location on the given board state<br>
     * - the piece at this location will be this knight<br>
     * @param location location for this knight on the board for which to determine all valid moves
     * @param boardState state of the board to analyze for current valid moves
     * @return List of valid moves for this knight at the given location on the given board state
     */
    @Override
    public List<Move> getValidMoves(Location location, BoardState boardState)
    {
        List<Move> validMoves = new ArrayList<>();
        if (location == null) return validMoves;
        Piece thisPiece = boardState.getPiece(location);
        if (!(thisPiece instanceof PieceKnight)) return validMoves;
        PlayerColor playerColor = thisPiece.getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        Location startLocation = Location.copyOf(location);
        
        Location[] moveLocations = { Location.left(Location.up2(startLocation)),
                                     Location.right(Location.up2(startLocation)),
                                     Location.left(Location.down2(startLocation)),
                                     Location.right(Location.down2(startLocation)),
                                     Location.up(Location.left2(startLocation)),
                                     Location.down(Location.left2(startLocation)),
                                     Location.up(Location.right2(startLocation)),
                                     Location.down(Location.right2(startLocation))};
        
        for (Location nextLocation : moveLocations)
        {
            startLocation = Location.copyOf(location);
            Move newMove = validateMove(startLocation,nextLocation,boardState);
            if (newMove != null)
            {
                validMoves.add(newMove);
            }
        }
        
        return validMoves;
    }
    
    /**
     * returns a deep-copy of this Piece, useful if class variables are added later
     * @return newly created copy of this Knight
     */
    @Override
    public Piece getCopy()
    {
        Piece newPiece = PieceKnight.create(getColor());
        newPiece.history = MoveHistory.createFrom(history);
        return newPiece;
    }
    
    /**
     * factory-style create of a Knight
     * @param color black, or white
     * @return newly-created Knight object
     */
    public static Piece create(PlayerColor color)
    {
        return new PieceKnight(color);
    }
}
