package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.LanguageManager;
import services.SceneManager;
import services.SessionManager;
import services.StageManager;
import utils.SceneLocator;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class BaseController {
    protected final LanguageManager languageManager = LanguageManager.getInstance();

    @FXML
    protected void handleButtonGjuhaClick() throws Exception {
        Locale currentLocale = languageManager.getLocale();

        if ("en".equals(currentLocale.getLanguage())) {
            currentLocale = new Locale("sq");
        } else {
            currentLocale = new Locale("en");
        }
        loadLanguage(currentLocale);
    }

    @FXML
    protected void loadLanguage(Locale locale) throws Exception {
        languageManager.setLocale(locale);
        SceneManager.reload();
    }

    @FXML
    private void handleGoBack() throws Exception {
        SceneManager.loadPrevious();
    }

    @FXML
    private void handleSignOut() throws Exception {
        SessionManager.getInstance().clearSession();
        SceneManager.load(SceneLocator.WELCOME);
    }

    @FXML
    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public Optional<ButtonType> showConfirmAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    @FXML
    protected void navigateToStudentInterface() throws Exception {
        SceneManager.load(SceneLocator.STUDENTI);
    }

    @FXML
    protected void navigateToProfessorInterface() throws Exception {
        SceneManager.load(SceneLocator.PROFESORI);
    }

    @FXML
    protected void navigateToAdminInterface() throws Exception {
        SceneManager.load(SceneLocator.ADMINISTRATA);
    }

    @FXML
    protected void navigateToRegisterStudent() throws Exception {
        SceneManager.load(SceneLocator.REGISTER_STUDENT);
    }

    @FXML
    protected void navigateToRegisterProfesor() throws Exception {
        SceneManager.load(SceneLocator.REGISTER_PROFESSOR);
    }

    @FXML
    protected void navigateToRegisterAdmin() throws Exception {
        SceneManager.load(SceneLocator.REGISTER_ADMIN);
    }

    @FXML
    public void handleHelp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HelpModal.fxml"));

            ResourceBundle bundle = languageManager.getResourceBundle();
            loader.setResources(bundle);

            Parent root = loader.load();

            Stage helpStage = new Stage();

            helpStage.initModality(Modality.APPLICATION_MODAL);


            String windowTitle = "en".equals(languageManager.getLocale().getLanguage()) ? "Help & Guide" : "Ndihmë";
            helpStage.setTitle(windowTitle);

            helpStage.setScene(new Scene(root));
            helpStage.setResizable(false);
            helpStage.getScene().addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, escEvent -> {
                if (escEvent.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                    helpStage.close();
                    escEvent.consume();
                }
            });

            helpStage.showAndWait();

        } catch (Exception e) {
            System.err.println("Could not display help view workspace canvas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}