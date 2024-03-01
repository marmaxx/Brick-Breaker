package physics;

import display.engine.rules.GraphicalObject;
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

    // updating speed thanks to the acceleration and time spent
    public void updateVelocity(double deltaTime) {
        this.speed = this.speed.add(acceleration.multiply(deltaTime));
    }

    // applying force to an object; its acceleration is modified
    public void applyForce(Vector2D force) {
        this.acceleration = this.acceleration.add(force.multiply(1.0 / mass));
    }

    // checking if a collision happened
    public boolean collidesWith(PhysicalObject<T> objectB) {
        return this.representation.isColliding(objectB.representation);
    }


    // resolving collisions
    public void resolveCollision(PhysicalObject<T> objectA) {
        //TODO: penser à l'élasticité : regarder formules physiques
        double massA = objectA.getMass();
        if (isMovable()){
            if (objectA.isMovable()){
                Vector2D x = objectA.getSpeed().multiply(2*massA/(massA+this.mass));
                Vector2D y = this.speed.multiply((this.mass-massA)/massA+this.mass);
                this.speed=x.add(y);
                Vector2D z = this.speed.multiply(2*this.mass/(massA+this.mass));
                Vector2D t = objectA.getSpeed().multiply((massA-this.mass)/massA+this.mass);
                objectA.setSpeed(z.add(t));
            }
            else{
                this.speed.multiply(-1);
            }
        }
    }

    
}
