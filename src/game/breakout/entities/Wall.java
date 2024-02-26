package game.breakout.entities;

import java.awt.Color;
import display.engine.shapes.Rectangle;
import game.breakout.entities.rules.Entity;

public class Wall extends Entity {
    public static final Color DEFAULT_COLOR = Color.GRAY;

    public Wall(int posX, int posY, int width, int height){
        super(new Rectangle(DEFAULT_COLOR, posX, posY, width, height));
    }
}
