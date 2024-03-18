package physics;

import java.util.Vector;

import display.engine.rules.GraphicalObject;
import display.engine.rules.GraphicalObject.Boundary;
import game.breakout.entities.Ball;
import game.breakout.entities.Brick;
import game.breakout.entities.Player;
import game.breakout.entities.Wall;
import game.breakout.entities.rules.Entity;
import physics.utils.*;

public class PhysicalObject<T> {
    private T object;
    private double mass;
    private Vector2D position ;
    private Vector2D speed = new Vector2D(0, 0);
    private Vector2D acceleration = new Vector2D(0, 0);
    private boolean movable; //indicates if the object can move, if its position is influenced by collisions or frictions 
    //TODO: regarder si movable est vraiment utile, pareil pour elasticity
    private GraphicalObject representation;
    private double elasticity;
    private double rotationCoeff=0.8; //coeff of rotation after the paddle hit the ball with speed ; has an impact on the next collision
    //TODO: gérer la rotation 
    private boolean active=true;

    private Vector2D normalVectorVT = new Vector2D(0, 1);
    private Vector2D normalVectorVB = new Vector2D(0, -1);
    private Vector2D normalVectorHR = new Vector2D(1, 0);
    private Vector2D normalVectorHL = new Vector2D(-1, 0);
    private Slop slop;
    private Vector2D topRightPosition;
    private Vector2D bottomLeftPosition;
    private Vector2D bottomRightPosition;

    public PhysicalObject(T obj, double mass, Vector2D position, boolean movable, GraphicalObject representation){
        this.object=obj;
        this.mass=mass;
        this.position=position;
        this.movable=movable;
        this.representation=representation;

    }

    public enum Slop {
        VERTICAL, HORIZONTAL, OTHER
    }

    public Slop getSlop(){
        return this.slop;
    }

