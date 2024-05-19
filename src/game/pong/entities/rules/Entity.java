package game.pong.entities.rules;

import display.engine.PhysicsEngine;
import display.engine.rules.GraphicalObject;
import display.engine.rules.GraphicalObject.Boundary;
import display.engine.rules.PhysicalObject;
import display.engine.utils.Vector2D;
import display.view.brickbreakerview.GamePanel;
import game.pong.Pong;
import game.pong.entities.Player;

/**
 * The abstract class representing an entity in the game.
 * An entity is a physical object with a graphical representation.
 */
public abstract class Entity extends PhysicalObject {
	protected GraphicalObject representation;
	protected final static int WALL_WIDTH = 20;

	private boolean movingLeft=false;
	private boolean movingRight=false;
	private boolean movingUp=false;
	private boolean movingDown=false;

	private static final boolean DEFAULT_MOVABLE =true;
	private static final int DEFAULT_MASS = 0;

	/**
	 * Instantiates a new Entity with the given mass, position, movability, and graphical representation.
	 * 
	 * @param mass the mass of the Entity
	 * @param position its current position
	 * @param movable true if collisions affect its position
	 * @param representation the graphical representation of the entity
	 */
	public Entity(double mass, Vector2D position, boolean movable, GraphicalObject representation){
		super(mass, position, movable, representation);
		this.setRepresentation(representation);
	}

	/**
	 * Instantiates a new Entity with the given graphical representation.
	 * 
	 * @param representation the graphical representation of the entity
	 */
	public Entity(GraphicalObject representation) {
		super(DEFAULT_MASS,new Vector2D(representation.getPosX(),representation.getPosY()),DEFAULT_MOVABLE,representation);
		this.setRepresentation(representation);
	}

	/**
	 * Gets the graphical representation of the entity.
	 * 
	 * @return the graphical representation of the entity
	 */
	public GraphicalObject getRepresentation(){
		return this.representation;
	}

	/**
	 * Gets the current position of the entity.
	 * 
	 * @return the current position of the entity
	 */
	public Vector2D getPosition(){
		return this.position;
	}

	/**
	 * Sets the position of the entity.
	 * 
	 * @param newPos the new position of the entity
	 */
	public void setPosition(Vector2D newPos){
		this.position=newPos;
	}

	/**
	 * Sets the graphical representation of the entity.
	 * 
	 * @param representation the new graphical representation of the entity
	 */
	public void setRepresentation(GraphicalObject representation) {
		this.representation = representation;
	}

	/**
	 * Checks if the entity is active.
	 * 
	 * @return true if the entity is active, false otherwise
	 */
	public boolean isActive(){
		return active;
	}

	/**
	 * Moves the entity to the left.
	 */
	public void moveLeft(){
		movingLeft=true;
	}

	/**
	 * Moves the entity to the right.
	 */
	public void moveRight(){
		movingRight=true;
	}

	/**
	 * Moves the entity up.
	 */
	public void moveUp(){
		movingUp=true;
	}

	/**
	 * Moves the entity down.
	 */
	public void moveDown(){
		movingDown=true;
	}

	/**
	 * Stops the entity from moving to the left.
	 */
	public void stopLeft(){
		movingLeft=false;
	}

	/**
	 * Stops the entity from moving to the right.
	 */
	public void stopRight(){
		movingRight=false;
	}

	/**
	 * Stops the entity from moving up.
	 */
	public void stopUp(){
		movingUp=false;
	}

	/**
	 * Stops the entity from moving down.
	 */
	public void stopDown(){
		movingDown=false;
	}

	/**
	 * Checks if the entity is moving to the left.
	 * 
	 * @return true if the entity is moving to the left, false otherwise
	 */
	public boolean movingLeft(){
		return movingLeft;
	}

	/**
	 * Checks if the entity is moving to the right.
	 * 
	 * @return true if the entity is moving to the right, false otherwise
	 */
	public boolean movingRight(){
		return movingRight;
	}

	/**
	 * Checks if the entity is moving up.
	 * 
	 * @return true if the entity is moving up, false otherwise
	 */
	public boolean movingUp(){
		return movingUp;
	}

	/**
	 * Checks if the entity is moving down.
	 * 
	 * @return true if the entity is moving down, false otherwise
	 */
	public boolean movingDown(){
		return movingDown;
	}

