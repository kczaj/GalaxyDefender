package pl.edu.pw.iem.galaxydefender.gui;

import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Window {

    protected Group root;
    protected Scene scene;
    public static final int sceneHeight = 600;
    public static final int sceneWidth = 800;
    private final Map<Integer, Window> windows;
    private final Stage primaryStage;

    public Window(Map<Integer, Window> windows, Stage primaryStage) {
        root = new Group();
        scene = new Scene(root, sceneWidth, sceneHeight);
        this.windows = windows;
        this.primaryStage = primaryStage;
    }

    public abstract void prepareScene();

    public Scene getScene() {
        return scene;
    }

    public Map<Integer, Window> getWindows() {
        return windows;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
