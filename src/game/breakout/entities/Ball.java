package game.breakout.entities;
import java.awt.Color;
import display.engine.images.BallImage;
import display.engine.rules.Collisions;
import display.engine.rules.GraphicalObject;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;
import display.view.GamePanel;
import display.engine.shapes.Circle;
import java.util.*;

public class Ball extends Entity {
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final double DEFAULT_POS_X = 300;
	public static final double DEFAULT_POS_Y = 200;
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
		this.posX=posX;
		this.posY=posY;
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
		this.posX=posX;
		this.posY=posY;
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

    // Method to check if the Ball is touching the Paddle 
    public boolean touchPaddle(Player paddle){ 
        return Collisions.checkCollisions(this.getRepresentation(),paddle.getRepresentation());
    }

    /* Method to check if the Ball is touching a Brick */
    public boolean touchBrick(){
        return false;
    }
    
    /* Method to handle wall collision */
    public void wallCollision(){
        // double y = -forceCoordinates.getY();
        // forceCoordinates=new Coordinates(forceCoordinates.getX(), y);
        setForce(forceX, -forceY);
    }

    /* Method to handle paddle collision */
    public void paddleCollision(){
        setForce(forceX, - forceY);
    }

    /* Method to handle brick collision */
    public void brickCollision(){}

    public void move(){

		double x = Math.pow(DELTA_TIME,2)*forceX/MASS;
		double y = Math.pow(DELTA_TIME,2)*forceY/MASS;

		setPos(posX + x, posY + y);
    }

	/*public void update(){
		this.move();
	}*/
	
	public void update(Player player){ //actualisation des conditions physiques impactant la balle
	Ball ball = this;
	/*Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run(){*/
    	//TODO : ajuster les fonctions appelées a la classe player au lieu de paddle
        if (ball.touchPaddle(player)){
            ball.paddleCollision();
        }
       if (ball.touchBrick()){
            ball.brickCollision();
        }
        else if (ball.touchWall()){
            ball.wallCollision();
            
        }
        ball.move();
        
        
        //paddle.update();
		/*}
	};
	timer.schedule(task,0,20);*/
    	
        
    }

}
