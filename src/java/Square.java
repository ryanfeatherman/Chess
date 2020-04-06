package java;

// Represents a square on a chess board
// 0,0 represents the top left corner

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
}