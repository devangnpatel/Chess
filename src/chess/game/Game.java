package chess.game;

import chess.board.BoardManager;
import chess.board.BoardState;
import chess.books.MoveHistory;
import chess.game.GameProperties.PlayerColor;
import chess.graphics.GraphicsBoard;
import chess.moves.Move;
import chess.moves.MovePromotion;
import chess.network.NetworkClient;
import chess.players.Player;
import javax.swing.JOptionPane;

/**
 *
 * @author devang
 */
public class Game {
    private static Game game = new Game();
    private BoardState boardState;
    private BoardManager board;
    private PlayerManager playerManager;
    private GameManager gameManager;
    private GraphicsBoard graphicsBoard;
    
    public static void startLocal2PGame()
    {
        game.board.initPieces();
        game.playerManager.initializePlayersLocalGame();
    }
    
    public static void startLocalAIGame()
    {
        game.board.initPieces();
        game.playerManager.initializePlayersAIGame();
    }
    public static void startNetworkGame(PlayerColor color, NetworkClient client)
    {
        game.board.initPieces();
        game.playerManager.initializePlayersNetworkGame(color,client);
    }    
    private Game()
    {
        boardState = new BoardState();
        playerManager = new PlayerManager();
        board = new BoardManager();
        gameManager = new GameManager();
        graphicsBoard = new GraphicsBoard();
    }
    
    public static Game getActiveGame()
    {
        return game;
    }
    
    public static GraphicsBoard getGraphicsBoard()
    {
        return game.graphicsBoard;
    }
    
    public static BoardState getBoardState()
    {
        return game.boardState;
    }
    
    public static PlayerManager getPlayerManager()
    {
        return game.playerManager;
    }
    
    public static GameManager getGameManager()
    {
        return game.gameManager;
    }
    
    public static MoveHistory getHistory()
    {
        return game.boardState.moveHistory;
    }
    
    public static void commitMove(Move move)
    {
        move.commitMove(game.boardState);
    }
    
}
