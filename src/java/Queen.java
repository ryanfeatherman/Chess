package java;

import java.util.Set;

public class Queen extends Piece {

    public Queen(Square s, Color c, Board b) {
        super(s, c, b);
    }

    public Queen(int x, int y, Color c, Board b) {
        super(x, y, c, b);
    }

    @Override
    public Set<Square> getMoves() {
        return null;
    }
}