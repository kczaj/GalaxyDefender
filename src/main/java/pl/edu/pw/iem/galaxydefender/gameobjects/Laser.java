package pl.edu.pw.iem.galaxydefender.gameobjects;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Laser extends GameObject {

    private final int velocity = 7;
    private final int angle;
    private final Rectangle laserBeam;
    private final int laserLength = 26;
    private final int laserWidth = 2;
    private final Ship owner;

    public Laser(Pane gamePane, Ship owner, Color laserColor) {
        super(gamePane);
        this.angle = owner.getAngle();
        this.owner = owner;
        laserBeam = new Rectangle(owner.getShip().getX() + owner.getShip().getFitWidth() / 2, 460, laserWidth, laserLength);
        laserBeam.setFill(laserColor);
        laserBeam.setRotate(angle);
        laserBeam.setX(owner.getShip().getX() + owner.getShip().getFitWidth() / 2 + (owner.getShip().getFitHeight() / 2) * Math.cos(Math.toRadians(90 - angle)) - laserLength / 2 * Math.sin(Math.toRadians(angle)) * Math.cos(Math.toRadians(angle)));
        laserBeam.setY(460 - Math.sin(Math.toRadians(90 - angle)) * (owner.getShip().getFitHeight() / 2) + owner.getShip().getFitHeight() / 2 - laserLength / 2 * Math.sin(Math.toRadians(angle)) * Math.sin(Math.toRadians(angle)));

        addToGame(laserBeam);
    }

    public boolean isVisibleForPlayer() {
        if (laserBeam.getY() > 0 && laserBeam.getX() > 0) {
            return true;
        }
        removeFromGame(laserBeam);
        return false;
    }

    public void move() {
        double radians = Math.toRadians(angle);
        laserBeam.setX(laserBeam.getX() + Math.sin(radians) * velocity);
        laserBeam.setY(laserBeam.getY() - Math.cos(radians) * velocity);
    }

    public Rectangle getLaserBeam() {
        return laserBeam;
    }
    
    public void removeYourself(){
        removeFromGame(laserBeam);
    }

    public Ship getOwner() {
        return owner;
    }
    
}
