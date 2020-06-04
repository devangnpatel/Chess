package chess.moves;

import chess.board.BoardState;
import chess.pieces.Piece;
import chess.utility.Location;
import java.io.Serializable;

/**
 *
 * @author devang
 */
public class MoveCastle extends Move implements Serializable {
    
    private final Location kingFromLocation;
    private final Location kingToLocation;
    private final Location rookFromLocation;
    private final Location rookToLocation;
    
    public MoveCastle(String move, Location kingFrom, Location kingTo, Location rookFrom, Location rookTo)
    {
        super(move);
        kingFromLocation = Location.copyOf(kingFrom);
        kingToLocation   = Location.copyOf(kingTo);
        rookFromLocation = Location.copyOf(rookFrom);
        rookToLocation   = Location.copyOf(rookTo);
    }
    
    @Override
    public Move getCopy()
    {
        return new MoveCastle(move,kingFromLocation,kingToLocation,rookFromLocation,rookToLocation);
    }
    
    /**
     * gets the target destination of this Move
     * @return destination Location
     */
    @Override
    public Location getToLocation()
    {
        return kingToLocation;
    }
    
    /**
     * commits this castling move to the board state in the argument
     * @param boardState state of a board of a game against to which to apply this move
     */
    @Override
    public void commitMove(BoardState boardState)
    {
        Piece kingPiece = boardState.getPiece(kingFromLocation);
        Piece rookPiece = boardState.getPiece(rookFromLocation);
        boardState.removePiece(kingFromLocation);
        boardState.removePiece(rookFromLocation);
        boardState.setPiece(kingPiece,kingToLocation);
        boardState.setPiece(rookPiece,rookToLocation);
        boardState.moveHistory.add(this);
        kingPiece.history.add(this);
        rookPiece.history.add(this);
    }
}
