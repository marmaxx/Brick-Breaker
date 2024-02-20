package game.breakout.entities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Collections;

import display.engine.shapes.Rectangle;
import display.engine.shapes.rules.Shape;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;
public class Brick extends Entity {
    protected boolean isDestroyed, dropBonus;
    protected int lifespan;

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
		this.updater(); //takes care of updating the brick

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


	public void updater(){ 
		final Timer[] timer = new Timer[2];  //timer[0] is the collision timer, and timer[1] is the deletion timer
		timer[0] = new Timer(40, (ActionEvent e)->{ 
			if(this.checkBallCollisions()){
				((Ball)Breakout.currentInstance.getBall()).touchBrick();
				this.haveCollision();
			}
		});

		timer[1] = new Timer(50, (ActionEvent e)->{
			if(this.isDestroyed){
				timer[0].stop();
				timer[1].stop();
				return;
			}
		});
		timer[0].start();
		timer[1].start();
    }
	/**
	 * 
	 * @return true if this brick is colliding with the ball, false otherwise
	 */
	private boolean checkBallCollisions(){
		return ((Shape)this.getRepresentation()).checkCollisions((Shape)Breakout.currentInstance.getBall().getRepresentation());
	}

	
}
