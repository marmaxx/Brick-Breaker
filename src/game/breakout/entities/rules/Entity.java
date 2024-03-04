package game.breakout.entities.rules;

import display.engine.rules.GraphicalObject;
import display.engine.rules.GraphicalObject.Boundary;
import display.view.GamePanel;


public abstract class Entity {
    protected GraphicalObject representation;
	protected final static int WALL_WIDTH = 20;
	protected int DEFAULT_SPEED = 3;

	public boolean UP;
	public boolean DOWN;
	public boolean LEFT;
	public boolean RIGHT;

    
	/**
	 * Instantiates a new Entity
	 * 
	 * @param representation the graphical representation of the entity
	 */
    public Entity(GraphicalObject representation) {
		this.setRepresentation(representation);
		 
    }

	/**
	 * Gets the graphical representation of the entity
	 * 
	 * @return the graphical representation of the entity
	 */
    public GraphicalObject getRepresentation() {
        return this.representation;
    }

	/**
	 * Sets the graphical representation of the entity
	 * 
	 * @param representation the new graphical representation of the entity
	 */
    public void setRepresentation(GraphicalObject representation) {
        this.representation = representation;
    }
	/**
	 * TODO: Add comments for all this
	 */
	public boolean movingUp(){
		return UP;
	}
	public boolean movingDown(){
		return DOWN;
	}public boolean movingLeft(){
		return LEFT;
	}public boolean movingRight(){
		return RIGHT;
	}


	public void moveUp(){
		UP=true;
	}
	public void moveDown(){
		DOWN=true;
	}
	public void moveLeft(){
		LEFT=true;
	}
	public void moveRight(){
		RIGHT=true;
	}
	public void stopUp(){
		UP=false;
	}
	public void stopDown(){
		DOWN=false;
	}
	public void stopLeft(){
		LEFT=false;
	}
	public void stopRight(){
		RIGHT=false;
	}

	/**
	 * Checks if the entity will be off the screen if it moves in a given direction
	 * 
	 * @param speed the number of pixels the entity will move
	 * @param direction the direction in which the entity will move
	 * 
	 * @return true if the entity will be off the screen, false otherwise
	 */
	public boolean willBeOffScreen(GamePanel panel, int speed) {
		int[] boundaries = this.getRepresentation().getBoundaries();
		if(UP){
			if(boundaries[GraphicalObject.Boundary.MIN_Y.ordinal()] - speed < WALL_WIDTH){
				return true;
			}
		}
		if(DOWN){
			if(boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed > panel.getGameZone().getHeight()){
				return true;
			}
		}
		if(LEFT){
			if(boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < WALL_WIDTH){
				return true;
			}
		}
		if(RIGHT){
			if(boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getGameZone().getWidth()-WALL_WIDTH){
				return true;
			}
		}
		return false;
	}

	public void reverseVerticalMomentum(){
		UP=!UP;
		DOWN=!DOWN;
	}
	public void reverseHorizontalMomentum(){
		RIGHT=!RIGHT;
		LEFT=!LEFT;
	}

	/**
	 * Move the entity in its current direction
	 * 
	 * @param speed the number of pixels the entity will move
	 */
	public void move(int speed){
		if(UP){
			this.getRepresentation().setPosY(this.getRepresentation().getPosY() - speed);
		}
		if(DOWN){
			this.getRepresentation().setPosY(this.getRepresentation().getPosY() + speed);
		}
		if(LEFT){
			this.getRepresentation().setPosX(this.getRepresentation().getPosX() - speed);
		}
		if(RIGHT){
			this.getRepresentation().setPosX(this.getRepresentation().getPosX() + speed);
		}
	}
	/**
	 *
	 * @param speed the number of pixels the entity will move
	 * @return a list containing the next positions of the ball. first element in the list is X and second is Y
	 */
	public int[] getNextPos(int speed){
		int[] rep= new int[2];
		if(UP){
			rep[0] =  this.getRepresentation().getPosY() - speed;
		}
		if(DOWN){
			rep[0] =  this.getRepresentation().getPosY() + speed;
		}
		if(LEFT){
			rep[1] =  this.getRepresentation().getPosX() - speed;
		}
		if(RIGHT){
			rep[1] =  this.getRepresentation().getPosX() + speed;
		}
		return rep;
	}


	
}
