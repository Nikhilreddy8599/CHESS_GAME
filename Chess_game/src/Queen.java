public class Queen extends Piece {
    public Queen(String color) {
        super(color);
    }

    @Override
    boolean validMove(int frR, int frC, int toR, int toC, Piece[][] board) {
        return false;
    }

    int[][] validMove(int r, int c) {
        return new int[][]{
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
    }
}
