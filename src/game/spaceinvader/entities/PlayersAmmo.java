package game.spaceinvader.entities;

import java.awt.Color;

import display.engine.rules.GraphicalObject;
import display.engine.rules.PhysicalObject;

import java.awt.Image;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import display.engine.shapes.Rectangle;
import display.engine.utils.Vector2D;
import display.view.brickbreakerview.GamePanel;
import game.spaceinvader.SpaceInvader;
import game.spaceinvader.entities.rules.Entity;

public class PlayersAmmo extends Entity {
	public static final Image DEFAULT_IMAGE = new ImageIcon(SpaceInvader.ASSETS_PATH + "images" + java.io.File.separator + "entities" + java.io.File.separator + "cannonBall.png").getImage();
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_SIZE = 30;
	public static final int DEFAULT_POS_X = 600;
	public static final int DEFAULT_POS_Y = 0;
	public static final int MOVE_SPEED = 5;
	public static boolean hasHitAnEnemy;
	public int angle; // it will be used later
	public boolean isMoving;

	//public PlayerAmmoTrail trail = new PlayerAmmoTrail();


	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 * @param size the size of the playerAmmo
	 * @param color the color of the playerAmmo (ignored if the playerAmmo is represented by an image)
	 */
    public PlayersAmmo(
		Color color,
        int size,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Rectangle(color, (int)position.getX(), (int)position.getY(), size/1, size));
    }

	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 * @param size the size of the playerAmmo
	 * @param color the color of the playerAmmo (ignored if the playerAmmo is represented by an image)
	 */
    public PlayersAmmo(
		Image image,
        int size,
		double mass, Vector2D position, boolean movable
    ) {
		super(mass,position,movable,new Rectangle(image, (int)position.getX(), (int)position.getY(), size/1, size));
    }

	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 */
	public PlayersAmmo(double mass, Vector2D position, boolean movable) {
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
    public PlayersAmmo(
		Color color,
        int posX, int posY,
        int size
    ) {
		super(new Rectangle(color, posX, posY, size/1, size));
    }

	/**
	 * Instantiates a new PlayerAmmo
	 * 
	 * @param posX the initial x position of the playerAmmo
	 * @param posY the initial y position of the playerAmmo
	 * @param size the size of the playerAmmo
	 * @param color the color of the playerAmmo (ignored if the playerAmmo is represented by an image)
	 */
    public PlayersAmmo(
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
	public PlayersAmmo(int posX, int posY) {
		this(DEFAULT_IMAGE, posX, posY, DEFAULT_SIZE);

	}

	/**
	 * Instantiates a new PlayerAmmo
	 */
	public PlayersAmmo() {
		this(DEFAULT_IMAGE, DEFAULT_POS_X, DEFAULT_POS_Y, DEFAULT_SIZE);
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




    public Vector2D getImpactPoint(PlayersAmmo ammo){
		double distanceX = Math.abs(this.getPosition().getX()+this.getRepresentation().getWidth()/2 - ammo.getPosition().getX());
        double distanceY = Math.abs(this.getPosition().getY()+this.getRepresentation().getWidth()/2 - ammo.getPosition().getY());
        Vector2D vectorImpact = new Vector2D(distanceX/2, distanceY/2);
        return vectorImpact.add(ammo.getPosition());
	}

	@Override
	public void resolveCollision(PhysicalObject object) {
		super.resolveCollision(object);
		if (isMovable()){
            if (object.isMovable()){
				if (object.getRepresentation() instanceof Rectangle){
					//TODO: handle playerAmmo to playerAmmo collision
				}
			}
		}
	}

	@Override
	public void collided(PhysicalObject object) {
		super.collided();
		if (object instanceof Enemy || object instanceof Wall){
			this.setHasHitAnEnemy(true);
		}
	}

	/* 
	public void collided(PhysicalObject object, SpaceInvader si) {
		super.collided();
		this.collided(object);
		deletePlayersAmmo(si);
		respawnPlayersAmmo(si, 100, 100);

	}
	*/
	

	// create the despawn for checkPlayerAmmoingame
	public void deletePlayersAmmo(SpaceInvader si){
		//si.getPlayersAmmo().trail.remove(si);
		si.getPanel().getGameZone().remove(si.getPlayersAmmo().getRepresentation());
		si.getPanel().getGameZone().remove(si.getPlayersAmmo().getRepresentation());
		si.getPhysicEngine().getPhysicalObjects().remove(si.getPlayersAmmo());
		si.getPlayersAmmos().remove(si.getPlayersAmmo());
	}

	// create the respawn for checkPlayerAmmoingame
	public void respawnPlayersAmmo(SpaceInvader si, int x, int y){
		/* 
		int x = si.player.getRepresentation().getX()+si.player.getRepresentation().getWidth()/3;
		int y = si.player.getRepresentation().getY()-si.player.getRepresentation().getWidth()/3;
		*/
		PlayersAmmo playerAmmo = new PlayersAmmo(PlayersAmmo.DEFAULT_IMAGE, 30, 50, new Vector2D(x, y), true);
		playerAmmo.getRepresentation().setBounds(x, y, 30, 30);
		playerAmmo.setSpeed(new Vector2D(0, -0.3));
		playerAmmo.active=false;
		si.setPlayersAmmo(playerAmmo);
		si.getPlayersAmmos().add(playerAmmo);
		si.getPanel().getGameZone().add(si.getPlayersAmmo().getRepresentation());
		si.getPhysicEngine().getPhysicalObjects().add(playerAmmo);
	}

	// to check if it collides with an enemy
	
	
	public class PlayerAmmoTrail {
		private class TrailPoint {
			public Rectangle point;
			public double opacity;
	
			public TrailPoint(Rectangle point, double opacity) {
				this.point = point;
				this.opacity = opacity;
			}
		}
	
		private LinkedList<TrailPoint> points = new LinkedList<>();
		private static final double OPACITY_DECREMENT = 0.1;

		public void addPoint(Rectangle point, SpaceInvader breakout) {
			for (TrailPoint tp : points) {
				tp.opacity = Math.max(0, tp.opacity - OPACITY_DECREMENT);
		
				// Interpolate between red and yellow based on the opacity
				float r = 1.0f;
				float g = (float) tp.opacity;  // Green component increases as opacity decreases
				float b = 0.0f;
		
				// Use the opacity field to set the color of the Rectangle
				tp.point.setColor(new Color(r, g, b, (float) tp.opacity));
			}
		
			points.add(new TrailPoint(point, 1));
			breakout.getPanel().getGameZone().add(point);
		
			if (points.size() > 10) { // Limit the trail length
				breakout.getPanel().getGameZone().remove(points.getFirst().point);
				points.removeFirst();
			}
		}

		public void remove(SpaceInvader breakout){
			for (int i = 0; i < points.size(); i++) breakout.getPanel().getGameZone().remove(points.getFirst().point);
			points.clear();
		}

		}


	public boolean getHasHitAnEnemy(){
		return PlayersAmmo.hasHitAnEnemy;
	}
	
	public void setHasHitAnEnemy(boolean b){
		PlayersAmmo.hasHitAnEnemy = b;
	}
}
