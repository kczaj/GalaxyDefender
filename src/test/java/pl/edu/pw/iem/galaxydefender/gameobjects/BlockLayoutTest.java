package pl.edu.pw.iem.galaxydefender.gameobjects;

import java.io.IOException;
import java.util.List;
import javafx.scene.layout.Pane;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.pw.iem.galaxydefender.Config;
import static pl.edu.pw.iem.galaxydefender.Config.coordinateSets;

public class BlockLayoutTest {

    private static Pane pane;
    public static int sceneHeight = 600;

    @BeforeClass
    public static void setUpClass() throws IOException {
        new Config();
        pane = new Pane();
    }

    @AfterClass
    public static void tearDownClass() {
        pane = null;
    }

    @Test
    public void testMove() {
        boolean expResult = false;
        boolean result = true;
        BlockLayout instance = new BlockLayout(pane, coordinateSets);
        List<Block> listBlock = instance.getSets();
        instance.move();
        for (Block blockOne : instance.getSets()) {
            for (Block blockTwo : listBlock) {
                if (blockOne.getBlock().getX() == blockTwo.getBlock().getX()
                        && blockOne.getBlock().getY() == blockTwo.getBlock().getY()) {
                    result = false;
                }
            }
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testIsVisibleForPlayer() {
        BlockLayout instance = new BlockLayout(pane, coordinateSets);
        for (Block block : instance.getSets()) {
            while (block.isVisibleForPlayer()) {
                block.changePosition(10);
            }
        }
        boolean expResult = false;
        boolean result = instance.isVisibleForPlayer();
        assertEquals(expResult, result);
    }

}
