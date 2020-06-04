package chess.pieces;

import chess.board.BoardState;
import chess.books.MoveHistory;
import chess.game.GameProperties;
import chess.game.GameProperties.PlayerColor;
import chess.moves.Move;
import chess.moves.MoveCastle;
import chess.utility.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devang
 */
public class PieceKing extends Piece {
    
    private PieceKing(PlayerColor color)
    {
        super(color);
    }
    
    /**
     * gets a list of valid moves for the piece at the given location on the given board state<br>
     * - the piece at this location will be this king<br>
     * @param location location for this king on the board for which to determine all valid moves
     * @param boardState state of the board to analyze for current valid moves
     * @return List of valid moves for this king at the given location on the given board state
     */
    @Override
    public List<Move> getValidMoves(Location location, BoardState boardState)
    {
        List<Move> validMoves = new ArrayList<>();
        if (location == null) return validMoves;
        Piece thisPiece = boardState.getPiece(location);
        if (!(thisPiece instanceof PieceKing)) return validMoves;
        PlayerColor playerColor = thisPiece.getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        Location startLocation = Location.copyOf(location);
        Location[] moveLocations = { Location.left(startLocation),
                                     Location.right(startLocation),
                                     Location.up(startLocation),
                                     Location.down(startLocation),
                                     Location.upLeft(startLocation),
                                     Location.upRight(startLocation),
                                     Location.downLeft(startLocation),
                                     Location.downRight(startLocation)};
        
        for (Location nextLocation : moveLocations)
        {
            startLocation = Location.copyOf(location);
            Move newMove = validateMove(startLocation,nextLocation,boardState);
            if (newMove != null)
                validMoves.add(newMove);
        }
        
        Location kingLocation;
        Location newLocation;
        Location rookLocation;
        int rookFile;
        int kingRank;
        Piece rook;
        
        // castle left
        kingLocation = Location.copyOf(location);
        newLocation = Location.left2(location);
        kingRank = Location.getRow(location);
        rookFile = 0;
        rookLocation = Location.of(rookFile,kingRank);
        rook = null;
        if (!boardState.isEmpty(rookLocation))
            rook = boardState.getPiece(rookLocation);
        if ((rook != null) && (rook instanceof PieceRook))
        {
            if (history.isEmpty() && rook.history.isEmpty())
            {
                MoveCastle moveCastle = validateLeftCastle(kingLocation,newLocation,rookLocation,boardState);
                if (moveCastle != null) validMoves.add(moveCastle);
            }
        }
        
        // castle right
        kingLocation = Location.copyOf(location);
        newLocation = Location.right2(location);
        kingRank = Location.getRow(location);
        rookFile = 7;
        //kingRank = 7;
        rookLocation = Location.of(rookFile,kingRank);
        rook = null;
        if (!boardState.isEmpty(rookLocation))
            rook = boardState.getPiece(rookLocation);
        if ((rook != null) && (rook instanceof PieceRook))
        {
            if (history.isEmpty() && rook.history.isEmpty())
            {
                MoveCastle moveCastle = validateRightCastle(kingLocation,newLocation,rookLocation,boardState);
                if (moveCastle != null) validMoves.add(moveCastle);
            }
        }

        return validMoves;
    }
    
