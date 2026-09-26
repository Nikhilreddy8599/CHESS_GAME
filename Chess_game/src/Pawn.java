public class Pawn extends Piece {
    static int k = 0;

    public Pawn(String color) {
        super(color);
    }

    @Override
    int[][] validMove(int frR, int frC) {
        return new int[0][];
    }

    public boolean validMove(int frR, int frC, int toR, int toC, Piece[][] board) {
        if (toR < 0 || toR >= 8 || toC < 0 || toC >= 8)
            return false;
        int direction;

        if (color.equals("white"))
            direction = -1;
        else
            direction = 1;

        //--------------------1 forward-------------------------------------------------------
        if (frC == toC && toR == frR + direction && board[toR][toC] == null)
            return true;

        //----------------2 forward frm start-------------------------------------------------
        if (frC == toC && toR == frR + 2 * direction &&
                board[toR][toC] == null &&
                board[frR + direction][frC] == null) {
            if (color.equals("white") && frR == 6)
                return true;

            if (color.equals("black") && frR == 1)
                return true;
        }
        //------------------------Captureeee-----------------------------------------------------------
        if (Math.abs(frC - toC) == 1 && toR == frR + direction &&
                board[toR][toC] != null &&
                !board[toR][toC].color.equals(color)) {
            k++;
            return true;
        }

        return false;
    }
}

