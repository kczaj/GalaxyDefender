package pl.edu.pw.iem.galaxydefender;

import java.io.IOException;
import java.util.*;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.edu.pw.iem.galaxydefender.gameobjects.BlockLayout;
import pl.edu.pw.iem.galaxydefender.gameobjects.Laser;
import pl.edu.pw.iem.galaxydefender.gui.AboutGameScreen;
import pl.edu.pw.iem.galaxydefender.gui.GameScreen;
import pl.edu.pw.iem.galaxydefender.gui.InputNameScreen;
import pl.edu.pw.iem.galaxydefender.gui.LoadGameScreen;
import pl.edu.pw.iem.galaxydefender.gui.StartScreen;
import pl.edu.pw.iem.galaxydefender.gui.Window;

public class GalaxyDefender extends Application {

    private List<BlockLayout> blocks;
    private List<Laser> lasers;
    private Map<Integer, Window> windows;

    public GalaxyDefender() {
        this.blocks = new ArrayList<>();
        this.lasers = new ArrayList<>();
        this.windows = new TreeMap<>();
        try {
            new Config();
        } catch (IOException ex) {
            System.err.println("Błąd: " + ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/icon.png"));
            alert.getDialogPane().setPrefHeight(200);
            alert.setHeaderText("Nie można otworzyć pliku konfiguracyjnego");
            alert.setContentText("Program zostanie zamknięty.");
            alert.showAndWait();
            System.exit(1);
        } catch (NumberFormatException ex) {
            System.err.println("Błąd: " + ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/icon.png"));
            alert.getDialogPane().setPrefHeight(200);
            alert.setHeaderText("Nieprawidłowa zawartość pliku konfiguracyjnego");
            alert.setContentText("Program zostanie zamknięty.");
            alert.showAndWait();
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image("/images/icon.png");

        windows.put(1, new StartScreen(windows, primaryStage));
        windows.put(2, new InputNameScreen(windows, primaryStage));
        windows.put(3, new LoadGameScreen(windows, primaryStage));
        windows.put(4, new AboutGameScreen(windows, primaryStage));
        windows.put(5, new GameScreen(windows, primaryStage, lasers, blocks));

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Galaxy Defender");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setScene(windows.get(1).getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

}
