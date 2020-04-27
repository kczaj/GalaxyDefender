package pl.edu.pw.iem.galaxydefender.gui;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class StartScreen extends Window {

    private Pane mainPane;
    private ImageView newGameButton;
    private ImageView loadGameButton;
    private ImageView aboutGameButton;

    public StartScreen(Map<Integer, Window> windows, Stage primaryStage) {
        super(windows, primaryStage);
        prepareScene();
    }

    @Override
    public void prepareScene() {
        scene.getStylesheets().add("/styles/startScreen.css");

        mainPane = new Pane();
        mainPane.getStyleClass().add("mainPane");

        newGameButton = new ImageView();
        newGameButton.getStyleClass().add("newGameButton");
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getPrimaryStage().setScene(getWindows().get(2).getScene());
            }
        });
        mainPane.getChildren().add(newGameButton);

        loadGameButton = new ImageView();
        loadGameButton.getStyleClass().add("loadGameButton");
        loadGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getPrimaryStage().setScene(getWindows().get(3).getScene());
            }
        });
        mainPane.getChildren().add(loadGameButton);

        aboutGameButton = new ImageView();
        aboutGameButton.getStyleClass().add("aboutGameButton");
        aboutGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getPrimaryStage().setScene(getWindows().get(4).getScene());
            }
        });
        mainPane.getChildren().add(aboutGameButton);

        root.getChildren().addAll(mainPane);
    }

}
