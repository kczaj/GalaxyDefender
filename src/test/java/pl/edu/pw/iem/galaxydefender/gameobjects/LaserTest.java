package pl.edu.pw.iem.galaxydefender.gameobjects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LaserTest {

    private static Pane pane;
    private static Ship owner;

    @BeforeClass
    public static void setUpClass() {
        pane = new Pane();
        ImageView playerShip = new ImageView();
        playerShip.getStyleClass().add("leftPlayerShip");
        owner = new Ship(pane, playerShip, Color.BLACK);
    }

    @AfterClass
    public static void tearDownClass() {
        pane = null;
        owner = null;
    }

    @Test
    public void testIsVisibleForPlayer() {
        Laser instance = new Laser(pane, owner, Color.BLACK);
        instance.getLaserBeam().setX(-2);
        instance.getLaserBeam().setY(-2);
        boolean expResult = false;
        boolean result = instance.isVisibleForPlayer();
        assertEquals(expResult, result);
    }

    @Test
    public void testMove() {
        Laser instance = new Laser(pane, owner, Color.BLACK);
        Laser check = new Laser(pane, owner, Color.BLACK);
        instance.move();
        boolean expResult = true;
        boolean result = true;
        if (instance.getLaserBeam().getX() == check.getLaserBeam().getX()
                && instance.getLaserBeam().getY() == check.getLaserBeam().getY()) {
            result = false;
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveYourself() {
        Laser instance = new Laser(pane, owner, Color.BLACK);
        instance.removeYourself();
        boolean expResult = false;
        boolean result = false;
        if (pane.getChildren().contains(instance.getLaserBeam())) {
            result = true;
        }
        assertEquals(expResult, result);
    }

}
