package pl.edu.pw.iem.galaxydefender.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.edu.pw.iem.galaxydefender.Config;
import static pl.edu.pw.iem.galaxydefender.Config.blocksAccelerationFrequency;
import pl.edu.pw.iem.galaxydefender.gameobjects.BlockLayout;
import pl.edu.pw.iem.galaxydefender.gameobjects.Laser;
import pl.edu.pw.iem.galaxydefender.gameobjects.Ship;

public final class GameScreen extends Window {

    static boolean goEastRight, goEastLeft, goWestRight, goWestLeft, rotateWestRight, rotateWestLeft, rotateEastRight, rotateEastLeft, shootLeft, shootRight;
    private final List<Laser> lasers;
    private final List<BlockLayout> blocks;

    private Pane mainPane;
    private Button startGameButton;
    private ImageView leftPlayerShip;
    private ImageView rightPlayerShip;

    private Pane topPane;
    private Label leftPlayerNameLabel;
    private Label leftPlayerPointsLabel;
    private Label rightPlayerNameLabel;
    private Label rightPlayerPointsLabel;
    private Label levelLabel;

    private Pane bottomPane;
    private Label timeLabel;
    private ImageView saveGameButton;
    private ImageView pauseGameButton;

    private Pane gamePausePane;
    private Button resumeGameBoxButton;
    private Button saveGameBoxButton;
    private Button quitGameBoxButton;

    private Pane gameOverPane;
    private Label subtitleBoxLabel;
    private Label titleBoxLabel;
    private Button playAgainBoxButton;
    private Button quitBoxButton;

    private Ship leftPlayer;
    private Ship rightPlayer;

    private Timeline timeline;
    private int mins, secs, millis;
    private GameAnimation gameAnimation;
    private ShipManager shipManager;
    private int level;

    public GameScreen(Map<Integer, Window> windows, Stage primaryStage, List<Laser> lasers, List<BlockLayout> blocks) {
        super(windows, primaryStage);
        this.lasers = lasers;
        this.blocks = blocks;
        prepareScene();
    }

