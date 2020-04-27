package pl.edu.pw.iem.galaxydefender.gui;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class AboutGameScreen extends Window {

    private Pane mainPane;
    private Label titleLabel;
    private Label textLabel;
    private Button backButton;

    public AboutGameScreen(Map<Integer, Window> windows, Stage primaryStage) {
        super(windows, primaryStage);
        prepareScene();
    }

    @Override
    public void prepareScene() {
        scene.getStylesheets().add("/styles/aboutGameScreen.css");

        mainPane = new Pane();
        mainPane.getStyleClass().add("mainPane");

        titleLabel = new Label("O GRZE");
        titleLabel.getStyleClass().add("titleLabel");
        mainPane.getChildren().add(titleLabel);

        textLabel = new Label("Galaxy Defender jest grą wieloosobową opartą na rywalizacji. W grze bierze udział dwóch zawodników, każdy z\u00A0nich reprezentowany jest przez statek.\n"
                + "Zadaniem graczy jest zdobycie jak największej liczby punktów i pokonanie w ten sposób przeciwnika.\n"
                + "Do poruszania się służą przyciski:\n"
                + "• dla lewego A, D, Q, E, S,\n"
                + "• dla prawego J, L, U, O, K.\n"
                + "Odpowiednio od lewej: poruszanie się w lewo, poruszanie się w prawo, obrót w lewo, obrót w prawo i strzał.\n"
                + "Gra ulega utrudnieniu co określony okres czasu i zostaje to ukazane na ekranie przez zwiększenie levela.\n"
                + "Jeżeli klocek lub ich blok spadnie poniżej linii statków, odejmowana zostaje zsumowana liczba punktów za klocki obydwu graczom.\n"
                + "Uwaga, jeżeli wynik punktowy jednego z graczy będzie liczbą ujemną, gra zakończy się, a owy zawodnik przegra.\n"
                + "\nAutorzy:\n"
                + "Daniel Daczko & Jakub Czajka\n"
                + "ⓒ Politechnika Warszawska 2019");
        textLabel.setWrapText(true);
        textLabel.getStyleClass().add("textLabel");
        mainPane.getChildren().add(textLabel);

        backButton = new Button("WRÓĆ");
        backButton.getStyleClass().add("backButton");
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getPrimaryStage().setScene(getWindows().get(1).getScene());
            }
        });
        mainPane.getChildren().add(backButton);

        root.getChildren().addAll(mainPane);
    }

}
