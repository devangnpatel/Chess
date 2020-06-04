package chess.game;

import chess.board.BoardState;
import chess.game.GameProperties.PlayerColor;
import chess.moves.Move;
import chess.moves.MovePromotion;
import chess.pieces.Piece;
import chess.players.Player;
import chess.utility.Location;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author devang
 */
public class GameManager {
            
    public GameManager()
    {
        
    }
    /**
     * sent from a Player (indicated in argument) to apply the move to the board state<br>
     * - performs the move<br>
     * - persists the move to the opponent's game state representation<br>
     * - updates the change to whoever has the current move in this board state<br>
     * - checks for end of game
     * 
     * @param player the Player making the move
     * @param move the Move to make
     */
    public void commitMove(Player player, Move move)
    {
        BoardState currentBoardState = Game.getBoardState();
        PlayerManager playerManager = Game.getPlayerManager();
        Player currentPlayer = playerManager.getActivePlayer();
        
        if (player != currentPlayer)      return;
        if (move == null)                 return;
        
        if (move instanceof MovePromotion)
        {
            MovePromotion.PieceType pieceType = pawnPromotionMenu();
            ((MovePromotion)move).setNewPieceType(pieceType);
        }
        
        // move.commitMove(currentBoardState);
        Game.commitMove(move);

        playerManager.togglePlayer();

        // persistMove(player,move);
        
        if (checkGameOver())
        {
            if (gameOverWindow()) terminate();
        }
    }
        
    protected void terminate()
    {
        PlayerManager playerManager = Game.getPlayerManager();
        Player[] players = playerManager.getPlayers();
        Player currentPlayer = playerManager.getActivePlayer();
        
        for (Player player : players)
        {
            if (player != null) player.terminate();
        }
        if (currentPlayer != null)
        {
            currentPlayer.terminate();
        }
    }
    
    public MovePromotion.PieceType pawnPromotionMenu()
    {
        Object[] possibleValues = { "queen", "bishop", "knight", "rook" };
        Object selectedValue = JOptionPane.showInputDialog(null,"pawn promotion", "pawn promotion",
            JOptionPane.PLAIN_MESSAGE,null,possibleValues, possibleValues[0]);
        
        if (selectedValue.equals("queen"))
            return MovePromotion.PieceType.QUEEN;
        if (selectedValue.equals("bishop"))
            return MovePromotion.PieceType.BISHOP;
        if (selectedValue.equals("knight"))
            return MovePromotion.PieceType.KNIGHT;
        if (selectedValue.equals("rook"))
            return MovePromotion.PieceType.ROOK;
        return MovePromotion.PieceType.QUEEN;
    }
        
    
    /**
     * checks if the game is over, an then displays a message if it is<br>
     * end of game established per rules of specific game and piece locations:<br>
     * - Chess ends when a King cannot legally move to another space<br>
     * @return True if the game is over, False otherwise
     */
    public boolean checkGameOver()
    {
        boolean validMovePossible = false;

        BoardState boardState = BoardState.copy(Game.getBoardState());
        PlayerManager playerManager = Game.getPlayerManager();
        PlayerColor currentPlayerColor = playerManager.getActivePlayerColor();
        
        for (Location location : Location.allLocations())
        {
            if (!boardState.isEmpty(location))
            {
                Piece piece = boardState.getPiece(location);
                if (piece.getColor() == currentPlayerColor)
                {
                    List<Move> moves = piece.getValidMoves(location,boardState);
                    if (moves.size() > 0)
                        validMovePossible = true;
                }
            }
        }

        return !validMovePossible;
    }
    
    
    public boolean gameOverWindow()
    {
        PlayerManager playerManager = Game.getPlayerManager();
        PlayerColor currentPlayerColor = playerManager.getActivePlayerColor();
        
        PlayerColor winnerColor = GameProperties.getOpponentColor(currentPlayerColor);
        String winnerColorText = "";
        String gameOverText;
        
        gameOverText = "game over: ";
        gameOverText+= winnerColor.toString();
        gameOverText+=" wins!";
        
        JOptionPane.showMessageDialog(null,gameOverText,"game over",JOptionPane.PLAIN_MESSAGE);
        
        return true;
    }

}
