
package game.spaceinvader.entities;

import java.util.HashMap;

import javax.swing.ImageIcon;
import java.util.Collections;
import java.awt.Image;

import display.engine.rules.PhysicalObject;
import display.engine.shapes.Circle;
import game.spaceinvader.SpaceInvader;
import game.spaceinvader.entities.rules.Entity;

public class EnemiesAmmo extends Entity {
    public static final String path = SpaceInvader.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator;
    public static final Image DEFAULT_IMAGE = new ImageIcon(path + "EnemiesAmmo1.png").getImage();

    protected boolean isDestroyed;
    protected EnemiesAmmoType enemiesAmmoType;

    public static final int DEFAULT_POS_X = 100;
    public static final int DEFAULT_POS_Y = 100;
    public static final int DEFAULT_SIZE = 30;
    private float bonusTime = 30.0f;

    public enum EnemiesAmmoType {
        LOW_DAMAGE_AMMO,
        HIGH_DAMAGE_AMMO,
        DEFAULT
    }

    public static final HashMap<EnemiesAmmoType, Image> enemiesAmmoTypes = new HashMap<EnemiesAmmoType, Image>() {
        {   
            put(EnemiesAmmoType.LOW_DAMAGE_AMMO, new ImageIcon(path + "EnemiesAmmo0.png").getImage());
            put(EnemiesAmmoType.HIGH_DAMAGE_AMMO, new ImageIcon(path + "EnemiesAmmo2.png").getImage());
            put(EnemiesAmmoType.DEFAULT, DEFAULT_IMAGE);
        }
    };

    public static final EnemiesAmmoType MIN_ENEMIESAMMOTYPE = Collections.min(enemiesAmmoTypes.keySet());
    public static final int MAX_ENEMIESAMMOTYPE = enemiesAmmoTypes.size();

    public EnemiesAmmo(
        Image image,
        int posX, int posY,
        int size,
        EnemiesAmmoType enemiesAmmoType
    ) {
        super(new Circle(enemiesAmmoTypes.get(enemiesAmmoType), posX, posY, size/5, size));
        if (!enemiesAmmoTypes.containsKey(enemiesAmmoType)) {
            throw new IllegalArgumentException("Argument invalide !");
        }
        if (!enemiesAmmoTypes.containsValue(image) || image == null) {
            throw new IllegalArgumentException("L'image du bonus est invalide !");
        }

        this.setenemiesAmmoType(enemiesAmmoType);
    }

    public EnemiesAmmo(
        int size,
		int posX,int posY,
        EnemiesAmmoType enemiesAmmoType
    ) {
        this(enemiesAmmoTypes.get(enemiesAmmoType), posX, posY, size, enemiesAmmoType);
    }

    public EnemiesAmmo(
        int posX, int posY,
        int size
    ) {
        this(enemiesAmmoTypes.get(EnemiesAmmoType.DEFAULT), posX, posY, size, EnemiesAmmoType.DEFAULT);
    }

    public EnemiesAmmoType getenemiesAmmoType() {
        return this.enemiesAmmoType;
    }

    public float getBonusTime() {
        return this.bonusTime;
    }

    public boolean setenemiesAmmoType(EnemiesAmmoType enemiesAmmoType) {
        if (!enemiesAmmoTypes.containsKey(enemiesAmmoType)) {
            return false;
        }
        this.enemiesAmmoType = enemiesAmmoType;
        this.getRepresentation().setImage(enemiesAmmoTypes.get(enemiesAmmoType));
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

    public int getBulletVelocity() {
        switch (this.enemiesAmmoType) {
            case LOW_DAMAGE_AMMO:
                return 10;
            case HIGH_DAMAGE_AMMO:
                return 4;
            default:
                return 7;
        }
    }
}
