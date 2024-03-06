package game.breakout.entities;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import display.engine.shapes.Rectangle;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;

public class Player extends Entity {
	public static final Image DEFAULT_IMAGE = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "player.png").getImage();
	public static final Color DEFAULT_COLOR = Color.WHITE;
	public static final int DEFAULT_SIZE = 150;
	public static final int DEFAULT_POS_X = 300;
	public static final int DEFAULT_POS_Y = 300;
	public static final int MOVE_SPEED = 10;

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
        int posX, int posY,
        int size
    ) {
		super(new Rectangle(color, posX, posY, size, 10));
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
        int posX, int posY,
        int size
    ) {
		super(new Rectangle(image, posX, posY, size, size/4));
    }

	/**
	 * Instantiates a new Player
	 * 
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 * @param size the size of the player
	 */
	public Player(int posX, int posY, int size) {
		this(DEFAULT_IMAGE, posX, posY, size);
	}

	/**
	 * Instantiates a new Player
	 * 
	 * @param posX the initial x position of the player
	 * @param posY the initial y position of the player
	 */
	public Player(int posX, int posY) {
		this(DEFAULT_IMAGE, posX, posY, DEFAULT_SIZE);
	}

	/**
	 * Instantiates a new Player
	 */
	public Player() {
		this(DEFAULT_IMAGE, DEFAULT_POS_X, DEFAULT_POS_Y, DEFAULT_SIZE);
	}
}
