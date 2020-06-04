package chess.pieces;

import chess.board.BoardState;
import chess.books.MoveHistory;
import chess.game.GameProperties;
import chess.game.GameProperties.Direction;
import static chess.game.GameProperties.Direction.UP;
import chess.game.GameProperties.PlayerColor;
import chess.moves.Move;
import chess.moves.MoveEnPassant;
import chess.moves.MovePromotion;
import chess.moves.MoveRegular;
import chess.utility.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devang
 */
public class PiecePawn extends Piece {
    
    private PiecePawn(PlayerColor color)
    {
        super(color);
    }
   /**
     * determines if this pawn can do en-passant
     * @param startLocation location of this pawn to evaluate en-passant
     * @param nextLocation location of the destination to evaluate en-passant
     * @param boardState state of the board to analyze for this move
     * @return Move object if the piece at startLocation can legally move to nextLocation
     */
     protected Move validateEnPassantMove(Location startLocation, Location nextLocation, BoardState boardState)
    {        
        PlayerColor playerColor = boardState.getPiece(startLocation).getColor();

        if (nextLocation != null)
        {
            int startFile = Location.getCol(startLocation);
            int startRank = Location.getRow(startLocation);
            int endFile   = Location.getCol(nextLocation);
            int endRank   = Location.getRow(nextLocation);
        
            Location locationCapture = Location.of(endFile,startRank);
            
            Move newMove = Move.createEnPassant(startLocation,nextLocation,locationCapture);
            BoardState tempBoardState = BoardState.copy(boardState);
            newMove.commitMove(tempBoardState);
            if (!tempBoardState.check(playerColor))
            {
                return newMove;
            }
        }
        
        return null;
    }
    /**
     * determines if this pawn at startLocation argument can legally move to nextLocation argument
     * @param startLocation location of this pawn to evaluate a move
     * @param nextLocation location of a destination of a move to validate
     * @param boardState state of the board to analyze for this move
     * @return Move object if this pawn at startLocation can legally move to nextLocation
     */
     @Override
    protected Move validateMove(Location startLocation, Location nextLocation, BoardState boardState)
    {        
        PlayerColor playerColor = boardState.getPiece(startLocation).getColor();

        if (nextLocation != null)
        {
            Move newMove;
            if (Location.isEndRow(nextLocation))
                newMove = Move.createPromotion(startLocation,nextLocation,null);
            else
                newMove = Move.createRegular(startLocation,nextLocation);
            
            BoardState tempBoardState = BoardState.copy(boardState);
            newMove.commitMove(tempBoardState);
            
            if (!tempBoardState.check(playerColor))
            {
                return newMove;
            }
        }
        
        return null;
    }
    
