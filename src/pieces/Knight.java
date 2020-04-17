package pieces;

import board.Board;
import board.Square;
import util.Color;

import java.util.HashSet;
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
        Set<Square> moves = new HashSet<>();
        Square[] knightSquares = {new Square(location.x - 2, location.y - 1),
                                  new Square(location.x - 2, location.y + 1),
                                  new Square(location.x - 1, location.y - 2),
                                  new Square(location.x - 1, location.y + 2),
                                  new Square(location.x + 1, location.y - 2),
                                  new Square(location.x + 1, location.y + 2),
                                  new Square(location.x + 2, location.y - 1),
                                  new Square(location.x + 2, location.y + 1)};
        for (Square s: knightSquares) {
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
        return "N";
    }

    @Override
    public char textSymbol() {
        if (color == Color.WHITE) return '\u2658';
        else return '\u265E';
    }
}