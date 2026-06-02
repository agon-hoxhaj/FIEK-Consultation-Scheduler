package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import services.SceneManager;
import utils.SceneLocator;

public class AdministrataController extends BaseController {

    @FXML
    private ScrollPane rightPane;

    @FXML
    public void handleDashboardClick() {
        try {
            SceneManager.load(SceneLocator.DASHBOARD, rightPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfesoriClick() {
        try {
            SceneManager.load(SceneLocator.ADMINISTRATA_PROFESOERT, rightPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStudentiClick() {
        try {
            SceneManager.load(SceneLocator.ADMINISTRATA_STUDENTAT, rightPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}