	/**
	 * Moves the entity with the given speed.
	 * 
	 * @param speed the speed at which the entity will move
	 */
	public void move(int speed){
		
		if(movingLeft()){
			this.getRepresentation().setPosX(this.getRepresentation().getPosX() - speed);
			this.setPosition(new Vector2D(this.getRepresentation().getPosX() , this.getRepresentation().getPosY()));
		}
		if(movingRight()){
			this.getRepresentation().setPosX(this.getRepresentation().getPosX() + speed);
			this.setPosition(new Vector2D(this.getRepresentation().getPosX() , this.getRepresentation().getPosY()));
		}
		if(movingUp()){
			this.getRepresentation().setPosY(this.getRepresentation().getPosY() - speed);
			this.setPosition(new Vector2D(this.getRepresentation().getPosX(), this.getRepresentation().getPosY()));
		}
		if(movingDown()){
			this.getRepresentation().setPosY(this.getRepresentation().getPosY() + speed);
			this.setPosition(new Vector2D(this.getRepresentation().getPosX(), this.getRepresentation().getPosY() ));
			
		}
	}

	/**
	 * Checks if the entity will be off the screen if it moves in a given direction.
	 * 
	 * @param panel the game panel
	 * @param speed the number of pixels the entity will move
	 * @return true if the entity will be off the screen, false otherwise
	 */
	public boolean willBeOffScreen(GamePanel panel,int speed) {
		int[] boundaries = this.getRepresentation().getBoundaries();
		if(movingUp()){
			if(boundaries[GraphicalObject.Boundary.MIN_Y.ordinal()] - speed < WALL_WIDTH){
				return true;
			}
		}
		if (movingRight()){
			if(boundaries[GraphicalObject.Boundary.MAX_X.ordinal()] + speed > panel.getGameZone().getWidth()-WALL_WIDTH){
				return true;
			}
		}

		if(movingDown()){
			if(boundaries[GraphicalObject.Boundary.MAX_Y.ordinal()] + speed > 800 ){
				return true;
			}
		}
		
		if(movingLeft()){
			if(boundaries[GraphicalObject.Boundary.MIN_X.ordinal()] - speed < WALL_WIDTH){
				return true;
			}
		}

		return false;
	}

	/**
	 * Updates the velocity of the entity using acceleration and time spent.
	 * This method is overridden by Player because its movement is different.
	 * 
	 * @param deltaTime the time spent since the last update
	 */
	public void updateVelocity(double deltaTime) {
		this.speed = this.speed.add(acceleration.multiply(deltaTime/1000000));
	}

	/**
	 * Gets the current position of the entity.
	 * 
	 * @param speed the number of pixels the entity will move
	 * @return an array containing the current position of the entity, where the first element is X and the second is Y
	 */
	public int[] getCurrPos(int speed){
		int[] rep= new int[2];
		rep[1] =  this.getRepresentation().getPosY();
		rep[0] =  this.getRepresentation().getPosX();
		return rep;
	}

	/**
	 * Returns a correct impact point for all non-ball shaped objects.
	 * For ball objects, there is an overloading done in each class.
	 * 
	 * @param object the entity we want to get the impact point of the collision with
	 * @return a vector representing the collision point
	 */
	public Vector2D getImpactPoint(PhysicalObject object){
		Vector2D topRightPositionA = new Vector2D(object.getRepresentation().getBoundaries()[Boundary.MAX_X.ordinal()],object.getRepresentation().getBoundaries()[Boundary.MAX_Y.ordinal()]);
		Vector2D topLeftPositionA = new Vector2D(object.getRepresentation().getBoundaries()[Boundary.MIN_X.ordinal()],object.getRepresentation().getBoundaries()[Boundary.MAX_Y.ordinal()]);
		Vector2D bottomLeftPositionA = new Vector2D(object.getRepresentation().getBoundaries()[Boundary.MIN_X.ordinal()],object.getRepresentation().getBoundaries()[Boundary.MIN_Y.ordinal()]);
		Vector2D bottomRightPositionA = new Vector2D(object.getRepresentation().getBoundaries()[Boundary.MAX_X.ordinal()],object.getRepresentation().getBoundaries()[Boundary.MIN_Y.ordinal()]);
		//the vector from the paddle's center to its top left corner
		Vector2D centerToTopRightCornerVectorA = object.getRepresentation().vectorCenterToCoordinates(topRightPositionA.getX(), topRightPositionA.getY()); 
		Vector2D centerToTopLeftCornerVectorA = object.getRepresentation().vectorCenterToCoordinates(topLeftPositionA.getX(), topLeftPositionA.getY());
		Vector2D centerToBottomLeftCornerVectorA = object.getRepresentation().vectorCenterToCoordinates(bottomLeftPositionA.getX(), bottomLeftPositionA.getY());
		Vector2D centerToBottomRightCornerVectorA = object.getRepresentation().vectorCenterToCoordinates(bottomRightPositionA.getX(), bottomRightPositionA.getY());

		Vector2D objectAToEntityVector = object.getRepresentation().vectorFromCenterToCenter(this.getRepresentation());
			
		if (centerToTopLeftCornerVectorA.angleFromTo(objectAToEntityVector)<0 && centerToTopRightCornerVectorA.angleFromTo(objectAToEntityVector)>0){
			// the entity is above
			return new Vector2D(this.getPosition().getX()+this.getRepresentation().getWidth()/2, object.getPosition().getY());
		} 
		else if(centerToBottomRightCornerVectorA.angleFromTo(objectAToEntityVector)>0 && centerToTopRightCornerVectorA.angleFromTo(objectAToEntityVector)<0){
			// the entity is to the right
			return new Vector2D(object.getPosition().getX(), this.getPosition().getY()+this.getRepresentation().getWidth()/2);
		}
		else if(centerToBottomLeftCornerVectorA.angleFromTo(objectAToEntityVector)<0 && centerToTopLeftCornerVectorA.angleFromTo(objectAToEntityVector)>0){
			// the entity is to the left
			return new Vector2D(object.getPosition().getX() + object.getRepresentation().getWidth(), this.getPosition().getY()+this.getRepresentation().getWidth()/2);
		}
		else if(centerToBottomLeftCornerVectorA.angleFromTo(objectAToEntityVector)>0 && centerToBottomRightCornerVectorA.angleFromTo(objectAToEntityVector)<0){
			// the entity is below
			return new Vector2D(this.getPosition().getX() + this.getRepresentation().getWidth()/2, object.getPosition().getY() + object.getRepresentation().getHeight());
		}
		else{
			Vector2D nearestVertex = object.getNearestVertex(this.getPosition());
			return nearestVertex;
		}
	}

