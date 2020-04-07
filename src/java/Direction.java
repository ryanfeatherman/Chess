package java;

public enum Direction {

    UP, DOWN, LEFT, RIGHT,
    UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;

    private static final Direction[] STRAIGHT_VALUES = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
    private static final Direction[] DIAGONAL_VALUES = {Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT};

    public static Direction[] straightValues() {
        return STRAIGHT_VALUES;
    }
    public static Direction[] diagonalValues() {
        return DIAGONAL_VALUES;
    }

    public boolean isUp() {
        return this == Direction.UP || this == Direction.UP_LEFT || this == Direction.UP_RIGHT;
    }
    public boolean isDown() {
        return this == Direction.DOWN || this == Direction.DOWN_LEFT || this == Direction.DOWN_RIGHT;

    }
    public boolean isLeft() {
        return this == Direction.LEFT || this == Direction.UP_LEFT || this == Direction.DOWN_LEFT;
    }
    public boolean isRight() {
        return this == Direction.RIGHT || this == Direction.UP_RIGHT || this == Direction.DOWN_RIGHT;
    }
}