package services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import utils.SceneLocator;

import java.util.Stack;

public class SceneManager {

    private static SceneManager instance;
    private Scene scene;
    private final LanguageManager languageManager;
    private String currentPath;
    private final Stack<String> history = new Stack<>();

    private SceneManager() {
        this.languageManager = LanguageManager.getInstance();
        this.currentPath = SceneLocator.WELCOME;
        this.scene = initScene();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private Scene initScene() {
        try {
            return new Scene(getParent(currentPath));
        } catch (Exception e) {
            e.printStackTrace();
            return new Scene(new ScrollPane());
        }
    }

    public static void load(String path) throws Exception {
        getInstance().loadParent(path);
    }

    public static void load(String path, ScrollPane pane) throws Exception {
        getInstance().loadParent(path, pane);
    }

    private void loadParent(String path) throws Exception {
        if (currentPath != null && !currentPath.equals(path)) {
            history.push(currentPath);
        }
        Parent parent = getParent(path);
        currentPath = path;
        scene.setRoot(parent);

        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.F1) {
                event.consume(); // Prevents other sub-controls from swallowing the keypress
                javafx.application.Platform.runLater(() -> {
                    try {
                        new controllers.BaseController() {}.handleHelp();
                    } catch (Exception e) {
                        System.err.println("Global F1 Error: " + e.getMessage());
                    }
                });
            }
        });
    }

    private void loadParent(String path, ScrollPane scrollPane) throws Exception {
        Parent parent = getParent(path);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(parent);
    }

    public static void reload() throws Exception {
        getInstance().reloadCurrentScene();
    }

    public static void loadPrevious() throws Exception {
        loadPrevious(1);
    }

    public static void loadPrevious(int steps) throws Exception {
        getInstance().loadPreviousScene(steps);
    }

    private void loadPreviousScene(int steps) throws Exception {
        if (history.isEmpty()) {
            throw new Exception("Nuk ka skenë paraardhëse!");
        }
        while (steps > 0 && !history.isEmpty()) {
            currentPath = history.pop();
            steps--;
        }
        load(currentPath);
    }

    private void reloadCurrentScene() throws Exception {
        load(currentPath);
    }

    private Parent getParent(String path) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setResources(languageManager.getResourceBundle());
        return loader.load();
    }

    public Scene getScene() {
        return scene;
    }
    public LanguageManager getLanguageManager() {
        return languageManager;
    }
}