package pieces;

import board.Board;
import board.Square;
import util.Color;

import java.util.Set;

public class Knight extends Piece {


    public Knight(Square s, Color c, Board b) {
        super(s, c, b);
    }

    public Knight(int x, int y, Color c, Board b) {
        super(x, y, c, b);
    }

    @Override
    public Set<Square> getMoves() {
        return null;
    }
}