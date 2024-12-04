package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class VierGewinntController {

    public Label Spielertext;
    @FXML
    private GridPane gameBoard;

    private String currentPlayer = "Rot"; // Startspieler
    private final int ROWS = 6;
    private final int COLS = 7;

    @FXML
    public void initialize() {
        // Spielfeld mit leeren Feldern initialisieren
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Pane cell = new Pane();
                cell.setPrefSize(50, 50);
                cell.setStyle("-fx-border-color: black; -fx-background-color: white;");
                gameBoard.add(cell, col, row);
            }
        }
        Spielertext.setText(VierGewinntApplication.player1Name + " (" + currentPlayer + ")" + " ist an der Reihe!");
    }

    public void onColumnClick(ActionEvent actionEvent) {
        Button clickedButton = (Button) actionEvent.getSource();
        int columnIndex = GridPane.getColumnIndex(clickedButton);

        // Finde die erste freie Zelle von unten nach oben in der angeklickten Spalte
        for (int row = ROWS; row >= 1; row--) {
            Pane cell = (Pane) getNodeByRowColumnIndex(row, columnIndex);
            if (cell != null && cell.getStyle().contains("white")) {
                // Setze das Symbol des aktuellen Spielers
                cell.setStyle(currentPlayer.equals("Rot") ? "-fx-background-color: red;" : "-fx-background-color: yellow;");

                // Überprüfen, ob das Spiel gewonnen wurde
                if (checkWin(row, columnIndex)) {
                    showWinnerAlert();
                    resetGame();
                    return;
                }

                // Spielerwechsel
                currentPlayer = currentPlayer.equals("Rot") ? "Gelb" : "Rot";
                break;
            }
        }
        if(currentPlayer.equals("Rot")) {
            Spielertext.setText(VierGewinntApplication.player1Name + " (" + currentPlayer + ")" + " ist an der Reihe!");
        } else {
            Spielertext.setText(VierGewinntApplication.player2Name + " (" + currentPlayer + ")" + " ist an der Reihe!");
        }
    }

    private boolean checkWin(int row, int col) {
        // Prüfe in alle Richtungen: horizontal, vertikal, diagonal links unten nach rechts oben, diagonal links oben nach rechts unten
        return checkDirection(row, col, 0, 1) ||   // Horizontal
                checkDirection(row, col, 1, 0) ||   // Vertikal
                checkDirection(row, col, 1, 1) ||   // Diagonal (↘)
                checkDirection(row, col, 1, -1);    // Diagonal (↙)
    }

    private boolean checkDirection(int row, int col, int rowStep, int colStep) {
        int count = 1; // Zähle den aktuellen Stein mit
        String currentStyle = getNodeByRowColumnIndex(row, col).getStyle();

        // Prüfe in die positive Richtung
        count += countConsecutive(row, col, rowStep, colStep, currentStyle);

        // Prüfe in die negative Richtung (umdrehen des Steps)
        count += countConsecutive(row, col, -rowStep, -colStep, currentStyle);

        return count >= 4; // Wenn 4 oder mehr Steine in einer Reihe sind, gibt es einen Gewinner
    }

    private int countConsecutive(int row, int col, int rowStep, int colStep, String currentStyle) {
        int count = 0;
        int newRow = row + rowStep;
        int newCol = col + colStep;

        while (newRow >= 1 && newRow <= ROWS && newCol >= 0 && newCol < COLS) {
            Pane cell = getNodeByRowColumnIndex(newRow, newCol);

            if (cell != null && cell.getStyle().equals(currentStyle)) {
                count++;
                newRow += rowStep;
                newCol += colStep;
            } else {
                break;
            }
        }

        return count;
    }


    private void showWinnerAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spiel beendet");
        alert.setHeaderText(null);
        if(currentPlayer.equals("Rot")) {
            alert.setContentText(VierGewinntApplication.player1Name + " mit der Farbe " + currentPlayer + " hat gewonnen!");
        } else {
            alert.setContentText(VierGewinntApplication.player1Name + " mit der Farbe " + currentPlayer + " hat gewonnen!");
        }
        alert.setContentText("Der Spieler mit dem Symbol " + currentPlayer + " hat gewonnen!");
        alert.showAndWait();
    }

    private void resetGame() {
        // Setze das Spielfeld zurück
        for (javafx.scene.Node node : gameBoard.getChildren()) {
            if (node instanceof Pane) {
                ((Pane) node).setStyle("-fx-border-color: black; -fx-background-color: white;");
            }
        }
        currentPlayer = "○"; // Setze den Startspieler zurück
    }

    private Pane getNodeByRowColumnIndex(int row, int column) {
        for (javafx.scene.Node node : gameBoard.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return (Pane) node;
            }
        }
        return null;
    }
}
