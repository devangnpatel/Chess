package chess.moves;

import chess.board.BoardState;
import chess.moves.MovePromotion.PieceType;
import chess.utility.Location;
import java.io.Serializable;

/**
 * Move class using composite pattern: so that the specific methods that differ
 * for captures, castling, en-passant and regular moves can be easily called
 * and used with their own operations
 * 
 * @author devang
 */
public abstract class Move implements Serializable {
    protected final String move;
    public abstract void commitMove(BoardState boardState);
    public abstract Location getToLocation();
    public abstract Move getCopy();
    
    public static Move create(String move)
    {
        // do regular,capture,castling separately here...
        return null;
    }
    
    /**
     * constructor
     * @param move String representation of this move
     */
    protected Move(String move)
    {
        this.move = move;
    }
    
    public static MoveRegular createRegular(Location fromLocation, Location toLocation)
    {
        String move = "";
        move+=Location.getFile(fromLocation);
        move+=Location.getRank(fromLocation);
        move+=Location.getFile(toLocation);
        move+=Location.getRank(toLocation);
        
        return new MoveRegular(move,fromLocation,toLocation);
    }
    
    public static MoveCapture createCapture(Location fromLocation, Location toLocation, Location captureLocation)
    {
        String move = "";
        move+=Location.getFile(fromLocation);
        move+=Location.getRank(fromLocation);
        move+=Location.getFile(toLocation);
        move+=Location.getRank(toLocation);
        
        return new MoveCapture(move,fromLocation,toLocation,captureLocation);
    }
    
    public static MoveEnPassant createEnPassant(Location fromLocation, Location toLocation, Location captureLocation)
    {
        String move = "";
        move+=Location.getFile(fromLocation);
        move+=Location.getRank(fromLocation);
        move+=Location.getFile(toLocation);
        move+=Location.getRank(toLocation);
        
        return new MoveEnPassant(move,fromLocation,toLocation,captureLocation);
    }
    
    public static MovePromotion createPromotion(Location fromLocation, Location toLocation, PieceType newPiece)
    {
        String move = "";
        move+=Location.getFile(fromLocation);
        move+=Location.getRank(fromLocation);
        move+=Location.getFile(toLocation);
        move+=Location.getRank(toLocation);
        
        return new MovePromotion(move,newPiece,fromLocation,toLocation);
    }
    
    public static MoveCastle createCastle(Location kingFrom,Location kingTo,Location rookFrom,Location rookTo)
    {
        String move = "";
        move+=Location.getFile(kingFrom);
        move+=Location.getRank(kingFrom);
        move+=Location.getFile(kingTo);
        move+=Location.getRank(kingTo);
        
        return new MoveCastle(move,kingFrom,kingTo,rookFrom,rookTo);
    }
}
