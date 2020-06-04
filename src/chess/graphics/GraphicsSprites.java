package chess.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Contains paths and performs File I/O for sprite images of pieces
 * @author devang
 */
public class GraphicsSprites {
    private final static GraphicsSprites sprites = new GraphicsSprites();
    private final static String GRAPHICS_FILE_ROOT = "chess2D/";
    private final HashMap<String,BufferedImage> pieceImages;
    
    public static BufferedImage getPieceImage(String pieceType,String pieceColor)
    {
        String filenameHash = sprites.getPieceFilename(pieceType,pieceColor);
        if (!sprites.pieceImages.containsKey(filenameHash))
        {
            BufferedImage tmpImage = sprites.loadPieceImage(pieceType,pieceColor);
            if (tmpImage != null)
            {
                sprites.pieceImages.put(filenameHash,tmpImage);
            }
        }
        return sprites.pieceImages.get(filenameHash);
    }
    
    private GraphicsSprites()
    {
        pieceImages = new HashMap<>();
    }
    
    private static String getPieceFilename(String pieceType, String pieceColor)
    {
        String graphicsFileName = "";
        
        graphicsFileName += GRAPHICS_FILE_ROOT;
        graphicsFileName += pieceColor + "_";
        graphicsFileName += pieceType;
        graphicsFileName += ".png";
        
        return graphicsFileName;
    }
        
    private static BufferedImage loadPieceImage(String pieceType, String pieceColor)
    {
        BufferedImage pieceImage = null;
        
        try {
            pieceImage = ImageIO.read(sprites.getClass().getClassLoader().getResource(sprites.getPieceFilename(pieceType,pieceColor)));
        } catch (IOException e) {
            System.out.println("Error loading Piece Image: " + pieceColor + " " + pieceType);
        }
        
        if (pieceImage == null) return null;
        
        return pieceImage;
    }
}
