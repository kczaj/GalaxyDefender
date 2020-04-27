package pl.edu.pw.iem.galaxydefender.gui;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class InputNameScreen extends Window {

    private Pane mainPane;
    private TextField leftPlayerTextField;
    private TextField rightPlayerTextField;
    private Button nextButton;

    public InputNameScreen(Map<Integer, Window> windows, Stage primaryStage) {
        super(windows, primaryStage);
        prepareScene();
    }

    @Override
    public void prepareScene() {
        scene.getStylesheets().add("/styles/inputNameScreen.css");

        mainPane = new Pane();
        mainPane.getStyleClass().add("mainPane");

        leftPlayerTextField = new TextField("GRACZ PIERWSZY");
        leftPlayerTextField.getStyleClass().add("playerTextField");
        leftPlayerTextField.setId("leftPlayerTextField");
        mainPane.getChildren().add(leftPlayerTextField);

        rightPlayerTextField = new TextField("GRACZ DRUGI");
        rightPlayerTextField.getStyleClass().add("playerTextField");
        rightPlayerTextField.setId("rightPlayerTextField");
        mainPane.getChildren().add(rightPlayerTextField);

        nextButton = new Button("DALEJ");
        nextButton.getStyleClass().add("nextButton");
        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (leftPlayerTextField.getText().matches("(.*)[żźćńółęąśŻŹĆĄŚĘŁÓŃ;](.*)") || rightPlayerTextField.getText().matches("(.*)[żźćńółęąśŻŹĆĄŚĘŁÓŃ;](.*)")) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Ostrzeżenie");
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/icon.png"));
                    alert.getDialogPane().setPrefHeight(200);
                    alert.setHeaderText("Niepoprawna nazwa gracza");
                    alert.setContentText("Pole nazwa gracza nie może zawierać polskich znaków ani symbolu średnika!");
                    alert.showAndWait();
                    return;
                }
                if (leftPlayerTextField.getText().isEmpty() || rightPlayerTextField.getText().isEmpty()) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Ostrzeżenie");
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/icon.png"));
                    alert.getDialogPane().setPrefHeight(200);
                    alert.setHeaderText("Niepoprawna nazwa gracza");
                    alert.setContentText("Pole nazwa gracza nie może być puste!");
                    alert.showAndWait();
                    return;
                }
                ((GameScreen) (getWindows().get(5))).getLeftPlayer().setName(leftPlayerTextField.getText().toUpperCase());
                ((GameScreen) (getWindows().get(5))).getRightPlayer().setName(rightPlayerTextField.getText().toUpperCase());
                ((GameScreen) (getWindows().get(5))).initialLabelsValues();
                getPrimaryStage().setScene(getWindows().get(5).getScene());
            }
        });
        mainPane.getChildren().add(nextButton);

        root.getChildren().addAll(mainPane);
    }

}
