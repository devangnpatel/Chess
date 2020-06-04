package chess.pieces;

import chess.board.BoardState;
import chess.books.MoveHistory;
import chess.game.GameProperties;
import chess.game.GameProperties.PlayerColor;
import chess.moves.Move;
import chess.utility.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 *
 * @author devang
 */
public class PieceQueen extends Piece {
    
    private PieceQueen(PlayerColor color)
    {
        super(color);
    }

    /**
     * gets a list of valid moves for the piece at the given location on the given board state<br>
     * - the piece at this location will be this queen<br>
     * @param location location for this queen on the board for which to determine all valid moves
     * @param boardState state of the board to analyze for current valid moves
     * @return List of valid moves for this queen at the given location on the given board state
     */
    @Override
    public List<Move> getValidMoves(Location location, BoardState boardState)
    {
        List<Move> validMoves = new ArrayList<>();
        if (location == null) return validMoves;
        Piece thisPiece = boardState.getPiece(location);
        if (!(thisPiece instanceof PieceQueen)) return validMoves;
        PlayerColor playerColor = thisPiece.getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        
        Location startLocation;
        Location nextLocation;

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
            nextLocation = nextLocation(startLocation,next,function);
            while ((nextLocation != null ) && boardState.isEmpty(nextLocation))
            {
                Move newMove = validateMove(startLocation,nextLocation,boardState);
                if (newMove != null)
                {
                    validMoves.add(newMove);
                }
                nextLocation = nextLocation(startLocation,next++,function);
            }
            if ((nextLocation != null) && !boardState.isEmpty(nextLocation))
            {
                Move newMove = validateMove(startLocation,nextLocation,boardState);
                if (newMove != null)
                {
                    validMoves.add(newMove);
                }
            }
        }
        
        return validMoves;
    }
    
    /**
     * returns a deep-copy of this Piece, useful if class variables are added later
     * @return newly created copy of this Queen
     */
    @Override
    public Piece getCopy()
    {
        Piece newPiece = PieceQueen.create(getColor());
        newPiece.history = MoveHistory.createFrom(history);
        return newPiece;
    }
    
    /**
     * factory-style create of a Queen
     * @param color black, or white
     * @return newly-created Queen object
     */
    public static Piece create(PlayerColor color)
    {
        return new PieceQueen(color);
    }

}
