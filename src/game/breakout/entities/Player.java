package game.breakout.entities;

import java.awt.Color;

import display.engine.images.PaddleImage;
import game.breakout.entities.rules.Entity;

public class Player extends Entity {
	public static final Color DEFAULT_COLOR = Color.WHITE;
	public static final int DEFAULT_SIZE = 100;
	public static final int DEFAULT_POS_X = 300;
	public static final int DEFAULT_POS_Y = 300;
	public static final int MOVE_STEP = 10;
	
	private Direction direction=Direction.NONE;  //current player direction

	public static enum Direction {
		LEFT, RIGHT, NONE
	}

	/**
	 * Instantiates a new Player
	 * 
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 * @param size the size of the player
	 * @param color the color of the player (ignored if the player is represented by an image)
	 */
    public Player(
        int posX, int posY,
        int size,
		Color color
    ) {
		super(new PaddleImage(posX, posY, size*3, size, color));
    }

	/**
	 * Instantiates a new Player
	 * 
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 * @param size the size of the player
	 */
	public Player(int posX, int posY, int size) {
		this(posX, posY, size, DEFAULT_COLOR);
	}

	/**
	 * Instantiates a new Player
	 * 
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 */
	public Player(int posX, int posY) {
		this(posX, posY, DEFAULT_SIZE, DEFAULT_COLOR);
	}

	/**
	 * Instantiates a new Player
	 */
	public Player() {
		this(DEFAULT_POS_X, DEFAULT_POS_Y, DEFAULT_SIZE, DEFAULT_COLOR);
	}
	
	/**
	 * Instantiates a new Player
	 */
	public void setDirection(Direction d) {
		this.direction=d;
	}

	/**
	 * Moves the player in the specified direction
	 * 
	 * @param direction the direction in which the player should move
	 * @see Direction
	 */
	public void move (Direction direction) {
		switch (direction) {
			case LEFT:
				this.getRepresentation().setPosX(this.getRepresentation().getPosX() - MOVE_STEP);
				break;
			case RIGHT:
				this.getRepresentation().setPosX(this.getRepresentation().getPosX() + MOVE_STEP);
				break;
			case NONE:break;
		}
	}
	
	public void update(){
		this.move(direction);
	}
}
