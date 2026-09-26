public abstract class Piece {
    String color;

    public Piece(String color) {
        this.color = color;
    }

    abstract boolean validMove(int frR, int frC, int toR, int toC, Piece[][] board);

    abstract int[][] validMove(int frR, int frC);

    boolean isSliding() {
        return true;
    }
}