    @Override
    public void prepareScene() {
        scene.getStylesheets().add("/styles/gameScreen.css");

        mainPane = new Pane();
        mainPane.getStyleClass().add("mainPane");

        startGameButton = new Button("START");
        startGameButton.getStyleClass().add("startGameButton");
        startGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startGameButton.setVisible(false);
                saveGameButton.setDisable(false);
                pauseGameButton.setDisable(false);
                gameAnimation = new GameAnimation(blocks, lasers, mainPane, leftPlayer, rightPlayer, secs);
                shipManager = new ShipManager(lasers, goEastRight, goEastLeft, goWestRight, goWestLeft, rotateWestRight, rotateWestLeft, rotateEastRight, rotateEastLeft, shootLeft, shootRight, leftPlayer, rightPlayer);
                startGame();
            }
        });
        mainPane.getChildren().add(startGameButton);

        topPane = new Pane();
        topPane.getStyleClass().add("topPane");

        leftPlayerNameLabel = new Label();
        leftPlayerNameLabel.getStyleClass().add("leftPlayerNameLabel");
        topPane.getChildren().add(leftPlayerNameLabel);

        leftPlayerPointsLabel = new Label();
        leftPlayerPointsLabel.getStyleClass().add("leftPlayerPointsLabel");
        topPane.getChildren().add(leftPlayerPointsLabel);

        rightPlayerNameLabel = new Label();
        rightPlayerNameLabel.getStyleClass().add("rightPlayerNameLabel");
        topPane.getChildren().add(rightPlayerNameLabel);

        rightPlayerPointsLabel = new Label();
        rightPlayerPointsLabel.getStyleClass().add("rightPlayerPointsLabel");
        topPane.getChildren().add(rightPlayerPointsLabel);

        levelLabel = new Label();
        levelLabel.getStyleClass().add("levelLabel");
        topPane.getChildren().add(levelLabel);

        bottomPane = new Pane();
        bottomPane.getStyleClass().add("bottomPane");

        timeLabel = new Label();
        timeLabel.getStyleClass().add("timeLabel");
        bottomPane.getChildren().add(timeLabel);

        saveGameButton = new ImageView();
        saveGameButton.getStyleClass().add("saveGameButton");
        saveGameButton.setDisable(true);
        saveGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stopGame();
                pauseGameButton.setDisable(true);
                saveGameButton.setDisable(true);
                gamePausePane.setVisible(true);

                FileChooser saveFileChooser = new FileChooser();
                saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Galaxy Defender Game (*.gdg)", "*.gdg"));
                saveFileChooser.setInitialFileName("Galaxy-Defender-" + System.currentTimeMillis() + ".gdg");
                File selectedFile = saveFileChooser.showSaveDialog(null);

                if (selectedFile != null) {
                    try {
                        Config.saveGame(selectedFile, (GameScreen) getWindows().get(5));
                    } catch (FileNotFoundException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd");
                        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/icon.png"));
                        alert.getDialogPane().setPrefHeight(200);
                        alert.setHeaderText("Nie można zapisać pliku gry");
                        alert.setContentText("Gra nie została zapisana.");
                        alert.showAndWait();
                    }
                }
            }
        });
        bottomPane.getChildren().add(saveGameButton);

        pauseGameButton = new ImageView();
        pauseGameButton.getStyleClass().add("pauseGameButton");
        pauseGameButton.setDisable(true);
        pauseGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stopGame();
                pauseGameButton.setDisable(true);
                saveGameButton.setDisable(true);
                gamePausePane.setVisible(true);
            }
        });
        bottomPane.getChildren().add(pauseGameButton);

        leftPlayerShip = new ImageView();
        leftPlayerShip.getStyleClass().add("leftPlayerShip");
        leftPlayerShip.setX(40);
        leftPlayerShip.setFitWidth(50);
        leftPlayerShip.setFitHeight(76);
        mainPane.getChildren().add(leftPlayerShip);

        rightPlayerShip = new ImageView();
        rightPlayerShip.getStyleClass().add("rightPlayerShip");
        rightPlayerShip.setX(710);
        rightPlayerShip.setFitWidth(50);
        rightPlayerShip.setFitHeight(76);
        mainPane.getChildren().add(rightPlayerShip);

        gamePausePane = new Pane();
        gamePausePane.getStyleClass().add("boxPane");
        gamePausePane.setVisible(false);

        resumeGameBoxButton = new Button("WZNÓW");
        resumeGameBoxButton.getStyleClass().add("boxButton");
        resumeGameBoxButton.setId("resumeGameBoxButton");
        resumeGameBoxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gamePausePane.setVisible(false);
                pauseGameButton.setDisable(false);
                saveGameButton.setDisable(false);
                startGame();
            }
        });
        gamePausePane.getChildren().add(resumeGameBoxButton);

        saveGameBoxButton = new Button("ZAPISZ");
        saveGameBoxButton.getStyleClass().add("boxButton");
        saveGameBoxButton.setId("saveGameBoxButton");
        saveGameBoxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveGameButton.fireEvent(event);
            }
        });
        gamePausePane.getChildren().add(saveGameBoxButton);

        quitGameBoxButton = new Button("WYJDŹ");
        quitGameBoxButton.getStyleClass().add("boxButton");
        quitGameBoxButton.setId("quitGameBoxButton");
        quitGameBoxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gamePausePane.setVisible(false);
                gameOver();
            }
        });
        gamePausePane.getChildren().add(quitGameBoxButton);

        gameOverPane = new Pane();
        gameOverPane.getStyleClass().add("boxPane");
        gameOverPane.setVisible(false);

        subtitleBoxLabel = new Label("WYGRAŁ(A)");
        subtitleBoxLabel.getStyleClass().add("subtitleBoxLabel");
        gameOverPane.getChildren().add(subtitleBoxLabel);

        titleBoxLabel = new Label();
        titleBoxLabel.getStyleClass().add("titleBoxLabel");
        gameOverPane.getChildren().add(titleBoxLabel);

        playAgainBoxButton = new Button("JESZCZE RAZ");
        playAgainBoxButton.getStyleClass().add("boxButton");
        playAgainBoxButton.setId("playAgainBoxButton");
        playAgainBoxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playAgain(leftPlayer.getName(), rightPlayer.getName());
            }
        });
        gameOverPane.getChildren().add(playAgainBoxButton);

        quitBoxButton = new Button("WYJDŹ");
        quitBoxButton.getStyleClass().add("boxButton");
        quitBoxButton.setId("quitBoxButton");
        quitBoxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
        gameOverPane.getChildren().add(quitBoxButton);

        leftPlayer = new Ship(mainPane, leftPlayerShip, Color.LIME);
        rightPlayer = new Ship(mainPane, rightPlayerShip, Color.RED);

        level = 1;

        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (millis == 1000) {
                    secs++;
                    gameAnimation.setCurrentTime(gameAnimation.getCurrentTime() + 1);
                    if (gameAnimation.getCurrentTime() % blocksAccelerationFrequency == 0) {
                        level++;
                    }
                    millis = 0;
                }
                if (secs == 60) {
                    mins++;
                    secs = 0;
                }
                timeLabel.setText((((mins / 10) == 0) ? "0" : "") + mins + ":"
                        + (((secs / 10) == 0) ? "0" : "") + secs);
                millis++;
                leftPlayerPointsLabel.setText(String.valueOf(leftPlayer.getPoints()));
                rightPlayerPointsLabel.setText(String.valueOf(rightPlayer.getPoints()));
                levelLabel.setText(String.format("%05d", level));
                leftPlayerShip.toFront();
                rightPlayerShip.toFront();
                if (leftPlayer.getPoints() < 0 || rightPlayer.getPoints() < 0) {
                    stopGame();
                    gameOver();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        MoveControllerPressed controllerPressed = new MoveControllerPressed();
        MoveControllerReleased controllerReleased = new MoveControllerReleased();

        scene.setOnKeyPressed(controllerPressed);
        scene.setOnKeyReleased(controllerReleased);
        root.getChildren().addAll(mainPane, topPane, bottomPane, gamePausePane, gameOverPane);
    }

    public void initialLabelsValues() {
        leftPlayerNameLabel.setText(leftPlayer.getName());
        rightPlayerNameLabel.setText(rightPlayer.getName());
        leftPlayerPointsLabel.setText(String.valueOf(leftPlayer.getPoints()));
        rightPlayerPointsLabel.setText(String.valueOf(rightPlayer.getPoints()));
        timeLabel.setText((((mins / 10) == 0) ? "0" : "") + mins + ":"
                + (((secs / 10) == 0) ? "0" : "") + secs);
        levelLabel.setText(String.format("%05d", level));
    }

    private void startGame() {
        timeline.play();
        gameAnimation.getWait().play();
        gameAnimation.start();
        shipManager.start();
    }

    private void stopGame() {
        timeline.pause();
        gameAnimation.getWait().pause();
        gameAnimation.stop();
        shipManager.stop();
    }

    private void playAgain(String leftPlayerName, String rightPlayerName) {
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
        lasers.clear();
        blocks.clear();
        getWindows().replace(5, new GameScreen(getWindows(), getPrimaryStage(), lasers, blocks));
        ((GameScreen) (getWindows().get(5))).getLeftPlayer().setName(leftPlayerName);
        ((GameScreen) (getWindows().get(5))).getRightPlayer().setName(rightPlayerName);
        ((GameScreen) (getWindows().get(5))).initialLabelsValues();
        getPrimaryStage().setScene(getWindows().get(5).getScene());
    }

    private void gameOver() {
        if (leftPlayer.getPoints() == rightPlayer.getPoints()) {
            subtitleBoxLabel.setVisible(false);
            titleBoxLabel.setText("GAME OVER");
        } else if (leftPlayer.getPoints() > rightPlayer.getPoints()) {
            titleBoxLabel.setText(leftPlayer.getName());
        } else {
            titleBoxLabel.setText(rightPlayer.getName());
        }
        gameOverPane.setVisible(true);
    }

    public Ship getLeftPlayer() {
        return leftPlayer;
    }

    public Ship getRightPlayer() {
        return rightPlayer;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public void setMins(int mins) {
        this.mins = mins;
    }

    public void setSecs(int secs) {
        this.secs = secs;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
