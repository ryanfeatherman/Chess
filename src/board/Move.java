package board;

import pieces.Piece;

public class Move {

    private Square origin;
    private Square dest;
    private Piece replaced;
    private String moveString;

    public Move(Square origin, Square dest, Piece replaced, String moveString) {
        this.origin = origin;
        this.dest = dest;
        this.replaced = replaced;
        this.moveString = moveString;
    }
}
