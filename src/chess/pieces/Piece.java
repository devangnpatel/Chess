package chess.pieces;

import chess.board.BoardState;
import chess.books.MoveHistory;
import chess.game.GameProperties;
import chess.game.GameProperties.Direction;
import static chess.game.GameProperties.Direction.UP;
import chess.game.GameProperties.PlayerColor;
import chess.moves.Move;
import chess.utility.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Piece class using Factory and Composite patterns: because each piece
 * is initialized similarly, but also have different functionality
 * @author devang
 */
public abstract class Piece {
    private final PlayerColor color;
    public MoveHistory history;
    
    public abstract Piece getCopy();
    
    protected Piece(PlayerColor color)
    {
        this.color = color;
        history = MoveHistory.createNew();
    }
    
    /**
     * gets the color (black,white) of this Piece
     * @return black, or white
     */
    public PlayerColor getColor()
    {
        return color;
    }
    
    /**
     * initiates deep-copy creation of a piece [using composite pattern]
     * @param piece piece of which to create a deep-copy
     * @return newly-created deep copy of the piece in the argument
     */
    public static Piece copy(Piece piece)
    {
        return piece.getCopy();
    }
    
    public abstract List<Move> getValidMoves(Location location, BoardState boardState);
    
    protected Location nextLocation(Location start, int i, BiFunction<Location,Integer,Location> locationMethod) {
        Location newLocation = locationMethod.apply(start, i);
        return newLocation;
    }
    /**
     * determines if a move for the piece at startLocation argument can legally move to nextLocation argument
     * @param startLocation location of a piece to evaluate a move
     * @param nextLocation location of a destination of a move to validate
     * @param boardState state of the board to analyze for this move
     * @return Move object if the piece at startLocation can legally move to nextLocation
     */
    protected Move validateMove(Location startLocation, Location nextLocation, BoardState boardState)
    {        
        PlayerColor playerColor = boardState.getPiece(startLocation).getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        if ((startLocation == null) || (nextLocation == null)) return null;
        Move newMove = null;
        
        if (!boardState.isEmpty(nextLocation))
        {
            Piece piece = boardState.getPiece(nextLocation);
            if (piece.getColor() == playerColor) return null;
            newMove = Move.createCapture(startLocation, nextLocation, nextLocation);
        }
        else
        {
            newMove = Move.createRegular(startLocation,nextLocation);
        }
        
        BoardState tempBoardState = BoardState.copy(boardState);
        newMove.commitMove(tempBoardState);
        if (!tempBoardState.check(playerColor))
            return newMove;
        
        return null;
    }
    
    /**
     * checks the king for attack from pawns
     * @param location location for the king to check from
     * @param boardState state of the board to analyze for a king in check
     * @return true if the king at the location argument is in check from a pawn
     */
    public boolean checkFromPawns(Location location, BoardState boardState)
    {
        PlayerColor playerColor = getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        Direction playerDirection = GameProperties.getColorDirection(playerColor);
        Direction opponentDirection = GameProperties.getColorDirection(opponentColor);
        
        List<Location> attackLocations = new ArrayList<>();
        if (playerDirection == UP)
        {
            attackLocations.add(Location.upLeft(location));
            attackLocations.add(Location.upRight(location));
        }
        else // Properties.getColorDirection(pieceColor) == DOWN
        {    //   || playerDirection == DOWN
            attackLocations.add(Location.downLeft(location));
            attackLocations.add(Location.downRight(location));
        }
        
        for (Location attackLocation : attackLocations)
        {
            if ((attackLocation != null) && !boardState.isEmpty(attackLocation))
            {
                Piece piece = boardState.getPiece(attackLocation);
                if ((piece.getColor() == opponentColor) && (piece instanceof PiecePawn))
                {
                    return true;
                }
            }
        }
        
        return false;
        
    }
    