	/**
	 * Resolves collisions between two entities.
	 * 
	 * @param object the entity we want to resolve collisions with
	 */
	public void resolveCollision(PhysicalObject object) {
		//TODO: penser à l'élasticité : regarder formules physiques
		if (isMovable()){
			if (object.isMovable()){
				// TODO resolve collision when the two objects are movable
			}
			else{
				Vector2D impact = this.getImpactPoint(object);
				// TODO resolve collision when the other object is not movable
                if (impact == object.getTopLeftPosition() || impact == object.getTopRightPosition() || impact == object.getBottomLeftPosition() || impact == object.getBottomRightPosition()){
                    this.setSpeed(new Vector2D(- this.getSpeed().getX(), - this.getSpeed().getY()));
                }else{
                    Vector2D nearestVertex = object.getNearestVertex(impact);
                    Vector2D surfaceVector = new Vector2D(impact.getX() - nearestVertex.getX(), impact.getY() - nearestVertex.getY());
                    double slopeDot = surfaceVector.dotProduct(normalVectorHR);


                    if (slopeDot == 0) object.setSlope(Slope.VERTICAL);
                    else if (slopeDot == surfaceVector.getX()) object.setSlope(Slope.HORIZONTAL);
                    else object.setSlope(Slope.OTHER);


                    double incidenceAngle = Math.atan2(this.getSpeed().getY(),this.getSpeed().getX());
                    //double objectAVelocityAngle = Math.atan2(objectA.getSpeed().getY(), objectA.getSpeed().getX());
                    //double angleDifference = incidenceAngle - objectAVelocityAngle;
                    double objectASpeed = object.getSpeed().magnitude();
                    double rotationAngle = Math.atan2(objectASpeed, this.getSpeed().magnitude());
                    double reflexionAngle;


                    switch(object.getSlope()){
                        case VERTICAL: reflexionAngle = Math.PI - incidenceAngle + rotationAngle; break;
                        case HORIZONTAL: reflexionAngle = - incidenceAngle + rotationAngle; break;
                        case OTHER: reflexionAngle = 0; break; // TODO handle a slope that is not vertical or horizontal
                        default: reflexionAngle = 0; break;
                    }        

					if (object instanceof Player){
						//System.out.println("VITESSE: "+this.getSpeed());
						//System.out.println("acc AVANT: "+this.getAcceleration());
						//this.resolveSpeedToHigh(); 
						if (this.getAcceleration().getY() > 100) this.applyForce(new Vector2D(0, - this.getMass() * PhysicsEngine.rebondForce));
						//System.out.println("acc APRES: "+this.getAcceleration());
					}
                    this.setSpeed(new Vector2D(this.getSpeed().magnitude() * Math.cos(reflexionAngle), this.getSpeed().magnitude()* Math.sin(reflexionAngle)));
                }
                
            }
        }
    }

	/**
	 * Delete the entity in the Pong game
	 * 
	 * @param pong
	 */
	public void deleteEntity(Pong pong) {
		pong.getPanel().getGameZone().remove(this.getRepresentation());
		pong.getPhysicEngine().getPhysicalObjects().remove(this);
	}

	public void collided(){
		
	}

	
}
