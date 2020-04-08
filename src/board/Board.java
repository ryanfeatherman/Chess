package board;

import pieces.*;
import util.Color;
import util.Direction;

import java.util.HashSet;
import java.util.Set;

public class Board {

    public final int SIZE = 8;

    // Square[row][column]
    private Piece[][] squares;
    private Set<Piece> whitePieces;
    private Set<Piece> blackPieces;
    private King whiteKing;
    private King blackKing;

    public Board() {
        squares = new Piece[SIZE][SIZE];
        whitePieces = new HashSet<>();
        blackPieces = new HashSet<>();
        whiteKing = null;
        blackKing = null;
    }

    public void add(Piece piece) {
        if (get(piece.location()) != null) {
            throw new IllegalArgumentException("Piece's square is already taken");
        }
        if (piece instanceof King) {
            if (piece.color() == Color.WHITE) {
                if (whiteKing != null) {
                    throw new IllegalArgumentException("More than one white king cannot be added");
                }
                whiteKing = (King) piece;
            }
            else {
                if (blackKing != null) {
                    throw new IllegalArgumentException("More than one black king cannot be added");
                }
                blackKing = (King) piece;
            }
        }
        if (piece.color() == Color.WHITE) {
            whitePieces.add(piece);
        }
        else {
            blackPieces.add(piece);
        }
        squares[piece.location().y][piece.location().x] = piece;
    }

    public Piece get(int x, int y) {
        return squares[y][x];
    }

    public Piece get(Square s) {
        return get(s.x, s.y);
    }

    public void set(int x, int y, Piece p) {
        squares[y][x] = p;
    }

    public void set(Square s, Piece p) {
        set(s.x, s.y, p);
    }

    public boolean contains(Piece p) {
        return whitePieces.contains(p) || blackPieces.contains(p);
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < SIZE && y>= 0 && y < SIZE;
    }

    public boolean inBounds(Square s) {
        return inBounds(s.x, s.y);
    }

    public Piece getNearestPieceInDirection(Square s, Direction d) {
        int xInc = 0;
        int yInc = 0;
        if (d.isUp()) {
            yInc = -1;
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
        int x = s.x + xInc;
        int y = s.y + yInc;
        while (inBounds(x, y)) {
            if (get(x, y) != null) {
                return get(x, y);
            }
            x += xInc;
            y += yInc;
        }
        return null;
    }

    public Set<Piece> causesCheck(Square origin, Square dest) {
        if (!inBounds(origin) || !inBounds(dest)) {
            throw new IllegalArgumentException("Sqaures must be in bounds");
        }
        Piece p = get(origin);
        if (p == null) {
            throw new IllegalArgumentException("Origin square must be occupied");
        }
        Piece replaced = get(dest);
        set(dest, p);
        set(origin, null);
        p.setLocation(dest);
        Set<Piece> result = inCheck(p.color());
        set(dest, replaced);
        set(origin, p);
        p.setLocation(origin);
        return result;
    }

    // inputted color refers to king to consider
    public Set<Piece> inCheck(Color c) {
        Set<Piece> threats = new HashSet<>();
        King king = king(c);
        Square kingSquare = king.location();
        for (Direction d: Direction.straightValues()) {
            Piece nearest = getNearestPieceInDirection(kingSquare, d);
            if ((nearest instanceof Rook || nearest instanceof Queen) && nearest.color() != c) {
                threats.add(nearest);
            }
        }
        for (Direction d: Direction.diagonalValues()) {
            Piece nearest = getNearestPieceInDirection(kingSquare, d);
            if ((nearest instanceof Bishop || nearest instanceof Queen) && nearest.color() != c) {
                threats.add(nearest);
            }
        }
        Square[] kingThreats = kingSquare.neighbors(Direction.values());
        for (Square square: kingThreats) {
            if (inBounds(square)) {
                Piece piece = get(square);
                if (piece instanceof King && piece.color() != c) {
                    threats.add(piece);
                }
            }
        }
        Square[] pawnThreats;
        if (c == Color.BLACK) {
            pawnThreats = kingSquare.neighbors(new Direction[] {Direction.DOWN_LEFT, Direction.DOWN_RIGHT});
        }
        else {
            pawnThreats = kingSquare.neighbors(new Direction[] {Direction.UP_LEFT, Direction.UP_RIGHT});
        }
        for (Square square: pawnThreats) {
            if (inBounds(square)) {
                Piece piece = get(square);
                if (piece instanceof Pawn && piece.color() != c) {
                    threats.add(piece);
                }
            }
        }
        Square[] knightThreats = {new Square(kingSquare.x - 2, kingSquare.y - 1),
                                  new Square(kingSquare.x - 2, kingSquare.y + 1),
                                  new Square(kingSquare.x - 1, kingSquare.y - 2),
                                  new Square(kingSquare.x - 1, kingSquare.y + 2),
                                  new Square(kingSquare.x + 1, kingSquare.y - 2),
                                  new Square(kingSquare.x + 1, kingSquare.y + 2),
                                  new Square(kingSquare.x + 2, kingSquare.y - 1),
                                  new Square(kingSquare.x + 2, kingSquare.y + 1)};
        for (Square square: knightThreats) {
            if (inBounds(square)) {
                Piece piece = get(square);
                if (piece instanceof Knight && piece.color() != c) {
                    threats.add(piece);
                }
            }
        }
        return threats;
    }

    public King king(Color c) {
        if (c == Color.WHITE) {
            return whiteKing;
        }
        else {
            return blackKing;
        }
    }

}