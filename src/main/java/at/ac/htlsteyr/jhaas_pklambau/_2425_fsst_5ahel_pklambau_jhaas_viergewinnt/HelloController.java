package at.ac.htlsteyr.jhaas_pklambau._2425_fsst_5ahel_pklambau_jhaas_viergewinnt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}