    /**
     * gets a list of valid moves for the piece at the given location on the given board state<br>
     * - the piece at this location will be this pawn<br>
     * - calls to validate en-passant<br>
     * - calls to validate two-space move<br>
     * - calls to validate a capture<br>
     * - calls to trigger event in case of pawn-promotion<br>
     * @param location location for this pawn on the board for which to determine all valid moves
     * @param boardState state of the board to analyze for current valid moves
     * @return List of valid moves for this pawn at the given location on the given board state
     */
    @Override
    public List<Move> getValidMoves(Location location, BoardState boardState)
    {
        MoveHistory moveHistory = boardState.moveHistory;
        List<Move> validMoves = new ArrayList<>();
        if (location == null) return validMoves;
        Piece thisPiece = boardState.getPiece(location);
        if (!(thisPiece instanceof PiecePawn)) return validMoves;
        PlayerColor playerColor = thisPiece.getColor();
        PlayerColor opponentColor = GameProperties.getOpponentColor(playerColor);
        Direction   playerDirection = GameProperties.getColorDirection(playerColor);
        
        Location startLocation;
        Move newMove;
        startLocation = Location.copyOf(location);
        List<Location> moveLocations = new ArrayList<>();
        List<Location> captureLocations = new ArrayList<>();
        
        if (playerDirection == UP)
        {
            moveLocations.add(Location.up(startLocation));
            if (Location.getRank(startLocation).equalsIgnoreCase("2"))
                moveLocations.add(Location.up2(startLocation));
            captureLocations.add(Location.left(Location.up(startLocation)));
            captureLocations.add(Location.right(Location.up(startLocation)));
        }
        else // Properties.getColorDirection(pieceColor) == DOWN
        {
            moveLocations.add(Location.down(startLocation));
            if (Location.getRank(startLocation).equalsIgnoreCase("7"))
                moveLocations.add(Location.down2(startLocation));
            captureLocations.add(Location.left(Location.down(startLocation)));
            captureLocations.add(Location.right(Location.down(startLocation)));
        }

        //////////////////////////
        // regular moves
        for (Location nextLocation : moveLocations)
        {
            startLocation = Location.copyOf(location);
            if ((nextLocation != null) && boardState.isEmpty(nextLocation))
            {
                newMove = validateMove(startLocation,nextLocation,boardState);
                if (newMove != null) validMoves.add(newMove);
            }
        }
        
        //////////////////////////
        // capture moves
        for (Location nextLocation : captureLocations)
        {
            startLocation = Location.copyOf(location);
            if ((nextLocation != null) && !boardState.isEmpty(nextLocation))
            {
                if (boardState.getPiece(nextLocation).getColor() == opponentColor)
                {
                    newMove = validateMove(startLocation,nextLocation,boardState);
                    if (newMove != null) validMoves.add(newMove);
                }
            }
        }
        
        //////////////////////////
        // en-passant
        Location currentLocation = Location.copyOf(location);
        Location leftLocation = Location.left(currentLocation);
        Location rightLocation = Location.right(currentLocation);
        PiecePawn pawnToLeft = null;
        PiecePawn pawnToRight = null;
        if ((leftLocation != null) && !boardState.isEmpty(leftLocation))
        {
            Piece piece = boardState.getPiece(leftLocation);
            if ((piece.getColor() == opponentColor) && (piece instanceof PiecePawn))
            {
                pawnToLeft = (PiecePawn)piece;
            }
        }
        if ((rightLocation != null) && !boardState.isEmpty(rightLocation))
        {
            Piece piece = boardState.getPiece(rightLocation);
            if ((piece.getColor() == opponentColor) && (piece instanceof PiecePawn))
            {
                pawnToRight = (PiecePawn)piece;
            }
        }        
        
        if (playerDirection == UP)
        {
            if ((pawnToLeft != null) && (moveHistory != null))
            {
                MoveRegular mostRecentPawnMove = null;
                MoveRegular mostRecentGameMove = null;
                Move pawnMove = pawnToLeft.history.getLast();
                Move gameMove = moveHistory.getLast();
                
                if (pawnMove instanceof MoveRegular) mostRecentPawnMove = (MoveRegular)pawnMove;
                if (gameMove instanceof MoveRegular) mostRecentGameMove = (MoveRegular)gameMove;
                
                if ((mostRecentPawnMove != null) && (mostRecentGameMove != null) && (mostRecentPawnMove == mostRecentGameMove))
                {
                    Location toLocation = mostRecentPawnMove.getToLocation();
                    Location fromLocation = mostRecentPawnMove.getFromLocation();
                    int toLocationIdx = Location.getRow(toLocation);
                    int fromLocationIdx = Location.getRow(fromLocation);

                    if ((pawnToLeft.history.getSize() == 1) && ((fromLocationIdx - toLocationIdx) == 2))
                    {
                        Location newFromLocation = Location.copyOf(location);
                        Location newToLocation = Location.upLeft(newFromLocation);
                        newMove = validateEnPassantMove(newFromLocation,newToLocation,boardState);
                        if (newMove != null) validMoves.add(newMove);
                    }
                }
            }
            
            if ((pawnToRight != null) && (moveHistory != null))
            {
                MoveRegular mostRecentPawnMove = null;
                MoveRegular mostRecentGameMove = null;
                Move pawnMove = pawnToRight.history.getLast();
                Move gameMove = moveHistory.getLast();
                
                if (pawnMove instanceof MoveRegular) mostRecentPawnMove = (MoveRegular)pawnMove;
                if (gameMove instanceof MoveRegular) mostRecentGameMove = (MoveRegular)gameMove;
                
                if ((mostRecentPawnMove != null) && (mostRecentGameMove != null) && (mostRecentPawnMove == mostRecentGameMove))
                {
                    Location toLocation = mostRecentPawnMove.getToLocation();
                    Location fromLocation = mostRecentPawnMove.getFromLocation();
                    int toLocationIdx = Location.getRow(toLocation);
                    int fromLocationIdx = Location.getRow(fromLocation);

                    if ((pawnToRight.history.getSize() == 1) && ((fromLocationIdx - toLocationIdx) == 2))
                    {
                        Location newFromLocation = Location.copyOf(location);
                        Location newToLocation = Location.upRight(newFromLocation);
                        newMove = validateEnPassantMove(newFromLocation,newToLocation,boardState);
                        if (newMove != null) validMoves.add(newMove);
                    }
                }
            }
        }
        else // Properties.getColorDirection(pieceColor) == DOWN
        {
            if ((pawnToLeft != null) && (moveHistory != null))
            {
                MoveRegular mostRecentPawnMove = null;
                MoveRegular mostRecentGameMove = null;
                Move pawnMove = pawnToLeft.history.getLast();
                Move gameMove = moveHistory.getLast();
                
                if (pawnMove instanceof MoveRegular) mostRecentPawnMove = (MoveRegular)pawnMove;
                if (gameMove instanceof MoveRegular) mostRecentGameMove = (MoveRegular)gameMove;
                
                if ((mostRecentPawnMove != null) && (mostRecentGameMove != null) && (mostRecentPawnMove == mostRecentGameMove))
                {
                    Location toLocation = mostRecentPawnMove.getToLocation();
                    Location fromLocation = mostRecentPawnMove.getFromLocation();
                    int toLocationIdx = Location.getRow(toLocation);
                    int fromLocationIdx = Location.getRow(fromLocation);

                    if ((pawnToLeft.history.getSize() == 1) && ((toLocationIdx - fromLocationIdx) == 2))
                    {
                        Location newFromLocation = Location.copyOf(location);
                        Location newToLocation = Location.downLeft(newFromLocation);
                        newMove = validateEnPassantMove(newFromLocation,newToLocation,boardState);
                        if (newMove != null) validMoves.add(newMove);
                    }
                }
            }
            
            if ((pawnToRight != null) && (moveHistory != null))
            {
                MoveRegular mostRecentPawnMove = null;
                MoveRegular mostRecentGameMove = null;
                Move pawnMove = pawnToRight.history.getLast();
                Move gameMove = moveHistory.getLast();
                
                if (pawnMove instanceof MoveRegular) mostRecentPawnMove = (MoveRegular)pawnMove;
                if (gameMove instanceof MoveRegular) mostRecentGameMove = (MoveRegular)gameMove;
                
                if ((mostRecentPawnMove != null) && (mostRecentGameMove != null) && (mostRecentPawnMove == mostRecentGameMove))
                {
                    Location toLocation = mostRecentPawnMove.getToLocation();
                    Location fromLocation = mostRecentPawnMove.getFromLocation();
                    int toLocationIdx = Location.getRow(toLocation);
                    int fromLocationIdx = Location.getRow(fromLocation);

                    if ((pawnToRight.history.getSize() == 1) && ((toLocationIdx - fromLocationIdx) == 2))
                    {
                        Location newFromLocation = Location.copyOf(location);
                        Location newToLocation = Location.downRight(newFromLocation);
                        newMove = validateEnPassantMove(newFromLocation,newToLocation,boardState);
                        if (newMove != null) validMoves.add(newMove);
                    }
                }
            } 
        }

        return validMoves;
    }
        
    
    /**
     * returns a deep-copy of this Piece, useful if class variables are added later
     * @return newly created copy of this Pawn
     */
    @Override
    public Piece getCopy()
    {
        Piece newPiece = PiecePawn.create(getColor());
        newPiece.history = MoveHistory.createFrom(history);
        return newPiece;
    }
    
    /**
     * factory-style create of a Pawn
     * @param color black, or white
     * @return newly-created Pawn object
     */
    public static Piece create(PlayerColor color)
    {
        return new PiecePawn(color);
    }
}
