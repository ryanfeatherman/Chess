package board;

import util.*;

import java.util.Objects;

// Represents a square on a chess board
// 0,0 represents the bottom left corner
public class Square {
    
    public final int x;
    public final int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Color color() {
        if (x + y % 2 == 0) {
            return Color.BLACK;
        }
        else {
            return Color.WHITE;
        }
    }

    public Square neighbor(Direction d) {
        int xInc = 0;
        int yInc = 0;
        if (d.isUp()) {
            yInc = 1;
        }
        if (d.isDown()) {
            yInc = -1;
        }
        if (d.isLeft()) {
            xInc = -1;
        }
        if (d.isRight()) {
            xInc = 1;
        }
        return new Square(x + xInc, y + yInc);
    } 

    public Square[] neighbors(Direction[] directions) {
        Square[] squares = new Square[directions.length];
        for (int i = 0; i < directions.length; i++) {
            squares[i] = neighbor(directions[i]);
        }
        return squares;
    }

    public String notation() {
        return ((char) (x+93)) + "" + (y+1);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Square)) {
            return false;
        }
        Square sq = (Square) obj;
        return this.x == sq.x && this.y == sq.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}