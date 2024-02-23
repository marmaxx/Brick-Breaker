package game.breakout.entities;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.text.View;

//import display.engine.images.PaddleImage;
import display.engine.shapes.PaddleImage;
import display.engine.shapes.Rectangle;
import display.view.GamePanel;
import game.breakout.entities.rules.Entity;

public class Player extends Entity {
	public static final Color DEFAULT_COLOR = Color.WHITE;
	public static final int DEFAULT_SIZE = 150; //the size of the paddle image
	public static final int DEFAULT_POS_X = 300;
	public static final int DEFAULT_POS_Y = 300;
	public static final int MOVE_STEP = 10;
	private boolean moving_left;
	private boolean moving_right;


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
		super(new Rectangle(posX, posY, size, size/4, color));
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
	
	public void startMovingRight(){
		this.moving_right=true;
	}
	public void startMovingLeft(){
		this.moving_left=true;
	}
	public void stopMovingRight(){
		this.moving_right=false;
	}
	public void stopMovingLeft(){
		this.moving_left=false;
	}

    private void moveLeft() {
        int newX = this.getRepresentation().getPosX() - MOVE_STEP; //to check if the paddle is going to go out of bounds
        if (newX < 0) {
            newX = 0; // Prevent the paddle from moving off the screen
        }
        this.getRepresentation().setPosX(newX);
    }

    private void moveRight() {
        Dimension SCREEN_SIZE = GamePanel.SCREEN_FULL_SIZE; //to check if the paddle is going out of bounds (to the right of the screen)
        int newX = this.getRepresentation().getPosX() + MOVE_STEP;
        if (newX > SCREEN_SIZE.width - DEFAULT_SIZE) {
            newX = SCREEN_SIZE.width - DEFAULT_SIZE; // Prevent the paddle from moving off the screen
        }
        this.getRepresentation().setPosX(newX);
    }

	public void update(){ 
		if(moving_right) {
			moveRight();
		}else if(moving_left) {
			moveLeft();
		}
	}

}
