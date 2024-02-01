package game.brickbreaker.entities;

import display.engine.rules.GraphicalObject;
import display.engine.shapes.Rectangle;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Entity implements KeyListener {

    protected static final int STEP_WIDTH = 10; // 10px
    

    public Player(
        int posX, int posY,
        int scaleX, int scaleY,
        int red, int blue, int green
    ) {
        super(posX, posY, scaleX, scaleY, red, blue, green);
        this.setRepresentation(new Rectangle(posX, posY, scaleX, scaleY, red, blue, green));
    }

    @Override

}
