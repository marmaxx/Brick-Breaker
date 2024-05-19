package game.pong.entities;

import java.awt.Color;

import display.engine.rules.GraphicalObject;
import display.engine.rules.PhysicalObject;

import java.awt.Image;

import javax.swing.ImageIcon;

import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import display.view.brickbreakerview.GamePanel;
import game.pong.Pong;
import game.pong.entities.rules.Entity;

public class Ball extends Entity {
	public static final Image DEFAULT_IMAGE = new ImageIcon(Pong.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "cannonBall.png").getImage();
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final int DEFAULT_POS_X = 600;
	public static final int DEFAULT_POS_Y = 0;
	public static final int MOVE_SPEED = 5;
	public static boolean hasHitAWall;
	public boolean isMoving;

	/**
	 * Instantiates a new Ball with color, size, mass, position, and movability.
	 * 
	 * @param color    the color of the ball (ignored if the ball is represented by an image)
	 * @param size     the size of the ball
	 * @param mass     the mass of the ball
	 * @param position the initial position of the ball
	 * @param movable  indicates if the ball is movable or not
	 */
	public Ball(Color color, int size, double mass, Vector2D position, boolean movable) {
		super(mass, position, movable, new Rectangle(color, (int) position.getX(), (int) position.getY(), size, size));
	}

	/**
	 * Instantiates a new Ball with image, size, mass, position, and movability.
	 * 
	 * @param image    the image representing the ball
	 * @param size     the size of the ball
	 * @param mass     the mass of the ball
	 * @param position the initial position of the ball
	 * @param movable  indicates if the ball is movable or not
	 */
	public Ball(Image image, int size, double mass, Vector2D position, boolean movable) {
		super(mass, position, movable, new Rectangle(image, (int) position.getX(), (int) position.getY(), size, size));
	}

	/**
	 * Instantiates a new Ball with default image, size, mass, position, and movability.
	 * 
	 * @param mass     the mass of the ball
	 * @param position the initial position of the ball
	 * @param movable  indicates if the ball is movable or not
	 */
	public Ball(double mass, Vector2D position, boolean movable) {
		this(DEFAULT_IMAGE, DEFAULT_SIZE, mass, position, movable);
	}

	/**
	 * Instantiates a new Ball with color, position, and size.
	 * 
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 * @param posX  the initial x position of the ball
	 * @param posY  the initial y position of the ball
	 * @param size  the size of the ball
	 */
	public Ball(Color color, int posX, int posY, int size) {
		super(new Rectangle(color, posX, posY, size, size));
	}

	/**
	 * Instantiates a new Ball with image, position, and size.
	 * 
	 * @param image the image representing the ball
	 * @param posX  the initial x position of the ball
	 * @param posY  the initial y position of the ball
	 * @param size  the size of the ball
	 */
	public Ball(Image image, int posX, int posY, int size) {
		super(new Rectangle(image, posX, posY, size / 1, size));
	}

	/**
	 * Instantiates a new Ball with default image, position, and size.
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 */
	public Ball(int posX, int posY) {
		this(DEFAULT_IMAGE, posX, posY, DEFAULT_SIZE);
	}

	/**
	 * Instantiates a new Ball with default values.
	 */
	public Ball() {
		this(DEFAULT_IMAGE, DEFAULT_POS_X, DEFAULT_POS_Y, DEFAULT_SIZE);
	}

	/**
	 * Returns whether the ball is moving or not.
	 * 
	 * @return true if the ball is moving, false otherwise
	 */
	public boolean getIsMoving() {
		return this.isMoving;
	}

	/**
	 * Sets the ball to be moving or not.
	 * 
	 * @param b true to set the ball as moving, false otherwise
	 */
	public void setIsMoving(boolean b) {
		this.isMoving = b;
	}

	/**
	 * Checks if the ball will go out of bounds by taking into account the panel size of the game and the speed of the entity.
	 * 
	 * @param panel the game panel
	 * @param speed the speed of the ball
	 * @return true if the ball will go out of bounds, false otherwise
	 */
	public boolean willLoose(GamePanel panel, int speed) {
		int[] boundaries = this.getRepresentation().getNextBoundaries(this.getNextPos(speed));
		return boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed > panel.getGameZone().getHeight();
	}

	/**
	 * Calculates the impact point where the ball may collide with another ball.
	 * 
	 * @param ammo the other ball
	 * @return the impact point as a Vector2D
	 */
	public Vector2D getImpactPoint(Ball ammo) {
		double distanceX = Math.abs(this.getPosition().getX() + this.getRepresentation().getWidth() / 2 - ammo.getPosition().getX());
		double distanceY = Math.abs(this.getPosition().getY() + this.getRepresentation().getWidth() / 2 - ammo.getPosition().getY());
		Vector2D vectorImpact = new Vector2D(distanceX / 2, distanceY / 2);
		return vectorImpact.add(ammo.getPosition());
	}

	@Override
	public void collided(PhysicalObject object) {
		super.collided();
		if (object instanceof Wall) {
			if (((Wall) object).getTriggerRespawn()) {
				this.setHasHitAWall(true);
			}
		}
	}

	/**
	 * Determines if the ball has collided with a wall and resets the collision state.
	 * 
	 * @return true if the ball has collided with a wall, false otherwise
	 */
	public boolean collidingWithWall() {
		if (this.getHasHitAWall() == true) {
			this.setHasHitAWall(false);
			return true;
		}
		return false;
	}

	/**
	 * Checks if the ball is colliding with another physical object.
	 * 
	 * @param object the other physical object
	 * @return true if the ball is colliding with the object, false otherwise
	 */
	public boolean checkCollision(PhysicalObject object) {
		return this.getRepresentation().isColliding(object.getRepresentation());
	}

	/**
	 * Respawns the ball at the specified x and y coordinates.
	 * 
	 * @param pong the Pong game instance
	 * @param x    the x coordinate of the respawn location
	 * @param y    the y coordinate of the respawn location
	 */
	public void respawnBall(Pong pong, int x, int y) {
		Ball ball = new Ball(Ball.DEFAULT_IMAGE, 30, 50, new Vector2D(x, y), true);
		ball.setSpeed(new Vector2D(0, -0.3));
		ball.active = false;
		pong.setBall(ball);
		pong.getPanel().getGameZone().add(pong.getBall().getRepresentation());
		pong.getPhysicEngine().getPhysicalObjects().add(ball);
	}

	/**
	 * Returns whether the ball has hit a wall or not.
	 * 
	 * @return true if the ball has hit a wall, false otherwise
	 */
	public boolean getHasHitAWall() {
		return Ball.hasHitAWall;
	}

	/**
	 * Sets whether the ball has hit a wall or not.
	 * 
	 * @param b true if the ball has hit a wall, false otherwise
	 */
	public void setHasHitAWall(boolean b) {
		Ball.hasHitAWall = b;
	}
}
