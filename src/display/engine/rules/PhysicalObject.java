package display.engine.rules;
import java.io.Serializable;

import display.engine.rules.GraphicalObject.Boundary;
import display.engine.shapes.Circle;
import display.engine.utils.*;
import game.breakout.entities.Planet;

/**
 * The abstract class representing a physical object in the game.
 */
public abstract class PhysicalObject implements Serializable{
    public static final long serialVersionUID = 2L;
    public boolean active;
    protected double mass;
    protected Vector2D position ;
    protected Vector2D speed = new Vector2D(0, 0);
    protected Vector2D acceleration = new Vector2D(0, 0);
    protected boolean movable; //indicates if the object can move, if its position is influenced by collisions or frictions 
    protected GraphicalObject representation;
    protected double rotationCoeff = 1; // coeff of rotation after the paddle hit the ball with speed; has an impact on the next collision

    protected Vector2D normalVectorVT = new Vector2D(0, 1);
    protected Vector2D normalVectorVB = new Vector2D(0, -1);
    protected Vector2D normalVectorHR = new Vector2D(1, 0);
    protected Vector2D normalVectorHL = new Vector2D(-1, 0);
    protected Slope slope;
    protected Vector2D topRightPosition;
    protected Vector2D bottomLeftPosition;
    protected Vector2D bottomRightPosition;
    protected double MAX_SPEED = 0.8;

    /**
     * Default constructor for PhysicalObject.
     */
    public PhysicalObject(){
        
    }

    /**
     * Constructor for PhysicalObject.
     * 
     * @param mass the mass of the object
     * @param position the position of the object
     * @param movable indicates if the object can move
     * @param representation the graphical representation of the object
     */
    public PhysicalObject(double mass, Vector2D position, boolean movable, GraphicalObject representation){
        this.mass=mass;
        this.position=position;
        this.movable=movable;
        this.representation=representation;
        active=true;
    }

    /**
     * Enum representing the slope of the object.
     */
    public enum Slope {
        VERTICAL, HORIZONTAL, OTHER
    }

    /**
     * Get the slope of the object.
     * 
     * @return the slope of the object
     */
    public Slope getSlope(){
        return this.slope;
    }

    /**
     * Set the slope of the object.
     * 
     * @param otherSlop the new slope of the object
     */
    public void setSlope (Slope otherSlop){
        this.slope=otherSlop;
    }

    /**
     * Handles non-physics related collision implications (updating score, etc.).
     * 
     * @param object the object that this object collided with
     */
    public abstract void collided(PhysicalObject object);

