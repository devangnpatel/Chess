package chess.moves;

import chess.board.BoardState;
import chess.pieces.Piece;
import chess.utility.Location;
import java.io.Serializable;

/**
 *
 * @author devang
 */
public class MoveRegular extends Move implements Serializable {
    
    private final Location fromLocation;
    private final Location toLocation;
    
    public MoveRegular(String move, Location from, Location to)
    {
        super(move);
        fromLocation = Location.copyOf(from);
        toLocation   = Location.copyOf(to);
    }
    
    @Override
    public Move getCopy()
    {
        return new MoveRegular(move,fromLocation,toLocation);
    }
    
    /**
     * gets the original location of the piece in this move
     * @return old Location of piece
     */
    @Override
    public Location getToLocation()
    {
        return toLocation;
    }
    
    /**
     * gets the destination of the Piece in this move
     * @return new Location for piece
     */
    public Location getFromLocation()
    {
        return fromLocation;
    }
    
    /**
     * commits this Move to the board state in the argument
     * @param boardState state of a board of a game against to which to apply this move
     */
    @Override
    public void commitMove(BoardState boardState)
    {
        Piece piece         = boardState.getPiece(fromLocation);
        Piece capturedPiece = null;
        boardState.removePiece(fromLocation);
        if (!boardState.isEmpty(toLocation))
            capturedPiece = boardState.removePiece(toLocation);
        boardState.setPiece(piece,toLocation);
        boardState.moveHistory.add(this);
        piece.history.add(this);
        if (capturedPiece != null)
            capturedPiece.history.add(this);
    }
}
