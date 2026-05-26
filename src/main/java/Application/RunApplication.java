package Application;

import javafx.application.Application;
import javafx.stage.Stage;
import services.StageManager;

public class RunApplication extends Application {
    @Override
    public void start(Stage stage) {
        StageManager.getInstance().start(stage);
    }

    public static void main(String[] args) { launch(); }
}
