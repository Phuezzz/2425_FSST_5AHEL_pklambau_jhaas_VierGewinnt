package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

public class Model {
    private final int rows = 6;
    private final int columns = 7;
    private char[][] board = new char[rows][columns];
    private char currentPlayer = 'O';

    public Model() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean makeMove(int column) {
        if (column < 0 || column >= columns || board[0][column] != ' ') {
            return false;
        }
        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][column] == ' ') {
                board[i][column] = currentPlayer;
                return true;
            }
        }
        return false;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isWin() {
        // Logik zur Überprüfung von Gewinnbedingungen
        return false;
    }
}
