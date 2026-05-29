package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import services.LanguageManager;
import services.SceneManager;
import utils.SceneLocator;

import java.util.Locale;

public class WelcomeController extends BaseController{

    @FXML
    private void handleSignIn() throws Exception {
        SceneManager.load(SceneLocator.SIGN_IN);
    }

    @FXML
    private void handleRegister() throws Exception {
        SceneManager.load(SceneLocator.REGISTER);
    }

}
