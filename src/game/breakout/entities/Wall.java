package game.breakout.entities;

import java.awt.Color;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import game.breakout.entities.rules.Entity;

public class Wall extends Entity  {
    public static final long serialVersionUID = 14L;

    public static final Color DEFAULT_COLOR = Color.GRAY;

    /**
     * Instantiates a new wall
     */
    public Wall(int width, int height, double mass, Vector2D position, boolean movable){
        super(mass,position,movable,new Rectangle(Color.BLACK, (int)position.getX(), (int)position.getY(), width, height));
    }

    /**
     * Instantiates a new wall
     */
    public Wall(int posX, int posY, int width, int height){
        super(new Rectangle(new Color(30,30,30,0), posX, posY, width, height));
    }


    @Override
    public void collided(PhysicalObject object) {
        super.collided();
    }

    @Override
    public void resolveCollision(PhysicalObject objectB) {
        super.resolveCollision(null);
    }
}
