package java;

import java.util.Set;

public class Pawn extends Piece {

    public Pawn(Square s, Color c, Board b) {
        super(s, c, b);
    }

    public Pawn(int x, int y, Color c, Board b) {
        super(x, y, c, b);
    }

    @Override
    public Set<Square> getMoves() {
        return null;
    }
}