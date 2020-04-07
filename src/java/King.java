package java;

import java.util.Set;

public class King extends Piece {

    public King(Square s, Color c, Board b) {
        super(s, c, b);
    }

    public King(int x, int y, Color c, Board b) {
        super(x, y, c, b);
    }

    @Override
    public Set<Square> getMoves() {
        return null;
    }
}