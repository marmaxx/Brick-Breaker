package game.breakout.entities;

import java.awt.Color;
import display.engine.shapes.BallImage;
import display.engine.rules.GraphicalObject;
import java.awt.Image;
import javax.swing.ImageIcon;

import display.engine.rules.GraphicalObject;
import display.engine.shapes.Circle;
import display.view.GamePanel;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;

public class Ball extends Entity {
	public static final Image DEFAULT_IMAGE = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "ball.png").getImage();
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final int DEFAULT_POS_X = 600;
	public static final int DEFAULT_POS_Y = 0;
	public static final int MOVE_SPEED = 7;
	public DirectionBall direction;
	public int angle;

	public enum DirectionBall{
		UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
	}

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 */
    public Ball(
		Color color,
        int posX, int posY,
        int size
    ) {
		super(new Circle(color, posX, posY, size, size));
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 */
    public Ball(
		Image image,
        int posX, int posY,
        int size
    ) {
		super(new Circle(image, posX, posY, size, size));
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 */
	public Ball(int posX, int posY) {
		this(DEFAULT_IMAGE, posX, posY, DEFAULT_SIZE);

	}

	/**
	 * Instantiates a new Ball
	 */
	public Ball() {
		this(DEFAULT_IMAGE, DEFAULT_POS_X, DEFAULT_POS_Y, DEFAULT_SIZE);
	}

	/**
	 * Sets the direction of the entity
	 * 
	 * @param direction the new direction of the entity
	 */
	public void setDirectionBall(DirectionBall direction) {
		this.direction = direction;
	}

	/**
	 * @return the direction of the ball
	 */
	public DirectionBall getDirectionBall(){
		return this.direction;
	}

	public void reverseDirectionBall(GamePanel panel, int speed){
		int[] boundaries = this.getRepresentation().getBoundaries();
		switch (this.getDirectionBall()) {
			case UP_LEFT:
				if ((boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < 0)) this.setDirectionBall(DirectionBall.UP_RIGHT);
				else this.setDirectionBall(DirectionBall.DOWN_LEFT);
				break;
			case UP_RIGHT:
				if ((boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getWidth())) this.setDirectionBall(DirectionBall.UP_LEFT);
				else this.setDirectionBall(DirectionBall.DOWN_RIGHT);
				break;
			case DOWN_LEFT:
				if ((boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < 0)) this.setDirectionBall(DirectionBall.DOWN_RIGHT);
				else this.setDirectionBall(DirectionBall.UP_LEFT);
				break;
			case DOWN_RIGHT:
				if ((boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getWidth())) this.setDirectionBall(DirectionBall.DOWN_LEFT);
				else this.setDirectionBall(DirectionBall.UP_RIGHT);
				break;
			default:
				break;
		}
	}

	/**
	 * Checks if the ball will be off the screen if it moves in a given direction
	 * 
	 * @param speed the number of pixels the ball will move
	 * @param direction the direction in which the ball will move
	 * 
	 * @return true if the ball will be off the screen, false otherwise
	 */
	@Override
	public boolean willBeOffScreen(GamePanel panel, int speed) {
		int[] boundaries = this.getRepresentation().getBoundaries();
		switch (this.getDirectionBall()) {
			case UP_LEFT:
				return (boundaries[GraphicalObject.Boundary.MIN_Y.ordinal()] - speed < 0) || (boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < 0);
			case UP_RIGHT:
				return (boundaries[GraphicalObject.Boundary.MIN_Y.ordinal()] - speed < 0) || (boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getWidth());
			case DOWN_LEFT:
				return (boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed > panel.getHeight()) || (boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < 0);
			case DOWN_RIGHT: 
				return (boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed > panel.getHeight()) || (boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getWidth());
			default:
				return false;
		}
	}

	/**
	 * Move the ball in its current direction
	 * 
	 * @param speed the number of pixels the entity will move
	 */
	@Override 
	public void move(int speed){
		switch(this.getDirectionBall()){
			case UP_LEFT: 
				this.getRepresentation().setPosY(this.getRepresentation().getPosY() - speed);
				this.getRepresentation().setPosX(this.getRepresentation().getPosX() - speed);
				break;
			case UP_RIGHT:
				this.getRepresentation().setPosY(this.getRepresentation().getPosY() - speed);
				this.getRepresentation().setPosX(this.getRepresentation().getPosX() + speed);
				break;
			case DOWN_LEFT:
				this.getRepresentation().setPosY(this.getRepresentation().getPosY() + speed);
				this.getRepresentation().setPosX(this.getRepresentation().getPosX() - speed);
				break;
			case DOWN_RIGHT:
				this.getRepresentation().setPosY(this.getRepresentation().getPosY() + speed);
				this.getRepresentation().setPosX(this.getRepresentation().getPosX() + speed);
				break;
			default:
				break;
		}

	}
}
