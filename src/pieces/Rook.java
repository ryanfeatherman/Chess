package pieces;

import java.util.HashSet;
import java.util.Set;

import board.Board;
import board.Square;
import util.Color;
import util.Direction;

public class Rook extends Piece {

    public Rook(Square s, Color c, Board b) {
        super(s, c, b);
    }

    public Rook(int x, int y, Color c, Board b) {
        super(x, y, c, b);
    }

    @Override
    public Set<Square> getMoves() {
        Set<Square> moves = new HashSet<>();
        for (Direction d: Direction.straightValues()) {
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
        return "R";
    }

    @Override
    public char textSymbol() {
        if (color == Color.WHITE) return '\u2656';
        else return '\u265C';
    }
}