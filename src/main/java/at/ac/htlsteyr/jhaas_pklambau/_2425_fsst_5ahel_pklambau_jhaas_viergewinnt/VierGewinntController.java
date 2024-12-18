package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.control.TextInputDialog;

public class VierGewinntController {
    @FXML
    private Label playerTurnLabel;

    @FXML
    private GridPane boardGrid;

    private ConnectFourModel model;

    @FXML
    public void initialize() {
        model = new ConnectFourModel();

        // Spielernamen abfragen
        String player1 = getPlayerName("Spieler 1");
        String player2 = getPlayerName("Spieler 2");

        model.setPlayerNames(player1, player2);
        setupBoardView();
        updateView();
    }

    private String getPlayerName(String defaultName) {
        TextInputDialog dialog = new TextInputDialog(defaultName);
        dialog.setTitle("Spielername eingeben");
        dialog.setHeaderText("Gib den Namen für " + defaultName + " ein:");
        dialog.setContentText("Name:");

        return dialog.showAndWait().orElse(defaultName);
    }

    private void setupBoardView() {
        char[][] board = model.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black; -fx-pref-width: 50; -fx-pref-height: 50; -fx-background-color: #e0e0e0;");
                int finalCol = col; // Effektiv final für Lambda
                cell.setOnMouseClicked(event -> handleColumnClick(finalCol));
                boardGrid.add(cell, col, row);
            }
        }
    }

    private void handleColumnClick(int column) {
        if (!model.dropToken(column)) {
            return;
        }

        if (model.checkWin()) {
            playerTurnLabel.setText(model.getCurrentPlayerName() + " hat gewonnen!");
        } else if (model.isDraw()) {
            playerTurnLabel.setText("Unentschieden!");
        } else {
            updateView();
        }
    }

    private void updateView() {
        char[][] board = model.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                StackPane cell = (StackPane) getNodeFromGridPane(boardGrid, col, row);
                if (cell != null) {
                    cell.getChildren().clear();
                    char symbol = board[row][col];
                    if (symbol != ' ') {
                        Label label = new Label(String.valueOf(symbol));
                        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
                        cell.getChildren().add(label);
                    }
                }
            }
        }
        playerTurnLabel.setText(model.getCurrentPlayerName() + " ist am Zug.");
    }

    private javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}