    public void setSlop (Slop otherSlop){
        this.slop=otherSlop;
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

    public boolean isActive(){
        return active;
    }
    /**
	 * deactivates the phyiscal object from the view
	 */
	public void destroy() {
        active=false;
        this.getRepresentation().destroy();
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

    public void setAcceleration(Vector2D newAcceleration){
        this.acceleration=newAcceleration;
    }

    public T getObject(){
        return this.object;
    }

    public boolean isMovable(){
        return this.movable;
    }
    public GraphicalObject getRepresentation(){
        return this.representation;
    }

    public Vector2D getPosition(){
        return this.position;
    }

    public void setPosition(Vector2D newPos){
        this.position=newPos;
    }

    public void setSpeed(Vector2D newSpeed){
        this.speed=newSpeed;
    }


    // updating the position thanks to the speed and time spent
    public void updatePosition(double deltaTime) {
        Vector2D displacement = speed.multiply(deltaTime);
        position = position.add(displacement);
    }

    // updating speed thanks to the acceleration and time spent
    public void updateVelocity(double deltaTime) {
        if (!(this.object instanceof Player)) this.speed = this.speed.add(acceleration.multiply(deltaTime/1000000));
        else{
            this.speed =((this.position.add(((Player)this.object).getLastPos().multiply(-1))).multiply(1/deltaTime));
            //System.out.println(this.speed);
        }

    }

    // applying force to an object; its acceleration is modified
    public void applyForce(Vector2D force) {
        if(isMovable()) this.acceleration = this.acceleration.add(force.multiply(1.0 / mass));
    }

    // checking if a collision happened
    public boolean collidesWith(PhysicalObject<T> objectB) {
        return this.representation.isColliding(objectB.representation);
    }

    public boolean isGoingToCollide(PhysicalObject<T> objectB){
        if (this.getObject() instanceof Ball && objectB.getObject() instanceof Player){
            int [] thisNextPos = ((Entity) this.object).getNextPos(Ball.MOVE_SPEED);
            int [] BNextPos = ((Entity) objectB.object).getNextPos(Player.MOVE_SPEED);
            return this.representation.isGoingToCollide(objectB.getRepresentation(), thisNextPos, BNextPos);
        }
        else if (this.getObject() instanceof Ball && objectB.getObject() instanceof Ball){
            int [] thisNextPos = ((Entity) this.object).getNextPos(Ball.MOVE_SPEED);
            int [] BNextPos = ((Entity) objectB.object).getNextPos(Player.MOVE_SPEED);
            return this.representation.isGoingToCollide(objectB.getRepresentation(), thisNextPos, BNextPos);
        }
        else if(this.getObject() instanceof Ball){
            int [] thisNextPos = ((Entity) this.object).getNextPos(Ball.MOVE_SPEED);
            int [] BNextPos = ((Entity) objectB.object).getCurrPos(0);
            return this.representation.isGoingToCollide(objectB.getRepresentation(), thisNextPos, BNextPos);
        }
        else{
            int [] BNextPos = ((Entity) this.object).getNextPos(Ball.MOVE_SPEED);
            int [] thisNextPos = ((Entity) objectB.object).getCurrPos(0);
            return this.representation.isGoingToCollide(objectB.getRepresentation(), thisNextPos, BNextPos);
        }
    }

    public Vector2D getImpactPoint(PhysicalObject<T> objectA){
        
        if (!(objectA.getObject() instanceof Ball)){
            System.out.println(objectA.getObject());
           Vector2D topRightPositionA = new Vector2D(objectA.getRepresentation().getBoundaries()[Boundary.MAX_X.ordinal()],objectA.getRepresentation().getBoundaries()[Boundary.MAX_Y.ordinal()]);
           Vector2D topLeftPositionA = new Vector2D(objectA.getRepresentation().getBoundaries()[Boundary.MIN_X.ordinal()],objectA.getRepresentation().getBoundaries()[Boundary.MAX_Y.ordinal()]);
           Vector2D bottomLeftPositionA = new Vector2D(objectA.getRepresentation().getBoundaries()[Boundary.MIN_X.ordinal()],objectA.getRepresentation().getBoundaries()[Boundary.MIN_Y.ordinal()]);
           Vector2D bottomRightPositionA = new Vector2D(objectA.getRepresentation().getBoundaries()[Boundary.MAX_X.ordinal()],objectA.getRepresentation().getBoundaries()[Boundary.MIN_Y.ordinal()]);

           
           Vector2D centerToTopRightCornerVectorA = objectA.getRepresentation().vectorCenterToCoordinates(topRightPositionA.getX(), topRightPositionA.getY()); //the vector from the paddle's center to its top left corner
           Vector2D centerToTopLeftCornerVectorA = objectA.getRepresentation().vectorCenterToCoordinates(topLeftPositionA.getX(), topLeftPositionA.getY());
           Vector2D centerToBottomLeftCornerVectorA = objectA.getRepresentation().vectorCenterToCoordinates(bottomLeftPositionA.getX(), bottomLeftPositionA.getY());
           Vector2D centerToBottomRightCornerVectorA = objectA.getRepresentation().vectorCenterToCoordinates(bottomRightPositionA.getX(), bottomRightPositionA.getY());

           Vector2D objectAToBallVector = objectA.getRepresentation().vectorFromCenterToCenter(this.getRepresentation());
            
            if (centerToTopLeftCornerVectorA.angleFromTo(objectAToBallVector)<0 && centerToTopRightCornerVectorA.angleFromTo(objectAToBallVector)>0){
                // la balle est au dessus 
                return new Vector2D(this.getPosition().getX()+this.getRepresentation().getWidth()/2, objectA.getPosition().getY());
            } 
            else if(centerToBottomRightCornerVectorA.angleFromTo(objectAToBallVector)>0 && centerToTopRightCornerVectorA.angleFromTo(objectAToBallVector)<0){
                    // la balle est a droite
                    return new Vector2D(objectA.getPosition().getX(), this.getPosition().getY()+this.getRepresentation().getWidth()/2);
            }
            else if(centerToBottomLeftCornerVectorA.angleFromTo(objectAToBallVector)<0 && centerToTopLeftCornerVectorA.angleFromTo(objectAToBallVector)>0){
                // la balle est a gauche 
                return new Vector2D(objectA.getPosition().getX() + objectA.getRepresentation().getWidth(), this.getPosition().getY()+this.getRepresentation().getWidth()/2);
            }
            else if(centerToBottomLeftCornerVectorA.angleFromTo(objectAToBallVector)>0 && centerToBottomRightCornerVectorA.angleFromTo(objectAToBallVector)<0){
                // la balle est en dessous
                return new Vector2D(this.getPosition().getX() + this.getRepresentation().getWidth()/2, objectA.getPosition().getY() + objectA.getRepresentation().getHeight());
            }
            else{
                Vector2D nearestVertex = objectA.getNearestVertex(this.getPosition());
                return nearestVertex;
            }
        }
        else{
            double distanceX = Math.abs(this.getPosition().getX()+this.getRepresentation().getWidth()/2 - objectA.getPosition().getX());
            double distanceY = Math.abs(this.getPosition().getY()+this.getRepresentation().getWidth()/2 - objectA.getPosition().getY());
            Vector2D vectorImpact = new Vector2D(distanceX/2, distanceY/2);
            return vectorImpact.add(objectA.getPosition());          
        }
       
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

    public Vector2D getNormalVector(Vector2D vect, PhysicalObject<T> objectA){
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


    // resolving collisions
    public void resolveCollision(PhysicalObject<T> objectA) {
        //TODO: penser à l'élasticité : regarder formules physiques
        if (isMovable()){
            if (objectA.isMovable()){
                // TODO resolve collision when the two objects are moveable
            }
            else{
                
                //System.out.println("OBJECT: "+objectA.getObject().toString());
                //System.out.println("pos paddle: "+objectA.getPosition().toString());
                //System.out.println("width paddle: "+objectA.getRepresentation().getWidth());
                //System.out.println("height paddle: "+objectA.getRepresentation().getHeight());
                Vector2D impact = this.getImpactPoint(objectA);
                if (impact == objectA.getTopLeftPosition() || impact == objectA.getTopRightPosition() || impact == objectA.getBottomLeftPosition() || impact == objectA.getBottomRightPosition()){
                    this.setSpeed(new Vector2D(- this.getSpeed().getX(), - this.getSpeed().getY()));
                }
                else{
                    //System.out.println("point impact: "+impact.toString());
                    Vector2D nearestVertex = objectA.getNearestVertex(impact);
                    //System.out.println("nearest vertex: "+nearestVertex.toString());
                    Vector2D surfaceVector = new Vector2D(impact.getX() - nearestVertex.getX(), impact.getY() - nearestVertex.getY());
                    //System.out.println("vecteur surface: "+surfaceVector.toString());
                    double slopeDot = surfaceVector.dotProduct(normalVectorHR);
                    //System.out.println("slope dot: "+slopeDot);
                    //System.out.println("slope angle: "+slopeAngle);
                    if (slopeDot == 0) objectA.setSlop(Slop.VERTICAL);
                    else if (slopeDot == surfaceVector.getX()) objectA.setSlop(Slop.HORIZONTAL);
                    else objectA.setSlop(Slop.OTHER);
                    double incidenceAngle = Math.atan2(this.getSpeed().getY(),this.getSpeed().getX());
                    //System.out.println("angle incidence: "+Math.toDegrees(incidenceAngle));
                    //double objectAVelocityAngle = Math.atan2(objectA.getSpeed().getY(), objectA.getSpeed().getX());
                    //double angleDifference = incidenceAngle - objectAVelocityAngle;
                    double objectASpeed = objectA.getSpeed().magnitude();
                    double rotationAngle = Math.atan2(objectASpeed, this.getSpeed().magnitude());
                    //System.out.println("angle paddle: "+Math.toDegrees(objectAVelocityAngle));
                    double reflexionAngle;
                    switch(objectA.getSlop()){
                        case VERTICAL: reflexionAngle = Math.PI - incidenceAngle + rotationAngle; break;
                        case HORIZONTAL: reflexionAngle = - incidenceAngle + rotationAngle; break;
                        case OTHER: reflexionAngle = 0; break; // TODO handle a slope that is not vertical or horizontal
                        default: reflexionAngle = 0; break;
                    }        
                    //System.out.println("angle reflexion: "+Math.toDegrees(reflexionAngle));
                    //System.out.println("***********************************");
                    this.setSpeed(new Vector2D(this.getSpeed().magnitude() * Math.cos(reflexionAngle), this.getSpeed().magnitude()* Math.sin(reflexionAngle)));
                    if (this.object instanceof Ball && objectA.getObject() instanceof Player){
                        //this.speed = this.speed.add(objectA.getSpeed().multiply(rotationCoeff));
                        //System.out.println(objectA.getSpeed());
                        this.applyForce(new Vector2D(0, - 100 *PhysicsEngine.GRAVITY_CONSTANT * this.getMass()));
                    }
                }
                
            }
        }
    }
}
