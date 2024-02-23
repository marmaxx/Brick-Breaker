package game.breakout.entities;
import java.awt.Color;
import display.engine.shapes.BallImage;
import display.engine.rules.GraphicalObject;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;
import display.view.GamePanel;
import display.engine.shapes.Circle;
import display.engine.shapes.rules.*;
import java.util.*;

public class Ball extends Entity {
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final double DEFAULT_POS_X = 600;
	public static final double DEFAULT_POS_Y = 0;
	public double posX, posY;
	private static final double DELTA_TIME = 1;
	private static final double GRAVITY_CONSTANT = 9.81;
    private static final double MASS = 0.2;
	private double forceX = MASS * GRAVITY_CONSTANT/2;
	private double forceY = MASS * GRAVITY_CONSTANT/2;
	private boolean collidingBrick;

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 */
    public Ball(
        double posX, double posY,
        int size,
		Color color
    ) {
		super(new Circle((int)posX, (int)posY, size, color));
        //super(new BallImage(posX, posY, size, size, color));
		this.posX=posX;
		this.posY=posY;
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 */
	public Ball(double posX, double posY, int size) {
		this(posX, posY, size, DEFAULT_COLOR);
		this.posX=posX;
		this.posY=posY;
	}

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 */
	public Ball(double posX, double posY) {
		this(posX, posY, DEFAULT_SIZE, DEFAULT_COLOR);

	}

	/**
	 * Instantiates a new Ball
	 */
	public Ball() {
		this(DEFAULT_POS_X, DEFAULT_POS_Y, DEFAULT_SIZE, DEFAULT_COLOR);
	}

	public double getX(){
		return posX;
	}

	public double getY(){
		return posY;
	}

	public void setPos(double x, double y){
		this.getRepresentation().setPosX((int)x);
		this.getRepresentation().setPosY((int)y);
		posX = x;
		posY = y;
	}

	public double getNextX(){ 
        double x = Math.pow(DELTA_TIME,2)*forceX/MASS;
        return getX()+2*x;
    }

	public double getNextY(){
		double y = Math.pow(DELTA_TIME,2)*forceY/MASS;
        return getY()+2*y;
	}

	public void setForce (double x, double y){
		forceX = x;
		forceY = y;
	}

	/* Method to check if the Ball is touching a wall except the lower wall */
    public boolean touchWall(){ 
        if (getX() >= 0 && getNextX() <= 0) return true;
        else if (getX() <= GamePanel.SCREEN_FULL_SIZE.getWidth() && getNextX() >= GamePanel.SCREEN_FULL_SIZE.getWidth()) return true;
        else if (getY() >= 0 && getNextY() <= 0) return true;
        return false;
    }

	/* Method to check if the Ball is touching the lower wall */
	public boolean touchLowerWall(){
        if (getY() <= GamePanel.SCREEN_FULL_SIZE.getHeight() && (getNextY() >= GamePanel.SCREEN_FULL_SIZE.getHeight())) return true;
		return false;
	}

    // Method to check if the Ball is touching the Paddle 
    public boolean touchPaddle(Player paddle){ 
        return ((Shape)this.getRepresentation()).checkCollisions((Shape)paddle.getRepresentation());
    }

    /* Method that sets collidingBrick to true. is called by bricks when they detect a collision with the ball */
    public void touchBrick(){
        collidingBrick=true;
    }
    
    /* Method to handle wall collision */
    public void handleWallCollision(){
		if ((getX() >= 0 && getNextX() <= 0) || (getX() <= GamePanel.SCREEN_FULL_SIZE.getWidth() && getNextX() >= GamePanel.SCREEN_FULL_SIZE.getWidth())){
			setForce(-forceX, forceY);
		}
		else if (getY() >= 0 && getNextY() <= 0){
			setForce(forceX, -forceY);
		}
    }

    /* Method to handle paddle collision */
    public void paddleCollision(){
        setForce(forceX, - forceY);
    }

    /* Method to handle brick collision */
    public void brickCollision(){
		setForce(forceX, -forceY);
	}

    public void move(){
		setPos(getNextX(), getNextY());
    }
	
	public void update(Player player){ //actualisation des conditions physiques impactant la balle
		Ball ball = this;
		if (ball.touchPaddle(player)){
			ball.paddleCollision();
		}
		if (collidingBrick){
			setForce(forceX, -forceY);
			collidingBrick=false; //resets the brick collision variable to false
        }
		if (ball.touchLowerWall()){
			ball.setPos(630, 0);
		}
        /*if (ball.touchWall()){
            ball.wallCollision();
        }*/
		handleWallCollision();
		ball.move();    
    }

}
