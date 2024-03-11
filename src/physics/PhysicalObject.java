package physics;

import display.engine.rules.GraphicalObject;
import game.breakout.entities.Ball;
import game.breakout.entities.Brick;
import game.breakout.entities.Player;
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
    private double rotationCoeff; //coeff of rotation after the paddle hit the ball with speed ; has an impact on the next collision
    //TODO: gérer la rotation 

    private Vector2D normalVectorVT = new Vector2D(0, 1);
    private Vector2D normalVectorHR = new Vector2D(-1, 0);

    public PhysicalObject(T obj, double mass, Vector2D position, boolean movable, GraphicalObject representation){
        this.object=obj;
        this.mass=mass;
        this.position=position;
        this.movable=movable;
        this.representation=representation;

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
            this.speed =((((Player)this.object).getLastPos().add(this.position.multiply(-1))).multiply(1/deltaTime));
            System.out.println(this.speed);
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
        else{
            int [] thisNextPos = ((Entity) this.object).getNextPos(Ball.MOVE_SPEED);
            int [] BNextPos = ((Entity) objectB.object).getCurrPos(0);
            return this.representation.isGoingToCollide(objectB.getRepresentation(), thisNextPos, BNextPos);
        }
    }

    public Vector2D getImpactPoint(PhysicalObject<T> objectA){
        if (objectA.getObject() instanceof Player || objectA.getObject() instanceof Brick){
            Vector2D topRightPositionA = new Vector2D(objectA.getPosition().getX() + objectA.getRepresentation().getWidth(), objectA.getPosition().getY());
            Vector2D bottomLeftPositionA = new Vector2D(objectA.getRepresentation().getX(), objectA.getRepresentation().getY() + objectA.getRepresentation().getHeight());
            if (this.getPosition().getX()+this.getRepresentation().getWidth()/2 >= objectA.getPosition().getX() && this.getPosition().getX()+this.getRepresentation().getWidth()/2 <= topRightPositionA.getX()){
                if (this.getPosition().getY()+this.getRepresentation().getWidth()/2 < objectA.getPosition().getY()){
                    // la balle est au dessus du rectangle
                    return new Vector2D(this.getPosition().getX()+this.getRepresentation().getWidth()/2, this.getPosition().getY()+this.getRepresentation().getWidth()/2 + this.getRepresentation().getWidth()/2);
                } 
                else{
                    // la balle est en dessous
                    return new Vector2D(this.getPosition().getX()+this.getRepresentation().getWidth()/2, this.getPosition().getY()+this.getRepresentation().getWidth()/2 - this.getRepresentation().getWidth()/2);
                }
            }
            else if (this.getPosition().getY()+this.getRepresentation().getWidth()/2 > objectA.getPosition().getY() && this.getPosition().getY()+this.getRepresentation().getWidth()/2 < bottomLeftPositionA.getY()){
                if (this.getPosition().getX()+this.getRepresentation().getWidth()/2 < objectA.getPosition().getX()){
                    // la balle est a droite du rectangle
                    return new Vector2D(this.getPosition().getX()+this.getRepresentation().getWidth()/2 + this.getRepresentation().getWidth()/2, this.getPosition().getY()+this.getRepresentation().getWidth()/2);
                }
                else{
                    // la balle est a gauche
                    return new Vector2D(this.getPosition().getX()+this.getRepresentation().getWidth()/2 - this.getRepresentation().getWidth()/2, this.getPosition().getY()+this.getRepresentation().getWidth()/2);
                }
            }
        }
        else{
            // les deux sont des balles 
            double distanceX = Math.abs(this.getPosition().getX()+this.getRepresentation().getWidth()/2 - objectA.getPosition().getX());
            double distanceY = Math.abs(this.getPosition().getY()+this.getRepresentation().getWidth()/2 - objectA.getPosition().getY());
            Vector2D vectorImpact = new Vector2D(distanceX/2, distanceY/2);
            return vectorImpact.add(objectA.getPosition());
        }
        return null;
    }

    public Vector2D getNormalVector(Vector2D vect){
        if (normalVectorHR.dotProduct(vect) == 0){
            return normalVectorHR;
        }
        return normalVectorVT;
    }


    // resolving collisions
    public void resolveCollision(PhysicalObject<T> objectA) {
        //TODO: penser à l'élasticité : regarder formules physiques
        if (isMovable()){
            if (objectA.isMovable()){
                // TODO resolve collision when the two objects are moveable
            }
            else{
                Vector2D pointA = this.getImpactPoint(objectA);
                Vector2D pointB;
                if (pointA.getX() >= objectA.getPosition().getX() && pointA.getX() <= objectA.getPosition().getX()+objectA.getRepresentation().getWidth()){
                    pointB = this.getImpactPoint(objectA).add(new Vector2D(1, 0));
                }
                else{
                    pointB = this.getImpactPoint(objectA).add(new Vector2D(0, 1));
                }
                Vector2D ab = pointB.subtract(pointA);
                Vector2D normal = getNormalVector(ab);
                double angle = this.getSpeed().angleBetween(normal);
                System.out.println(Math.toDegrees(angle));
                if (angle == 0){
                    this.setSpeed(new Vector2D(this.speed.getX(), -this.speed.getY()));
                }
                else if (Math.toDegrees(angle) > 0 && Math.toDegrees(angle) < 90){
                    this.setSpeed(new Vector2D(this.speed.getX(), -this.speed.getY()));
                }
                else if (Math.toDegrees(angle) > 90 && Math.toDegrees(angle) < 180){
                    this.setSpeed(new Vector2D(-this.speed.getX(), this.speed.getY()));
                }
                //this.applyForce(new Vector2D(0, 10000));
            }
        }
    }
}
