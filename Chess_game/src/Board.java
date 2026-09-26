public class Board {

    String[][] BoardObj = new String[8][8];
    Piece[][] piece = new Piece[8][8];

    public Board() {
        setup();
    }

    void setup() {
        BoardObj[0][0] = "♜";
        BoardObj[0][1] = "♞";
        BoardObj[0][2] = "♝";
        BoardObj[0][3] = "♛";
        BoardObj[0][4] = "♚";
        BoardObj[0][5] = "♝";
        BoardObj[0][6] = "♞";
        BoardObj[0][7] = "♜";
        for (int i = 0; i < 8; i++) BoardObj[1][i] = ("♟");
        BoardObj[7][0] = "♖";
        BoardObj[7][1] = "♘";
        BoardObj[7][2] = "♗";
        BoardObj[7][3] = "♕";
        BoardObj[7][4] = "♔";
        BoardObj[7][5] = "♗";
        BoardObj[7][6] = "♘";
        BoardObj[7][7] = "♖";
        for (int i = 0; i < 8; i++) BoardObj[6][i] = ("♙");

        piece[0][0] = new Rook("black");
        piece[0][1] = new Knight("black");
        piece[0][2] = new Bishop("black");
        piece[0][3] = new Queen("black");
        piece[0][4] = new King("black");
        piece[0][5] = new Bishop("black");
        piece[0][6] = new Knight("black");
        piece[0][7] = new Rook("black");

        for (int i = 0; i < 8; i++) {
            piece[1][i] = new Pawn("black");
            piece[6][i] = new Pawn("white");
        }

        piece[7][0] = new Rook("white");
        piece[7][1] = new Knight("white");
        piece[7][2] = new Bishop("white");
        piece[7][3] = new Queen("white");
        piece[7][4] = new King("white");
        piece[7][5] = new Bishop("white");
        piece[7][6] = new Knight("white");
        piece[7][7] = new Rook("white");

    }
}


