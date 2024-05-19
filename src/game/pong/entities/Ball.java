package game.pong.entities;

import java.awt.Color;

import display.engine.rules.GraphicalObject;
import display.engine.rules.PhysicalObject;

import java.awt.Image;

import javax.swing.ImageIcon;

import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import display.view.GamePanel;
import game.pong.Pong;
import game.pong.entities.rules.Entity;

public class Ball extends Entity {
	public static final Image DEFAULT_IMAGE = new ImageIcon(Pong.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "cannonBall.png").getImage();
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final int DEFAULT_POS_X = 600;
	public static final int DEFAULT_POS_Y = 0;
	public static final int MOVE_SPEED = 5;
	public static boolean hasHitAWall;
	public boolean isMoving;

	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 * @param size the size of the playerAmmo
	 * @param color the color of the playerAmmo (ignored if the playerAmmo is represented by an image)
	 */
    public Ball(
		Color color,
        int size,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Rectangle(color, (int)position.getX(), (int)position.getY(), size, size));
    }

	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 * @param size the size of the playerAmmo
	 * @param color the color of the playerAmmo (ignored if the playerAmmo is represented by an image)
	 */
    public Ball(
		Image image,
        int size,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Rectangle(image, (int)position.getX(), (int)position.getY(), size, size));
    }

	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 */
	public Ball(double mass, Vector2D position, boolean movable) {
		this(DEFAULT_IMAGE, DEFAULT_SIZE,mass,position,movable);

	}

  	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 * @param size the size of the playerAmmo
	 * @param color the color of the playerAmmo (ignored if the playerAmmo is represented by an image)
	 */
    public Ball(
		Color color,
        int posX, int posY,
        int size
    ) {
		super(new Rectangle(color, posX, posY, size, size));
    }

	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 * @param size the size of the playerAmmo
	 * @param color the color of the playerAmmo (ignored if the playerAmmo is represented by an image)
	 */
    public Ball(
		Image image,
        int posX, int posY,
        int size
    ) {
		super(new Rectangle(image, posX, posY, size/1, size));
    }

	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 */
	public Ball(int posX, int posY) {
		this(DEFAULT_IMAGE, posX, posY, DEFAULT_SIZE);

	}

	/**
	 * Instantiates a new PlayerAmmo
	 */
	public Ball() {
		this(DEFAULT_IMAGE, DEFAULT_POS_X, DEFAULT_POS_Y, DEFAULT_SIZE);
	}

	/**
	 * Indicate if the ball is moving or not
	 * 
	 * @return
	 */
	public boolean getIsMoving(){
		return this.isMoving;
	}

	/**
	 * Set the ball to moving or not
	 * 
	 * @param b
	 */
	public void setIsMoving(boolean b){
		this.isMoving = b;
	}

	/**
	 * Check if the ball will out by taking into account the panel size of the game and the speed of the entity
	 * 
	 * @param panel
	 * @param speed
	 * @return
	 */
	public boolean willLoose(GamePanel panel, int speed){
		int [] boundaries = this.getRepresentation().getNextBoundaries(this.getNextPos(speed));
		return boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed  > panel.getGameZone().getHeight();
	}

	/**
	 * Get the impact point of where the entity may collide
	 * 
	 * @param ammo
	 * @return
	 */
    public Vector2D getImpactPoint(Ball ammo){
		double distanceX = Math.abs(this.getPosition().getX()+this.getRepresentation().getWidth()/2 - ammo.getPosition().getX());
        double distanceY = Math.abs(this.getPosition().getY()+this.getRepresentation().getWidth()/2 - ammo.getPosition().getY());
        Vector2D vectorImpact = new Vector2D(distanceX/2, distanceY/2);
        return vectorImpact.add(ammo.getPosition());
	}
	

	@Override
	public void collided(PhysicalObject object) {
		super.collided();
		if (object instanceof Wall){
			if (((Wall)object).getTriggerRespawn()){
				this.setHasHitAWall(true);
			}
		}
	}

	/**
	 * Determine if the ball has collided with a wall and reset the bool if so
	 * 
	 * @return a bool corresponding with the collision state of the ball
	 */
	public boolean collidingWithWall() {
		if (this.getHasHitAWall() == true) {
			this.setHasHitAWall(false);
			return true;
		}
		return false;
	}

	/**
	 * check if two objects are colliding
	 * 
	 * @param object1
	 * @param object2
	 * @return boolean true if the two objects are colliding
	 */
	public boolean checkCollision(PhysicalObject object){
		return this.getRepresentation().isColliding(object.getRepresentation());
	}

	/**
	 * Respawn the ball at the x,y location of the game
	 * 
	 * @param pong
	 * @param x
	 * @param y
	 */
	public void respawnBall(Pong pong, int x, int y){
		Ball ball = new Ball(Ball.DEFAULT_IMAGE, 30, 50, new Vector2D(x, y), true);
		ball.setSpeed(new Vector2D(0, -0.3));
		ball.active=false;
		pong.setBall(ball);
		pong.getPanel().getGameZone().add(pong.getBall().getRepresentation());
		pong.getPhysicEngine().getPhysicalObjects().add(ball);
	}

	/**
	 * get a boolean to indicate if it has hitten a wall or not
	 * 
	 * @return
	 */	
	public boolean getHasHitAWall(){
		return Ball.hasHitAWall;
	}
	
	/**
	 * Set the boolean to indicate if it has hitten a wall or not
	 * 
	 * @param b
	 */
	public void setHasHitAWall(boolean b){
		Ball.hasHitAWall = b;
	}
}
