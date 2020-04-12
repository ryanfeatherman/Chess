package pieces;

import board.Board;
import board.Square;
import util.Color;
import util.Direction;

import java.util.HashSet;
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
        Set<Square> moves = new HashSet<>();
        for (Direction d: Direction.diagonalValues()) {
            Square s = location;
            s = s.neighbor(d);
            while (board.inBounds(s) && board.get(s) == null) {
                if ((board.causesCheck(location, s)).isEmpty()) {
                    moves.add(s);
                }
                s = s.neighbor(d);
            }
            if (board.inBounds(s)) {
                Piece p = board.get(s);
                if (p.color() != color && !board.causesCheck(location, s).isEmpty()) {
                    moves.add(s);
                }
            }
        }
        return moves;
    }

    @Override
    public String notation() {
        return "B";
    }
}