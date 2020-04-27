package pl.edu.pw.iem.galaxydefender.gameobjects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Ship extends GameObject {

    private int velocity = 2;
    private int rotateStep = 2;
    private int angle;
    private final int maxAngle = 60;
    private Color laserColor;
    private int points;
    private String name;
    private final ImageView ship;

    public Ship(Pane gamePane, ImageView ship, Color laserColor) {
        super(gamePane);
        this.ship = ship;
        this.laserColor = laserColor;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void moveLeft() {
        ship.setX(ship.getX() - velocity);
    }

    public void moveRight() {
        ship.setX(ship.getX() + velocity);
    }

    public void rotateLeft() {
        if (angle - rotateStep >= -maxAngle) {
            ship.setRotate(ship.getRotate() - rotateStep);
            angle -= rotateStep;
        }
    }

    public void rotateRight() {
        if (angle + rotateStep <= maxAngle) {
            ship.setRotate(ship.getRotate() + rotateStep);
            angle += rotateStep;
        }
    }

    public Laser fire() {
        Laser laser = new Laser(gamePane, this, laserColor);
        return laser;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getAngle() {
        return angle;
    }

    public ImageView getShip() {
        return ship;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
