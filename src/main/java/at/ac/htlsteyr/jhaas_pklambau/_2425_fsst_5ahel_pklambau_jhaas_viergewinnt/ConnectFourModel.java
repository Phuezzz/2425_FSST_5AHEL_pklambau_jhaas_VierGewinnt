package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class ConnectFourModel {

    private char[][] board;
    protected String player1Name;
    protected String player2Name;
    private Color player1Color;
    private Color player2Color;
    private boolean isPlayer1Turn;
    private List<int[]> winningTokens;
    protected boolean gameWon;

    public int getROWS(){
        return board.length;
    }
    public boolean isCellFree(int row, int col){
        return board[row][col] == ' ';
    }
    public ConnectFourModel() {
        board = new char[6][7];  // 6 Zeilen, 7 Spalten
        winningTokens = new ArrayList<>(); // Initialisiere die winningTokens-Liste
        gameWon = false;  // Zu Beginn ist das Spiel nicht gewonnen
        resetBoard();
        isPlayer1Turn = true;
    }

    public void resetBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = ' ';  // Leeres Feld
            }
        }
        winningTokens.clear(); // Leeren der Gewinnfelder
    }

    public boolean dropToken(int column) {
        if (column < 0 || column >= 7 || isColumnFull(column) || gameWon) {
            return false;
        }

        // Finde die erste freie Zeile in der Spalte
        for (int row = 5; row >= 0; row--) {
            if (board[row][column] == ' ') {
                board[row][column] = isPlayer1Turn ? 'o' : 'x';
                isPlayer1Turn = !isPlayer1Turn;
                return true;
            }
        }
        return false;
    }

    public boolean isColumnFull(int column) {
        return board[0][column] != ' ';  // Wenn die oberste Zeile voll ist
    }

    public boolean checkWin() {
        // Überprüfen auf horizontale, vertikale und diagonale Reihen
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != ' ' && checkDirection(row, col)) {
                    gameWon = true; // Das Spiel wurde gewonnen
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col) {
        char currentPlayer = board[row][col];

        // Horizontal
        if (col + 3 < 7 && board[row][col + 1] == currentPlayer && board[row][col + 2] == currentPlayer && board[row][col + 3] == currentPlayer) {
            markWinningTokens(row, col, 0, 1);
            return true;
        }

        // Vertikal
        if (row + 3 < 6 && board[row + 1][col] == currentPlayer && board[row + 2][col] == currentPlayer && board[row + 3][col] == currentPlayer) {
            markWinningTokens(row, col, 1, 0);
            return true;
        }

        // Diagonal (von unten links nach oben rechts)
        if (row + 3 < 6 && col + 3 < 7 && board[row + 1][col + 1] == currentPlayer && board[row + 2][col + 2] == currentPlayer && board[row + 3][col + 3] == currentPlayer) {
            markWinningTokens(row, col, 1, 1);
            return true;
        }

        // Diagonal (von oben links nach unten rechts)
        if (row - 3 >= 0 && col + 3 < 7 && board[row - 1][col + 1] == currentPlayer && board[row - 2][col + 2] == currentPlayer && board[row - 3][col + 3] == currentPlayer) {
            markWinningTokens(row, col, -1, 1);
            return true;
        }

        return false;
    }

    private void markWinningTokens(int startRow, int startCol, int rowIncrement, int colIncrement) {
        // Markiere die Gewinnreihen und speichere sie in der winningTokens-Liste
        int row = startRow;
        int col = startCol;

        for (int i = 0; i < 4; i++) {
            winningTokens.add(new int[]{row, col});
            row += rowIncrement;
            col += colIncrement;
        }
    }

    public boolean isDraw() {
        for (int col = 0; col < 7; col++) {
            if (!isColumnFull(col)) {
                return false;  // Eine freie Spalte bedeutet noch kein Unentschieden
            }
        }
        return true;  // Wenn alle Spalten voll sind und keiner gewonnen hat, Unentschieden
    }

    public void setPlayerNames(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void setPlayerColors(Color player1Color, Color player2Color) {
        this.player1Color = player1Color;
        this.player2Color = player2Color;
    }

    public String getCurrentPlayerName() {
        return isPlayer1Turn ? player1Name : player2Name;
    }

    public String getWinningPlayerName() {
        return isPlayer1Turn ? player2Name : player1Name;
    }

    public Color getCurrentPlayerColor() {
        return isPlayer1Turn ? player1Color : player2Color;
    }

    public char[][] getBoard() {
        return board;
    }

    public List<int[]> getWinningTokens() {
        return winningTokens;
    }

    public boolean isGameWon() {
        return gameWon;
    }
}



