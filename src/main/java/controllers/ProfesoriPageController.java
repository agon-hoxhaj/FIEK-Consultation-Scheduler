package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.SceneManager;
import utils.SceneLocator;

import java.io.IOException;

public class ProfesoriPageController extends BaseController{


    @FXML
    private ScrollPane rightPane;

    @FXML
    private void handleOraretClick() throws Exception {
        SceneManager.load(SceneLocator.PROFESORI_ORARET, rightPane);
    }
    @FXML
    private void handleStudentatClick() throws Exception{
        SceneManager.load(SceneLocator.PROFESORI_STUDENTAT ,rightPane);
    }
    @FXML
    private void handleKonsultimetClick() throws Exception{
        SceneManager.load(SceneLocator.PROFESORI_TERMINET,rightPane);
    }
    @FXML
    private void handleFeedbackClick() {
        try {
            SceneManager.load(SceneLocator.PROFESORI_FEEDBACK, rightPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
