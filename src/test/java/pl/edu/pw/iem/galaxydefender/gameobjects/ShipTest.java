package pl.edu.pw.iem.galaxydefender.gameobjects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipTest {

    private static Pane pane;
    private static ImageView playerShip;

    @BeforeClass
    public static void setUpClass() {
        pane = new Pane();
        playerShip = new ImageView();
        playerShip.getStyleClass().add("leftPlayerShip");
        playerShip.setX(0);
        playerShip.setY(0);
    }

    @AfterClass
    public static void tearDownClass() {
        pane = null;
        playerShip = null;
    }

    @Test
    public void testAddPoints() {
        int points = 3;
        Ship instance = new Ship(pane, playerShip, Color.BLACK);
        instance.addPoints(points);
        assertEquals(points, instance.getPoints());
    }

    @Test
    public void testMoveLeft() {
        Ship instance = new Ship(pane, playerShip, Color.BLACK);
        double x = instance.getShip().getX();
        double y = instance.getShip().getY();

        boolean expResult = true;
        boolean result = true;
        instance.moveLeft();
        if (instance.getShip().getX() == x
                && instance.getShip().getY() == y) {
            result = false;
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testMoveRight() {
        Ship instance = new Ship(pane, playerShip, Color.BLACK);
        double x = instance.getShip().getX();
        double y = instance.getShip().getY();
        instance.moveRight();
        boolean expResult = true;
        boolean result = true;
        if (instance.getShip().getX() == x
                && instance.getShip().getY() == y) {
            result = false;
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testRotateLeft() {
        Ship instance = new Ship(pane, playerShip, Color.BLACK);
        double rotate = instance.getAngle();

        boolean expResult = true;
        boolean result = true;
        instance.rotateLeft();
        if (instance.getAngle() == rotate) {
            result = false;
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testRotateRight() {
        Ship instance = new Ship(pane, playerShip, Color.BLACK);
        double rotate = instance.getAngle();

        boolean expResult = true;
        boolean result = true;
        instance.rotateRight();
        if (instance.getAngle() == rotate) {
            result = false;
        }
        assertEquals(expResult, result);
    }
}
