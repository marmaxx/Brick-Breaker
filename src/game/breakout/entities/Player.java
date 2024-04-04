package game.breakout.entities;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;

public class Player extends Entity {
	public static final Image DEFAULT_IMAGE = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "player.png").getImage();
	public static final Color DEFAULT_COLOR = Color.WHITE;
	public static final int DEFAULT_SIZE = 100;
	public static final int MIN_SIZE = DEFAULT_SIZE - (int)(0.2f * DEFAULT_SIZE);
	public static final int MAX_SIZE = DEFAULT_SIZE + (int)(0.3f * DEFAULT_SIZE);
	public static final int DEFAULT_POS_X = 300;
	public static final int DEFAULT_POS_Y = 300;
	public static final int DEFAULT_SPEED = 10;
	public static final int MIN_SPEED = DEFAULT_SPEED - (int)(0.5f * DEFAULT_SPEED);
	public static final int MAX_SPEED = DEFAULT_SPEED + (int)(1.0f * DEFAULT_SPEED);
	private Vector2D lastPos = new Vector2D(630, 700);

	/**
	 * Instantiates a new Player
	 * 
	 * @param color the color of the player object
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 * @param size the size of the player
	 */
    public Player(
		Color color,
        int size,
		int speed,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Rectangle(color, (int)position.getX(), (int)position.getY(), size, size/4, speed));
    }

	/**
	 * Instantiates a new Player
	 * 
	 * @param image the image representing the player
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 * @param size the size of the player
	 */
    public Player(
		Image image,
        int size,
		int speed,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Rectangle(image, (int)position.getX(), (int)position.getY(), size, size/4, speed));
    }

	/**
	 * Instantiates a new Player
	 * 
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 * @param size the size of the player
	 */
	public Player(
		 int size,
		 double mass, Vector2D position, boolean movable
	) {
		this(DEFAULT_IMAGE, size, DEFAULT_SPEED,mass,position,movable);
	}

	/**
	 * Instantiates a new Player
	 * 
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 */
	public Player(double mass, Vector2D position, boolean movable) {
		this(DEFAULT_IMAGE,  DEFAULT_SIZE, DEFAULT_SPEED,mass,position,movable);
	}


	// getters for the different variations of speed
	public int getMoveSpeed() {
		return this.getRepresentation().getSpeed();
	}

	public int getMinSpeed() {
		return MIN_SPEED;
	}

	public int getMaxSpeed() {
		return MAX_SPEED;
	}

	public Vector2D getLastPos(){
		return this.lastPos;
	}
	public void setLastPos(Vector2D vect){
		this.lastPos = vect;
	}



	/**
	 * updating speed using previous position and time 
	 */
	@Override
	public void updateVelocity(double deltaTime) {
			this.speed =((this.position.add(this.getLastPos().multiply(-1))).multiply(1/deltaTime));
	}

	@Override
	public void collided(PhysicalObject object) {
		super.collided();
	}

}
