package chess.utility;

import chess.game.GameProperties;
import java.util.ArrayList;
import java.util.List;

/**
 * Location rank,file on Chess Game Board, using Factory Pattern
 * 
 * initialized with String pairs "a" - "h" and "1" - "8" 
 * internally handled with 0-based indices, 0 - 7
 * @author devang
 */
public class Location {

    public final int rank;
    public final int file;
    
    private Location(int f, int r)
    {
        rank = r;
        file = f;
    }
    public static int getCol(Location location)
    {
        if (location == null) return -1;
        return location.file;
    }
    
    public static int getRow(Location location)
    {
        if (location == null) return -1;
        return location.rank;
    }
    
    public static boolean isEndRow(Location location)
    {
        if (location == null) return false;
        if (location.rank == 0) return true;
        if (location.file == 7) return true;
        return false;
    }
    
    public static String getRank(Location location)
    {
        if (location == null) return null;
        return String.valueOf(location.rank + 1);
    }
    
    public static String getFile(Location location)
    {
        if (location == null) return null;
        return String.valueOf("abcdefgh".charAt(location.file));
    }
    
    public static boolean isValid(int col,int row)
    {
        if ((col < 0) || (col >= 8))
        {
            return false;
        }
        
        if ((row < 0) || (row >= 8))
        {
            return false;
        }
        
        return true;
    }
    
    public static boolean isValid(Location location)
    {
        if (location == null) return false;
        return isValid(location.file,location.rank);
    }
    
    public static Location upLeftX(Location location, int x)
    {
        if (location == null) return null;
        return Location.of(location.file-x,location.rank+x);
    }
    
    public static Location upRightX(Location location, int x)
    {
        if (location == null) return null;
        return Location.of(location.file+x,location.rank+x);
    }
    
    public static Location downLeftX(Location location, int x)
    {
        if (location == null) return null;
        return Location.of(location.file-x,location.rank-x);
    }
    
    public static Location downRightX(Location location, int x)
    {
        if (location == null) return null;
        return Location.of(location.file+x,location.rank-x);
    }
    
    public static Location upX(Location location, int x)
    {
        if (location == null) return null;
        return Location.of(location.file,location.rank+x);
    }
    
    public static Location downX(Location location, int x)
    {
        if (location == null) return null;
        return Location.of(location.file,location.rank-x);
    }
        
    public static Location leftX(Location location, int x)
    {
        if (location == null) return null;
        return Location.of(location.file-x,location.rank);
    }
            
    public static Location rightX(Location location, int x)
    {
        if (location == null) return null;
        return Location.of(location.file+x,location.rank);
    }
    
    public static Location upLeft(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file-1,location.rank+1);
    }
    
    public static Location upLeft2(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file-2,location.rank+2);
    }
    
    public static Location upRight(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file+1,location.rank+1);
    }
    
    public static Location upRight2(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file+2,location.rank+2);
    }
    
    public static Location downLeft(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file-1,location.rank-1);
    }
    
    public static Location downLeft2(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file-2,location.rank-2);
    }
    
    public static Location downRight(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file+1,location.rank-1);
    }
    
    public static Location downRight2(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file+2,location.rank-2);
    }
    
    public static Location right(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file+1,location.rank);
    }
    
    public static Location left(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file-1,location.rank);
    }
    
    public static Location down(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file,location.rank-1);
    }
    
    public static Location up(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file,location.rank+1);
    }

    public static Location up2(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file,location.rank+2);
    }

    public static Location down2(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file,location.rank-2);
    }

    public static Location left2(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file-2,location.rank);
    }

    public static Location right2(Location location)
    {
        if (location == null) return null;
        return Location.of(location.file+2,location.rank);
    }
    
    public static List<Location> allLocations()
    {
        List<Location> locationsList = new ArrayList<>();
        for (int c = 0; c < 8; c++)
        {
            for (int r = 0; r < 8; r++)
            {
                Location location = Location.of(c,r);
                if (location != null) locationsList.add(location);
            }
        }
        return locationsList;
    }

    
    
    /**
     * returns a newly-created Location, copy of the specified Location
     * @param location Location to copy
     * @return Location at the same rank,file
     */
    public static Location copyOf(Location location)
    {
        if (location == null) return null;
        return new Location(location.file,location.rank);
    }
    
    /**
     * Creates and returns a new Location object at specified rank,file pair
     * input parameters are lowercase strings
     * @param f "a","b","c","d","e","f","g","h"
     * @param r "1","2","3","4","5","6","7","8"
     * @return new Location object  -or-  null if either parameter, r or f, are invalid
     */
    public static Location at(String f, String r)
    {
        int rank = getRankIndex(r);
        int file = getFileIndex(f);
        if ((rank == -1) || (file == -1)) return null;
        return new Location(file,rank);
    }
    
    /**
     * Creates and returns a new Location object at specified rank,file pair
     * input parameters are zero-based indices 0-7 mapping to a-h and 1-8
     * @param f 0,1,2,3,4,5,6,7
     * @param r 0,1,2,3,4,5,6,7
     * @return new Location object  -or-  null if either parameter, r or f, are invalid
     */
    public static Location of(int f, int r)
    {
        if ((r < 0) || (r > 7)) return null;
        if ((f < 0) || (f > 7)) return null;
        return new Location(f,r);
    }
    
    /**
     * converts String representation of rank (1,2,3,4,5,6,7,8) to
     * zero-based indexing integer (0 through 7)
     * @param r "1","2","3","4","5","6","7","8"
     * @return 0,1,2,3,4,5,6,7  -or-  -1 if input parameter, r, is invalid
     */
    private static int getRankIndex(String r)
    {
        if (r == null)       return -1;
        int rank = "12345678".indexOf(r);
        if (rank < 0)           return -1;
        if (rank > 7)           return -1;
        return rank;
    }
    
    /**
     * converts String representation of file (a,b,c,d,e,f,g,h) to
     * zero-based indexing integer (0 through 7)
     * @param f "a","b","c","d","e","f","g","h"
     * @return 0,1,2,3,4,5,6,7   -or-   -1 if input parameter, f, is invalid
     */
    private static int getFileIndex(String f)
    {
        if (f == null)       return -1;
        int file = "abcdefgh".indexOf(f);
        if (f.length() != 1) return -1;
        if (file < 0)        return -1;
        if (file > 7)        return -1;
        return file;
    }
    
    /**
     * Used for HashMaps with Location as a Key, where
     * Key is based on Rank and File indices
     * @param obj to compare with this Location
     * @return true if the obj is Equivalent to this Location
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true;
        if (!(obj instanceof Location)) return false;
        Location location = (Location)obj;
        return ((location.file == file) && (location.rank == rank));
    }

    /**
     * Used for HashMaps with Location as a Key, where
     * Key is based on Rank and File indices
     * @return integer hashCode used for Key
     */
    @Override
    public int hashCode() {
        int x = GameProperties.getNumCols();
        int y = GameProperties.getNumRows();
        int result = x*y + 1;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }
    
}
