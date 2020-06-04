package chess.board;

import chess.books.MoveHistory;
import chess.game.GameProperties.PlayerColor;
import chess.moves.Move;
import chess.pieces.Piece;
import chess.pieces.PieceKing;
import chess.utility.Location;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author devang
 */
public class BoardState {
    
    public List<Move> getValidMoves(Location location)
    {
        if (isEmpty(location)) return null;
        Piece piece = getPiece(location);
        return piece.getValidMoves(location,this);
    }
    
    public MoveHistory moveHistory;
    
    /////////////////////////////////
    
    protected Set<Piece>                 pieces;
    protected Map<Location,Piece>        pieceLocations;
    //protected Map<Piece,Location>        locations;
    private   Map<PlayerColor,PieceKing> kings;
    private   Map<PieceKing,Location>    kingLocations;
    
    public BoardState()
    {
        pieces         = new HashSet<>();
        pieceLocations = new HashMap<>();
        kings          = new HashMap<>();
        kingLocations  = new HashMap<>();
        
        moveHistory = MoveHistory.createNew();
    }
    
    /**
     * Tests if the king (of the parameter player's color) is in Check
     * @param color the Player whose king is tested in check
     * @return True if the king is in Check, False otherwise
     */
    public boolean check(PlayerColor color)
    {
        PlayerColor playerColor   = color;
        
        PieceKing kingPiece    = kings.get(playerColor);
        Location  kingLocation = kingLocations.get(kingPiece);

        return kingPiece.check(kingLocation,this);
    }
    
    /**
     * returns a deep-copy of this board state, using this Class set,get,remove
     * methods as an ordinary game does during regular game-play
     * @return newly-created board state from a deep-copy of this
     */
    protected BoardState getCopy()
    {
        BoardState newBoardState = new BoardState();
        for (Location location : pieceLocations.keySet())
        {
            Piece piece = pieceLocations.get(location);
            Location newLocation = Location.copyOf(location);
            Piece newPiece = Piece.copy(piece);
            newBoardState.setPiece(newPiece,newLocation);
        }
        newBoardState.moveHistory = MoveHistory.createFrom(moveHistory);
        return newBoardState;
    }
    
    /**
     * Gets a newly-created copy of the given boardState
     * @param boardState original boardState to be copied
     * @return new BoardState object, copy of the input
     */
    public static BoardState copy(BoardState boardState)
    {
        if (boardState == null) return null;
        return boardState.getCopy();
    }
    
    /**
     * determines whether the space at location on the board is empty
     * @param location location on the board at which to determine emptiness
     * @return true if the space on the board is empty, false otherwise
     */
    public boolean isEmpty(Location location)
    {
        if (pieceLocations.containsKey(location))
        {
            if (pieceLocations.get(location) != null)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * gets the piece at the location on the board
     * @param location location at which to get a reference to a piece
     * @return piece at the location, if not empty, null otherwise
     */
    public Piece getPiece(Location location)
    {
        Piece piece = pieceLocations.get(location);
        return piece;
    }
    
    /**
     * removes, and returns, the piece at the location on the board
     * @param location location at which to remove the piece
     * @return reference to removed Piece, if needed
     */
    public Piece removePiece(Location location)
    {
        Piece piece = pieceLocations.remove(location);
        if (piece != null) pieces.remove(piece);
        return piece;
    }
    
    /**
     * sets a piece to a location on the board:
     * adds the pieces to a Set and Map keyed by Location, and
     * creates quick-access maps to Kings based on their color, and
     * the locations of the Kings
     * @param piece piece to set on the board
     * @param location location at which to set the piece
     */
    public void setPiece(Piece piece, Location location)
    {        
        if (piece instanceof PieceKing)
        {
            PlayerColor kingColor = ((PieceKing)piece).getColor();
            kings.put(kingColor,(PieceKing)piece);
            if (kingLocations.containsKey((PieceKing)piece))
                kingLocations.replace((PieceKing)piece,location);
            else
                kingLocations.put((PieceKing)piece,location);
        }
        pieces.add(piece);
        pieceLocations.put(location,piece);
    }
}
