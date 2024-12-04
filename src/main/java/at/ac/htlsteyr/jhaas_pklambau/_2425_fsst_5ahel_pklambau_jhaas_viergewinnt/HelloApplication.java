package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    public static String player1Name;
    public static String player2Name;

    @Override
    public void start(Stage stage) throws IOException {
        // Spielernamen abfragen
        player1Name = getPlayerName("Spieler 1");
        player2Name = getPlayerName("Spieler 2");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Vier Gewinnt");
        stage.setScene(scene);
        stage.show();
    }

    private String getPlayerName(String playerLabel) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Spielername eingeben");
        dialog.setHeaderText(playerLabel + " bitte Namen eingeben:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(playerLabel);
    }

    public static void main(String[] args) {
        launch();
    }
}
