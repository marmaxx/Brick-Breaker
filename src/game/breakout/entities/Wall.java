package game.breakout.entities;

import java.awt.Color;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import game.breakout.entities.rules.Entity;

/**
 * The Wall class represents a wall entity in the breakout game.
 * It extends the Entity class and implements the PhysicalObject interface.
 */
public class Wall extends Entity  {
    public static final long serialVersionUID = 14L;

    public static final Color DEFAULT_COLOR = Color.GRAY;

    /**
     * Instantiates a new wall with the specified width, height, mass, position, and movability.
     * 
     * @param width the width of the wall
     * @param height the height of the wall
     * @param mass the mass of the wall
     * @param position the position of the wall
     * @param movable indicates whether the wall is movable or not
     */
    public Wall(int width, int height, double mass, Vector2D position, boolean movable){
        super(mass,position,movable,new Rectangle(Color.BLACK, (int)position.getX(), (int)position.getY(), width, height));
    }

    /**
     * Instantiates a new wall with the specified position, width, and height.
     * 
     * @param posX the x-coordinate of the wall's position
     * @param posY the y-coordinate of the wall's position
     * @param width the width of the wall
     * @param height the height of the wall
     */
    public Wall(int posX, int posY, int width, int height){
        super(new Rectangle(new Color(30,30,30,0), posX, posY, width, height));
    }

    /**
     * This method is called when the wall collides with another physical object.
     * It overrides the collided method in the Entity class.
     * 
     * @param object the physical object that the wall collided with
     */
    @Override
    public void collided(PhysicalObject object) {
        super.collided();
    }

    /**
     * This method is called to resolve the collision between the wall and another physical object.
     * It overrides the resolveCollision method in the Entity class.
     * 
     * @param objectB the physical object that the wall collided with
     */
    @Override
    public void resolveCollision(PhysicalObject objectB) {
        super.resolveCollision(null);
    }
}
