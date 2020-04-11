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
        Set<Square> moves = new HashSet<>();
        for (Square s: location.neighbors(Direction.values())) {
            if (board.inBounds(s)) {
                Piece p = board.get(s);
                if (p.color() != color && !board.causesCheck(location, s).isEmpty()) {
                    moves.add(s);
                }
            }
        }
        if (color == Color.WHITE) {
            if (board.wCastleKing() && !board.whiteInCheck()) {
                Square right = location.neighbor(Direction.RIGHT);
                Square dest = new Square(location.x + 2, location.y);
                if (board.get(right) == null && board.causesCheck(location, right).isEmpty() &&
                    board.get(dest) == null && board.causesCheck(location, dest).isEmpty()) {
                    moves.add(dest);
                }
            }
            if (board.wCastleQueen() && !board.whiteInCheck()) {
                Square left = location.neighbor(Direction.LEFT);
                Square dest = new Square(location.x - 2, location.y);
                if (board.get(left) == null && board.causesCheck(location, left).isEmpty() &&
                        board.get(dest) == null && board.causesCheck(location, dest).isEmpty()) {
                    moves.add(dest);
                }
            }
        }
        else {
            if (board.bCastleKing() && !board.blackInCheck()) {
                Square right = location.neighbor(Direction.RIGHT);
                Square dest = new Square(location.x + 2, location.y);
                if (board.get(right) == null && board.causesCheck(location, right).isEmpty() &&
                        board.get(dest) == null && board.causesCheck(location, dest).isEmpty()) {
                    moves.add(dest);
                }
            }
            if (board.bCastleQueen() && !board.blackInCheck()) {
                Square left = location.neighbor(Direction.LEFT);
                Square dest = new Square(location.x - 2, location.y);
                if (board.get(left) == null && board.causesCheck(location, left).isEmpty() &&
                        board.get(dest) == null && board.causesCheck(location, dest).isEmpty()) {
                    moves.add(dest);
                }
            }
        }
        return moves;
    }
}