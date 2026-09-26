public class Knight extends Piece {
    public Knight(String color) {
        super(color);
    }

    @Override
    boolean validMove(int frR, int frC, int toR, int toC, Piece[][] board) {
        return false;
    }

    @Override
    int[][] validMove(int frR, int frC) {
        return new int[][]{
                {-2, -1}, {-2, 1},
                {-1, -2}, {-1, 2},
                {1, -2}, {1, 2},
                {2, -1}, {2, 1}
        };
    }

    ;

    boolean isSliding() {
        return false;
    }
}

