package game.breakout.entities;
import java.awt.Color;
import display.engine.images.BallImage;
import game.breakout.entities.rules.Entity;
import display.view.GamePanel;
import display.engine.shapes.Circle;

public class Ball extends Entity {
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final double DEFAULT_POS_X = 0;
	public static final double DEFAULT_POS_Y = 0;
	public double posX, posY;
	private static final double DELTA_TIME = 1;
	private static final double GRAVITY_CONSTANT = 9.81;
    private static final double MASS = 0.2;
    // private static final double WEIGHTX = 0;
	// private static final double WEIGHTY = MASS * GRAVITY_CONSTANT;
	private double forceX = 0;
	private double forceY = MASS * GRAVITY_CONSTANT;

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
		//super(new Circle((int)posX, (int)posY, size, color));
        super(new BallImage(posX, posY, size, size, color));
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param getX() the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 */
	public Ball(double posX, double posY, int size) {
		this(posX, posY, size, DEFAULT_COLOR);
	}

	/**
	 * Instantiates a new Ball
	 * 
	 * @param getX() the initial x position of the ball
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

	/* Method to check if the Ball is touching a wall */
    public boolean touchWall(){ 
        if (getX() >= 0 && getNextX() <= 0) return true;
        else if (getX() <= GamePanel.SCREEN_FULL_SIZE.getWidth() && getNextX() >= GamePanel.SCREEN_FULL_SIZE.getWidth()) return true;
        else if (getY() >= 0 && getNextY() <= 0) return true;
        else if (getY() <= GamePanel.SCREEN_FULL_SIZE.getHeight() && getNextY() >= GamePanel.SCREEN_FULL_SIZE.getHeight()) return true;
        return false;
    }

    /* Method to check if the Ball is touching the Paddle 
    public boolean touchPaddle(Paddle paddle){ 
    	Rectangle paddleBounds = paddle.getBounds();
    	Rectangle myBounds = this.getBounds();
        return myBounds.intersects(paddleBounds);
    }*/

    /* Method to check if the Ball is touching a Brick */
    public boolean touchBrick(){
        return false;
    }
    
    /* Method to handle wall collision */
    public void wallCollision(){
        // double y = -forceCoordinates.getY();
        // forceCoordinates=new Coordinates(forceCoordinates.getX(), y);
        setForce(forceX, - forceY);
    }

    /* Method to handle paddle collision */
    public void paddleCollision(){
        setForce(forceX, - forceY);
    }

    /* Method to handle brick collision */
    public void brickCollision(){}

    public void move(){
		//System.out.println("Delta time: "+DELTA_TIME);
		//System.out.println("forceX :"+forceX);
		//System.out.println("forceY :"+forceY);
		//System.out.println("Mass :"+MASS);
		//System.out.println();
		double x = Math.pow(DELTA_TIME,2)*forceX/MASS;
		double y = Math.pow(DELTA_TIME,2)*forceY/MASS;
		//System.out.println("x :"+x);
		//System.out.println("y :"+y);
		//System.out.println();
		//System.out.println("posX :"+posX);
		//System.out.println("posY :"+posY);
		//System.out.println();
        /*double x = forceX/MASS;
        double y = forceY/MASS;
        double x1 = DELTA_TIME*x;
        double y1 = DELTA_TIME*y;
        double x2 = DELTA_TIME*x1;
        double y2 = DELTA_TIME*y1;*/
		
		setPos(posX + x, posY + y);
    }

	public void update(){
		this.move();
	}

}
