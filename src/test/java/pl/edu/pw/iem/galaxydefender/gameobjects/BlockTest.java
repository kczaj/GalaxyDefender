package pl.edu.pw.iem.galaxydefender.gameobjects;

import javafx.scene.layout.Pane;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class BlockTest {

    private static Pane pane;
    public static int sceneHeight = 600;

    @BeforeClass
    public static void setUpClass() {
        pane = new Pane();
    }

    @AfterClass
    public static void tearDownClass() {
        pane = null;
    }

    @Test
    public void testChangePosition() {
        double velocity = 3.5;
        Block instance = new Block(1, 1, 20, pane, 200, 0);
        Block expBlock = new Block(1, 1, 20, pane, 200, 0);
        boolean expResult = true;
        instance.changePosition(velocity);
        if (expBlock.getBlock().getX() == instance.getBlock().getX()
                && expBlock.getBlock().getY() == instance.getBlock().getY()) {
            expResult = false;
        }
        assertEquals(true, expResult);
    }

    @Test
    public void testIsVisibleForPlayer() {
        Block instance = new Block(1, 1, 20, pane, 200, 601);
        boolean expResult = false;
        boolean result = instance.isVisibleForPlayer();
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveYourself() {
        Block instance = new Block(1, 1, 20, pane, 200, 0);
        instance.removeYourself();
        boolean expResult = false;
        boolean result = false;
        if (pane.getChildren().contains(instance.getBlock())) {
            result = true;
        }
        assertEquals(expResult, result);
    }
}
