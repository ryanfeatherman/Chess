package pieces;

import board.Board;
import board.Square;
import util.Color;
import util.Direction;

import java.util.HashSet;
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
        Set<Square> moves = new HashSet<Square>();
        for (Square s: location.neighbors(Direction.values())) {
            if (board.inBounds(s)) {
                Piece p = board.get(s);
                if (p.color() != color && !board.causesCheck(location, s).isEmpty()) {
                    moves.add(s);
                }
            }
        }
        return moves;
    }
}