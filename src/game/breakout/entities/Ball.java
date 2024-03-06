package game.breakout.entities;

import java.awt.Color;
import display.engine.rules.GraphicalObject;
import java.awt.Image;
import javax.swing.ImageIcon;
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
	public static final int MOVE_SPEED = 5;
	public int angle; // it will be used later
	public boolean isMoving;



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



	public boolean getIsMoving(){
		return this.isMoving;
	}

	public void setIsMoving(boolean b){
		this.isMoving = b;
	}

	/**
	 * Reverse the direction of the ball
	 *
	public void reverseDirectionBall(GamePanel panel, int speed){
		int[] boundaries = this.getRepresentation().getBoundaries();
		switch (this.getDirectionBall()) {
			case UP_LEFT:
				if ((boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < WALL_WIDTH)) this.setDirectionBall(DirectionBall.UP_RIGHT);
				else this.setDirectionBall(DirectionBall.DOWN_LEFT);
				break;
			case UP_RIGHT:
				if ((boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getGameZone().getWidth()-WALL_WIDTH)) this.setDirectionBall(DirectionBall.UP_LEFT);
				else this.setDirectionBall(DirectionBall.DOWN_RIGHT);
				break;
			case DOWN_LEFT:
				if ((boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < WALL_WIDTH)) this.setDirectionBall(DirectionBall.DOWN_RIGHT);
				else this.setDirectionBall(DirectionBall.UP_LEFT);
				break;
			case DOWN_RIGHT:
				if ((boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getGameZone().getWidth()-WALL_WIDTH)) this.setDirectionBall(DirectionBall.DOWN_LEFT);
				else this.setDirectionBall(DirectionBall.UP_RIGHT);
				break;
			default:
				break;
		}
	}*/

	/**
	 * handles taking care of collisions based on which side the ball was hit from
	 *
	public void DirectionalCollision(GamePanel panel, int speed ,Direction hitPosition){
		switch (hitPosition) {
			case UP:
				this.reverseVerticalMomentum();
				break;
			case LEFT:
				this.reverseHorizontalMomentum();
				break;
			case RIGHT:
				this.reverseHorizontalMomentum();
				break;
			case DOWN:
				this.reverseVerticalMomentum();
				break;
			default:
				break;
		}
	}*/

	/**
	 * handles when the ball is hit from its side
	 * @param panel
	 * @param speed
	 *
	public void sideCollision(GamePanel panel, int speed){
		switch (this.getDirectionBall()) {
			case UP_LEFT: this.setDirectionBall(DirectionBall.UP_RIGHT);
				break;
			case UP_RIGHT: this.setDirectionBall(DirectionBall.UP_LEFT);
				break;
			case DOWN_LEFT: this.setDirectionBall(DirectionBall.DOWN_RIGHT);
				break;
			case DOWN_RIGHT: this.setDirectionBall(DirectionBall.DOWN_LEFT);
				break;
			default:
				break;
		}
	}*/


	public boolean willLoose(GamePanel panel, int speed){
		int [] boundaries = this.getRepresentation().getBoundaries();
		return boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed > panel.getGameZone().getHeight();
	}


	/**
	 * Checks if the entity will be off the screen if it moves in a given direction
	 * 
	 * @param speed the number of pixels the entity will move
	 * @param direction the direction in which the entity will move
	 * 
	 * @return true if the entity will be off the screen, false otherwise
	 */
	@Override
	public boolean willBeOffScreen(GamePanel panel,int speed) {
		int[] boundaries = this.getRepresentation().getBoundaries();
		if(forceY<0){
			if(boundaries[GraphicalObject.Boundary.MIN_Y.ordinal()] - forceY*speed < WALL_WIDTH){
				reverseVerticalMomentum();
				return true;
			}
		}
		if(forceY>0){
			if(boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + forceY*speed > panel.getGameZone().getHeight()){
				reverseVerticalMomentum();
				return true;
			}
		}
		if(forceX<0){
			if(boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] + forceX*speed < WALL_WIDTH){
				reverseHorizontalMomentum();
				return true;
			}
		}
		if(forceX>0){
			if(boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + forceX*speed > panel.getGameZone().getWidth()-WALL_WIDTH){
				reverseHorizontalMomentum();
				return true;
			}
		}
		return false;
	}

	
}
