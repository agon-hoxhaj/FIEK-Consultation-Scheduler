package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import services.SceneManager;
import utils.SceneLocator;

import java.io.IOException;

public class StudentiPageController extends BaseController {

    @FXML
    private Button lendetProf;

    @FXML
    private Button konsultTerm;

    @FXML
    private Button feedback;

    @FXML
    private ScrollPane rightPane;

    public void initialize() {
        SceneManager scmng = SceneManager.getInstance();
        scmng.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN),
                lendetProf::fire
        );
        scmng.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN),
                konsultTerm::fire
        );
        scmng.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN),
                feedback::fire
        );
    }

    @FXML
    private void handleKonsultimetTerminetClick() {
        try {
            SceneManager.load(SceneLocator.STUDENTI_KONSULTIMET_TERMINET,rightPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFeedbackClick() {
        try {
            SceneManager.load(SceneLocator.STUDENTI_FEEDBACK,rightPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLendetClick() {
        try {
            SceneManager.load(SceneLocator.STUDENTI_LENDETPROFESORET,rightPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
