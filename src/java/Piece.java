package java;

import java.util.Set;

public abstract class Piece {
    
    private Board board;
    private Square location;
    private Color color;

    public Piece(Square s, Color c, Board b) {
        location = s;
        board = b;
        color = c;
    }

    public Piece(int x, int y, Color c, Board b) {
        this(new Square(x, y), c, b);
    }    

    public Square location() {
        return location;
    }

    public void setLocation(Square s) {
        location = s;
    }

    public Color color() {
        return color;
    }

    public abstract Set<Square> getMoves();
}