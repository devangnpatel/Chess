package chess.utility;

/**
 * Point x,y on a Cartesian Graph using Factory Pattern
 * 
 * prevents modifying x,y values
 * in a Java Canvas in this Chess Game when handling Mouse Events in a GUI Canvas
 * @author devang
 */
public class Point {
    public final int x;
    public final int y;

    private Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Factory pattern to control access and prevent modifications to x,y pair
     * @param d_x double-value of x coordinate
     * @param d_y double-value of y coordinate
     * @return new Point at x,y pair
     */
    public static Point at(double d_x, double d_y)
    {
        int x = (int)Math.floor(d_x);
        int y = (int)Math.floor(d_y);
        
        return new Point(x,y);
    }
    
    /**
     * Factory pattern to control access and prevent modifications to x,y pair
     * @param i_x integer-value of x coordinate
     * @param i_y integer-value of y coordinate
     * @return new Point at x,y pair
     */
    public static Point at(int i_x, int i_y)
    {
        return new Point(i_x,i_y);
    }
}
