package game.breakout.entities.rules;

import display.engine.rules.GraphicalObject;
import display.engine.rules.GraphicalObject.Boundary;
import display.view.GamePanel;


public abstract class Entity {
    protected GraphicalObject representation;
	protected final static int WALL_WIDTH = 20;

	public double forceX;
	public double forceY;


    
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
		return forceY>0;
	}
	public boolean movingDown(){
		return forceY<0;
	}public boolean movingLeft(){
		return forceX<0;
	}public boolean movingRight(){
		return forceX>0;
	}


	public void moveUp(){
		forceY=1;
	}
	public void moveDown(){
		forceY=-1;
	}
	public void moveLeft(){
		forceX=-1;
	}
	public void moveRight(){
		forceX=1;
	}
	
	public void stopRight(){
		if(forceX>0){
			forceX=0;
		}
	}
	public void stopLeft(){
		if(forceX<0){
			forceX=0;
		}
	}
	public void stopUp(){
		if(forceY>0){
			forceY=0;
		}
	}
	public void stopDown(){
		if(forceY<0){
			forceY=0;
		}
	}

	/**
	 * Checks if the entity will be off the screen if it moves in a given direction
	 * 
	 * @param speed the number of pixels the entity will move
	 * @param direction the direction in which the entity will move
	 * 
	 * @return true if the entity will be off the screen, false otherwise
	 */
	public boolean willBeOffScreen(GamePanel panel,int speed) {
		int[] boundaries = this.getRepresentation().getBoundaries();
		if(forceY>0){
			if(boundaries[GraphicalObject.Boundary.MIN_Y.ordinal()] - forceY*speed < WALL_WIDTH){
				return true;
			}
		}
		if(forceY<0){
			if(boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] - forceY*speed > panel.getGameZone().getHeight()){
				return true;
			}
		}
		if(forceX<0){
			if(boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] + forceX*speed < WALL_WIDTH){
				return true;
			}
		}
		if(forceX>0){
			if(boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + forceX*speed > panel.getGameZone().getWidth()-WALL_WIDTH){
				return true;
			}
		}
		return false;
	}



	public void reverseVerticalMomentum(){
		forceY=-forceY;
	}
	public void reverseHorizontalMomentum(){
		forceX=-forceX;
	}

	/**
	 * Move the entity in its current direction
	 * 
	 * @param speed the number of pixels the entity will move
	 */
	public void move(int speed){
		this.getRepresentation().setPosY(this.getRepresentation().getPosY() - (int)forceY*speed);
		this.getRepresentation().setPosX(this.getRepresentation().getPosX() + (int)forceX*speed);
	}
	/**
	 *
	 * @param speed the number of pixels the entity will move
	 * @return a list containing the next positions of the ball. first element in the list is X and second is Y
	 */
	public int[] getNextPos(int speed){
		int[] rep= new int[2];
		rep[1] =  this.getRepresentation().getPosY() - (int)forceY*speed;
		rep[0] =  this.getRepresentation().getPosX() + (int)forceX*speed;
		return rep;
	}


	
}
