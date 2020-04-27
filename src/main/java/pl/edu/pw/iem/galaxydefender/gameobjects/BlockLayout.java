package pl.edu.pw.iem.galaxydefender.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;
import static pl.edu.pw.iem.galaxydefender.Config.*;

public class BlockLayout extends GameObject {

    private double velocity = blocksVelocity;
    private List<Block> sets;
    private final int setCoordinates[][];
    private int currentSet;
    private int blockSize = primarySize;
    private final int randX;

    public BlockLayout(Pane gamePane, int[][] coordinateSets) {
        super(gamePane);
        setCoordinates = coordinateSets;
        sets = new ArrayList<>();
        setPattern();
        randX = randomPosition();

        sets.add(new Block(0, 0, blockSize, gamePane, randX, 0));
        sets.add(new Block(setCoordinates[currentSet][0], setCoordinates[currentSet][1], blockSize, gamePane, randX, 0));
        sets.add(new Block(setCoordinates[currentSet][2], setCoordinates[currentSet][3], blockSize, gamePane, randX, 0));
        sets.add(new Block(setCoordinates[currentSet][4], setCoordinates[currentSet][5], blockSize, gamePane, randX, 0));
    }

    private void setPattern() {
        Random generator = new Random();
        currentSet = generator.nextInt(19);
    }

    public void move() {
        for (Block block : sets) {
            block.changePosition(velocity);
        }
    }

    private int randomPosition() {
        int max = 0;
        int min = 0;
        int rand;
        while (true) {
            rand = (new Random()).nextInt(800);
            for (int i = 0; i < 6; i = i + 2) {
                if (min >= setCoordinates[currentSet][i]) {
                    min = setCoordinates[currentSet][i];
                }
                if (max <= setCoordinates[currentSet][i]) {
                    max = setCoordinates[currentSet][i];
                }
            }
            if ((rand + (max + 1) * blockSize <= 800) && (rand + min * blockSize >= 0)) {
                return rand;
            }
        }
    }

    public boolean isVisibleForPlayer() {
        int counter = 0;
        for (Block block : sets) {
            if (block.isVisibleForPlayer()) {
                counter++;
            }
        }
        if (counter == 0) {
            return false;
        }
        return true;
    }

    public List<Block> getSets() {
        return sets;
    }

}
