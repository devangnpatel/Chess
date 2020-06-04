package chess.moves;

import chess.board.BoardState;
import chess.pieces.Piece;
import chess.utility.Location;
import java.io.Serializable;

/**
 *
 * @author devang
 */
public class MoveEnPassant extends Move implements Serializable {
    
    private final Location fromLocation;
    private final Location toLocation;
    private final Location captureLocation;
    
    public MoveEnPassant(String move, Location from, Location to, Location capture)
    {
        super(move);
        fromLocation    = Location.copyOf(from);
        toLocation      = Location.copyOf(to);
        captureLocation = Location.copyOf(capture);
    }
    
    @Override
    public Move getCopy()
    {
        return new MoveEnPassant(move,fromLocation,toLocation,captureLocation);
    }
    
    /**
     * gets the target destination of this Move
     * @return destination Location
     */
    @Override
    public Location getToLocation()
    {
        return toLocation;
    }
    
    /**
     * commits this en-passant Move to the board state in the argument
     * @param boardState state of a board of a game against to which to apply this move
     */
    @Override
    public void commitMove(BoardState boardState)
    {
        Piece piece = boardState.getPiece(fromLocation);
        Piece capturedPiece = boardState.removePiece(captureLocation);
        boardState.removePiece(fromLocation);
        boardState.setPiece(piece,toLocation);
        boardState.moveHistory.add(this);
        piece.history.add(this);
        capturedPiece.history.add(this);
    }
}
