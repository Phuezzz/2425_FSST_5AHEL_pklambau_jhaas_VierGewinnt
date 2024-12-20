package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Duration;

public class VierGewinntController {
    @FXML
    private Label playerTurnLabel;

    @FXML
    private GridPane boardGrid;

    private ConnectFourModel model;

    private Color player1Color;
    private Color player2Color;

    @FXML
    public void initialize() {
        model = new ConnectFourModel();

        // Spielernamen und Farben abfragen
        String player1 = getPlayerNameAndColor("Spieler 1", true);
        String player2 = getPlayerNameAndColor("Spieler 2", false);

        model.setPlayerNames(player1, player2);

        setupBoardView();
        updateView();
    }

    private String getPlayerNameAndColor(String defaultName, boolean isPlayer1) {
        TextInputDialog dialog = new TextInputDialog(defaultName);
        dialog.setTitle("Spielername eingeben");
        dialog.setHeaderText("Gib den Namen für " + defaultName + " ein:");
        dialog.setContentText("Name:");

        String playerName = dialog.showAndWait().orElse(defaultName);

        // Farbauswahl-Dialog erstellen
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(isPlayer1 ? Color.RED : Color.YELLOW); // Standardfarben

        Dialog<ButtonType> colorDialog = new Dialog<>();
        colorDialog.setTitle("Farbe auswählen");
        colorDialog.setHeaderText("Wähle eine Farbe für " + playerName + ":");
        colorDialog.getDialogPane().setContent(colorPicker);
        colorDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Zeige den Farbdialog und speichere die gewählte Farbe
        Optional<ButtonType> result = colorDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (isPlayer1) {
                player1Color = colorPicker.getValue();
            } else {
                player2Color = colorPicker.getValue();
            }
        } else {
            // Falls der Benutzer abbricht, Standardfarbe setzen
            if (isPlayer1) {
                player1Color = Color.RED;
            } else {
                player2Color = Color.YELLOW;
            }
        }

        return playerName;
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
            playerTurnLabel.setText(model.getWinningPlayerName() + " hat gewonnen!");
            highlightWinningTokens();
            resetGameAfterDelay();
        } else if (model.isDraw()) {
            playerTurnLabel.setText("Unentschieden!");
        } else {
            updateView();
        }
    }

    private void highlightWinningTokens() {
        // Hier gehen wir davon aus, dass das Modell uns die gewonnenen Positionen liefert
        List<int[]> winningTokens = model.getWinningTokens();
        for (int[] pos : winningTokens) {
            int row = pos[0];
            int col = pos[1];
            StackPane cell = (StackPane) getNodeFromGridPane(boardGrid, col, row);
            if (cell != null) {
                Rectangle rect = new Rectangle(50, 50);
                rect.setFill(model.getCurrentPlayerColor());
                cell.getChildren().clear();
                cell.getChildren().add(rect);
            }
        }
    }

    private void resetGameAfterDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(event -> resetGame());
        pause.play();
    }

    private void resetGame() {
        model.resetBoard();  // Board reset im Modell
        updateView();  // View zurücksetzen
        playerTurnLabel.setText("Das Spiel beginnt!");

        // Es ist wichtig, dass der aktuelle Spieler und die Farben erhalten bleiben
    }

    private void updateView() {
        char[][] board = model.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                StackPane cell = (StackPane) getNodeFromGridPane(boardGrid, col, row);
                if (cell != null) {
                    cell.getChildren().clear();
                    char symbol = board[row][col];
                    if (symbol == 'o' || symbol == 'x') {
                        Rectangle rect = new Rectangle(50, 50);
                        rect.setFill(symbol == 'o' ? player1Color : player2Color);
                        cell.getChildren().add(rect);
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



