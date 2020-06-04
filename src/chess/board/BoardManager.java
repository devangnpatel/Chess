package chess.board;

import chess.game.Game;
import chess.game.GameProperties;
import static chess.game.GameProperties.PlayerColor.BLACK;
import static chess.game.GameProperties.PlayerColor.WHITE;
import chess.pieces.PieceBishop;
import chess.pieces.PieceKing;
import chess.pieces.PieceKnight;
import chess.pieces.PiecePawn;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import chess.utility.Location;

/**
 *
 * @author devang
 */
public class BoardManager {
    
    
    public BoardManager()
    {

    }
    
    public void initPieces()
    {
        BoardState boardState = Game.getBoardState();
        
        boardState.setPiece(PiecePawn.create(WHITE),Location.at("a","2"));
        boardState.setPiece(PiecePawn.create(WHITE),Location.at("b","2"));
        boardState.setPiece(PiecePawn.create(WHITE),Location.at("c","2"));
        boardState.setPiece(PiecePawn.create(WHITE),Location.at("d","2"));
        boardState.setPiece(PiecePawn.create(WHITE),Location.at("e","2"));
        boardState.setPiece(PiecePawn.create(WHITE),Location.at("f","2"));
        boardState.setPiece(PiecePawn.create(WHITE),Location.at("g","2"));
        boardState.setPiece(PiecePawn.create(WHITE),Location.at("h","2"));
        
        boardState.setPiece(PieceRook.create(WHITE),Location.at("a","1"));
        boardState.setPiece(PieceKnight.create(WHITE),Location.at("b","1"));
        boardState.setPiece(PieceBishop.create(WHITE),Location.at("c","1"));
        boardState.setPiece(PieceQueen.create(WHITE),Location.at("d","1"));
        boardState.setPiece(PieceKing.create(WHITE),Location.at("e","1"));
        boardState.setPiece(PieceBishop.create(WHITE),Location.at("f","1"));
        boardState.setPiece(PieceKnight.create(WHITE),Location.at("g","1"));
        boardState.setPiece(PieceRook.create(WHITE),Location.at("h","1"));
        
        boardState.setPiece(PiecePawn.create(BLACK),Location.at("a","7"));
        boardState.setPiece(PiecePawn.create(BLACK),Location.at("b","7"));
        boardState.setPiece(PiecePawn.create(BLACK),Location.at("c","7"));
        boardState.setPiece(PiecePawn.create(BLACK),Location.at("d","7"));
        boardState.setPiece(PiecePawn.create(BLACK),Location.at("e","7"));
        boardState.setPiece(PiecePawn.create(BLACK),Location.at("f","7"));
        boardState.setPiece(PiecePawn.create(BLACK),Location.at("g","7"));
        boardState.setPiece(PiecePawn.create(BLACK),Location.at("h","7"));
        
        boardState.setPiece(PieceRook.create(BLACK),Location.at("a","8"));
        boardState.setPiece(PieceKnight.create(BLACK),Location.at("b","8"));
        boardState.setPiece(PieceBishop.create(BLACK),Location.at("c","8"));
        boardState.setPiece(PieceQueen.create(BLACK),Location.at("d","8"));
        boardState.setPiece(PieceKing.create(BLACK),Location.at("e","8"));
        boardState.setPiece(PieceBishop.create(BLACK),Location.at("f","8"));
        boardState.setPiece(PieceKnight.create(BLACK),Location.at("g","8"));
        boardState.setPiece(PieceRook.create(BLACK),Location.at("h","8"));
    }
}
