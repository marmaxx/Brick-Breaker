package game.pong.entities;

import java.awt.Color;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import game.pong.entities.rules.Entity;

/**
 * The Wall class represents a wall entity in the Pong game.
 * It extends the Entity class and implements the PhysicalObject interface.
 */
public class Wall extends Entity {
    public static final Color DEFAULT_COLOR = Color.WHITE;

    public boolean triggerRespawn;

    /**
     * Instantiates a new wall with the specified parameters.
     * 
     * @param width the width of the wall
     * @param height the height of the wall
     * @param mass the mass of the wall
     * @param position the position of the wall
     * @param movable determines if the wall is movable
     * @param bool determines if the wall will trigger respawn
     */
    public Wall(int width, int height, double mass, Vector2D position, boolean movable, boolean bool){
        super(mass,position,movable,new Rectangle(DEFAULT_COLOR, (int)position.getX(), (int)position.getY(), width, height));
        this.triggerRespawn = bool;
    }

    /**
     * Instantiates a new wall with the specified parameters.
     * 
     * @param posX the x-coordinate of the wall's position
     * @param posY the y-coordinate of the wall's position
     * @param width the width of the wall
     * @param height the height of the wall
     * @param bool determines if the wall will trigger respawn
     */
    public Wall(int posX, int posY, int width, int height, boolean bool){
        super(new Rectangle(DEFAULT_COLOR, posX, posY, width, height));
        this.triggerRespawn = bool;
    }


    @Override
    public void collided(PhysicalObject object) {
        super.collided();
    }

    @Override
    public void resolveCollision(PhysicalObject objectB) {
        super.resolveCollision(null);
    }

    /**
     * Get the value of triggerRespawn.
     * 
     * @return a boolean indicating whether or not the wall will trigger respawn
     */
    public boolean getTriggerRespawn(){
        return this.triggerRespawn;
    }

    /**
     * Set the value of triggerRespawn.
     * 
     * @param bool the boolean value to set
     */
    public void setTriggerRespawn(boolean bool){
        this.triggerRespawn = bool;
    }

}
