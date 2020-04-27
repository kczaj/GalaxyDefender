package pl.edu.pw.iem.galaxydefender.gui;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.edu.pw.iem.galaxydefender.Config;

public final class LoadGameScreen extends Window {

    private Pane mainPane;
    private TextField pathTextField;
    private ImageView fileButton;
    private Button nextButton;

    private File file;

    public LoadGameScreen(Map<Integer, Window> windows, Stage primaryStage) {
        super(windows, primaryStage);
        prepareScene();
    }

    @Override
    public void prepareScene() {
        scene.getStylesheets().add("/styles/loadGameScreen.css");

        mainPane = new Pane();
        mainPane.getStyleClass().add("mainPane");

        pathTextField = new TextField("ŚCIEŻKA DO PLIKU");
        pathTextField.getStyleClass().add("pathTextField");
        pathTextField.setEditable(false);
        mainPane.getChildren().add(pathTextField);

        fileButton = new ImageView();
        fileButton.getStyleClass().add("fileButton");
        fileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser openFileChooser = new FileChooser();
                openFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Galaxy Defender Game (*.gdg)", "*.gdg"));
                file = openFileChooser.showOpenDialog(null);
                if (file != null) {
                    pathTextField.setText(file.getAbsolutePath());
                    pathTextField.end();
                }
            }
        });
        mainPane.getChildren().add(fileButton);

        nextButton = new Button("DALEJ");
        nextButton.getStyleClass().add("nextButton");
        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (file != null) {
                    try {
                        Config.loadGame(file, ((GameScreen) (getWindows().get(5))));
                    } catch (IOException ex) {
                        System.err.println("Błąd: " + ex.getMessage());
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd");
                        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/icon.png"));
                        alert.getDialogPane().setPrefHeight(200);
                        alert.setHeaderText("Nie można otworzyć pliku gry");
                        alert.setContentText("Gra nie została wczytana.");
                        alert.showAndWait();
                        return;
                    } catch (NumberFormatException ex) {
                        System.err.println("Błąd: " + ex.getMessage());
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd");
                        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/icon.png"));
                        alert.getDialogPane().setPrefHeight(200);
                        alert.setHeaderText("Nieprawidłowa zawartość pliku gry");
                        alert.setContentText("Gra nie została wczytana.");
                        alert.showAndWait();
                        return;
                    }
                    ((GameScreen) (getWindows().get(5))).initialLabelsValues();
                    getPrimaryStage().setScene(getWindows().get(5).getScene());
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ostrzeżenie");
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/icon.png"));
                    alert.getDialogPane().setPrefHeight(200);
                    alert.setHeaderText("Brak ścieżki do pliku");
                    alert.setContentText("Nie wybrano pliku do wczytania. Należy kliknąć w ikonę folderu i zlokalizować plik gry w formacie Galaxy Defender Game (*.gdg) na dysku.");
                    alert.showAndWait();
                }
            }
        });
        mainPane.getChildren().add(nextButton);

        root.getChildren().addAll(mainPane);
    }

}