    /**
     * Deactivates the physical object from the view.
     */
    public void destroy() {
        active=false;
        this.getRepresentation().destroy();
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
     * Gets the position of the object.
     * 
     * @return the position of the object
     */
    public Vector2D getPosition(){
        return this.position;
    }

    /**
     * Gets the rotation coefficient of the object.
     * 
     * @return the rotation coefficient of the object
     */
    public double getRotationCoeff() {
        return rotationCoeff;
    }

    /**
     * Sets the rotation coefficient of the object.
     * 
     * @param rotationCoeff the new rotation coefficient of the object
     */
    public void setRotationCoeff(double rotationCoeff) {
        this.rotationCoeff = rotationCoeff;
    }

    // Getters and setters

    /**
     * Gets the mass of the object.
     * 
     * @return the mass of the object
     */
    public double getMass() {
        return this.mass;
    }

    /**
     * Gets the speed of the object.
     * 
     * @return the speed of the object
     */
    public Vector2D getSpeed() {
        return this.speed;
    }

    /**
     * Gets the acceleration of the object.
     * 
     * @return the acceleration of the object
     */
    public Vector2D getAcceleration(){
        return this.acceleration;
    }

    /**
     * Gets the top left position of the object.
     * 
     * @return the top left position of the object
     */
    public Vector2D getTopLeftPosition(){
        return new Vector2D(this.getPosition().getX(),this.getPosition().getY());
    }

    /**
     * Gets the top right position of the object.
     * 
     * @return the top right position of the object
     */
    public Vector2D getTopRightPosition(){
        return new Vector2D(this.getPosition().getX() + this.getRepresentation().getWidth(), this.getPosition().getY());
    }

    /**
     * Gets the bottom left position of the object.
     * 
     * @return the bottom left position of the object
     */
    public Vector2D getBottomLeftPosition(){
        return new Vector2D(this.getRepresentation().getX(), this.getRepresentation().getY() + this.getRepresentation().getHeight());
    }

    /**
     * Gets the bottom right position of the object.
     * 
     * @return the bottom right position of the object
     */
    public Vector2D getBottomRightPosition(){
        return new Vector2D(this.getRepresentation().getX() + this.getRepresentation().getWidth(), this.getRepresentation().getY() + this.getRepresentation().getHeight());
    }

    /**
     * Sets the acceleration of the object.
     * 
     * @param newAcceleration the new acceleration of the object
     */
    public void setAcceleration(Vector2D newAcceleration){
        this.acceleration=newAcceleration;
    }

    /**
     * Checks if the object is movable.
     * 
     * @return true if the object is movable, false otherwise
     */
    public boolean isMovable(){
        return this.movable;
    }

    /**
     * Checks if the object is a planet.
     * 
     * @return true if the object is a planet, false otherwise
     */
    public boolean isAPlanet(){
        return (this instanceof Planet);
    }

    /**
     * Sets the speed of the object.
     * 
     * @param newSpeed the new speed of the object
     */
    public void setSpeed(Vector2D newSpeed){
        this.speed=newSpeed;
    }

    /**
     * Updates the position of the object based on the speed and time spent.
     * 
     * @param deltaTime the time spent
     */
    public void updatePosition(double deltaTime) {
        Vector2D displacement = speed.multiply(deltaTime);
        position = position.add(displacement);
    }

    /**
     * Applies a force to the object, modifying its acceleration.
     * 
     * @param force the force to be applied
     */
    public void applyForce(Vector2D force) {
        if(isMovable()) this.acceleration = this.acceleration.add(force.multiply(1.0 / mass));
    }

    /**
     * Gets the next position of the object after a given time.
     * 
     * @param deltaTime the time spent
     * @return the next position of the object as an array, where [0] is x and [1] is y
     */
    public int[] getNextPos(double deltaTime){
        int[] rep = new int[2];
        Vector2D displacement = speed.multiply(deltaTime);
        rep[0] = (int) (position.getX()+displacement.getX());
        rep[1] = (int) (position.getY()+displacement.getY());
        return rep;
    }

    /**
     * Checks if the object is colliding with another object.
     * 
     * @param objectB the other object to check collision with
     * @return true if the objects are colliding, false otherwise
     */
    public boolean isColliding(PhysicalObject objectB) {
        int[] thisBoundingBox = this.getRepresentation().getBoundaries();
        int[] objectBoundingBox = objectB.getRepresentation().getBoundaries();

        if (objectB.getRepresentation() instanceof Circle && this.getRepresentation() instanceof Circle){
            Circle circle1 = (Circle) this.getRepresentation();
            Circle circle2 = (Circle) objectB.getRepresentation();

            double distance = Math.sqrt(Math.pow(circle2.getCenterX() - circle1.getCenterX(), 2) + Math.pow(circle2.getCenterY() - circle1.getCenterY(), 2));
            return distance <= (circle1.getWidth() + circle2.getWidth());
        }    
        else{
            boolean isColliding = (thisBoundingBox[Boundary.MAX_Y.ordinal()] >= objectBoundingBox[Boundary.MIN_Y.ordinal()]
                && thisBoundingBox[Boundary.MIN_Y.ordinal()] <= objectBoundingBox[Boundary.MAX_Y.ordinal()]
                && thisBoundingBox[Boundary.MIN_X.ordinal()] <= objectBoundingBox[Boundary.MAX_X.ordinal()]
                && thisBoundingBox[Boundary.MAX_X.ordinal()] >= objectBoundingBox[Boundary.MIN_X.ordinal()]);

            return isColliding;
        }
    }

    /**
     * Checks if the object is going to collide with another object after a given time.
     * 
     * @param objectB the other object to check collision with
     * @param deltaTime the time spent
     * @return true if the objects are going to collide, false otherwise
     */
    public boolean isGoingToCollide(PhysicalObject objectB, double deltaTime){
        int[] thisNextPos = this.getNextPos(deltaTime);
        int[] BNextPos = objectB.getNextPos(deltaTime);

        int[] thisBoundingBox = this.getRepresentation().getNextBoundaries(thisNextPos);
        int[] objectBoundingBox = objectB.getRepresentation().getNextBoundaries(BNextPos);

        if (objectB.getRepresentation() instanceof Circle && this.getRepresentation() instanceof Circle){
            Circle circle1 = (Circle) this.getRepresentation();
            Circle circle2 = (Circle) objectB.getRepresentation();

            double distance = Math.sqrt(Math.pow(circle2.getCenterX() - circle1.getCenterX(), 2) + Math.pow(circle2.getCenterY() - circle1.getCenterY(), 2));
            return distance <= (circle1.getWidth() + circle2.getWidth())/2;
        }
        else{
            boolean isColliding = (thisBoundingBox[Boundary.MAX_Y.ordinal()] >= objectBoundingBox[Boundary.MIN_Y.ordinal()]
            && thisBoundingBox[Boundary.MIN_Y.ordinal()] <= objectBoundingBox[Boundary.MAX_Y.ordinal()]
            && thisBoundingBox[Boundary.MIN_X.ordinal()] <= objectBoundingBox[Boundary.MAX_X.ordinal()]
            && thisBoundingBox[Boundary.MAX_X.ordinal()] >= objectBoundingBox[Boundary.MIN_X.ordinal()]);

        return isColliding;
        }
    }

    /**
     * Gets the nearest vertex of the object to a given point.
     * 
     * @param point the point to find the nearest vertex to
     * @return the nearest vertex as a Vector2D object
     */
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

    /**
     * Gets the normal vector of the object based on a given vector and another object.
     * 
     * @param vect the vector to calculate the normal vector from
     * @param objectA the other object
     * @return the normal vector as a Vector2D object
     */
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

    /**
     * Updates the velocity of the object based on a given time.
     * 
     * @param deltaTime the time spent
     */
    public abstract void updateVelocity(double deltaTime);

    /**
     * Checks if the object is active.
     * 
     * @return true if the object is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the activity of the object.
     * 
     * @param active the new activity status of the object
     */
    public void setActive(boolean active){
        this.active = active;
    }

    /**
     * Resolves the collision between this object and another object.
     * 
     * @param objectB the other object involved in the collision
     */
    public abstract void resolveCollision(PhysicalObject objectB);

    /**
     * Resolves the situation where the speed of the object is too high.
     */
    public void resolveSpeedToHigh(){
        // TODO: Implement the resolution for high speed

        if (this.getSpeed().getX() > MAX_SPEED) this.setSpeed(new Vector2D(MAX_SPEED, this.getSpeed().getY()));
        if (this.getSpeed().getY() > MAX_SPEED) this.setSpeed(new Vector2D(this.getSpeed().getX(), MAX_SPEED));
    }

    /**
     * Applies gravitational field forces to all movable objects
     * 
     * @param deltaTime the time since last tick
     */
    public void applyGravitationalForces(double deltaTime, PhysicalObject planete) {
		//System.out.println(planete.isActive());
		if (!planete.isActive()) return;
        final double G = 6.67430; // gravitational constant

			Vector2D r = planete.getPosition().subtract(this.getPosition());
			
			double distance = r.magnitude();
			if (distance<200) return;

			//System.out.println("objet 1: "+this.getMass()+" "+this.getPosition());
			//System.out.println("objet 2: "+planete.getMass()+" "+planete.getPosition());

			double forceMagnitude = G * (this.getMass() * planete.getMass()) / (distance * distance);
			//System.out.println("force norme :"+forceMagnitude);
			Vector2D force = r.normalize().multiply(forceMagnitude*4);

			this.applyForce(force.multiply(deltaTime));
			//this.setSpeed(force.multiply(deltaTime));
			//System.out.println(force.multiply(deltaTime));
			//System.out.println(this.getAcceleration());
			//System.out.println(this.getSpeed());
			//System.out.println();
    }


}
