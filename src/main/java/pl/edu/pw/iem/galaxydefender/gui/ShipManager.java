package pl.edu.pw.iem.galaxydefender.gui;

import java.util.List;
import javafx.animation.AnimationTimer;
import static pl.edu.pw.iem.galaxydefender.Config.lasersCount;
import pl.edu.pw.iem.galaxydefender.gameobjects.Laser;
import pl.edu.pw.iem.galaxydefender.gameobjects.Ship;
import static pl.edu.pw.iem.galaxydefender.gui.GameScreen.*;

public class ShipManager extends AnimationTimer {

    private final Ship leftPlayer, rightPlayer;
    private final List<Laser> lasers;
    private int counterLeft;
    private int counterRight;

    public ShipManager(List<Laser> lasers, boolean goEastRight, boolean goEastLeft, boolean goWestRight, boolean goWestLeft, boolean rotateWestRight, boolean rotateWestLeft, boolean rotateEastRight, boolean rotateEastLeft, boolean shootLeft, boolean shootRight, Ship leftPlayer, Ship rightPlayer) {
        this.lasers = lasers;
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;
    }

    @Override
    public void handle(long now) {
        if (goWestLeft) {
            if (leftPlayer.getShip().getX() - leftPlayer.getVelocity() >= 0) {
                leftPlayer.moveLeft();
            }
        }
        if (goEastLeft) {
            if (leftPlayer.getShip().getX() + leftPlayer.getShip().getFitWidth() + leftPlayer.getVelocity() <= rightPlayer.getShip().getX()) {
                leftPlayer.moveRight();
            }
        }
        if (goEastRight) {
            if (rightPlayer.getShip().getX() + rightPlayer.getVelocity() + rightPlayer.getShip().getFitWidth() <= Window.sceneWidth) {
                rightPlayer.moveRight();
            }
        }
        if (goWestRight) {
            if (rightPlayer.getShip().getX() - rightPlayer.getVelocity() >= leftPlayer.getShip().getX() + leftPlayer.getShip().getFitWidth()) {
                rightPlayer.moveLeft();
            }
        }
        if (rotateEastLeft) {
            leftPlayer.rotateRight();
        }
        if (rotateWestLeft) {
            leftPlayer.rotateLeft();
        }
        if (rotateEastRight) {
            rightPlayer.rotateRight();
        }
        if (rotateWestRight) {
            rightPlayer.rotateLeft();
        }
        if (shootLeft) {
            if (counterLeft != lasersCount) {
                shoot(leftPlayer);
                counterLeft++;
            }
        } else {
            counterLeft = 0;
        }
        if (shootRight) {
            if (counterRight != lasersCount) {
                shoot(rightPlayer);
                counterRight++;
            }
        } else {
            counterRight = 0;
        }
    }

    private void shoot(Ship player) {
        lasers.add(player.fire());
    }
    
}
