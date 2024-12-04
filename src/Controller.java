// Controller.java
import java.util.Scanner;

public class Controller {
    private Model model;
    private View view;
    private Scanner scanner;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        while (true) {
            view.printBoard(model.getBoard());
            view.printMessage("Spieler " + model.getCurrentPlayer() + ", wähle eine Spalte (0-6):");

            int column;
            try {
                column = scanner.nextInt();
            } catch (Exception e) {
                scanner.next(); // Eingabepuffer leeren
                view.printMessage("Ungültige Eingabe. Bitte eine Zahl zwischen 0 und 6 eingeben.");
                continue;
            }

            if (!model.makeMove(column)) {
                view.printMessage("Ungültiger Zug. Versuche es erneut.");
                continue;
            }

            if (model.checkWin()) {
                view.printBoard(model.getBoard());
                view.printMessage("Spieler " + model.getCurrentPlayer() + " hat gewonnen!");
                break;
            }

            if (model.isBoardFull()) {
                view.printBoard(model.getBoard());
                view.printMessage("Das Spiel endet unentschieden!");
                break;
            }

            model.switchPlayer();
        }
    }
}
