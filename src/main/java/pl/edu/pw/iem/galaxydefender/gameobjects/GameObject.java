package pl.edu.pw.iem.galaxydefender.gameobjects;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public abstract class GameObject {

    protected Pane gamePane;

    public GameObject(Pane gamePane) {
        this.gamePane = gamePane;
    }

    protected void addToGame(Shape gameObject) {
        gamePane.getChildren().add(gameObject);
    }

    protected void removeFromGame(Shape gameObject) {
        gamePane.getChildren().remove(gameObject);
    }

    public Pane getGamePane() {
        return gamePane;
    }

}
