package chess.moves;

import chess.board.BoardState;
import chess.books.MoveHistory;
import chess.game.GameProperties.PlayerColor;
import chess.pieces.Piece;
import chess.pieces.PieceBishop;
import chess.pieces.PieceKnight;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import chess.utility.Location;
import java.io.Serializable;

/**
 *
 * @author devang
 */
public class MovePromotion extends Move implements Serializable {
    
    private PieceType newPieceType;
    private final Location fromLocation;
    private final Location toLocation;
    
    public enum PieceType {
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK
    }
    
    public MovePromotion(String move, PieceType newPiece, Location from, Location to)
    {
        super(move);
        if (newPiece == null) newPiece = PieceType.QUEEN;
        newPieceType = newPiece;
        fromLocation = Location.copyOf(from);
        toLocation   = Location.copyOf(to);
    }
    
    public void setNewPieceType(PieceType pieceType)
    {
        newPieceType = pieceType;
    }
    
    @Override
    public Move getCopy()
    {
        return new MovePromotion(move,newPieceType,fromLocation,toLocation);
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
     * commits this pawn-promoting Move to the board state in the argument
     * @param boardState state of a board of a game against to which to apply this move
     */
    @Override
    public void commitMove(BoardState boardState)
    {
        Piece oldPiece         = boardState.getPiece(fromLocation);
        Piece newPiece         = null;
        Piece capturedPiece    = null;
        PlayerColor pieceColor = oldPiece.getColor();

        switch (newPieceType)
        {
            case QUEEN:
                newPiece = PieceQueen.create(pieceColor);
                break;
            case BISHOP:
                newPiece = PieceBishop.create(pieceColor);
                break;
            case KNIGHT:
                newPiece = PieceKnight.create(pieceColor);
                break;
            case ROOK:
                newPiece = PieceRook.create(pieceColor);
                break;
            default:
                newPiece = PieceQueen.create(pieceColor);
                break;
        }
        newPiece.history = MoveHistory.createFrom(oldPiece.history);
        
        if (!boardState.isEmpty(toLocation))
            capturedPiece = boardState.removePiece(toLocation);
        boardState.removePiece(fromLocation);
        boardState.setPiece(newPiece,toLocation);
        boardState.moveHistory.add(this);
        oldPiece.history.add(this);
        newPiece.history.add(this);
        if (capturedPiece != null)
            capturedPiece.history.add(this);
    }

}
