package chess.books;

import chess.moves.Move;
import java.util.ArrayList;
import java.util.List;

/**
 * MoveList an adapter patterned wrapper of a java.util.List of Move objects, to
 * allow future enhancements when parsing a list of Moves from file or from a 
 * dictionary of moves and to help manipulate trees of Move possibilities
 * when analyzing via Minimax search and alpha-beta pruning
 * 
 * @author devang
 */
public class MoveList {
    private List<Move> moves;
    
    /**
     * constructor: initializes a new java.util.list Object for List of Moves
     */
    public MoveList()
    {
        moves = new ArrayList<>();
    }
    
    /**
     * gets the number of moves in the list
     * @return number of moves in list
     */
    public int getSize()
    {
        return moves.size();
    }
    
    /**
     * gets the list of moves, as an array
     * @return Move[] array representation of the list of Moves
     */
    public Move[] asArray()
    {
        return (Move[])moves.toArray();
    }
    
    /**
     * appends a a reference the specified Move to the end of the List
     * @param move Move object to add, but does nothing if null
     */
    public void add(Move move)
    {
        if (move == null) return;
        moves.add(move);
    }
    
    /**
     * returns the last Move at the end of the List
     * @return last Move in list, null if list is empty
     */
    public Move getLast()
    {
        if (moves.isEmpty()) return null;
        return moves.get(moves.size() - 1);
    }
    
    /**
     * removes, and returns, the last Move at the end of the list, but 
     * does nothing if List is empty
     * @return last Move in list, null if list is empty
     */
    public Move removeLast()
    {
        if (moves.isEmpty()) return null;
        Move lastMove = moves.get(moves.size() - 1);
        moves.remove(moves.size() - 1);
        return lastMove;
    }
    
    /**
     * returns a deep-copy of this MoveList
     * @return newly created MoveList copy of this list
     */
    public MoveList getCopy()
    {
        MoveList newList = new MoveList();
        for (Move move : moves)
            newList.add(move.getCopy());
        return newList;
    }
}
