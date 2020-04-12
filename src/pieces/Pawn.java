package pieces;

import board.Board;
import board.Square;
import util.Color;
import util.Direction;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {

    private Direction moveDirection;

    public Pawn(Square s, Color c, Board b) {
        super(s, c, b);
        if (c == Color.WHITE) {
            moveDirection = Direction.UP;
        }
        else {
            moveDirection = Direction.DOWN;
        }
    }

    public Pawn(int x, int y, Color c, Board b) {
        this(new Square(x, y), c, b);
    }

    @Override
    public Set<Square> getMoves() {
        Set<Square> moves = new HashSet<>();
        Square next = location.neighbor(moveDirection);
        if (board.inBounds(next) && board.get(next) == null) {
            if (board.causesCheck(location, next).isEmpty()) {
                moves.add(next);
            }
        }
        if ((color == Color.WHITE && location.y == 1) || (color == Color.BLACK && location.x == board.SIZE - 1)) {
            Square extra = next.neighbor(moveDirection);
            if (board.inBounds(extra) && board.get(extra) == null) {
                if (board.causesCheck(location, extra).isEmpty()) {
                    moves.add(next);
                }
            }
        }
        for (Square capture: next.neighbors(new Direction[] {Direction.LEFT, Direction.RIGHT})) {
            if (board.inBounds(capture) && board.get(capture) != null) {
                Piece take = board.get(capture);
                if (take.color() != color && board.causesCheck(location, capture).isEmpty()) {
                    moves.add(next);
                }
            }
        }
        return moves;
    }

    @Override
    public String notation() {
        return "";
    }


}