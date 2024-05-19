package game.pong.entities;

import java.awt.Color;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import game.pong.entities.rules.Entity;

public class Wall extends Entity {
    public static final Color DEFAULT_COLOR = Color.WHITE;

    public boolean triggerRespawn;

    /**
     * Instantiates a new wall
     */
    public Wall(int width, int height, double mass, Vector2D position, boolean movable, boolean bool){
        super(mass,position,movable,new Rectangle(DEFAULT_COLOR, (int)position.getX(), (int)position.getY(), width, height));
        this.triggerRespawn = bool;
    }

    /**
     * Instantiates a new wall
     */
    public Wall(int posX, int posY, int width, int height, boolean bool){
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

    /**
     * Get triggerRespawn
     * 
     * @return a boolean which determines wether or not the wall will trigger respawn
     */
    public boolean getTriggerRespawn(){
        return this.triggerRespawn;
    }

    /**
     * Set triggerRespawn
     */
    public void setTriggerRespawn(boolean bool){
        this.triggerRespawn = bool;
    }

}
