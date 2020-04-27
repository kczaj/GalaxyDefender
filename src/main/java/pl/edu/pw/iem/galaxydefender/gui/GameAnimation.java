package pl.edu.pw.iem.galaxydefender.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import static pl.edu.pw.iem.galaxydefender.Config.blocksAcceleration;
import static pl.edu.pw.iem.galaxydefender.Config.blocksAccelerationFrequency;
import static pl.edu.pw.iem.galaxydefender.Config.blocksVelocity;
import static pl.edu.pw.iem.galaxydefender.Config.coordinateSets;
import static pl.edu.pw.iem.galaxydefender.Config.primarySize;
import pl.edu.pw.iem.galaxydefender.gameobjects.Block;
import pl.edu.pw.iem.galaxydefender.gameobjects.BlockLayout;
import pl.edu.pw.iem.galaxydefender.gameobjects.GameObject;
import pl.edu.pw.iem.galaxydefender.gameobjects.Laser;
import pl.edu.pw.iem.galaxydefender.gameobjects.Ship;

public class GameAnimation extends AnimationTimer {

    private final List<BlockLayout> blocks;
    private final List<Laser> lasers;
    private final Pane gamePane;
    private final PauseTransition wait;
    private int currentTime;
    private int assistantTime;
    private int timeToSpawn;
    private final Ship leftPlayer;
    private final Ship rightPlayer;

    public GameAnimation(List<BlockLayout> blocks, List<Laser> lasers, Pane gamePane, Ship leftPlayer, Ship rightPlayer, int currentTime) {
        this.currentTime = currentTime;
        this.timeToSpawn = 10;
        this.blocks = blocks;
        this.lasers = lasers;
        this.gamePane = gamePane;
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;
        blocks.add(new BlockLayout(this.gamePane, coordinateSets));
        wait = new PauseTransition(Duration.seconds(timeToSpawn));
        wait.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addBlockLayout();
                wait.playFromStart();
            }
        });
        wait.play();
    }

    @Override
    public void handle(long now) {
        detectHit();
        moveObjects();
        if (assistantTime != currentTime) {
            assistantTime = currentTime;
            checkLevelUp();
        }
    }

    private void detectHit() {
        List<GameObject> objectsToRemove = new ArrayList<>();
        for (BlockLayout blockLayout : blocks) {
            for (Block block : blockLayout.getSets()) {
                for (Laser laser : lasers) {
                    if (laser.getLaserBeam().getX() >= block.getBlock().getX()
                            && laser.getLaserBeam().getX() <= block.getBlock().getX() + block.getBlockSize()
                            && laser.getLaserBeam().getY() >= block.getBlock().getY()
                            && laser.getLaserBeam().getY() <= block.getBlock().getY() + block.getBlockSize()) {
                        laser.getOwner().addPoints(block.getPoints());
                        laser.removeYourself();
                        block.removeYourself();
                        objectsToRemove.add(block);
                        objectsToRemove.add(laser);
                    }
                }
            }
            blockLayout.getSets().removeAll(objectsToRemove);
        }
        blocks.removeAll(objectsToRemove);
        lasers.removeAll(objectsToRemove);
    }

    private void moveBlocks() {
        List<BlockLayout> blocksToDelete = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).isVisibleForPlayer()) {
                blocks.get(i).move();
            } else {
                int pointsToBeDeleted = 0;
                for (Block block : blocks.get(i).getSets()) {
                    pointsToBeDeleted += block.getPoints();
                }
                leftPlayer.addPoints(-pointsToBeDeleted);
                rightPlayer.addPoints(-pointsToBeDeleted);
                blocksToDelete.add(blocks.get(i));
            }
        }
        blocks.removeAll(blocksToDelete);
    }

    private void moveLaser() {
        List<Laser> lasersToDelete = new ArrayList<>();
        for (int i = 0; i < lasers.size(); i++) {
            if (lasers.get(i).isVisibleForPlayer()) {
                lasers.get(i).move();
            } else {
                lasersToDelete.add(lasers.get(i));
            }
        }
        lasers.removeAll(lasersToDelete);
    }

    private void moveObjects() {
        moveLaser();
        moveBlocks();
    }

    private void addBlockLayout() {
        blocks.add(new BlockLayout(this.gamePane, coordinateSets));
    }

    private void checkLevelUp() {
        if (currentTime % blocksAccelerationFrequency == 0) {
            if (primarySize - 2 > 0 && timeToSpawn - 1 > 0) {
                primarySize -= 2;
                blocksVelocity += blocksAcceleration;
                timeToSpawn -= 1;
                wait.setDuration(Duration.seconds(timeToSpawn));
            }
        }
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public PauseTransition getWait() {
        return wait;
    }

}
