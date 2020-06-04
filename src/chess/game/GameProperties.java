package chess.game;

import java.awt.Color;
import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.GREEN;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.ORANGE;
import static java.awt.Color.YELLOW;

/**
 * Properties of a Chess Game Using Singleton Pattern
 * 
 * avoids multiple instantiations with conflicting game attributes, 
 * allows efficient access to game attribute variables, 
 * enables future extensions of game to modify attributes at
 * compile-time, or even run-time
 * 
 * @author devang
 */
public class GameProperties {
    private static GameProperties properties = new GameProperties();
    private int NUM_COLS = 8;
    private int NUM_ROWS = 8;
    private int BOARD_WIDTH = 600;
    private int BOARD_HEIGHT = 600;
    
    private Color LIGHT_SPACE_COLOR               = new Color(188,185,83);
    private Color DARK_SPACE_COLOR                = new Color(101,147,90);
    private Color HIGHLIGHTED_SPACE_COLOR         = YELLOW.darker().darker();
    private Color HIGHLIGHTED_SPACE_BORDER_COLOR  = DARK_GRAY;
    private Color HOVERED_OVER_SPACE_COLOR        = new Color(145,245,145,125);
    private Color HOVERED_OVER_SPACE_BORDER_COLOR = new Color(100,200,100,175);
    private Color SELECTED_SPACE_COLOR            = ORANGE.darker();
    private Color SELECTED_SPACE_BORDER_COLOR     = LIGHT_GRAY;

    public static Color getColor(String colorItem)
    {
        switch (colorItem) {
            case "light_space":
                return properties.LIGHT_SPACE_COLOR;
            case "dark_space":
                return properties.DARK_SPACE_COLOR;
            case "highlighted_space":
                return properties.HIGHLIGHTED_SPACE_COLOR;
            case "highlighted_space_border":
                return properties.HIGHLIGHTED_SPACE_BORDER_COLOR;
            case "hovered_space":
                return properties.HOVERED_OVER_SPACE_COLOR;
            case "hovered_space_border":
                return properties.HOVERED_OVER_SPACE_BORDER_COLOR;
            case "selected_space":
                return properties.SELECTED_SPACE_COLOR;
            case "selected_space_border_color":
                return properties.SELECTED_SPACE_BORDER_COLOR;
        }
        return GREEN;
    }
    
    public enum PlayerColor {
        WHITE,
        BLACK
    }
    
    public enum Direction {
        UP,
        DOWN
    }
    
    public static PlayerColor getOpponentColor(PlayerColor color)
    {
        if (color == PlayerColor.WHITE) return PlayerColor.BLACK;
        return PlayerColor.WHITE;
    }
    
    public static Direction getColorDirection(PlayerColor color)
    {
        if (color == PlayerColor.WHITE) return Direction.UP;
        return Direction.DOWN;
    }
    
    /**
     * Gets the number of columns in the game board (usually 8)
     * @return number of columns for game
     */
    public static int getNumCols()
    {
        return properties.NUM_COLS;
    }
    
    /**
     * Gets the number of rows on the game board (usually 8)
     * @return number of rows for game
     */
    public static int getNumRows()
    {
        return properties.NUM_ROWS;
    }
    
    /**
     * Gets the pixel width of the Board
     * @return pixels for width of board
     */
    public static int getBoardWidth()
    {
        return properties.BOARD_WIDTH;
    }
    
    /**
     * Gets the pixel height of the Board
     * @return pixels for height of board
     */
    public static int getBoardHeight()
    {
        return properties.BOARD_HEIGHT;
    }
    
    /**
     * Gets the pixel width of one space on the Board
     * @return pixels for width of a space
     */
    public static int getSpaceWidth()
    {
        return properties.BOARD_WIDTH / properties.NUM_COLS;
    }
    
    /**
     * Gets the pixel height of one space on the Board
     * @return pixels for height of a space
     */
    public static int getSpaceHeight()
    {
        return properties.BOARD_HEIGHT / properties.NUM_COLS;
    }
    
    /**
     * Gets the static instance of a GameProperties object
     * @return GameProperties object
     */
    public static GameProperties getInstance()
    {
        return properties;
    }
    
    private GameProperties()
    {
        
    }
    
}
