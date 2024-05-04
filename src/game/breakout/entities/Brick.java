package game.breakout.entities;

import java.util.HashMap;
import java.util.Collections;

import javax.swing.ImageIcon;

import java.awt.Image;


import display.engine.rules.PhysicalObject;
import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;



public class Brick extends Entity {
	public static final long serialVersionUID = 12L;

    public static final String path = Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator;
    transient public static final Image DEFAULT_IMAGE = new ImageIcon(path + "ball.png").getImage();

    protected boolean isDestroyed, dropBonus, unbreakable;
    protected int lifespan;

	public static final int DEFAULT_POS_X = 1;
	public static final int DEFAULT_POS_Y = 100;
	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGHT = 20;

	public static final HashMap<Integer, Image> lifespans = new HashMap<Integer, Image>() {
		{
			put(0, new ImageIcon(path + "unbreakableBrick.png").getImage());
			put(1, new ImageIcon(path + "Brick0.png").getImage());
			put(2, new ImageIcon(path + "Brick1.png").getImage());
			put(3, new ImageIcon(path + "Brick2.png").getImage());
			
		}
	};

	public static final int MIN_LIFESPAN = 0;//Collections.min(lifespans.keySet());
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
		Image image,
        int width, int height,
        int lifespan, boolean dropBonus, boolean unbreakable,
		double mass, Vector2D position, boolean movable
    ) {
        super(mass,position,movable,new Rectangle(lifespans.get(lifespan), (int)position.getX(), (int)position.getY(),  width, height));
		if (!lifespans.containsKey(lifespan)) {
			throw new IllegalArgumentException("La durée de vie d'une brique doit être 0, 1, 2 ou 3 !");
		}
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("La taille d'une brique doit être strictement positive !");
		}
		if (!lifespans.containsValue(image) || image == null) {
			throw new IllegalArgumentException("La couleur d'une brique doit être rouge, orange, jaune ou verte !");
		}
		this.setUnbreakable(unbreakable);
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
        int width, int height,
        int lifespan, boolean dropBonus, boolean unbreakable,
		double mass, Vector2D position, boolean movable
    ) {
		this(lifespans.get(lifespan), width, height, lifespan, dropBonus, unbreakable, mass, position,movable);
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
		int width, int height,
		boolean dropBonus, boolean unbreakable, 
		double mass, Vector2D position, boolean movable
	) {
		this(lifespans.get(MAX_LIFESPAN), width, height, MAX_LIFESPAN, dropBonus, unbreakable, mass,position,movable);
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
	 * 
	 * @throws IllegalArgumentException if the lifespan is not in the hashmap
	 * @throws IllegalArgumentException if the width or height is less than or equal to 0
	 * @throws IllegalArgumentException if the color is not in the hashmap
	 */
	public Brick(
		Image image,
        int posX, int posY,
        int width, int height,
        int lifespan, boolean dropBonus, boolean unbreakable
    ) {
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
        int lifespan, boolean dropBonus, boolean unbreakable
    ) {
		this(lifespans.get(lifespan), posX, posY, width, height, lifespan, dropBonus, unbreakable);
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
		boolean dropBonus, boolean unbreakable
	) {
		this(lifespans.get(MAX_LIFESPAN), posX, posY, width, height, MAX_LIFESPAN, dropBonus, unbreakable);
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
		this.getRepresentation().setImage(lifespans.get(lifespan));
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

	
	public boolean getUnbreakable(){
		return this.unbreakable;
	}

	public void setUnbreakable(boolean unbreakable){
		this.unbreakable = unbreakable;
	}

	@Override
	public void collided(){
		super.collided();
		if (this.getLifespan() <= Brick.MIN_LIFESPAN) {
			this.destroy();
		}
		this.setLifespan(this.getLifespan() - 1);

	}

	/**
 	* Retrieves the Rectangle associated with the current brick.
 	* 
 	* @return the rectangle associated with the brick
 	*/
	 public Rectangle getRectangle() {
        return ((Rectangle) this.getRepresentation());
	}



	@Override
	public void collided(PhysicalObject object) {
		super.collided();
		if(object instanceof Ball){
			if (this.getLifespan() <= Brick.MIN_LIFESPAN) {
				if (!(this.unbreakable)) this.destroy();
				this.setLifespan(0);
			}
			this.setLifespan(this.getLifespan() - 1);
		}
	}


}
