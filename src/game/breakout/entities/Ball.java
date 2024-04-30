package game.breakout.entities;

import java.awt.Color;

import display.engine.rules.GraphicalObject;
import display.engine.rules.PhysicalObject;

import java.awt.Image;

import java.util.LinkedList;


import javax.swing.ImageIcon;

import display.engine.shapes.Circle;
import display.engine.utils.Vector2D;
import display.view.GamePanel;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;


import java.io.Serializable;

public class Ball extends Entity  {
	public static final long serialversionUID =10L;

	transient public static final Image DEFAULT_IMAGE = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "ball.png").getImage();
	transient public static final Image DEFAULT_IMAGE2 = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Meteorite.png").getImage();
	transient public static final Image PLANET_IMAGE = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "etoileNoire.png").getImage();
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final int DEFAULT_POS_X = 600;
	public static final int DEFAULT_POS_Y = 0;
	public static final int MOVE_SPEED = 5;
	public int angle; // it will be used later
	public boolean isMoving;

	public BallTrail trail = new BallTrail();

	/**
 	*constructor to be used only for deserialziation 
 	*/
	public Ball(){

	}

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 */
    public Ball(
		Color color,
        int size,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Circle(color, (int)position.getX(), (int)position.getY(), size, size));
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 */
    public Ball(
		Image image,
        int size,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Circle(image, (int)position.getX(), (int)position.getY(), size, size));
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 */
	public Ball(double mass, Vector2D position, boolean movable) {
		this(DEFAULT_IMAGE, DEFAULT_SIZE,mass,position,movable);

	}

  	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 */
    public Ball(
		Color color,
        int posX, int posY,
        int size
    ) {
		super(new Circle(color, posX, posY, size, size));
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 * @param size the size of the ball
	 * @param color the color of the ball (ignored if the ball is represented by an image)
	 */
    public Ball(
		Image image,
        int posX, int posY,
        int size
    ) {
		super(new Circle(image, posX, posY, size, size));
    }

	/**
	 * Instantiates a new Ball
	 * 
	 * @param posX the initial x position of the ball
	 * @param posY the initial y position of the ball
	 */
	public Ball(int posX, int posY) {
		this(DEFAULT_IMAGE, posX, posY, DEFAULT_SIZE);

	}






	public boolean getIsMoving(){
		return this.isMoving;
	}

	public void setIsMoving(boolean b){
		this.isMoving = b;
	}




	public boolean willLoose(GamePanel panel, int speed){
		int [] boundaries = this.getRepresentation().getNextBoundaries(this.getNextPos(speed));
		return boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed  > panel.getGameZone().getHeight();
	}




    public Vector2D getImpactPoint(Ball ball){
		double distanceX = Math.abs(this.getPosition().getX()+this.getRepresentation().getWidth()/2 - ball.getPosition().getX());
        double distanceY = Math.abs(this.getPosition().getY()+this.getRepresentation().getWidth()/2 - ball.getPosition().getY());
        Vector2D vectorImpact = new Vector2D(distanceX/2, distanceY/2);
        return vectorImpact.add(ball.getPosition());
	}

	@Override
	public void resolveCollision(PhysicalObject object) {
		super.resolveCollision(object);
		if (isMovable()){
            if (object.isMovable()){
				if (object.getRepresentation() instanceof Circle){
					//TODO: handle ball to ball collision
				}
			}
		}
	}

	@Override
	public void collided(PhysicalObject object) {
		
	}

	public class BallTrail implements Serializable{
		private class TrailPoint implements Serializable{
			public Circle point;
			public double opacity;
	
			public TrailPoint(Circle point, double opacity) {
				this.point = point;
				this.opacity = opacity;
			}
		}

		
	
		private LinkedList<TrailPoint> points = new LinkedList<>();
		private static final double OPACITY_DECREMENT = 0.1;

		public void addPoint(Circle point, Breakout breakout) {
			for (TrailPoint tp : points) {
				tp.opacity = Math.max(0, tp.opacity - OPACITY_DECREMENT);
		
				// Interpolate between red and yellow based on the opacity
				float r = 1.0f;
				float g = (float) tp.opacity;  // Green component increases as opacity decreases
				float b = 0.0f;
		
				// Use the opacity field to set the color of the Circle
				tp.point.setColor(new Color(r, g, b, (float) tp.opacity));
			}
		
			points.add(new TrailPoint(point, 1));
			breakout.getPanel().getGameZone().add(point);
		
			if (points.size() > 10) { // Limit the trail length
				breakout.getPanel().getGameZone().remove(points.getFirst().point);
				points.removeFirst();
			}
		}

		public void remove(Breakout breakout){
			for (int i = 0; i < points.size(); i++) breakout.getPanel().getGameZone().remove(points.getFirst().point);
			points.clear();
		}

	
	}

	/**
     * Applies gravitational field forces to all movable objects
     * 
     * @param deltaTime the time since last tick
     */
    public void applyGravitationalForces(double deltaTime, Ball planete) {
        final double G = 6.67430; // gravitational constant

			Vector2D r = planete.getPosition().subtract(this.getPosition());
			//if (r.magnitude()<200) return;
			double distance = r.magnitude();

			System.out.println("objet 1: "+this.getMass()+" "+this.getPosition());
			System.out.println("objet 2: "+planete.getMass()+" "+planete.getPosition());

			double forceMagnitude = G * (this.getMass() * planete.getMass()) / (distance * distance);
			//System.out.println("force norme :"+forceMagnitude);
			Vector2D force = r.normalize().multiply(forceMagnitude);

			this.applyForce(force.multiply(deltaTime));
			//this.setSpeed(force.multiply(deltaTime));
			System.out.println(force.multiply(deltaTime));
			System.out.println(this.getAcceleration());
			System.out.println(this.getSpeed());
			System.out.println();
    }


	
}
