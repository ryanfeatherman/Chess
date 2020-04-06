package java;

import java.util.Set;

public class Board {

    private static final int SIZE = 8;

    // Square[row][column]
    private Piece[][] squares;
    private Set<Piece> whitePieces;
    private Set<Piece> blackPieces;

    public Board() {
        squares = new Piece[SIZE][SIZE];
    }

    public void add(Piece p) {
        if (get(p.location()) != null) {
            throw new IllegalArgumentException();
        }
        if (p.color() == Color.WHITE) {
            whitePieces.add(p);
        }
        else {
            blackPieces.add(p);
        }
        squares[p.location().y][p.location().x] = p;
    }

    public Piece get(Square s) {
        return squares[s.y][s.x];
    }

    public boolean contains(Piece p) {
        return whitePieces.contains(p) || blackPieces.contains(p);
    }

}