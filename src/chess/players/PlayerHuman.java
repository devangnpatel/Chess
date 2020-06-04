package chess.players;

import chess.game.Game;
import chess.game.GameProperties.PlayerColor;
import chess.game.PlayerManager;
import chess.graphics.GraphicsBoard;
import chess.moves.Move;
import chess.pieces.Piece;
import chess.utility.Location;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Map;

/**
 *
 * @author devang
 */
public class PlayerHuman extends Player implements KeyListener, MouseListener, MouseMotionListener {
    private Map<Location,Move> possibleMoves;
    private boolean            moveInitiated;
    
    public PlayerHuman(PlayerColor color)
    {
        super(color);
        moveInitiated = false;
        possibleMoves = null;

    }
    
    public void setGui(GraphicsBoard gui)
    {
        if (gui != null)
        {
            gui.addKeyListener(this);
            gui.addMouseListener(this);
            gui.addMouseMotionListener(this);
        }
    }
    
    @Override
    public void terminate()
    {
        
    }
    
    
    private void setHighlightedSpaces(Map<Location,Move> moveLocations)
    {
        GraphicsBoard graphicsBoard = Game.getGraphicsBoard();
        graphicsBoard.clearHighlighted();
        
        for (Location location : moveLocations.keySet())
        {
            graphicsBoard.setHighlighted(location);
        }
    }
    
    private void saveMoveMap(Map<Location,Move> moveLocations)
    {
        possibleMoves = moveLocations;
    }
    
    private void eraseMoveMap()
    {
        if (possibleMoves != null)
            possibleMoves.clear();
    }
    
    private Move getMoveFromMap(Location location)
    {
        if ((possibleMoves != null) && possibleMoves.containsKey(location))
        {
            return possibleMoves.get(location);
        }
        return null;
    }

    protected void handleSpaceSelection(Location hoveredSpace, Location selectedSpace)
    {
        if (moveInitiated)
        {
            Location moveLocation = Location.copyOf(hoveredSpace);
            Move     move         = getMoveFromMap(moveLocation);
            GraphicsBoard graphicsBoard = Game.getGraphicsBoard();
        
            graphicsBoard.clearHighlighted();
            graphicsBoard.clearSelectedSpace();
            eraseMoveMap();

            commitMove(move);

            moveInitiated = false;
        }
        else if (!moveInitiated)
        {
            if (Game.getBoardState().isEmpty(hoveredSpace))
                return;

            Piece piece = Game.getBoardState().getPiece(hoveredSpace);
            if (piece.getColor() != this.getColor())
                return;

            List<Move> possibleMovesList     = getValidMoves(hoveredSpace);
            Map<Location,Move> moveLocations = getValidMoves(possibleMovesList);
            if (moveLocations.isEmpty())
                return;

            GraphicsBoard graphicsBoard = Game.getGraphicsBoard();
        
            selectedSpace = Location.copyOf(hoveredSpace);
            graphicsBoard.setSelectedSpace(selectedSpace);
            saveMoveMap(moveLocations);
            setHighlightedSpaces(moveLocations);
            moveInitiated = true;
        }
    }

    public void repaint()
    {
        GraphicsBoard graphicsBoard = Game.getGraphicsBoard();
        
        if (graphicsBoard != null) graphicsBoard.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        
        GraphicsBoard graphicsBoard = Game.getGraphicsBoard();
        PlayerManager playerManager = Game.getPlayerManager();
        Player player = playerManager.getActivePlayer();
        if (player != this) return;

        Location hoveredSpace  = Location.copyOf(graphicsBoard.getHoveredSpace());
        Location selectedSpace = Location.copyOf(graphicsBoard.getSelectedSpace());

        handleSpaceSelection(hoveredSpace,selectedSpace);
        
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GraphicsBoard graphicsBoard = Game.getGraphicsBoard();
        PlayerManager playerManager = Game.getPlayerManager();
        Player player = playerManager.getActivePlayer();
        if (player != this) return;

        Location hoveredSpace  = Location.copyOf(graphicsBoard.getHoveredSpace());
        Location selectedSpace = Location.copyOf(graphicsBoard.getSelectedSpace());
        
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        hoveredSpace = getLocationOfSpaceAt(mouseX,mouseY);
        
        if (hoveredSpace == null)
            hoveredSpace = Location.copyOf(graphicsBoard.getHoveredSpace());
        
        /* graphicsBoard.setHoveredSpace(hoveredSpace);
        repaint();*/
        
        if (!hoveredSpace.equals(graphicsBoard.getHoveredSpace()))
        {
            graphicsBoard.setHoveredSpace(hoveredSpace);
            repaint();
        }
    }
    
    public Location getLocationOfSpaceAt(int x, int y)
    {
        GraphicsBoard graphicsBoard = Game.getGraphicsBoard();
        return graphicsBoard.getLocationOfSpaceAt(x,y);
    }
    
    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    
    /**
     * Handles user input of the game board:<br>
     * - if it is not this player's turn: does nothing<br>
     * - if no space is hovered, highlighted or selected, when Enter is pressed 
     *   the hovered-over space will be evaluated for possible legal moves<br>
     * - if a space has been selected and legal moves are possible, when Enter 
     *   is pressed on a highlighted Space (indicating a legal move), that Move 
     *   will be committed to the current Game state, and then persisted to an
     *   opponent's representation of the current Game state<br>
     * - up,left,down,right moves the hovered-over space
     * 
     * @param e the KeyEvent that was triggered: looking for up,down,left,right, and enter
     */
    public void keyPressed(KeyEvent e) {
        GraphicsBoard graphicsBoard = Game.getGraphicsBoard();
        PlayerManager playerManager = Game.getPlayerManager();
        Player player = playerManager.getActivePlayer();
        if (player != this) return;
        
        Location hoveredSpace  = Location.copyOf(graphicsBoard.getHoveredSpace());
        Location selectedSpace = Location.copyOf(graphicsBoard.getSelectedSpace());
        
        switch (e.getKeyCode())
        {
            case VK_DOWN:
                hoveredSpace = Location.down(hoveredSpace);
                if (hoveredSpace == null)
                    hoveredSpace = Location.copyOf(graphicsBoard.getHoveredSpace());
                graphicsBoard.setHoveredSpace(hoveredSpace);
                break;
            case VK_UP:
                hoveredSpace = Location.up(hoveredSpace);
                if (hoveredSpace == null)
                    hoveredSpace = Location.copyOf(graphicsBoard.getHoveredSpace());
                graphicsBoard.setHoveredSpace(hoveredSpace);
                break;
            case VK_LEFT:
                hoveredSpace = Location.left(hoveredSpace);
                if (hoveredSpace == null)
                    hoveredSpace = Location.copyOf(graphicsBoard.getHoveredSpace());
                graphicsBoard.setHoveredSpace(hoveredSpace);
                break;
            case VK_RIGHT:
                hoveredSpace = Location.right(hoveredSpace);
                if (hoveredSpace == null)
                    hoveredSpace = Location.copyOf(graphicsBoard.getHoveredSpace());
                graphicsBoard.setHoveredSpace(hoveredSpace);
                break;
            case VK_ENTER:
                handleSpaceSelection(hoveredSpace,selectedSpace);
        }
        
        repaint();
    }

}
