package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class ConnectFourView extends GridPane {
    private Label[][] cells;
    private Label playerTurnLabel;

    public ConnectFourView() {
        cells = new Label[6][7];
        playerTurnLabel = new Label("Spieler ist am Zug:");
        this.add(playerTurnLabel, 0, 0, 7, 1);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black; -fx-pref-width: 50; -fx-pref-height: 50;");
                Label label = new Label();
                cell.getChildren().add(label);
                cells[row][col] = label;
                this.add(cell, col, row + 1);
            }
        }
    }

    public void updateBoard(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                cells[row][col].setText(String.valueOf(board[row][col]));
            }
        }
    }

    public void setPlayerTurn(String playerName) {
        playerTurnLabel.setText(playerName + " ist am Zug:");
    }
}
