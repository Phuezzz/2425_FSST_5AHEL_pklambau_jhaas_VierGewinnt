package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

public class ConnectFourModel {
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private final char[][] board = new char[ROWS][COLUMNS];
    private String player1Name, player2Name;
    private boolean player1Turn = true;

    public ConnectFourModel() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = ' ';
            }
        }
    }

    public int getROWS() {
        return ROWS;
    }

    public int getCOLUMNS() {
        return COLUMNS;
    }

    public void setPlayerNames(String player1, String player2) {
        this.player1Name = player1;
        this.player2Name = player2;
    }

    public String getCurrentPlayerName() {
        return player1Turn ? player1Name : player2Name;
    }

    public boolean dropToken(int column) {
        if (column < 0 || column >= COLUMNS || board[0][column] != ' ') {
            return false;
        }
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][column] == ' ') {
                board[row][column] = player1Turn ? 'o' : 'x';
                player1Turn = !player1Turn;
                return true;
            }
        }
        return false;
    }

    public boolean checkWin() {
        char currentSymbol = player1Turn ? 'x' : 'o';

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (checkDirection(row, col, 1, 0, currentSymbol) ||
                        checkDirection(row, col, 0, 1, currentSymbol) ||
                        checkDirection(row, col, 1, 1, currentSymbol) ||
                        checkDirection(row, col, 1, -1, currentSymbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDraw() {
        for (int col = 0; col < COLUMNS; col++) {
            if (board[0][col] == ' ') {
                return false;
            }
        }
        return true;
    }

    public char[][] getBoard() {
        return board;
    }

    private boolean checkDirection(int row, int col, int deltaRow, int deltaCol, char symbol) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int newRow = row + i * deltaRow;
            int newCol = col + i * deltaCol;

            if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLUMNS && board[newRow][newCol] == symbol) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }

}
