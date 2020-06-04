package chess.graphics;

import chess.game.GameProperties;
import chess.utility.Location;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author devang
 */
public class GraphicsSpace {
    private final Location location;
    private final Color spaceColor;
    private final Color highlightedColor;
    private final Color highlightedBorderColor;
    private final Color hoveredColor;
    private final Color hoveredBorderColor;
    private final Color selectedColor;
    private final Color selectedBorderColor;
    
    private int x;
    private int y;
    private final int spaceWidth;
    private final int spaceHeight;
    private final int boardWidth;
    private final int boardHeight;
    private final int numCols;
    private final int numRows;
    
    private boolean isHovered;
    private boolean isSelected;
    private boolean isHighlighted;
    
    /**
     * paints this Space to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    public void paint(Graphics g)
    {
        
        // paint space: background
        g.setColor(spaceColor);
        g.fillRect(x*spaceWidth, y*spaceHeight, spaceWidth, spaceHeight);
        
        // paint space: highlighted
        if (isHighlighted())
        {
            // space
            g.setColor(highlightedColor);
            g.fillRect(x*spaceWidth, y*spaceHeight, spaceWidth, spaceHeight);

            // border
            g.setColor(highlightedBorderColor);
            g.drawRect(x*spaceWidth, y*spaceHeight, spaceWidth, spaceHeight);
        }
                        
        // paint space: selected          
        if (isSelected())
        {
            // space
            g.setColor(selectedColor);
            g.fillRect(x*spaceWidth, y*spaceHeight, spaceWidth, spaceHeight);

            // border
            g.setColor(selectedBorderColor);
            g.drawRect(x*spaceWidth, y*spaceHeight, spaceWidth, spaceHeight);
        }
                
        // paint space: hovered-over
        if (isHovered())
        {
            // space
            g.setColor(hoveredColor);
            g.fillRect(x*spaceWidth, y*spaceHeight, spaceWidth, spaceHeight);

            // border
            g.setColor(hoveredBorderColor);
            g.drawRect(x*spaceWidth, y*spaceHeight, spaceWidth, spaceHeight);
        }
    }
    
    public GraphicsSpace(Location location,Color spaceColor)
    {
        this.location = location;
        this.spaceColor = spaceColor;
        
        highlightedColor = GameProperties.getColor("highlighted_space");
        highlightedBorderColor = GameProperties.getColor("highlighted_space_border");
        hoveredColor = GameProperties.getColor("hovered_space");
        hoveredBorderColor = GameProperties.getColor("hovered_space_border");
        selectedColor = GameProperties.getColor("selected_space");
        selectedBorderColor = GameProperties.getColor("selected_space_border");
    
        isHovered = false;
        isSelected = false;
        isHighlighted = false;
        spaceWidth = GameProperties.getSpaceWidth();
        spaceHeight = GameProperties.getSpaceHeight();
        boardWidth = GameProperties.getBoardWidth();
        boardHeight = GameProperties.getBoardHeight();
        numCols = GameProperties.getNumCols();
        numRows = GameProperties.getNumRows();
        x = Location.getCol(location);
        y = numRows - 1 - Location.getRow(location);
    }
    
    public boolean isHovered()
    {
        return isHovered;
    }
    
    public boolean isSelected()
    {
        return isSelected;
    }
    
    public boolean isHighlighted()
    {
        return isHighlighted;
    }
    
    public void setHovered(boolean hovered)
    {
        isHovered = hovered;
    }
    
    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }
    
    public void setHighlighted(boolean highlighted)
    {
        isHighlighted = highlighted;
    }
}
