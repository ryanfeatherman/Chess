package util;

public enum Color {
    WHITE, BLACK;

    public static Color inv(Color c) {
        if (c == WHITE) {
            return BLACK;
        }
        else {
            return WHITE;
        }
    }

}