package game.breakout.entities;

import java.util.HashMap;

import javax.swing.ImageIcon;
import java.util.Collections;
import java.awt.Image;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Circle;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;

public class Bonus extends Entity {
    public static final String path = Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator;
    public static final Image DEFAULT_IMAGE = new ImageIcon(path + "ball.png").getImage();

    protected boolean isDestroyed;
    protected BonusType bonusType;

    public static final int DEFAULT_POS_X = 100;
    public static final int DEFAULT_POS_Y = 100;
    public static final int DEFAULT_SIZE = 30;
    public static final int MOVE_SPEED = 2;
    private float bonusTime = 30.0f;

    public enum BonusType {
        BONUS_SIZE,
        MALUS_SIZE,
        BONUS_SPEED,
        MALUS_SPEED,
        BONUS_HEALTH,
        DEFAULT
    }

    public static final HashMap<BonusType, Image> bonusTypes = new HashMap<BonusType, Image>() {
        {   
            put(BonusType.BONUS_SIZE, new ImageIcon(path + "BonusSize.png").getImage());
            put(BonusType.MALUS_SIZE, new ImageIcon(path + "MalusSize.png").getImage());
            put(BonusType.BONUS_SPEED, new ImageIcon(path + "BonusSpeed.png").getImage());
            put(BonusType.MALUS_SPEED, new ImageIcon(path + "MalusSpeed.png").getImage());
            put(BonusType.BONUS_HEALTH, new ImageIcon(path + "BonusTime.png").getImage());
            put(BonusType.DEFAULT, DEFAULT_IMAGE);
        }
    };

    public static final BonusType MIN_BONUSTYPE = Collections.min(bonusTypes.keySet());
    public static final int MAX_BONUSTYPE = bonusTypes.size();

    public Bonus(
        Image image,
        int posX, int posY,
        int size,
        BonusType bonusType
    ) {
        super(new Circle(bonusTypes.get(bonusType), posX, posY, size, size));
        if (!bonusTypes.containsKey(bonusType)) {
            throw new IllegalArgumentException("Argument invalide !");
        }
        if (!bonusTypes.containsValue(image) || image == null) {
            throw new IllegalArgumentException("L'image du bonus est invalide !");
        }

        this.setBonusType(bonusType);
    }

    public Bonus(
        int size,
		int posX,int posY,
        BonusType bonusType
    ) {
        this(bonusTypes.get(bonusType), posX, posY, size, bonusType);
    }

    public Bonus(
        int posX, int posY,
        int size
    ) {
        this(bonusTypes.get(BonusType.DEFAULT), posX, posY, size, BonusType.DEFAULT);
    }

    public BonusType getBonusType() {
        return this.bonusType;
    }

    public float getBonusTime() {
        return this.bonusTime;
    }

    public boolean setBonusType(BonusType bonusType) {
        if (!bonusTypes.containsKey(bonusType)) {
            return false;
        }
        this.bonusType = bonusType;
        this.getRepresentation().setImage(bonusTypes.get(bonusType));
        return true;
    }

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
