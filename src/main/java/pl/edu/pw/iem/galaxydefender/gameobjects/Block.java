package pl.edu.pw.iem.galaxydefender.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static pl.edu.pw.iem.galaxydefender.gui.Window.sceneHeight;

public class Block extends GameObject {

    private int x;
    private int y;
    private Rectangle block;
    private Color blockColor;
    private int blockSize;
    private List<Color> blockColorSet;
    private int points;

    public Block(int x, int y, int blockSize, Pane gamePane, int baseX, int baseY) {
        super(gamePane);
        prepareList();
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
        int randomColor = new Random().nextInt(4);
        blockColor = blockColorSet.get(randomColor);
        setPoints();
        setRectangle(baseX, baseY);
        addToGame(block);
    }

    public void changePosition(double velocity) {
        block.setY(block.getY() + velocity);
    }

    private void setRectangle(int baseX, int baseY) {
        block = new Rectangle(baseX + x * blockSize, baseY + y * blockSize, blockSize - 1, blockSize - 1);
        block.setX(baseX + x * blockSize);
        block.setY(baseY + y * blockSize);
        block.setFill(blockColor);
    }

    public boolean isVisibleForPlayer() {
        if (block.getY() < sceneHeight) {
            return true;
        }
        removeFromGame(block);
        return false;
    }

    private void prepareList() {
        blockColorSet = new ArrayList<>();
        blockColorSet.add(Color.AQUA);
        blockColorSet.add(Color.CADETBLUE);
        blockColorSet.add(Color.DARKCYAN);
        blockColorSet.add(Color.CRIMSON);
    }

    public Rectangle getBlock() {
        return block;
    }

    public int getBlockSize() {
        return blockSize;
    }
    public void removeYourself(){
        removeFromGame(block);
        block.setX(-1111);
        block.setY(-1111);
    }

    private void setPoints() {
       if(blockColor == blockColorSet.get(0)){
           points = 100;
       } else if (blockColor == blockColorSet.get(1)){
           points = 200;
       } else if(blockColor == blockColorSet.get(2)){
           points = 200;
       }else {
           points = 300;
       }
    }

    public int getPoints() {
        return points;
    }
    
}
