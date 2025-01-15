package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
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
    private Label playerColorLabel;

    @FXML
    private Label player1label;

    @FXML
    private Label player2label;

    @FXML
    private GridPane boardGrid;

    @FXML
    private Button resetbutton;

    @FXML
    private Button resetbuttonprogress;

    @FXML
    private ProgressBar progressbar1;

    @FXML
    private ProgressBar progressbar2;

    private ConnectFourModel model;

    private Color player1Color;
    private Color player2Color;

    private double progress1;
    private double progress2;

    @FXML
    public void initialize() {
        model = new ConnectFourModel();

        // Spielernamen und Farben abfragen
        String player1 = getPlayerNameAndColor("Spieler 1", true);
        String player2 = getPlayerNameAndColor("Spieler 2", false);

        model.setPlayerNames(player1, player2);

        player1label.setText(player1);
        player2label.setText(player2);

        progressbar1.setProgress(0);
        progressbar2.setProgress(0);

        setupBoardView();
        updateView();

        resetbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetGame();
            }
        });

        resetbuttonprogress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                progressbar1.setProgress(0);
                progressbar2.setProgress(0);
            }
        });
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

        if(isPlayer1){
            progressbar1.setStyle("-fx-accent: #" + player1Color.toString().replace("0x", "") + ";");
        }else{
            progressbar2.setStyle("-fx-accent: #" + player2Color.toString().replace("0x", "") + ";");
        }

        return playerName;
    }


    private void setupBoardView() {
        char[][] board = model.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                StackPane cell = new StackPane();
                cell.getStyleClass().add("default");
                int finalCol = col; // Effektiv final für Lambda
                cell.setOnMouseClicked(event -> handleColumnClick(finalCol));
                cell.setOnMouseEntered(event -> highlightNextAvailableCell(finalCol));
                cell.setOnMouseExited(event -> clearHighlightFromNextAvailableCell(finalCol));
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
            if(model.getCurrentPlayerName().equals(model.player2Name)) {
                progress1 += 0.1;
                progressbar1.setProgress(progress1);
            }else if(model.getCurrentPlayerName().equals(model.player1Name)) {
                progress2 += 0.1;
                progressbar2.setProgress(progress2);
            }
            highlightWinningTokens();
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
                Rectangle rect = new Rectangle(48, 45);
                rect.setFill(Color.GREEN);
                cell.getChildren().add(rect);
                playerTurnLabel.setText(model.getWinningPlayerName() + " hat Gewonnen!");
                if(model.getWinningPlayerName().equals(model.player1Name)){
                    playerColorLabel.setStyle("-fx-background-color: #" + player1Color.toString().replace("0x", "") + ";");
                }else{
                    playerColorLabel.setStyle("-fx-background-color: #" + player2Color.toString().replace("0x", "") + ";");
                }
            }
        }
    }

    private void resetGame() {
        model.resetBoard();  // Board reset im Modell
        updateView();  // View zurücksetzen
        playerTurnLabel.setText(model.getCurrentPlayerName() + " ist am Zug.");
        model.gameWon = false;
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
                        Rectangle rect = new Rectangle(48, 45);
                        rect.setFill(symbol == 'o' ? player1Color : player2Color);
                        cell.getChildren().add(rect);
                    }else{
                        setElementStyle(cell, "default");
                    }
                }
            }
        }
        playerTurnLabel.setText(model.getCurrentPlayerName() + " ist am Zug.");
        if(model.getCurrentPlayerName().equals(model.player1Name)){
            playerColorLabel.setStyle("-fx-background-color: #" + player1Color.toString().replace("0x", "") + ";");
        }else{
            playerColorLabel.setStyle("-fx-background-color: #" + player2Color.toString().replace("0x", "") + ";");
        }
    }

    private javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void highlightNextAvailableCell(int col) {
        int rowToHighlight = getNextAvailableRow(col); // Ermittelt die unterste freie Zeile in der Spalte
        if (rowToHighlight >= 0 && !model.gameWon) { // Prüfen, ob es eine freie Zeile gibt
            Region cell = (Region) getNodeByRowColumnIndex(rowToHighlight, col);
            if (cell != null) {
                setElementStyle(cell, "highlight");
            }
        }
    }

    private void clearHighlightFromNextAvailableCell(int col) {
        int rowToHighlight = getNextAvailableRow(col); // Ermittelt die unterste freie Zeile in der Spalte
        if (rowToHighlight >= 0) { // Prüfen, ob es eine freie Zeile gibt
            Region cell = (Region) getNodeByRowColumnIndex(rowToHighlight, col);
            if (cell != null) {
                setElementStyle(cell, "default");
            }
        }
    }

    private int getNextAvailableRow(int col) {
        for (int row = model.getROWS() - 1; row >= 0; row--) { // Von unten nach oben prüfen
            if (model.isCellFree(row, col)) { // Prüft, ob die Zelle verfügbar ist
                return row;
            }
        }
        return -1; // Keine verfügbare Zelle gefunden
    }

    private Region getNodeByRowColumnIndex(int row, int col) {
        for (var child : boardGrid.getChildren()) {
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                return (Region) child;
            }
        }
        return null;
    }
    private void setElementStyle(Region element, String cssClass){
        element.getStyleClass().clear();
        element.setStyle("");
        element.getStyleClass().add(cssClass);
    }
}

