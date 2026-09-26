public class Bishop extends Piece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    boolean validMove(int frR, int frC, int toR, int toC, Piece[][] board) {
        return false;
    }

    int[][] validMove(int r, int c) {
        return new int[][]{
                {-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
    }
}

