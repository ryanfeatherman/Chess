package board;

import pieces.*;
import util.Color;
import util.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

    public final int SIZE = 8;

    // Square[row][column]
    private Piece[][] squares;
    private Set<Piece> whitePieces;
    private Set<Piece> blackPieces;
    private King whiteKing;
    private King blackKing;
    //private Rook wKingRook;
    //private Rook wQueenRook;
    //private Rook bKingRook;
    //private Rook bQueenRook;
    private boolean wCastleKing;
    private boolean wCastleQueen;
    private boolean bCastleKing;
    private boolean bCastleQueen;
    private boolean whiteInCheck;
    private boolean blackInCheck;
    private List<Move> moveList;
    private int fiftyCount;

    public Board() {
        squares = new Piece[SIZE][SIZE];
        whitePieces = new HashSet<>();
        blackPieces = new HashSet<>();
        whiteKing = null;
        blackKing = null;
        //wKingRook = null;
        //wQueenRook = null;
        //bKingRook = null;
        //bQueenRook = null;
        wCastleKing = true;
        wCastleQueen = true;
        bCastleKing = true;
        bCastleQueen = true;
        whiteInCheck = false;
        blackInCheck = false;
        moveList = new ArrayList<>();
        fiftyCount = 0;
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

    public boolean wCastleKing() {
        return wCastleKing;
    }

    public boolean wCastleQueen() {
        return wCastleQueen;
    }

    public boolean bCastleKing() {
        return bCastleKing;
    }

    public boolean bCastleQueen() {
        return bCastleQueen;
    }

    public boolean whiteInCheck() {
        return whiteInCheck;
    }

    public boolean blackInCheck() {
        return blackInCheck;
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
            throw new IllegalArgumentException("Squares must be in bounds");
        }
        if (origin.equals(dest)) {
            throw new IllegalArgumentException("Origin and destination cannot be the same");
        }
        Piece p = get(origin);
        if (p == null) {
            throw new IllegalArgumentException("Origin square must be occupied");
        }
        boolean castle = p instanceof King && Math.abs(dest.x - origin.x) == 2;
        if (castle) {
            Square rookOrigin;
            Square rookDest;
            if (dest.x > origin.x) {  // king side
                rookOrigin = new Square(7, origin.y);
                rookDest = new Square(5, origin.y);
            }
            else {  // queen side
                rookOrigin = new Square(0, origin.y);
                rookDest = new Square(3, origin.y);
            }
            Piece rook = get(rookOrigin);
            set(rookDest, rook);
            set(rookOrigin, null);
            rook.setLocation(rookDest);
        }
        Piece replaced = get(dest);
        set(dest, p);
        set(origin, null);
        p.setLocation(dest);
        Set<Piece> result = inCheck(p.color());
        set(dest, replaced);
        set(origin, p);
        p.setLocation(origin);
        if (castle) {
            Square rookOrigin;
            Square rookDest;
            if (dest.x > origin.x) {  // king side
                rookOrigin = new Square(7, origin.y);
                rookDest = new Square(5, origin.y);
            }
            else {  // queen side
                rookOrigin = new Square(0, origin.y);
                rookDest = new Square(3, origin.y);
            }
            Piece rook = get(rookDest);
            set(rookDest, null);
            set(rookOrigin, rook);
            rook.setLocation(rookOrigin);
        }
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

    public void move(Square origin, Square dest) {
        fiftyCount++;
        if (!inBounds(origin) || !inBounds(dest)) {
            throw new IllegalArgumentException("Squares must be in bounds");
        }
        if (origin.equals(dest)) {
            throw new IllegalArgumentException("Origin and destination cannot be the same");
        }
        Piece p = get(origin);
        if (p == null) {
            throw new IllegalArgumentException("Origin square must be occupied");
        }
        if (p instanceof Rook) {
            if (p.color() == Color.WHITE) {
                if (wCastleKing && p.location().equals(new Square(7, 0))) {
                    wCastleKing = false;
                }
                if (wCastleQueen && p.location().equals(new Square(0, 0))) {
                    wCastleQueen = false;
                }
            }
            else {
                if (bCastleKing && p.location().equals(new Square(7, 7))) {
                    bCastleKing = false;
                }
                if (bCastleQueen && p.location().equals(new Square(0, 7))) {
                    bCastleQueen = false;
                }
            }
        }
        if (p instanceof King) {
            if (p.color() == Color.WHITE) {
                wCastleKing = false;
                wCastleQueen = false;
            }
            else {
                bCastleKing = false;
                bCastleQueen = false;
            }
        }
        Piece replaced = get(dest);
        set(dest, p);
        set(origin, null);
        p.setLocation(dest);
        String moveString = p.notation();
        if (replaced != null) {
            getPieceSet(replaced.color()).remove(replaced);
            moveString += "x";
            fiftyCount = 0;
        }
        if (p instanceof Pawn) {
            fiftyCount = 0;
        }
        moveString += dest.notation();
        if (p instanceof King && Math.abs(dest.x - origin.x) == 2) {
            Square rookOrigin;
            Square rookDest;
            if (dest.x > origin.x) {  // king side
                rookOrigin = new Square(7, origin.y);
                rookDest = new Square(5, origin.y);
                moveString = "O-O";
            }
            else {  // queen side
                rookOrigin = new Square(0, origin.y);
                rookDest = new Square(3, origin.y);
                moveString = "O-O-O";
            }
            Piece rook = get(rookOrigin);
            set(rookDest, rook);
            set(rookOrigin, null);
            rook.setLocation(rookDest);
        }
        if (inCheck(Color.inv(p.color())).isEmpty()) {  // does not cause check
            if (p.color() == Color.WHITE) {
                blackInCheck = false;
            }
            else {
                whiteInCheck = false;
            }
        }
        else {                                         // does cause check
            if (p.color() == Color.WHITE) {
                blackInCheck = true;
            }
            else {
                whiteInCheck = true;
            }
            moveString += "+";
        }
        moveList.add(new Move(origin, dest, replaced, moveString));
    }

    public Set<Piece> getPieceSet(Color c) {
        if (c == Color.WHITE) {
            return whitePieces;
        }
        else {
            return blackPieces;
        }
    }

    public King king(Color c) {
        if (c == Color.WHITE) {
            return whiteKing;
        }
        else {
            return blackKing;
        }
    }

    public boolean hasMoves(Color c) {
        for (Piece p: getPieceSet(c)) {
            if (!p.getMoves().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean sufficientMaterial() {
        if (whitePieces.size() > 2 || blackPieces.size() > 2) {
            return true;
        }
        Piece lastWhite = null;
        for (Piece p: whitePieces) {
            if (!(p instanceof King)) {
                lastWhite = p;
            }
        }
        Piece lastBlack = null;
        for (Piece p: blackPieces) {
            if (!(p instanceof King)) {
                lastBlack = p;
            }
        }
        if (lastWhite instanceof Queen || lastBlack instanceof Queen ||
            lastWhite instanceof Rook || lastBlack instanceof Rook ||
            lastWhite instanceof Pawn || lastBlack instanceof Pawn) {
            return false;
        }
        // both pieces are knight or bishop or null
        else if (lastWhite == null || lastBlack == null) {
            // king king, king bishop, king knight are draws
            return true;
        }
        else if (lastWhite instanceof Bishop && lastBlack instanceof Bishop) {
            // same color bishops are a draw, opposite are not
            return lastWhite.location().color() == lastBlack.location().color();
        }
        // bishop knight and knight knight are not draws
        return true;
    }

    // to be called right before c moves
    public String isGameOver(Color c) {
        if (!hasMoves(c)) {
            if (!inCheck(c).isEmpty()) {
                return "Checkmate";
            }
            else {
                return "Stalemate";
            }
        }
        else if (!sufficientMaterial()) {
            return "Insufficient material";
        }
        else if (fiftyCount >= 50) {
            return "50 move rule";
        }
        else {
            return null;
        }
    }

}