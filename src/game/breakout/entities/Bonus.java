package game.breakout.entities;

import java.util.HashMap;

import javax.swing.ImageIcon;
import java.util.Collections;
import java.awt.Image;


import display.engine.rules.PhysicalObject;
import display.engine.shapes.Circle;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;

/**
 * The Bonus class represents a bonus entity in the Breakout game.
 * It extends the Entity class and implements the PhysicalObject interface.
 */
public class Bonus extends Entity  {
    public static final long serialVersionUID = 11L;

    public static final String path = Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator;
    transient public static final Image DEFAULT_IMAGE = new ImageIcon(path + "ball.png").getImage();

    protected boolean isDestroyed;
    protected BonusType bonusType;

    public static final int DEFAULT_POS_X = 100;
    public static final int DEFAULT_POS_Y = 100;
    public static final int DEFAULT_SIZE = 30;
    public static final int MOVE_SPEED = 2;
    private float bonusTime = 30.0f;

    /**
     * Enumeration of the possible bonus types.
     */
    public enum BonusType {
        BONUS_SIZE,
        MALUS_SIZE,
        BONUS_SPEED,
        MALUS_SPEED,
        BONUS_HEALTH,
        DEFAULT
    }

    /**
     * A map that associates each bonus type with its corresponding image.
     */
    public static final HashMap<BonusType, Image> bonusTypes = new HashMap<BonusType, Image>() {
        {   
            put(BonusType.BONUS_SIZE, new ImageIcon(path + "BonusSize.png").getImage());
            put(BonusType.MALUS_SIZE, new ImageIcon(path + "MalusSize.png").getImage());
            put(BonusType.BONUS_SPEED, new ImageIcon(path + "BonusSpeed.png").getImage());
            put(BonusType.MALUS_SPEED, new ImageIcon(path + "MalusSpeed.png").getImage());
            put(BonusType.BONUS_HEALTH, new ImageIcon(path + "BonusHealth.png").getImage());
            put(BonusType.DEFAULT, DEFAULT_IMAGE);
        }
    };

    /**
     * The minimum bonus type value.
     */
    public static final BonusType MIN_BONUSTYPE = Collections.min(bonusTypes.keySet());

    /**
     * The maximum bonus type value.
     */
    public static final int MAX_BONUSTYPE = bonusTypes.size();

    /**
     * Constructs a Bonus object with the specified image, position, size, and bonus type.
     * 
     * @param image The image of the bonus.
     * @param posX The x-coordinate of the bonus position.
     * @param posY The y-coordinate of the bonus position.
     * @param size The size of the bonus.
     * @param bonusType The type of the bonus.
     * @throws IllegalArgumentException if the bonus type is invalid or the image is null or not found.
     */
    public Bonus(
        Image image,
        int posX, int posY,
        int size,
        BonusType bonusType
    ) {
        super(new Circle(bonusTypes.get(bonusType), posX, posY, size, size));
        if (!bonusTypes.containsKey(bonusType)) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        if (!bonusTypes.containsValue(image) || image == null) {
            throw new IllegalArgumentException("Invalid bonus image!");
        }

        this.setBonusType(bonusType);
    }

    /**
     * Constructs a Bonus object with the specified size, position, and bonus type.
     * 
     * @param size The size of the bonus.
     * @param posX The x-coordinate of the bonus position.
     * @param posY The y-coordinate of the bonus position.
     * @param bonusType The type of the bonus.
     */
    public Bonus(
        int size,
		int posX,int posY,
        BonusType bonusType
    ) {
        this(bonusTypes.get(bonusType), posX, posY, size, bonusType);
    }

    /**
     * Constructs a Bonus object with the default image, specified position, and size.
     * 
     * @param posX The x-coordinate of the bonus position.
     * @param posY The y-coordinate of the bonus position.
     * @param size The size of the bonus.
     */
    public Bonus(
        int posX, int posY,
        int size
    ) {
        this(bonusTypes.get(BonusType.DEFAULT), posX, posY, size, BonusType.DEFAULT);
    }

    /**
     * Returns the type of the bonus.
     * 
     * @return The bonus type.
     */
    public BonusType getBonusType() {
        return this.bonusType;
    }

    /**
     * Returns the remaining time of the bonus.
     * 
     * @return The bonus time.
     */
    public float getBonusTime() {
        return this.bonusTime;
    }

    /**
     * Sets the type of the bonus.
     * 
     * @param bonusType The bonus type to set.
     * @return true if the bonus type is valid and set successfully, false otherwise.
     */
    public boolean setBonusType(BonusType bonusType) {
        if (!bonusTypes.containsKey(bonusType)) {
            return false;
        }
        this.bonusType = bonusType;
        this.getRepresentation().setImage(bonusTypes.get(bonusType));
        return true;
    }

    /**
     * Sets the remaining time of the bonus.
     * 
     * @param bonusTime The bonus time to set.
     * @return true if the bonus time is non-negative and set successfully, false otherwise.
     */
    public boolean setBonusTime(float bonusTime) {
        if (bonusTime < 0) {
            return false;
        }
        this.bonusTime = bonusTime;
        return true;
    }

    @Override
    public void move(int speed) {
        this.getRepresentation().setPosY(this.getRepresentation().getPosY() + speed);
    }

    @Override
    public void collided(PhysicalObject object) {
        super.collided();
    }

}
