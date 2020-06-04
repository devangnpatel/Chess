package chess.books;

import chess.moves.Move;

/**
 * MoveNode a Node on a tree of Moves, containing a set of next Moves for each
 * node, allowing extensibility for scoring a node, parsing through tree in most
 * efficient manner, and integrating with a HashMap if future needs find that useful.
 * @author devang
 */
public class MoveNode {
    private final Move move;
    private final MoveNodeSet nextNodes;
    
    /**
     * constructor for a new MoveNode
     * @param move Move object to set at this Node
     */
    public MoveNode(Move move)
    {
        this.move = move;
        nextNodes = new MoveNodeSet();
    }
    
    /**
     * Creates and returns a deep-copy of this Node, and iterates leaf nodes
     * @return deep-copy of the tree starting at this node
     */
    public MoveNode getCopy()
    {
        MoveNode newNode = new MoveNode(move.getCopy());
        for (MoveNode nextNode : nextNodes.asArray())
            newNode.addNode(nextNode.getCopy());
        return newNode;
    }
    
    /**
     * returns the Move at this Node, that should never be null
     * @return Move at this node
     */
    public Move getMove()
    {
        return move;
    }
    
    /**
     * adds a MoveNode object to the set of next nodes at this node,
     * but does nothing if null
     * @param moveNode MoveNode to add to this set
     */
    public void addNode(MoveNode moveNode)
    {
        if (moveNode == null) return;
        nextNodes.add(moveNode);
    }
    
    /**
     * adds a new Move object to the set, which creates a MoveNode object, first, 
     * but does nothing if Move is null
     * @param move to add as a leaf at this node
     */
    public void addMove(Move move)
    {
        if (move == null) return;
        nextNodes.add(move);
    }
    
    /**
     * gets a set of the next leaf nodes, as an array, though, order is irrelevant
     * @return array of MoveNode objects
     */
    public MoveNode[] getNext()
    {
        return nextNodes.asArray();
    }
}
