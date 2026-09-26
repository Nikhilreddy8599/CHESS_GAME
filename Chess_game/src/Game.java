import javax.swing.*;
import java.awt.*;

public class Game {
    JPanel boardp;
    boolean gameOver = false;
    JButton[][] position = new JButton[8][8];
    Board board = new Board();
    int selectedRow = -1;
    int selectedCol = -1;
    int count = 0;
    int n = 0;
    int moves=0;
    boolean whiteKingMoved = false;
    boolean blackKingMoved = false;
    boolean whiteLeftRookMoved = false;
    boolean whiteRightRookMoved = false;
    boolean blackLeftRookMoved = false;
    boolean blackRightRookMoved = false;

    public Game() {
        boardp = new JPanel(new GridLayout(8, 8));
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                position[r][c] = new JButton();
                boardp.add(position[r][c]);
                int row = r;
                int col = c;
                if ((r + c) % 2 == 0)
                    position[r][c].setBackground(Color.WHITE);
                else
                    position[r][c].setBackground(Color.GRAY);
                if (board.piece[r][c] != null)
                    position[r][c].setText(board.BoardObj[r][c]);
                position[r][c].setFont(new Font("Segoe UI Symbol", Font.PLAIN, 60));
                position[r][c].addActionListener(e -> nextStep(row, col));
            }
        }
    }


    public int[] findKing(String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.piece[i][j] instanceof King &&
                        board.piece[i][j].color.equals(color)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public void nextStep(int r, int c) {
        if (gameOver) return;
        // --------------------------------selectingggggg------------------------------
        if (selectedRow == -1 && board.piece[r][c] != null && validPlayer(r, c)) {
            selectedRow = r;
            selectedCol = c;
            position[r][c].setBackground(Color.YELLOW);
            setLegalMoves(r, c);
        }
        // --------------------------------movinggggggg----------------------------------
        else if (selectedRow != -1 && canMove(selectedRow, selectedCol, r, c)) {
            Piece movingPiece = board.piece[selectedRow][selectedCol];
            Piece targetPiece = board.piece[r][c];

            if (movingPiece instanceof Pawn || targetPiece != null) {
                moves = 0;
            } else {
                moves++;
            }


            if (movingPiece instanceof King && Math.abs(c - selectedCol) == 2) {
                doCastling(selectedRow, selectedCol, r, c);
            }
            else {
                board.piece[r][c] = movingPiece;
                board.piece[selectedRow][selectedCol] = null;
                board.BoardObj[r][c] = board.BoardObj[selectedRow][selectedCol];
                board.BoardObj[selectedRow][selectedCol] = "";
                position[r][c].setText(position[selectedRow][selectedCol].getText());
                position[selectedRow][selectedCol].setText("");
                promotePawnIfNeeded(r, c);
            }
            updateMovedFlags(movingPiece, selectedRow, selectedCol);
            count++;
            resetBoardColors();
            String currentPlayer = (count % 2 == 0) ? "white" : "black";
            highlightCheck(currentPlayer);
            if (isCheckmate(currentPlayer)) return;
            if (moves >= 100) {
                gameOver = true;
                JOptionPane.showMessageDialog(boardp, "DRAW! 50-MOVE RULE");
                return;
            }
            if (isStalemate(currentPlayer)) return;
            if (isInsufficientMaterial()) {
                gameOver = true;
                JOptionPane.showMessageDialog(boardp, "DRAW! INSUFFICIENT MATERIAL");
                return;
            }
            selectedRow = -1;
            selectedCol = -1;
        }

        // ----------------------reselectinggggg--------------------------------------
        else if (selectedRow != -1 && board.piece[r][c] != null && validPlayer(r, c)) {
            resetBoardColors();

            selectedRow = r;
            selectedCol = c;
            position[r][c].setBackground(Color.YELLOW);
            setLegalMoves(r, c);
        }
        // ---------------------------------invaliddd------------------------------------
        else {
            resetBoardColors();
            selectedRow = -1;
            selectedCol = -1;
        }
    }



    boolean canMove(int frR, int frC, int toR, int toC) {
        return position[toR][toC].getBackground().equals(Color.GREEN) || position[toR][toC].getBackground().equals(Color.RED);
    }



    void setLegalMoves(int r, int c) {
        if (board.piece[r][c] instanceof King) {
            addCastlingMoves(r, c);
        }
        if (!(board.piece[r][c] instanceof Pawn)) {
            int[][] directions = board.piece[r][c].validMove(r, c);
            for (int[] d : directions) {


                int row = r + d[0];
                int col = c + d[1];


                while (row >= 0 && row < 8 && col >= 0 && col < 8) {
                    if (board.piece[row][col] == null) {
                        if (isLegalAfterMove(r, c, row, col))
                            position[row][col].setBackground(Color.GREEN);
                    } else {
                        if (!board.piece[row][col].color.equals(board.piece[r][c].color)) {
                            if (isLegalAfterMove(r, c, row, col))
                                position[row][col].setBackground(Color.RED);
                        }
                        break;
                    }
                    if (!board.piece[r][c].isSliding())
                        break;
                    row += d[0];
                    col += d[1];
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board.piece[r][c].validMove(r, c, i, j, board.piece)) {
                        Piece moving = board.piece[r][c];
                        Piece captured = board.piece[i][j];

                        board.piece[i][j] = moving;
                        board.piece[r][c] = null;

                        if (!isCheck(moving.color)) {
                            if (captured == null)
                                position[i][j].setBackground(Color.GREEN);
                            else
                                position[i][j].setBackground(Color.RED);
                        }

                        board.piece[r][c] = moving;
                        board.piece[i][j] = captured;
                    }
                }
            }
        }

    }



    boolean validPlayer(int r, int c) {
        if (board.piece[r][c] == null)
            return false;
        if (count % 2 == 0 && board.piece[r][c].color.equals("white"))
            return true;
        if (count % 2 == 1 && board.piece[r][c].color.equals("black"))
            return true;
        return false;
    }



    boolean isCheck(String color) {
        int[] kingPos = findKing(color);
        if (kingPos == null) return false;
        int kr = kingPos[0];
        int kc = kingPos[1];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.piece[i][j] != null &&
                        !board.piece[i][j].color.equals(color)) {
                    if (canAttack(i, j, kr, kc)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    boolean canAttack(int frR, int frC, int toR, int toC) {
        Piece p = board.piece[frR][frC];
        if (p instanceof Pawn) {
            int dir = p.color.equals("white") ? -1 : 1;
            return (toR == frR + dir && (toC == frC + 1 || toC == frC - 1));
        }
        int[][] directions = p.validMove(frR, frC);
        for (int[] d : directions) {
            int r = frR + d[0];
            int c = frC + d[1];
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (r == toR && c == toC)
                    return true;
                if (board.piece[r][c] != null)
                    break;
                if (!p.isSliding())
                    break;
                r += d[0];
                c += d[1];
            }
        }
        return false;
    }



    boolean isStalemate(String color) {
        if (isCheck(color)) return false;

        if (!hasAnyLegalMove(color)) {
            gameOver = true;
            JOptionPane.showMessageDialog(boardp, "STALEMATE! DRAW!");
            return true;
        }
        return false;
    }



    boolean isLegalAfterMove(int frR, int frC, int toR, int toC) { //after moving is it check or not
        Piece moving = board.piece[frR][frC];
        Piece captured = board.piece[toR][toC];
        board.piece[toR][toC] = moving;
        board.piece[frR][frC] = null;
        boolean bool = !isCheck(moving.color);
        board.piece[frR][frC] = moving;
        board.piece[toR][toC] = captured;
        return bool;
    }



    void resetBoardColors() {
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                if ((a + b) % 2 == 0)
                    position[a][b].setBackground(Color.WHITE);
                else
                    position[a][b].setBackground(Color.GRAY);
            }
        }
    }


    boolean isInsufficientMaterial() {//2 king 1 bishop or 2 king or 2 king 2 bishop ==draw
        int white1 = 0;
        int black1 = 0;
        int white2 = 0;
        int black2 = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board.piece[i][j];
                if (p == null) continue;

                if (p instanceof King) continue;

                if (p instanceof Bishop || p instanceof Knight) {
                    if (p.color.equals("white")) white1++;
                    else black1++;
                } else {
                    if (p.color.equals("white")) white2++;
                    else black2++;
                }
            }
        }
        if (white1 == 0 && black1 == 0 &&
                white2 == 0 && black2 == 0)
            return true;
        if (white2 == 0 && black2 == 0) {
            if (white1 == 1 && black1 == 0) return true;
            if (white1 == 0 && black1 == 1) return true;
        }
        return false;
    }


    boolean isCheckmate(String color) {
        if (!isCheck(color)) return false;
        if (!hasAnyLegalMove(color)) {
            gameOver = true;
            String winner = color.equals("white") ? "BLACK" : "WHITE";
            JOptionPane.showMessageDialog(boardp, "CHECKMATE! " + winner + " WINS!");
            return true;
        }

        return false;
    }


    boolean hasAnyLegalMove(String color) { //if not hasanylegalmoves then check cannot be blocked
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board.piece[i][j];
                if (p == null || !p.color.equals(color)) continue;
                if (p instanceof Pawn) {
                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            if (p.validMove(i, j, r, c, board.piece) &&
                                    isLegalAfterMove(i, j, r, c)) {
                                return true;
                            }
                        }
                    }
                } else {
                    int[][] dirs = p.validMove(i, j);
                    for (int[] d : dirs) {
                        int nr = i + d[0];
                        int nc = j + d[1];
                        while (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                            if (board.piece[nr][nc] == null) {
                                if (isLegalAfterMove(i, j, nr, nc)) return true;
                            } else {
                                if (!board.piece[nr][nc].color.equals(color)) {
                                    if (isLegalAfterMove(i, j, nr, nc)) return true;
                                }
                                break;
                            }
                            if (!p.isSliding()) break;
                            nr += d[0];
                            nc += d[1];
                        }
                    }
                }
            }
        }
        return false;
    }



    void promotePawn(int r, int c, String color) {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        String choice = (String) JOptionPane.showInputDialog(
                boardp,
                "Choose promotion piece:",
                "Pawn Promotion",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );


        if (choice == null) choice = "Queen";
        switch (choice) {
            case "Rook":
                board.piece[r][c] = new Rook(color);
                board.BoardObj[r][c] = color.equals("white") ? "♖" : "♜";
                break;
            case "Bishop":
                board.piece[r][c] = new Bishop(color);
                board.BoardObj[r][c] = color.equals("white") ? "♗" : "♝";
                break;
            case "Knight":
                board.piece[r][c] = new Knight(color);
                board.BoardObj[r][c] = color.equals("white") ? "♘" : "♞";
                break;
            default:
                board.piece[r][c] = new Queen(color);
                board.BoardObj[r][c] = color.equals("white") ? "♕" : "♛";
                break;
        }
        position[r][c].setText(board.BoardObj[r][c]);
    }


    void promotePawnIfNeeded(int r, int c) {
        Piece p = board.piece[r][c];
        if (!(p instanceof Pawn)) return;
        if (p.color.equals("white") && r == 0) {
            promotePawn(r, c, "white");
        } else if (p.color.equals("black") && r == 7) {
            promotePawn(r, c, "black");
        }
    }


    boolean canCastle(int frR, int frC, int toR, int toC) {
        Piece p = board.piece[frR][frC];
        if (!(p instanceof King)) return false;

        if (isCheck(p.color)) return false;

        if (p.color.equals("white")) {
            if (whiteKingMoved || frR != 7 || frC != 4) return false;
            if (toR == 7 && toC == 6) {
                if (whiteRightRookMoved) return false;
                if (!(board.piece[7][7] instanceof Rook)) return false;
                if (board.piece[7][5] != null || board.piece[7][6] != null) return false;
                if (!isLegalAfterMove(7, 4, 7, 5)) return false;
                if (!isLegalAfterMove(7, 4, 7, 6)) return false;
                return true;
            }
            if (toR == 7 && toC == 2) {
                if (whiteLeftRookMoved) return false;
                if (!(board.piece[7][0] instanceof Rook)) return false;
                if (board.piece[7][1] != null || board.piece[7][2] != null || board.piece[7][3] != null) return false;
                if (!isLegalAfterMove(7, 4, 7, 3)) return false;
                if (!isLegalAfterMove(7, 4, 7, 2)) return false;
                return true;
            }
        } else {
            if (blackKingMoved || frR != 0 || frC != 4) return false;
            if (toR == 0 && toC == 6) {
                if (blackRightRookMoved) return false;
                if (!(board.piece[0][7] instanceof Rook)) return false;
                if (board.piece[0][5] != null || board.piece[0][6] != null) return false;
                if (!isLegalAfterMove(0, 4, 0, 5)) return false;
                if (!isLegalAfterMove(0, 4, 0, 6)) return false;
                return true;
            }
            if (toR == 0 && toC == 2) {
                if (blackLeftRookMoved) return false;
                if (!(board.piece[0][0] instanceof Rook)) return false;
                if (board.piece[0][1] != null || board.piece[0][2] != null || board.piece[0][3] != null) return false;
                if (!isLegalAfterMove(0, 4, 0, 3)) return false;
                if (!isLegalAfterMove(0, 4, 0, 2)) return false;
                return true;
            }
        }
        return false;
    }


    void addCastlingMoves(int r, int c) {
        if (canCastle(r, c, r, 6)) {
            position[r][6].setBackground(Color.GREEN);
        }
        if (canCastle(r, c, r, 2)) {
            position[r][2].setBackground(Color.GREEN);
        }
    }


    void updateMovedFlags(Piece p, int frR, int frC) {
        if (p instanceof King) {
            if (p.color.equals("white")) whiteKingMoved = true;
            else blackKingMoved = true;
        }

        if (p instanceof Rook) {
            if (frR == 7 && frC == 0) whiteLeftRookMoved = true;
            if (frR == 7 && frC == 7) whiteRightRookMoved = true;
            if (frR == 0 && frC == 0) blackLeftRookMoved = true;
            if (frR == 0 && frC == 7) blackRightRookMoved = true;
        }
    }


    void doCastling(int frR, int frC, int toR, int toC) {
        Piece king = board.piece[frR][frC];
        if (toC == 6) {
            Piece rook = board.piece[frR][7];
            board.piece[frR][6] = king;
            board.piece[frR][5] = rook;
            board.piece[frR][4] = null;
            board.piece[frR][7] = null;
            board.BoardObj[frR][6] = board.BoardObj[frR][4];
            board.BoardObj[frR][5] = board.BoardObj[frR][7];
            board.BoardObj[frR][4] = "";
            board.BoardObj[frR][7] = "";
            position[frR][6].setText(position[frR][4].getText());
            position[frR][5].setText(position[frR][7].getText());
            position[frR][4].setText("");
            position[frR][7].setText("");
        }
        else if (toC == 2) {
            Piece rook = board.piece[frR][0];
            board.piece[frR][2] = king;
            board.piece[frR][3] = rook;
            board.piece[frR][4] = null;
            board.piece[frR][0] = null;
            board.BoardObj[frR][2] = board.BoardObj[frR][4];
            board.BoardObj[frR][3] = board.BoardObj[frR][0];
            board.BoardObj[frR][4] = "";
            board.BoardObj[frR][0] = "";
            position[frR][2].setText(position[frR][4].getText());
            position[frR][3].setText(position[frR][0].getText());
            position[frR][4].setText("");
            position[frR][0].setText("");
        }
    }


    void highlightCheck(String color) {
        if (isCheck(color)) {
            int[] kingPos = findKing(color);
            if (kingPos != null) {
                position[kingPos[0]][kingPos[1]].setBackground(Color.ORANGE);
            }
        }
    }
}
