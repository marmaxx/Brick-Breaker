package game.breakout.entities;

import java.awt.Color;
import java.util.HashMap;
import java.util.Collections;

import display.engine.shapes.Rectangle;
import game.breakout.entities.rules.Entity;
import java.awt.geom.AffineTransform;


public class Brick extends Entity {
    protected boolean isDestroyed, dropBonus;
    protected int lifespan;
	private int PosX; 
	private int PosY;

	public static final int DEFAULT_POS_X = 1;
	public static final int DEFAULT_POS_Y = 100;
	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGHT = 20;

	public static final HashMap<Integer, Color> lifespans = new HashMap<Integer, Color>() {
		{
			put(0, Color.RED);
			put(1, Color.ORANGE);
			put(2, Color.YELLOW);
			put(3, Color.GREEN);
		}
	};

	public static final int MIN_LIFESPAN = Collections.min(lifespans.keySet());
	public static final int MAX_LIFESPAN = lifespans.size();

	/**
	 * Instantiates a new Brick
	 * 
	 * @param posX the initial x position of the brick
	 * @param posY the initial y position of the brick
	 * @param width the width of the brick
	 * @param height the height of the brick
	 * @param lifespan the lifespan of the brick, see the lifespans hashmap
	 * @param dropBonus whether the brick drops a bonus when destroyed
	 * 
	 * @throws IllegalArgumentException if the lifespan is not in the hashmap
	 * @throws IllegalArgumentException if the width or height is less than or equal to 0
	 * @throws IllegalArgumentException if the color is not in the hashmap
	 */
	public Brick(
		Color color,
        int posX, int posY,
        int width, int height,
        int lifespan, boolean dropBonus
    ) {
        super(new Rectangle(lifespans.get(lifespan), posX, posY, width, height));
		this.PosX = posX; 
		this.PosY = posY;
		if (!lifespans.containsKey(lifespan)) {
			throw new IllegalArgumentException("La durée de vie d'une brique doit être 0, 1, 2 ou 3 !");
		}
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("La taille d'une brique doit être strictement positive !");
		}
		if (!lifespans.containsValue(color) || color == null) {
			throw new IllegalArgumentException("La couleur d'une brique doit être rouge, orange, jaune ou verte !");
		}

        this.setLifespan(lifespan);
        this.setDropBonus(dropBonus);
    }

	/**
	 * Instantiates a new Brick
	 * 
	 * @param posX the initial x position of the brick
	 * @param posY the initial y position of the brick
	 * @param width the width of the brick
	 * @param height the height of the brick
	 * @param lifespan the lifespan of the brick, see the lifespans hashmap
	 * @param dropBonus whether the brick drops a bonus when destroyed
	 */
    public Brick(
        int posX, int posY,
        int width, int height,
        int lifespan, boolean dropBonus
    ) {
		this(lifespans.get(lifespan), posX, posY, width, height, lifespan, dropBonus);
    }

	/**
	 * Instantiates a new Brick
	 * 
	 * @param posX the initial x position of the brick
	 * @param posY the initial y position of the brick
	 * @param width the width of the brick
	 * @param height the height of the brick
	 * @param dropBonus whether the brick drops a bonus when destroyed
	 */
	public Brick(
		int posX, int posY,
		int width, int height,
		boolean dropBonus
	) {
		this(lifespans.get(MAX_LIFESPAN), posX, posY, width, height, MAX_LIFESPAN, dropBonus);
	}


	/**
	 * Gets the lifespan of the brick
	 * 
	 * @return the lifespan of the brick
	 */
    public int getLifespan() {
        return this.lifespan;
    }

	/**
	 * Sets the lifespan of the brick
	 * 
	 * @param lifespan the lifespan of the brick
	 * 
	 * @return whether the lifespan was set
	 */
    public boolean setLifespan(int lifespan) {
		// If the lifespan is not in the hashmap, prevent
		// the lifespan from being set to an invalid value
		if (!lifespans.containsKey(lifespan)) {
			return false;
		}
		this.lifespan = lifespan;
		this.getRepresentation().setColor(lifespans.get(lifespan));
		return true;
    }

	/**
	 * Gets whether the brick drops a bonus
	 * 
	 * @return whether the brick drops a bonus
	 */
    public boolean doesDropBonus() {
        return this.dropBonus;
    }

	/**
	 * Sets whether the brick drops a bonus
	 * 
	 * @param dropBonus whether the brick drops a bonus
	 * 
	 * @return whether the brick drops a bonus
	 */
    public void setDropBonus(boolean dropBonus) {
        this.dropBonus = dropBonus;
    }

	public void rotate(double angle) {
    // Calculer le centre de la brique
    double centerX = DEFAULT_POS_X + DEFAULT_WIDTH / 2;
    double centerY = DEFAULT_POS_Y + DEFAULT_HEIGHT / 2;

    // Calculer les nouvelles coordonnées des coins après rotation
    double newX1 = centerX + (DEFAULT_POS_X - centerX) * Math.cos(angle) - (DEFAULT_POS_Y - centerY) * Math.sin(angle);
    double newY1 = centerY + (DEFAULT_POS_X - centerX) * Math.sin(angle) + (DEFAULT_POS_Y - centerY) * Math.cos(angle);

    double newX2 = centerX + (DEFAULT_POS_X + DEFAULT_WIDTH - centerX) * Math.cos(angle) - (DEFAULT_POS_Y - centerY) * Math.sin(angle);
    double newY2 = centerY + (DEFAULT_POS_X + DEFAULT_WIDTH - centerX) * Math.sin(angle) + (DEFAULT_POS_Y - centerY) * Math.cos(angle);

    double newX3 = centerX + (DEFAULT_POS_X + DEFAULT_WIDTH - centerX) * Math.cos(angle) - (DEFAULT_POS_Y + DEFAULT_HEIGHT - centerY) * Math.sin(angle);
    double newY3 = centerY + (DEFAULT_POS_X + DEFAULT_WIDTH - centerX) * Math.sin(angle) + (DEFAULT_POS_Y + DEFAULT_HEIGHT - centerY) * Math.cos(angle);

    double newX4 = centerX + (DEFAULT_POS_X - centerX) * Math.cos(angle) - (DEFAULT_POS_Y + DEFAULT_HEIGHT - centerY) * Math.sin(angle);
    double newY4 = centerY + (DEFAULT_POS_X - centerX) * Math.sin(angle) + (DEFAULT_POS_Y + DEFAULT_HEIGHT - centerY) * Math.cos(angle);

    // Mettre à jour les coordonnées de la brique
	this.PosX = (int) Math.min(Math.min(newX1, newX2), Math.min(newX3, newX4));
    this.PosY = (int) Math.min(Math.min(newY1, newY2), Math.min(newY3, newY4));
}

}
