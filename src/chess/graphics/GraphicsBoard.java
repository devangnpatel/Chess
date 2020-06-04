package chess.graphics;

import chess.board.BoardState;
import chess.game.Game;
import chess.game.GameProperties;
import chess.pieces.Piece;
import chess.utility.Location;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author devang
 */
public class GraphicsBoard extends Canvas {
    private Frame             frame;
    private GraphicsSpace[][] spaces;
    private Map<Location,GraphicsSpace> spaceMap;
    private Location          hoveredSpace;
    private Location          selectedSpace;
    private Set<Location>     highlightedSpaces;
    
    public void setHoveredSpace(Location location)
    {
        if (location == null) return;
        if (hoveredSpace != null)
            spaceMap.get(hoveredSpace).setHovered(false);
        hoveredSpace = location;
        spaceMap.get(hoveredSpace).setHovered(true);
    }
    
    public Location getHoveredSpace()
    {
        return hoveredSpace;
    }
    
    public void clearHoveredSpace()
    {
        if (hoveredSpace != null)
            spaceMap.get(hoveredSpace).setHovered(false);
        hoveredSpace = null;
    }
    
    public void setSelectedSpace(Location location)
    {
        if (location == null) return;
        if (selectedSpace != null)
            spaceMap.get(selectedSpace).setSelected(false);
        selectedSpace = location;
        spaceMap.get(selectedSpace).setSelected(true);
    }
    
    public Location getSelectedSpace()
    {
        return selectedSpace;
    }
    
    public void clearSelectedSpace()
    {
        if (selectedSpace != null)
            spaceMap.get(selectedSpace).setSelected(false);
        selectedSpace = null;
    }
    
    public boolean isHovered(Location location)
    {
        if (location == null) return false;
        if (hoveredSpace == null) return false;
        return (hoveredSpace.equals(location));
    }
    
    public boolean isSelected(Location location)
    {
        if (location == null) return false;
        if (selectedSpace == null) return false;
        return (selectedSpace.equals(location));
    }
    
    public boolean isHighlighted(Location location)
    {
        if (location == null) return false;
        if (highlightedSpaces == null) return false;
        return highlightedSpaces.contains(location);
    }
    
    public void setHighlighted(Location location)
    {
        if (location == null) return;
        if (spaceMap == null) return;
        if (highlightedSpaces == null) return;
        spaceMap.get(location).setHighlighted(true);
        highlightedSpaces.add(location);
    }
    
    public void clearHighlighted()
    {
        if (spaceMap == null) return;
        for (GraphicsSpace space : spaceMap.values())
            space.setHighlighted(false);
        highlightedSpaces.clear();
    }
    
    public GraphicsBoard()
    {
        super();
        spaceMap = new HashMap<>();
        highlightedSpaces = new HashSet<>();
        
        int numCols = GameProperties.getNumCols();
        int numRows = GameProperties.getNumRows();
        spaces = new GraphicsSpace[numCols][numRows];
        
        for (int col = 0; col < numCols; col++)
        {
            for (int row = 0; row < numRows; row++)
            {
                Color spaceColor;
                if ((((col % 2) == 0) && ((row % 2) == 0)) || (((col % 2) == 1) && ((row % 2) == 1)))
                    spaceColor = GameProperties.getColor("light_space");
                else // ((((w % 2) == 1) && ((h % 2) == 0)) || (((w % 2) == 1) && ((h % 2) == 0)))
                    spaceColor = GameProperties.getColor("dark_space");
                Location location = Location.of(col,row);
                GraphicsSpace space = new GraphicsSpace(location,spaceColor);
                spaces[col][row] = space;
                spaceMap.put(location,space);
            }
        }
        
        
        
        frame = new Frame("chess");
        frame.setIgnoreRepaint(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                frame.dispose();
            }
        });
        int xLocation = 50 + (int)Math.floor(Math.random()*600);
        int yLocation = 100 + (int)Math.floor(Math.random()*500);
        frame.setLocation(xLocation,yLocation);
        frame.setResizable(false);
        frame.setVisible(false);
        
        //setHoveredSpace(Location.of(5,5));
        //setSelectedSpace(Location.of(5,5));
        //clearHighlighted();
        
        int width = GameProperties.getBoardWidth();
        int height = GameProperties.getBoardHeight();
        setSize(width,height);
        
        setVisible(true);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);

    }
    /**
     * gets the Space that contains the input (x,y) coordinate
     * @param x input x of coordinate
     * @param y_c input y of coordinate
     * @return GraphicsSpace that contains the x,y coordinate
     */
    public Location getLocationOfSpaceAt(int x, int y_c)
    {
        int y = GameProperties.getBoardHeight() - y_c;
        int boardMargin = 0;
        for (int c=0;c<GameProperties.getNumCols();c++)
        {
            for (int r=0;r<GameProperties.getNumRows();r++)
            {
                Location location = Location.of(c,r);
                int spaceX = c; //GameProperties.getSpaceWidth()*c;
                int spaceY = r; //GameProperties.getSpaceHeight()*r;
                int spaceWidth = GameProperties.getSpaceWidth();
                int spaceHeight = GameProperties.getSpaceHeight();
                if ((boardMargin + (spaceX*spaceWidth))     < x &&
                    (boardMargin + (spaceX+1)*spaceWidth)   > x &&
                    (boardMargin + (spaceY*spaceHeight))    < y &&
                    (boardMargin + ((spaceY+1)*spaceHeight) > y))
                {
                    return location;
                }
            }
        }
        return null;
    }
    
    /**
     * destroys this canvas and frame so a game can officially be terminated
     */
    public void dispose()
    {
        spaces        = null;
        hoveredSpace  = null;
        selectedSpace = null;

        frame.dispose();
    }
    
    /**
     * paints this board, its spaces and the contents of the space to this canvas
     * @param g graphics object on which to paint this space
     */
    @Override
    public void paint(Graphics g)
    {
        BoardState boardState = Game.getBoardState();
        
        for (int c = 0; c < GameProperties.getNumCols(); c++)
        {
            for (int r = 0; r < GameProperties.getNumRows(); r++)
            {
                Location location = Location.of(c,r);
                GraphicsSpace space = spaces[c][r];
                space.paint(g);
                if (!boardState.isEmpty(location))
                {
                    Piece piece = boardState.getPiece(location);
                    GraphicsPiece graphicsPiece = GraphicsPiece.get(piece,location);
                    graphicsPiece.paint(g);
                }
            }
        }
    }    
    
}