    /**
     * determines if this king at startLocation argument can legally castle to the left<br>
     * - validates if the king is in check, or would move through check in castling<br>
     * - validates if the king has already made a move<br>
     * - validates if the left-side rook has already made a move<br>
     * @param kingLocation present location of this king
     * @param newLocation potential castling location for this king
     * @param rookLocation present location of the left-side rook
     * @param boardState state of the board to analyze for this castling
     * @return Move object if this king can legally castle to the left
     */
    protected MoveCastle validateLeftCastle(Location kingLocation, Location newLocation, Location rookLocation, BoardState boardState)
    {   
        if ((kingLocation == null) || (newLocation == null) || (rookLocation == null))
            return null;
        
        PlayerColor playerColor = boardState.getPiece(kingLocation).getColor();
        Location[] leftLocations = new Location[5];
        for (int i = 0; i < 5; i++)
        {
            leftLocations[i] = Location.leftX(kingLocation,i);
            if (leftLocations[i] == null) return null;
        }
        
        for (int i = 1; i < 4; i++)
        {
            if (!boardState.isEmpty(leftLocations[i])) return null;
        }
        
        Move newMove;
        BoardState tempBoardState;
        
        newMove = Move.createRegular(kingLocation,leftLocations[1]);
        tempBoardState = BoardState.copy(boardState);
        newMove.commitMove(tempBoardState);
        if (tempBoardState.check(playerColor))
            return null;
        
        newMove = Move.createRegular(kingLocation,leftLocations[2]);
        tempBoardState = BoardState.copy(boardState);
        newMove.commitMove(tempBoardState);
        if (tempBoardState.check(playerColor))
            return null;
        
        Location newRookLocation = Location.left(kingLocation);
        MoveCastle moveCastle = Move.createCastle(kingLocation,newLocation,rookLocation,newRookLocation);
        
        return moveCastle;
    }
    
    /**
     * determines if this king at startLocation argument can legally castle to the right<br>
     * - validates if the king is in check, or would move through check in castling<br>
     * - validates if the king has already made a move<br>
     * - validates if the right-side rook has already made a move<br>
     * @param kingLocation present location of this king
     * @param newLocation potential castling location for this king
     * @param rookLocation present location of the right-side rook
     * @param boardState state of the board to analyze for this castling
     * @return Move object if this king can legally castle to the right
     */
     protected MoveCastle validateRightCastle(Location kingLocation, Location newLocation, Location rookLocation, BoardState boardState)
    {   
        if ((kingLocation == null) || (newLocation == null) || (rookLocation == null))
            return null;
        
        PlayerColor playerColor = boardState.getPiece(kingLocation).getColor();
        Location[] rightLocations = new Location[4];
        for (int i = 0; i < 4; i++)
        {
            rightLocations[i] = Location.rightX(kingLocation,i);
            if (rightLocations[i] == null) return null;
        }
        
        for (int i = 1; i < 3; i++)
        {
            if (!boardState.isEmpty(rightLocations[i])) return null;
        }
        
        Move newMove;
        BoardState tempBoardState;
        
        newMove = Move.createRegular(kingLocation,rightLocations[1]);
        tempBoardState = BoardState.copy(boardState);
        newMove.commitMove(tempBoardState);
        if (tempBoardState.check(playerColor))
            return null;
        
        newMove = Move.createRegular(kingLocation,rightLocations[2]);
        tempBoardState = BoardState.copy(boardState);
        newMove.commitMove(tempBoardState);
        if (tempBoardState.check(playerColor))
            return null;
        
        Location newRookLocation = Location.right(kingLocation);
        MoveCastle moveCastle = Move.createCastle(kingLocation,newLocation,rookLocation,newRookLocation);
        
        return moveCastle;
    }


    
    /**
     * returns a deep-copy of this Piece, useful if class variables are added later
     * @return newly created copy of this King
     */
    @Override
    public Piece getCopy()
    {
        Piece newPiece = PieceKing.create(getColor());
        newPiece.history = MoveHistory.createFrom(history);
        return newPiece;
    }
    
    /**
     * factory-style create of a King
     * @param color black, or white
     * @return newly-created King object
     */
    public static Piece create(PlayerColor color)
    {
        return new PieceKing(color);
    }
    
    /**
     * typical equals override for use in hash-map, for quick access to the King
     * and its location when a board examines its state
     * @param obj piece to which to compare this
     * @return true if this equals the parameter object (shallow-basis), false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof PieceKing)) return false;
        PieceKing piece = (PieceKing)obj;
        return (piece.getColor() == getColor());
    }

    /**
     * hash-code value for use in hash-map, for quick access to the King and 
     * its location when a board examines its state
     * @return returns hash-code (determined to be unique for every piece)
     */
    @Override
    public int hashCode()
    {
        int result = 7;
        result = 17 * result + getColor().ordinal();
        return result;
    }
}
