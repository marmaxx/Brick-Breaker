package display.engine.rules;
import java.io.Serializable;
import java.util.Vector;

import display.engine.rules.GraphicalObject.Boundary;
import display.engine.utils.*;
import game.breakout.entities.Ball;
import game.breakout.entities.Brick;
import game.breakout.entities.Player;
import game.breakout.entities.Wall;
import game.breakout.entities.rules.Entity;

public abstract class PhysicalObject implements Serializable{
    public static final long serialVersionUID = 2L;
    public boolean active;
    protected double mass;
    protected Vector2D position ;
    protected Vector2D speed = new Vector2D(0, 0);
    protected Vector2D acceleration = new Vector2D(0, 0);
    protected boolean movable; //indicates if the object can move, if its position is influenced by collisions or frictions 
    //TODO: regarder si movable est vraiment utile, pareil pour elasticity
    protected GraphicalObject representation;
    protected double elasticity;
    protected double rotationCoeff=1; //coeff of rotation after the paddle hit the ball with speed ; has an impact on the next collision
    //TODO: gérer la rotation 


    protected Vector2D normalVectorVT = new Vector2D(0, 1);
    protected Vector2D normalVectorVB = new Vector2D(0, -1);
    protected Vector2D normalVectorHR = new Vector2D(1, 0);
    protected Vector2D normalVectorHL = new Vector2D(-1, 0);
    protected Slope slope;
    protected Vector2D topRightPosition;
    protected Vector2D bottomLeftPosition;
    protected Vector2D bottomRightPosition;
    protected double MAX_SPEED = 0.8;

    public PhysicalObject(){
        
    }

    public PhysicalObject(double mass, Vector2D position, boolean movable, GraphicalObject representation){

        this.mass=mass;
        this.position=position;
        this.movable=movable;
        this.representation=representation;
        active=true;
    }

    public enum Slope {
        VERTICAL, HORIZONTAL, OTHER
    }

    public Slope getSlope(){
        return this.slope;
    }

    public void setSlope (Slope otherSlop){
        this.slope=otherSlop;
    }

    /**
     * handles non physics related collision implications (updating score etc)
     * @param object
     */
    public abstract void collided(PhysicalObject object);

    /**
	 * deactivates the phyiscal object from the view
	 */
	public void destroy() {
        active=false;
        this.getRepresentation().destroy();
    }


    /**
	 * Gets the graphical representation of the entity
	 * 
	 * @return the graphical representation of the entity
	 */
    public GraphicalObject getRepresentation(){
        return this.representation;
    }

    public Vector2D getPosition(){
        return this.position;
    }



    // Getters et setters
    public double getMass() {
            return this.mass;
        }

    public Vector2D getSpeed() {
        return this.speed;
    }

    public Vector2D getAcceleration(){
        return this.acceleration;
    }
    public Vector2D getTopLeftPosition(){
        return new Vector2D(this.getPosition().getX(),this.getPosition().getY());
    }
    public Vector2D getTopRightPosition(){
        return new Vector2D(this.getPosition().getX() + this.getRepresentation().getWidth(), this.getPosition().getY());
    }

    public Vector2D getBottomLeftPosition(){
        return new Vector2D(this.getRepresentation().getX(), this.getRepresentation().getY() + this.getRepresentation().getHeight());
    }

    public Vector2D getBottomRightPosition(){
        return new Vector2D(this.getRepresentation().getX() + this.getRepresentation().getWidth(), this.getRepresentation().getY() + this.getRepresentation().getHeight());
    }


    public void setAcceleration(Vector2D newAcceleration){
        this.acceleration=newAcceleration;
    }

    public boolean isMovable(){
        return this.movable;
    }


    public void setSpeed(Vector2D newSpeed){
        this.speed=newSpeed;
    }


    // updating the position thanks to the speed and time spent
    public void updatePosition(double deltaTime) {
        Vector2D displacement = speed.multiply(deltaTime);
        position = position.add(displacement);
    }




    // applying force to an object; its acceleration is modified
    public void applyForce(Vector2D force) {
        if(isMovable()) this.acceleration = this.acceleration.add(force.multiply(1.0 / mass));
    }

    /**
     * 
     * @param deltaTime
     * @return the physical object next position [0] is x and [1] is y
     */
    public int[] getNextPos(double deltaTime){
        int[] rep = new int[2];
        Vector2D displacement = speed.multiply(deltaTime);
        rep[0] = (int) (position.getX()+displacement.getX());
        rep[1] = (int) (position.getY()+displacement.getY());
        return rep;
    }


    // checking if a collision happened
    public boolean isColliding(PhysicalObject objectB) {
        return this.representation.isColliding(objectB.representation);
    }

    public boolean isGoingToCollide(PhysicalObject objectB, double deltaTime){
        int[] thisNextPos = this.getNextPos(deltaTime);
        int[] BNextPos = objectB.getNextPos(deltaTime);
        return this.representation.isGoingToCollide(objectB.getRepresentation(), thisNextPos, BNextPos);

    }


    public Vector2D getNearestVertex(Vector2D point){
        double distTL = Math.sqrt(Math.pow(point.getX() - this.getPosition().getX(), 2) + Math.pow(point.getY() - this.getPosition().getY(), 2));
        double distTR = Math.sqrt(Math.pow(point.getX() - this.getTopRightPosition().getX(), 2) + Math.pow(point.getY() - this.getTopRightPosition().getY(), 2));
        double distBL = Math.sqrt(Math.pow(point.getX() - this.getBottomLeftPosition().getX(), 2) + Math.pow(point.getY() - this.getBottomLeftPosition().getY(), 2));
        double distBR = Math.sqrt(Math.pow(point.getX() - this.getBottomRightPosition().getX(), 2) + Math.pow(point.getY() - this.getBottomRightPosition().getY(), 2));
        double dist1 = Math.min(distTL, distTR);
        double dist2 = Math.min(distBL, distBR);
        double dist = Math.min(dist1, dist2);
        if (dist == distTL){return this.getPosition();}
        else if (dist == distTR){ return new Vector2D(this.getPosition().getX() + this.getRepresentation().getWidth(), this.getPosition().getY());}
        else if (dist == distBL){ return new Vector2D(this.getPosition().getX(), this.getPosition().getY() + this.getRepresentation().getHeight());}
        else{ return new Vector2D(this.getPosition().getX() + this.getRepresentation().getWidth(), this.getPosition().getY()  + this.getRepresentation().getHeight());}
    }

    public Vector2D getNormalVector(Vector2D vect, PhysicalObject objectA){
        if (normalVectorHR.dotProduct(vect) == 0){
            if (this.getPosition().getX() < objectA.getPosition().getX()){
                return normalVectorHL;
            }
            return normalVectorHR;
        }
        else if(this.getPosition().getY() < objectA.getPosition().getY()){
            return normalVectorVT;
        }
        return normalVectorVB;
    }

    public abstract void updateVelocity(double deltaTime);

    public boolean isActive() {
        return active;
    }

    public abstract void resolveCollision(PhysicalObject objectB);

    public void resolveSpeedToHigh(){
        if (this.getSpeed().getX() > MAX_SPEED) this.setSpeed(new Vector2D(MAX_SPEED, this.getSpeed().getY()));
        if (this.getSpeed().getY() > MAX_SPEED) this.setSpeed(new Vector2D(this.getSpeed().getX(), MAX_SPEED));
    }

}
