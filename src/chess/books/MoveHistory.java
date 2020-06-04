package chess.books;

import chess.moves.Move;

/**
 * MoveHistory to encapsulate a MoveList, and used for a currently-playing game,
 * copy-constructor option, access to a deep-copy, initialize an online move list
 * 
 * allows reading a history from file, writing a history to a file
 * 
 * @author devang
 */
public class MoveHistory {

    private MoveList moveList;
    
    private MoveHistory()
    {
        moveList = new MoveList();
    }
    
    /**
     * isEmpty if number of moves in list equals 0
     * @return true if list is empty, false otherwise
     */
    public boolean isEmpty()
    {
        if (moveList.getSize() == 0) return true;
        return false;
    }
    
    /**
     * gets the number of moves in list
     * @return number of moves
     */
    public int getSize()
    {
        return moveList.getSize();
    }
    
    /**
     * gets the sequence of moves, as an array
     * @return Move[] of moves, in order
     */
    public Move[] asArray()
    {
        return moveList.asArray();
    }
    
    /**
     * appends a Move to this Move History, but does nothing if move is null
     * @param move Move to add to the end of the list of Moves
     */
    public void add(Move move)
    {
        moveList.add(move);
    }
    
    /**
     * returns the last Move in this Move History
     * @return Move object at the end of this list, null if list is empty
     */
    public Move getLast()
    {
        return moveList.getLast();
    }
    
    /**
     * removes and returns the last Move in this Move History
     * @return Move object at the end of this list, null if list is empty
     */
    public Move removeLast()
    {
        return moveList.removeLast();
    }
    
    public void writeToFile()
    {
        
    }
    
    public void loadFromFile()
    {
        
    }
    
    /**
     * Deep-Copy of this MoveHistory
     * @return newly-created copy of this MoveHistory
     */
    private MoveHistory getCopy()
    {
        MoveHistory newHistory = new MoveHistory();
        newHistory.moveList = moveList.getCopy();
        return newHistory;
    }
    
    /**
     * Static Factory function to create a new MoveHistory
     * @return newly-created, and empty, MoveHistory
     */
    public static MoveHistory createNew()
    {
        return new MoveHistory();
    }
    
    /**
     * Static Factory function to create a new MoveHistory from another MoveHistory
     * @param from MoveHistory to copy
     * @return newly-created copy of the specified MoveHistory
     */
    public static MoveHistory createFrom(MoveHistory from)
    {
        return from.getCopy();
    }

}
