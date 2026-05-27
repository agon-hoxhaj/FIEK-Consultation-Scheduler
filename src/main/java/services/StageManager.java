package services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageManager {
    private static StageManager instance;
    private Stage primaryStage;
    private Stage modalStage;

    private StageManager() {
    }

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setScene(SceneManager.getInstance().getScene());
        stage.setTitle("My Application");
        stage.setWidth(900);
        stage.setHeight(600);
        stage.show();
    }
    public void showModal(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setResources(SceneManager.getInstance().getLanguageManager().getResourceBundle());
            Parent root = loader.load();

            modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(primaryStage);
            modalStage.setTitle(title);
            modalStage.setResizable(false);
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void closeModal(){
        if (modalStage != null) {
            modalStage.close();
            modalStage = null;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
