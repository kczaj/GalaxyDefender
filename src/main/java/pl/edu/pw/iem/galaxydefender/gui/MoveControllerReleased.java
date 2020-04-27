package pl.edu.pw.iem.galaxydefender.gui;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import static pl.edu.pw.iem.galaxydefender.gui.GameScreen.*;

public class MoveControllerReleased implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                goWestLeft = false;
                break;
            case D:
                goEastLeft = false;
                break;
            case Q:
                rotateWestLeft = false;
                break;
            case E:
                rotateEastLeft = false;
                break;
            case S:
                shootLeft = false;
                break;
            case J:
                goWestRight = false;
                break;
            case L:
                goEastRight = false;
                break;
            case U:
                rotateWestRight = false;
                break;
            case O:
                rotateEastRight = false;
                break;
            case K:
                shootRight = false;
                break;
        }
    }

}
