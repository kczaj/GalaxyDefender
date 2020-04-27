package pl.edu.pw.iem.galaxydefender.gui;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import static pl.edu.pw.iem.galaxydefender.gui.GameScreen.*;

public class MoveControllerPressed implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                goWestLeft = true;
                break;
            case D:
                goEastLeft = true;
                break;
            case Q:
                rotateWestLeft = true;
                break;
            case E:
                rotateEastLeft = true;
                break;
            case S:
                shootLeft = true;
                break;
            case J:
                goWestRight = true;
                break;
            case L:
                goEastRight = true;
                break;
            case U:
                rotateWestRight = true;
                break;
            case O:
                rotateEastRight = true;
                break;
            case K:
                shootRight = true;
                break;
        }
    }

}
