package physics;

import display.engine.rules.GraphicalObject;
import game.breakout.entities.Brick;
import game.breakout.entities.Player;
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
        this.speed = this.speed.add(acceleration.multiply(deltaTime/1000000));
    }

    // applying force to an object; its acceleration is modified
    public void applyForce(Vector2D force) {
        if(isMovable())
            this.acceleration = this.acceleration.add(force.multiply(1.0 / mass));
    }

    // checking if a collision happened
    public boolean collidesWith(PhysicalObject<T> objectB) {
        return this.representation.isColliding(objectB.representation);
    }

    public boolean isGoingToCollide(PhysicalObject<T> objectB){
        int [] thisNextPos = new int [2];
        int [] BNextPos = new int [2];
        thisNextPos[0]=(int)this.getPosition().getX();
        thisNextPos[1]=(int)this.getPosition().getY();
        BNextPos[0]=(int)objectB.getPosition().getX();
        BNextPos[1]=(int)objectB.getPosition().getY();
        return this.representation.isGoingToCollide(objectB.getRepresentation(), thisNextPos, BNextPos);
    }

    public Vector2D getImpactPoint(PhysicalObject<T> objectA){
        if (objectA.getObject() instanceof Player || objectA.getObject() instanceof Brick){
            System.out.println("balle posx: "+this.getPosition().getX()+this.getRepresentation().getWidth()/2);
            System.out.println("balle posy: "+this.getPosition().getY()+this.getRepresentation().getWidth()/2);
            System.err.println("paddle posx :"+objectA.getPosition().getX());
            System.err.println("paddle posy :"+objectA.getPosition().getY());
            Vector2D topRightPositionA = new Vector2D(objectA.getPosition().getX() + objectA.getRepresentation().getWidth(), objectA.getPosition().getY());
            Vector2D bottomLeftPositionA = new Vector2D(objectA.getPosition().getX(), objectA.getPosition().getY() + objectA.getRepresentation().getHeight());
            // Vector2D bottomRightPositionA = new Vector2D(objectA.getPosition().getX() + objectA.getRepresentation().getWidth(), objectA.getPosition().getY() + objectA.getRepresentation().getHeight());
            if (this.getPosition().getX()+this.getRepresentation().getWidth()/2 > objectA.getPosition().getX() && this.getPosition().getX()+this.getRepresentation().getWidth()/2 <= topRightPositionA.getX()){
                if (this.getPosition().getY()+this.getRepresentation().getWidth()/2 < objectA.getPosition().getY()){
                    // la balle est au dessus au dessus du rectangle
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


    // resolving collisions
    public void resolveCollision(PhysicalObject<T> objectA) {
        //TODO: penser à l'élasticité : regarder formules physiques
        double massA = objectA.getMass();
        if (isMovable()){
            System.out.println(this.getImpactPoint(objectA));
            if (objectA.isMovable()){
            Vector2D x = objectA.getSpeed().multiply(2*massA/(massA+this.mass));
            Vector2D y = this.speed.multiply((this.mass-massA)/massA+this.mass);
            this.speed=x.add(y);
            Vector2D z = this.speed.multiply(2*this.mass/(massA+this.mass));
            Vector2D t = objectA.getSpeed().multiply((massA-this.mass)/massA+this.mass);
            objectA.setSpeed(z.add(t));
            }
            else{
                //System.out.println(this.acceleration);
                this.acceleration=this.acceleration.multiply(-1); 
                //System.out.println(this.acceleration); 
                //System.out.println(this.speed);
                //System.out.println(this.position);
            }
        }
    }

    
}
