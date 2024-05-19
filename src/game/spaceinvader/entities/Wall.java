package game.spaceinvader.entities;

import java.awt.Color;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import game.spaceinvader.entities.rules.Entity;

public class Wall extends Entity {
    public static final Color DEFAULT_COLOR = new Color(0,0,0,0);

    /**
     * Instantiates a new wall
     */
    public Wall(int width, int height, double mass, Vector2D position, boolean movable){
        super(mass,position,movable,new Rectangle(DEFAULT_COLOR, (int)position.getX(), (int)position.getY(), width, height));
    }

    /**
     * Instantiates a new wall
     */
    public Wall(int posX, int posY, int width, int height){
        super(new Rectangle(DEFAULT_COLOR, posX, posY, width, height));
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