    /**
     * checks the king for attack from bishops
     * @param location location for the king to check from
     * @param boardState state of the board to analyze for a king in check
     * @return true if the king at the location argument is in check from a bishop
     */
    public boolean checkFromBishops(Location location, BoardState boardState)
    {
        PlayerColor playerColor = getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        Location startLocation;
        Location attackLocation;

        List<BiFunction<Location,Integer,Location>> locationDirections;
        locationDirections = new ArrayList<>();
        locationDirections.add(Location::upLeftX);
        locationDirections.add(Location::upRightX);
        locationDirections.add(Location::downLeftX);
        locationDirections.add(Location::downRightX);

        startLocation = Location.copyOf(location);
        for (BiFunction<Location,Integer,Location> function : locationDirections)
        {
            int next = 1;
            attackLocation = nextLocation(startLocation,next,function);
            while ((attackLocation != null ) && boardState.isEmpty(attackLocation))
            {
                next++;
                attackLocation = nextLocation(startLocation,next,function);
            }
            if ((attackLocation != null) && !boardState.isEmpty(attackLocation))
            {
                Piece piece = boardState.getPiece(attackLocation);
                if ((piece.getColor() == opponentColor) && (piece instanceof PieceBishop))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * checks the king for attack from knights
     * @param location location for the king to check from
     * @param boardState state of the board to analyze for a king in check
     * @return true if the king at the location argument is in check from a knight
     */
    public boolean checkFromKnights(Location location, BoardState boardState)
    {
        PlayerColor playerColor = getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        Location[] attackLocations = { Location.left(Location.up2(location)),
                                       Location.right(Location.up2(location)),
                                       Location.left(Location.down2(location)),
                                       Location.right(Location.down2(location)),
                                       Location.up(Location.left2(location)),
                                       Location.down(Location.left2(location)),
                                       Location.up(Location.right2(location)),
                                       Location.down(Location.right2(location))};
        
        for (Location attackLocation : attackLocations)
        {
            if ((attackLocation != null) && !boardState.isEmpty(attackLocation))
            {
                Piece piece = boardState.getPiece(attackLocation);
                if ((piece.getColor() == opponentColor) && (piece instanceof PieceKnight))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * checks the king for attack from rooks
     * @param location location for the king to check from
     * @param boardState state of the board to analyze for a king in check
     * @return true if the king at the location argument is in check from a rook
     */
    public boolean checkFromRooks(Location location, BoardState boardState)
    {
        PlayerColor playerColor = getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        Location startLocation;
        Location attackLocation;

        List<BiFunction<Location,Integer,Location>> locationDirections;
        locationDirections = new ArrayList<>();
        locationDirections.add(Location::leftX);
        locationDirections.add(Location::rightX);
        locationDirections.add(Location::upX);
        locationDirections.add(Location::downX);

        startLocation = Location.copyOf(location);
        for (BiFunction<Location,Integer,Location> function : locationDirections)
        {
            int next = 1;
            attackLocation = nextLocation(startLocation,next,function);
            while ((attackLocation != null ) && boardState.isEmpty(attackLocation))
            {
                next++;
                attackLocation = nextLocation(startLocation,next,function);
            }
            if ((attackLocation != null) && !boardState.isEmpty(attackLocation))
            {
                Piece piece = boardState.getPiece(attackLocation);
                if ((piece.getColor() == opponentColor) && (piece instanceof PieceRook))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * checks the king for attack from a queen
     * @param location location for the king to check from
     * @param boardState state of the board to analyze for a king in check
     * @return true if the king at the location argument is in check from a queen
     */
    public boolean checkFromQueens(Location location, BoardState boardState)
    {
        PlayerColor playerColor = getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        Location startLocation;
        Location attackLocation;

        List<BiFunction<Location,Integer,Location>> locationDirections;
        locationDirections = new ArrayList<>();
        locationDirections.add(Location::leftX);
        locationDirections.add(Location::rightX);
        locationDirections.add(Location::upX);
        locationDirections.add(Location::downX);
        locationDirections.add(Location::upLeftX);
        locationDirections.add(Location::upRightX);
        locationDirections.add(Location::downLeftX);
        locationDirections.add(Location::downRightX);

        startLocation = Location.copyOf(location);
        for (BiFunction<Location,Integer,Location> function : locationDirections)
        {
            int next = 1;
            attackLocation = nextLocation(startLocation,next,function);
            while ((attackLocation != null ) && boardState.isEmpty(attackLocation))
            {
                next++;
                attackLocation = nextLocation(startLocation,next,function);
            }
            if ((attackLocation != null) && !boardState.isEmpty(attackLocation))
            {
                Piece piece = boardState.getPiece(attackLocation);
                if ((piece.getColor() == opponentColor) && (piece instanceof PieceQueen))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * checks the king for attack from the opponent's king
     * @param location location for the king to check from
     * @param boardState state of the board to analyze for a king in check
     * @return true if the king at the location argument is in check from opponent's king
     */
    public boolean checkFromKing(Location location, BoardState boardState)
    {
        PlayerColor playerColor = getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        Location[] attackLocations = { Location.up(location),
                                       Location.down(location),
                                       Location.left(location),
                                       Location.right(location),
                                       Location.upLeft(location),
                                       Location.downLeft(location),
                                       Location.upRight(location),
                                       Location.downRight(location) };
        
        for (Location attackLocation : attackLocations)
        {
            if ((attackLocation != null) && !boardState.isEmpty(attackLocation))
            {
                Piece piece = boardState.getPiece(attackLocation);
                if ((piece.getColor() == opponentColor) && (piece instanceof PieceKing))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    /**
     * checks the king, at the location argument for attack from opponent
     * @param location location for the king to check from
     * @param boardState state of the board to analyze for a king in check
     * @return true if the king at the location argument is in check from an opponent's piece
     */
    public boolean check(Location location, BoardState boardState)
    {
        PlayerColor playerColor = getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        Direction playerDirection = GameProperties.getColorDirection(playerColor);
        Direction opponentDirection = GameProperties.getColorDirection(opponentColor);

        // check pawn up-left,up-right or down-left,down-right (2 cases)
        if (checkFromPawns(location,boardState))
            return true;
        
        // check bishop up-rightX ... (4 cases)
        if (checkFromBishops(location,boardState))
            return true;
        
        // check knight up-left2, up-right2, up2-left, up2-right ... (8 cases)
        if (checkFromKnights(location,boardState))
            return true;
        
        // check rook upX,leftX ... (4 cases)
        if (checkFromRooks(location,boardState))
            return true;
        
        // check queen up-leftX, downX ... (8 cases)
        if (checkFromQueens(location,boardState))
            return true;
        
        // check king up, down, left, right (4 cases)
        if (checkFromKing(location,boardState))
            return true;
        
        return false;
    }

}
