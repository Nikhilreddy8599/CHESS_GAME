public class King extends Piece {
    public King(String color) {
        super(color);
    }

    @Override
    boolean validMove(int frR, int frC, int toR, int toC, Piece[][] board) {
        return false;
    }

    @Override
    int[][] validMove(int frR, int frC) {
        return new int[][]{
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
    }

    boolean isSliding() {
        return false;
    }
}

