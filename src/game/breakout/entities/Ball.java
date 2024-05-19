package game.breakout.entities;

import java.awt.Color;

import display.engine.rules.GraphicalObject;
import display.engine.rules.PhysicalObject;

import java.awt.Image;
import java.io.Serializable;
import java.util.LinkedList;


import javax.swing.ImageIcon;

import display.engine.shapes.Circle;
import display.engine.utils.Vector2D;
import display.view.brickbreakerview.*;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;


/**
 * Represents a ball entity in the breakout game.
 */
public class Ball extends Entity  {
	public static final long serialversionUID =10L;

	transient public static Image DEFAULT_IMAGE = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "ball.png").getImage();
	transient public static Image DEFAULT_IMAGE2 = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Meteorite.png").getImage();
	public static Color DEFAULT_COLOR = Color.RED;
	public static Color DEFAULT_TRAIL_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final int DEFAULT_POS_X = 600;
	public static final int DEFAULT_POS_Y = 0;
	public static final int MOVE_SPEED = 5;
	public int angle; // it will be used later
	public boolean isMoving;

	public BallTrail trail = new BallTrail(DEFAULT_TRAIL_COLOR, this);

	/**
	 * Default constructor to be used only for deserialization.
	 */
	public Ball(){

	}

	/**
	 * Instantiates a new Ball with the specified color, size, mass, position, and movability.
	 * 
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 * @param size the size of the ball
	 * @param mass the mass of the ball
	 * @param position the initial position of the ball
	 * @param movable whether the ball is movable or not
	 */
    public Ball(
		Color color,
        int size,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Circle(color, (int)position.getX(), (int)position.getY(), size, size));
    }

	/**
	 * Instantiates a new Ball with the specified image, size, mass, position, and movability.
	 * 
	 * @param image the image representing the ball
	 * @param size the size of the ball
	 * @param mass the mass of the ball
	 * @param position the initial position of the ball
	 * @param movable whether the ball is movable or not
	 */
    public Ball(
		Image image,
        int size,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Circle(image, (int)position.getX(), (int)position.getY(), size, size));
    }

	/**
	 * Instantiates a new Ball with the default image, size, mass, position, and movability.
	 * 
	 * @param mass the mass of the ball
	 * @param position the initial position of the ball
	 * @param movable whether the ball is movable or not
	 */
	public Ball(double mass, Vector2D position, boolean movable) {
		this(DEFAULT_IMAGE, DEFAULT_SIZE,mass,position,movable);

	}

  	/**
	 * Instantiates a new Ball with the specified color, position, and size.
	 * 
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 */
    public Ball(
		Color color,
        int posX, int posY,
        int size
    ) {
		super(new Circle(color, posX, posY, size, size));
    }

	/**
	 * Instantiates a new Ball with the specified image, position, and size.
	 * 
	 * @param image the image representing the ball
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 */
    public Ball(
		Image image,
        int posX, int posY,
        int size
    ) {
		super(new Circle(image, posX, posY, size, size));
    }

	/**
	 * Instantiates a new Ball with the default image, position, and size.
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 */
	public Ball(int posX, int posY) {
		this(DEFAULT_IMAGE, posX, posY, DEFAULT_SIZE);

	}

	/**
	 * Gets the current moving state of the ball.
	 * 
	 * @return true if the ball is moving, false otherwise
	 */
	public boolean getIsMoving(){
		return this.isMoving;
	}

	/**
	 * Sets the moving state of the ball.
	 * 
	 * @param b the new moving state of the ball
	 */
	public void setIsMoving(boolean b){
		this.isMoving = b;
	}

	/**
	 * Checks if the ball will lose the game by going below the game panel.
	 * 
	 * @param panel the game panel
	 * @param speed the speed of the ball
	 * @return true if the ball will lose, false otherwise
	 */
	public boolean willLoose(GamePanel panel, int speed){
		int [] boundaries = this.getRepresentation().getNextBoundaries(this.getNextPos(speed));
		return boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed  > panel.getGameZone().getHeight();
	}

    /**
     * Gets the impact point of this ball with another ball.
     * 
     * @param ball the other ball
     * @return the impact point as a vector
     */
    public Vector2D getImpactPoint(Ball ball){
		double distanceX = Math.abs(this.getPosition().getX()+this.getRepresentation().getWidth()/2 - ball.getPosition().getX());
        double distanceY = Math.abs(this.getPosition().getY()+this.getRepresentation().getWidth()/2 - ball.getPosition().getY());
        Vector2D vectorImpact = new Vector2D(distanceX/2, distanceY/2);
        return vectorImpact.add(ball.getPosition());
	}

	@Override
	public void resolveCollision(PhysicalObject object) {
		if (isMovable() && object.isMovable() && object.getRepresentation() instanceof Circle) {
			// handle ball to ball collision
			double dx = this.getPosition().getX() - object.getPosition().getX();
			double dy = this.getPosition().getY() - object.getPosition().getY();
	
			// Get the radii of the balls
			double r0 = ((Circle) this.getRepresentation()).getWidth();
			double r1 = ((Circle) object.getRepresentation()).getWidth();
	
			// If the balls are intersecting, do nothing
			if (Math.sqrt(dx * dx + dy * dy) < (r0 + r1)/2) {
				return;
			}
	
			double angle = Math.atan2(dy, dx);
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
	
			// Rotate velocities to line up with collision angle
			double vel0 = (this.getSpeed().getX() * cos + this.getSpeed().getY() * sin);
			double vel1 = (object.getSpeed().getX() * cos + object.getSpeed().getY() * sin);
	
			// Get the masses of the balls
			double m0 = this.getMass();
			double m1 = object.getMass();
	
			// Collision reaction along the normal
			double velocityX0 = (m0 == m1) ? vel0 : ((m0 - m1) / (m0 + m1)) * vel0 + ((2 * m1) / (m0 + m1)) * vel1;
	
			// Velocities along the tangent remain the same
			double velocityY0 = this.getSpeed().getY() * cos - this.getSpeed().getX() * sin;
	
			// Rotate velocities back
			this.getSpeed().setX(velocityX0 * cos - velocityY0 * sin);
			this.getSpeed().setY(velocityY0 * cos + velocityX0 * sin);
		}else if (isMovable() && object.getRepresentation() instanceof Circle) {
            // If the other object is not movable, reverse the direction of this object
            this.getSpeed().setX(-this.getSpeed().getX());
            this.getSpeed().setY(-this.getSpeed().getY());
        }else{
			super.resolveCollision(object);
		}
	}

	@Override
	public void destroy(){
		super.destroy();
		for (Ball.BallTrail.TrailPoint point: trail.points){
			point.point.destroy();
		}
	}

	@Override
	public void collided(PhysicalObject object) {
		
	}

	/**
	 * Represents the trail of the ball.
	 */
	public class BallTrail implements Serializable{
		public Ball ball;
		public Color trailColor;
		public ImageIcon Image = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Meteorite.png");
		public float r;
		public float g;
		public float b;
		
		public BallTrail(Color trailColor, Ball thisBall) {
			this.ball = thisBall;
			this.trailColor = trailColor;
			this.r = trailColor.getRed();
			this.g = trailColor.getGreen();
			this.b = trailColor.getBlue();
		}

		public BallTrail(Ball thisBall) {
			this.ball = thisBall;
			this.trailColor = Color.RED;
		}
		public BallTrail(Image imageBall ,Ball thisBall) {
			this.ball = thisBall;
			this.Image = new ImageIcon(imageBall);
		}
		
		private class TrailPoint implements Serializable{
			public Circle point;
			public double opacity;
	
			public TrailPoint(Circle point, double opacity) {
				this.point = point;
				this.opacity = opacity;
			}
		}
	
		
	
		public LinkedList<TrailPoint> points = new LinkedList<>();
		private static final double OPACITY_DECREMENT = 0.1;

		/**
		 * Adds a new point to the ball trail.
		 * 
		 * @param breakout the breakout game instance
		 */
		public void addPoint(Breakout breakout) {
			for (TrailPoint tp : points) {
				tp.opacity = Math.max(0, tp.opacity - OPACITY_DECREMENT);

				tp.point.setColor(new Color(r/255, g/255, b/255, (float) tp.opacity));
			}
			Circle point =null;
			if (trailColor ==null){
				point = new Circle(Image.getImage(), ball.getRepresentation().getPosX()+(ball.getRepresentation().getWidth()/2), ball.getRepresentation().getPosY()+(ball.getRepresentation().getHeight()/2), 10, 10);
				
			}else{
				point = new Circle(trailColor, ball.getRepresentation().getPosX()+(ball.getRepresentation().getWidth()/2), ball.getRepresentation().getPosY()+(ball.getRepresentation().getHeight()/2), 10, 10);
			}
			point.setBounds(ball.getRepresentation().getPosX()+(ball.getRepresentation().getWidth()/2), ball.getRepresentation().getPosY()+(ball.getRepresentation().getHeight()/2), 10, 10);
			points.add(new TrailPoint(point, 1));
			breakout.getPanel().getGameZone().add(point);

			if (points.size() > 10) { // Limit the trail length
				breakout.getPanel().getGameZone().remove(points.getFirst().point);
				points.removeFirst();
			}
		}

		/**
		 * Removes the ball trail from the game panel.
		 * 
		 * @param breakout the breakout game instance
		 */
		public void remove(Breakout breakout){
			for (int i = 0; i < points.size(); i++) breakout.getPanel().getGameZone().remove(points.getFirst().point);
			points.clear();
		}

	
	}




	
}
