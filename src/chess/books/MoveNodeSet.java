package chess.books;

import chess.moves.Move;
import java.util.HashSet;
import java.util.Set;

/**
 * MoveNodeSet: a Set of Move Nodes used in a tree of MovesNodes, 
 * allowing extensibility for scoring a node, parsing through tree in most
 * efficient manner, and integrating with a HashMap if future needs find that useful.
 * @author devang
 */
public class MoveNodeSet {
    Set<MoveNode> moveNodes;
    
    public MoveNodeSet()
    {
        moveNodes = new HashSet<>();
    }
    
    /**
     * gets all the elements in this set, as an array
     * @return array of moveNodes in this Set, null if empty
     */
    public MoveNode[] asArray()
    {
        if (moveNodes.isEmpty()) return null;
        return (MoveNode[])moveNodes.toArray();
    }
    
    /**
     * adds a MoveNode object to the set, but does nothing if moveNode is null
     * @param moveNode Node to add to set
     */
    public void add(MoveNode moveNode)
    {
        if (moveNode == null) return;
        moveNodes.add(moveNode);
    }
    
    /**
     * adds a MoveNode object to this set, created from the specified Move, but 
     * does nothing if move is null
     * @param move Move to add to this set, created in a new MoveNode
     */
    public void add(Move move)
    {
        if (move==null) return;
        moveNodes.add(new MoveNode(move));
    }
    
    /**
     * deep-copy of this MoveNodeSet that iterates, fully, through each Node
     * @return New MoveNodeSet, copied from this Set
     */
    public MoveNodeSet getCopy()
    {
        MoveNodeSet newSet = new MoveNodeSet();
        for (MoveNode moveNode : moveNodes)
            newSet.add(moveNode.getCopy());
        return newSet;
    }
}
