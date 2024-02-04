package game.breakout.entities;

import java.awt.Color;

import display.engine.images.BallImage;
import game.breakout.entities.rules.Entity;

public class Ball extends Entity {
	public static final Color DEFAULT_COLOR = Color.GREEN;
	public static final int DEFAULT_SIZE = 300;
	public static final int DEFAULT_POS_X = 0;
	public static final int DEFAULT_POS_Y = 0;

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 */
    public Ball(
        int posX, int posY,
        int size,
		Color color
    ) {
        super(new BallImage(posX, posY, size, size, color));
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 */
	public Ball(int posX, int posY, int size) {
		this(posX, posY, size, DEFAULT_COLOR);
	}

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 */
	public Ball(int posX, int posY) {
		this(posX, posY, DEFAULT_SIZE, DEFAULT_COLOR);
	}

	/**
	 * Instantiates a new Ball
	 */
	public Ball() {
		this(DEFAULT_POS_X, DEFAULT_POS_Y, DEFAULT_SIZE, DEFAULT_COLOR);
	}
}
