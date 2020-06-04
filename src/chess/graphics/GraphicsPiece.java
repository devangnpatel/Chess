package chess.graphics;

import chess.game.GameProperties;
import static chess.game.GameProperties.PlayerColor.BLACK;
import static chess.game.GameProperties.PlayerColor.WHITE;
import chess.pieces.*;
import chess.utility.Location;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author devang
 */
public class GraphicsPiece {
    
    private Location      location;
    private final Piece         piece;
    private final BufferedImage image;
    
    private int x;
    private int y;
    private final int spaceWidth;
    private final int spaceHeight;
    private final int boardWidth;
    private final int boardHeight;
    private final int numCols;
    private final int numRows;
    
    public static GraphicsPiece get(Piece piece, Location location)
    {
        return new GraphicsPiece(piece,location);
    }
    
    private GraphicsPiece(Piece piece,Location location)
    {
        this.piece = piece;
        image = getImage(piece);
        this.location = location;
        spaceWidth = GameProperties.getSpaceWidth();
        spaceHeight = GameProperties.getSpaceHeight();
        boardWidth = GameProperties.getBoardWidth();
        boardHeight = GameProperties.getBoardHeight();
        numCols = GameProperties.getNumCols();
        numRows = GameProperties.getNumRows();
        x = Location.getCol(location);
        y = numRows - 1 - Location.getRow(location);
    }
    
    public void setLocation(Location location)
    {
        this.location = location;
                
        x = Location.getCol(location);
        y = numRows - 1 - Location.getRow(location);
    }
    
    /**
     * paints this Chess Piece (real image) to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    public void paint(Graphics g)
    {
        g.drawImage(image,x*spaceWidth,y*spaceHeight,null);
    }
    
    private BufferedImage getImage(Piece piece)
    {
        String pieceColor = "";
        if (piece.getColor() == WHITE) pieceColor = "white";
        if (piece.getColor() == BLACK) pieceColor = "black";
        BufferedImage img = null;
        if (piece instanceof PieceBishop)
            img = GraphicsSprites.getPieceImage("bishop",pieceColor);
        if (piece instanceof PieceKing)
            img = GraphicsSprites.getPieceImage("king",pieceColor);
        if (piece instanceof PieceKnight)
            img = GraphicsSprites.getPieceImage("knight",pieceColor);
        if (piece instanceof PiecePawn)
            img = GraphicsSprites.getPieceImage("pawn",pieceColor);
        if (piece instanceof PieceQueen)
            img = GraphicsSprites.getPieceImage("queen",pieceColor);
        if (piece instanceof PieceRook)
            img = GraphicsSprites.getPieceImage("rook",pieceColor);
        return img;
    }
}
