package java;

import java.util.Set;

public class Rook extends Piece {

    public Rook(Square s, Color c, Board b) {
        super(s, c, b);
    }

    public Rook(int x, int y, Color c, Board b) {
        super(x, y, c, b);
    }

    @Override
    public Set<Square> getMoves() {
        return null;
    }
}