package game.spaceinvader.entities;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.HashMap;
import java.util.Collections;
import display.engine.shapes.Rectangle;
import display.engine.rules.PhysicalObject;
import display.engine.utils.Vector2D;
import game.spaceinvader.SpaceInvader;
import game.spaceinvader.entities.rules.Entity;

public class Enemy extends Entity {
    public static final String path = SpaceInvader.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator;
    public static final Image DEFAULT_IMAGE = new ImageIcon(path + "Enemy0.png").getImage();

    protected boolean isDestroyed;
    protected int lifespan;

	public static final int DEFAULT_POS_X = 1;
	public static final int DEFAULT_POS_Y = 100;
	public static final int DEFAULT_WIDTH = 50;
	public static final int DEFAULT_HEIGHT = 50;

    public static final HashMap<Integer, Image> lifespans = new HashMap<Integer, Image>() {
        {
            put(0, new ImageIcon(path + "Enemy0.png").getImage());
			put(1, new ImageIcon(path + "Enemy1.png").getImage());
			put(2, new ImageIcon(path + "Enemy2.png").getImage());
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
	 * 
	 * @throws IllegalArgumentException if the lifespan is not in the hashmap
	 * @throws IllegalArgumentException if the width or height is less than or equal to 0
	 * @throws IllegalArgumentException if the color is not in the hashmap
	 */

    public Enemy(Image image, int width, int height, int lifespan, double mass, Vector2D position, boolean movable) {
        super(mass, position, movable, new Rectangle(lifespans.get(lifespan), (int) position.getX(), (int) position.getY(), width, height));
        if (!lifespans.containsKey(lifespan)) {
            throw new IllegalArgumentException("La durée de vie d'une brique doit être 0, 1, 2 ou 3 !");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("La taille d'une brique doit être strictement positive !");
        }
        if (!lifespans.containsValue(image) || image == null) {
            throw new IllegalArgumentException("La couleur d'une brique doit être rouge, orange, jaune ou verte !");
        }

        this.setLifespan(lifespan);
    }

    public Enemy(int width, int height, int lifespan, double mass, Vector2D position, boolean movable) {
        this(lifespans.get(lifespan), width, height, lifespan, mass, position, movable);
    }

    public Enemy(int width, int height, double mass, Vector2D position, boolean movable) {
        this(lifespans.get(MAX_LIFESPAN), width, height, MAX_LIFESPAN, mass, position, movable);
    }

    public Enemy(Image image, int posX, int posY, int width, int height, int lifespan) {
        super(new Rectangle(lifespans.get(lifespan), posX, posY, width, height));
        if (!lifespans.containsKey(lifespan)) {
            throw new IllegalArgumentException("La durée de vie d'une brique doit être 0, 1, 2 ou 3 !");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("La taille d'une brique doit être strictement positive !");
        }
        if (!lifespans.containsValue(image) || image == null) {
            throw new IllegalArgumentException("La couleur d'une brique doit être rouge, orange, jaune ou verte !");
        }

        this.setLifespan(lifespan);
    }

    public Enemy(int posX, int posY, int width, int height, int lifespan) {
        this(lifespans.get(lifespan), posX, posY, width, height, lifespan);
    }

    public Enemy(int posX, int posY, int width, int height) {
        this(lifespans.get(MAX_LIFESPAN), posX, posY, width, height, MAX_LIFESPAN);
    }

    public int getLifespan() {
        return this.lifespan;
    }

    public boolean setLifespan(int lifespan) {
        if (!lifespans.containsKey(lifespan)) {
            return false;
        }
        this.lifespan = lifespan;
        this.getRepresentation().setImage(lifespans.get(lifespan));
        return true;
    }

    @Override
    public void collided() {
        super.collided();
        if (this.getLifespan() <= Enemy.MIN_LIFESPAN) {
            this.destroy();
        }
        this.setLifespan(this.getLifespan() - 1);
    }

    @Override
    public void collided(PhysicalObject object) {
        super.collided();
        if (object instanceof PlayersAmmo) {
            if (this.getLifespan() <= Enemy.MIN_LIFESPAN) {
                this.destroy();
            }
            this.setLifespan(this.getLifespan() - 1);
        }
    }

    public Rectangle getRectangle() {
        return ((Rectangle) this.getRepresentation());
    }
}

