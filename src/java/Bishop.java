package java;

import java.util.Set;

public class Bishop extends Piece {

    public Bishop(Square s, Color c, Board b) {
        super(s, c, b);
    }

    public Bishop(int x, int y, Color c, Board b) {
        super(x, y, c, b);
    }

    @Override
    public Set<Square> getMoves() {
        return null;
    }
}