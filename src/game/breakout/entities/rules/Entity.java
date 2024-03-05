package game.breakout.entities.rules;

import display.engine.rules.GraphicalObject;
import display.view.GamePanel;


public abstract class Entity {
    protected GraphicalObject representation;
	protected Direction direction;
	protected final static int WALL_WIDTH = 20;

	public enum Direction {
		NONE, UP, DOWN, LEFT, RIGHT
	}
    
	/**
	 * Instantiates a new Entity
	 * 
	 * @param representation the graphical representation of the entity
	 */
    public Entity(GraphicalObject representation) {
		this.setRepresentation(representation);
		this.setDirection(Direction.NONE);    
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
	 * Sets the direction of the entity
	 * 
	 * @param direction the new direction of the entity
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Gets the direction of the entity
	 * 
	 * @return the direction of the entity
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Reverse the direction of the entity
	 */
	public void reverseDirection() {
		switch (this.getDirection()) {
			case UP:
				this.setDirection(Direction.DOWN);
				break;
			case DOWN:
				this.setDirection(Direction.UP);
				break;
			case LEFT:
				this.setDirection(Direction.RIGHT);
				break;
			case RIGHT:
				this.setDirection(Direction.LEFT);
				break;
			case NONE:
				break;
			default:
				break;
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
	public boolean willBeOffScreen(GamePanel panel, int speed) {
		int[] boundaries = this.getRepresentation().getBoundaries();

		switch (this.getDirection()) {
			case UP:
				return (boundaries[GraphicalObject.Boundary.MIN_Y.ordinal()] - speed < WALL_WIDTH);
			case DOWN:
				return (boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed > panel.getGameZone().getHeight());
			case LEFT:
				return (boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < WALL_WIDTH);
			case RIGHT:
				return (boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getGameZone().getWidth()-WALL_WIDTH);
			case NONE:
				return false;
			default:
				return false;
		}
	}

	/**
	 * Move the entity in its current direction
	 * 
	 * @param speed the number of pixels the entity will move
	 */
	public void move(int speed) {
		switch (this.getDirection()) {
			case UP:
				this.getRepresentation().setPosY(this.getRepresentation().getPosY() - speed);
				break;
			case DOWN:
				this.getRepresentation().setPosY(this.getRepresentation().getPosY() + speed);
				break;
			case LEFT:
				this.getRepresentation().setPosX(this.getRepresentation().getPosX() - speed);
				break;
			case RIGHT:
				this.getRepresentation().setPosX(this.getRepresentation().getPosX() + speed);
				break;
			case NONE:
				break;
			default:
				break;
		}
	}
}
