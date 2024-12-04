// Model.java
public class Model {
    private final int rows = 6;
    private final int columns = 7;
    private char[][] board = new char[rows][columns];
    private char currentPlayer = 'O'; // Spieler 1

    public Model() {
        // Spielfeld initialisieren
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean makeMove(int column) {
        if (column < 0 || column >= columns || board[0][column] != ' ') {
            return false; // Ungültiger Zug
        }
        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][column] == ' ') {
                board[i][column] = currentPlayer;
                return true;
            }
        }
        return false;
    }

    public boolean checkWin() {
        // Überprüfen von Siegbedingungen (horizontal, vertikal, diagonal)
        // Für jede Position auf dem Brett
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                char symbol = board[row][col];
                if (symbol != ' ' && (checkDirection(row, col, 1, 0, symbol) ||
                        checkDirection(row, col, 0, 1, symbol) ||
                        checkDirection(row, col, 1, 1, symbol) ||
                        checkDirection(row, col, 1, -1, symbol))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int rowDelta, int colDelta, char symbol) {
        for (int i = 0; i < 4; i++) {
            int newRow = row + i * rowDelta;
            int newCol = col + i * colDelta;
            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= columns || board[newRow][newCol] != symbol) {
                return false;
            }
        }
        return true;
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

    public boolean isBoardFull() {
        for (int i = 0; i < columns; i++) {
            if (board[0][i] == ' ') {
                return false;
            }
        }
        return true;
    }
}
