package game.breakout.entities;

import java.util.HashMap;

import javax.swing.ImageIcon;

import java.util.Collections;
import java.awt.Image;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Circle;
import display.engine.utils.Vector2D;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;

public class Bonus extends Entity {
	public static final String path = Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator;
	public static final Image DEFAULT_IMAGE = new ImageIcon(path + "ball.png").getImage();

    protected boolean isDestroyed;
    protected int bonusType;

	public static final int DEFAULT_POS_X = 100;
	public static final int DEFAULT_POS_Y = 100;
	public static final int DEFAULT_SIZE = 30;
	public static final int MOVE_SPEED = 2;
	private float bonusTime = 30.0f;


	public static final HashMap<Integer, Image> bonusTypes = new HashMap<Integer, Image>() {
		{	
			put(0, new ImageIcon(path + "BonusSize.png").getImage());
			put(1, new ImageIcon(path + "MalusSize.png").getImage());
			put(2, new ImageIcon(path + "BonusSpeed.png").getImage());
			put(3, new ImageIcon(path + "MalusSpeed.png").getImage());
			put(4, new ImageIcon(path + "BonusTime.png").getImage());
			put(5, DEFAULT_IMAGE);
		}
	};

	public static final int MIN_BONUSTYPE = Collections.min(bonusTypes.keySet());
	public static final int MAX_BONUSTYPE = bonusTypes.size();

	/**
	 * Instantiates a new Bonus
	 * 
	 * @param posX the initial x position of the bonus
	 * @param posY the initial y position of the bonus
	 * @param size the size of the bonus
	 * @param bonusType the bonusType of the bonus, see the bonusTypessss hashmap
	 * @param  whether the bonus drops a bonus when destroyed
	 * 
	 * @throws IllegalArgumentException if the bonusType is not in the hashmap
	 * @throws IllegalArgumentException if the size or size is less than or equal to 0
	 * @throws IllegalArgumentException if the color is not in the hashmap
	 */
	/* 
	public Bonus(
		Color color,
        int posX, int posY,
        int size,
        int bonusType
    ) {
        super(new Circle(bonusTypes.get(bonusType), posX, posY, size, size));
		if (!bonusTypes.containsKey(bonusType)) {
			throw new IllegalArgumentException("La valeur donnée doit être entre  0 à x !");
		}
		if (size <= 0 || size <= 0) {
			throw new IllegalArgumentException("la taille d'un bonus doit être positive !");
		}
		if (!bonusTypes.containsValue(color) || color == null) {
			throw new IllegalArgumentException("L'image du bonus est invalide !");
		}

        this.setbonusType(bonusType);
    }
	*/

	public Bonus(
		Image image,
        int size,
        int bonusType,
		double mass, Vector2D position, boolean movable
    ) {
        super(mass,position,movable, new Circle(bonusTypes.get(bonusType), (int)position.getX(),(int)position.getY(), size, size));
		if (!bonusTypes.containsKey(bonusType)) {
			throw new IllegalArgumentException("La valeur donnée doit être entre  0 à x !");
		}
		if (size <= 0 || size <= 0) {
			throw new IllegalArgumentException("la taille d'un bonus doit être positive !");
		}
		if (!bonusTypes.containsValue(image) || image == null) {
			throw new IllegalArgumentException("L'image du bonus est invalide !");
		}

        this.setbonusType(bonusType);
    }

	/**
	 * Instantiates a new Bonus
	 * 
	 * @param posX the initial x position of the bonus
	 * @param posY the initial y position of the bonus
	 * @param size the size of the bonus
	 * @param bonusType the bonusType of the bonus, see the bonusTypes hashmap
	 */
    public Bonus(
        int size,
        int bonusType,
		double mass, Vector2D position, boolean movable
    ) {
		this(bonusTypes.get(bonusType), size, bonusType,mass,position,movable);
    }

	/**
	 * Instantiates a new Bonus
	 * 
	 * @param posX the initial x position of the bonus
	 * @param posY the initial y position of the bonus
	 * @param size the size of the bonus
	 * @param size the size of the bonus
	 * @param  whether the bonus drops a bonus when destroyed
	 */
	public Bonus(
		int size,
		double mass, Vector2D position, boolean movable
	) {
		this(bonusTypes.get(MAX_BONUSTYPE), size, MAX_BONUSTYPE,mass,position,movable);
	}


	/**
	 * Gets the bonusType of the Bonus
	 * 
	 * @return the bonusType of the Bonus
	 */
    public int getbonusType() {
        return this.bonusType;
    }

	/**
	 * Gets the duration of the Bonus
	 * 
	 * @return the bonusTime of the bonus
	 */
	public float getBonusTime() {
		return this.bonusTime;
	}

	/**
	 * Sets the bonusType of the Bonus
	 * 
	 * @param bonusType the bonusType of the Bonus
	 * 
	 * @return whether the bonusType was set
	 */
    public boolean setbonusType(int bonusType) {
		// If the bonusType is not in the hashmap, prevent
		// the bonusType from being set to an invalid value
		if (!bonusTypes.containsKey(bonusType)) {
			return false;
		}
		this.bonusType = bonusType;
		this.getRepresentation().setImage(bonusTypes.get(bonusType));
		return true;
    }

	/**
	 * Sets the duration of the Bonus
	 * 	
	 * @param bonusTime the duration of the Bonus
	 * 	
	 * @return whether the bonusTime was set
	 */
	public boolean setBonusTime(float bonusTime) {
		if (bonusTime < 0) {
			return false;
		}
		this.bonusTime = bonusTime;
		return true;
	}



	@Override
	public void collided(PhysicalObject object) {
		super.collided();
	}
}
