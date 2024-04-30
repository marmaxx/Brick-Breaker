package game.breakout.entities;

import java.awt.Color;

import display.engine.rules.GraphicalObject;
import display.engine.rules.PhysicalObject;

import java.awt.Image;

import java.util.LinkedList;


import javax.swing.ImageIcon;

import display.engine.shapes.Circle;
import display.engine.utils.Vector2D;
import display.view.brickbreakerview.*;
import game.breakout.Breakout;
import game.breakout.entities.rules.Entity;


public class Ball extends Entity  {
	public static final long serialversionUID =10L;

	transient public static Image DEFAULT_IMAGE = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "ball.png").getImage();
	transient public static final Image DEFAULT_IMAGE2 = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Meteorite.png").getImage();
	public static  Color DEFAULT_COLOR = Color.RED;
	public static  Color DEFAULT_TRAIL_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final int DEFAULT_POS_X = 600;
	public static final int DEFAULT_POS_Y = 0;
	public static final int MOVE_SPEED = 5;
	public int angle; // it will be used later
	public boolean isMoving;

	public BallTrail trail = new BallTrail(DEFAULT_TRAIL_COLOR, this);

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
	public void destroy(){
		super.destroy();
		for (Ball.BallTrail.TrailPoint point: trail.points){
			point.point.destroy();
		}
	}

	@Override
	public void collided(PhysicalObject object) {
		
	}

	public class BallTrail {
		public Ball ball;
		public Color trailColor;
		public Image Image = new ImageIcon(Breakout.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "Meteorite.png").getImage();
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
			this.Image = imageBall;
		}
		
		private class TrailPoint {
			public Circle point;
			public double opacity;
	
			public TrailPoint(Circle point, double opacity) {
				this.point = point;
				this.opacity = opacity;
			}
		}
		
	
		public LinkedList<TrailPoint> points = new LinkedList<>();
		private static final double OPACITY_DECREMENT = 0.1;

		public void addPoint(Breakout breakout) {
			for (TrailPoint tp : points) {
				tp.opacity = Math.max(0, tp.opacity - OPACITY_DECREMENT);

				tp.point.setColor(new Color(r/255, g/255, b/255, (float) tp.opacity));
			}
			Circle point =null;
			if (trailColor ==null){
				point = new Circle(Image, ball.getRepresentation().getPosX()+(ball.getRepresentation().getWidth()/2), ball.getRepresentation().getPosY()+(ball.getRepresentation().getHeight()/2), 10, 10);
			
			}else{
				point = new Circle(trailColor, ball.getRepresentation().getPosX()+(ball.getRepresentation().getWidth()/2), ball.getRepresentation().getPosY()+(ball.getRepresentation().getHeight()/2), 10, 10);
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


	
}
