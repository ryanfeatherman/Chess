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
        return null;
    }


}