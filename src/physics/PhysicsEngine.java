package physics;

import java.util.ArrayList;
import java.util.List;

import game.breakout.entities.Ball;
import game.breakout.entities.Wall;
import physics.utils.*;
//TODO: java docs
public class PhysicsEngine<T> {
    public static double GRAVITY_CONSTANT=1; 
    private static final double FRICTION_COEFFICIENT = 0.5;
    private List<PhysicalObject<T>> physicalObjects;

    public PhysicsEngine() {
        this.physicalObjects = new ArrayList<>();
    }

    public static void setGravityConstant (double scalar){
        GRAVITY_CONSTANT*=scalar;
    }

    public List<PhysicalObject<T>> getPhysicalObjects(){
        return this.physicalObjects;
    }

    // adding a physical object 
    public void addPhysicalObject(PhysicalObject<T> object) {
        physicalObjects.add(object);
    }

    // updating the objects state relatively to the time spent
    public void update(double deltaTime) {
    
        applyGravity(deltaTime);
        handleCollisions();
        //applyFriction(FRICTION_COEFFICIENT);
       
            // updating objects position relatively to the time spent
         for (PhysicalObject<T> object : physicalObjects) {
            object.updateVelocity(deltaTime); 
            if (object.getObject() instanceof Ball){
                //System.out.println("vitesse: "+object.getSpeed());
                //System.out.println("acceleration: "+object.getAcceleration());
                //System.out.println("DeltaTime: "+deltaTime);
                //System.out.println(object.getPosition());
                object.updatePosition(deltaTime);
                object.getRepresentation().setPosX((int)object.getPosition().getX());
                object.getRepresentation().setPosY((int)object.getPosition().getY());
            }
            
            
        }
    }

    // detecting and resolving collisions between objects
    private void handleCollisions() {
        for (int i = 0; i < physicalObjects.size(); i++) {
            PhysicalObject<T> objectA = physicalObjects.get(i);
            for (int j = i+1; j < physicalObjects.size(); j++) {
                PhysicalObject<T> objectB = physicalObjects.get(j);
                if (objectA.isGoingToCollide(objectB) && objectA!=objectB && !(objectA.getObject() instanceof Wall) && !(objectB.getObject() instanceof Wall)) {
                    
                    //System.out.println("COLLISION");
                    //System.out.println(objectB.getPosition());
                    //if (objectA.getObject() instanceof Wall) System.out.println(objectA.getRepresentation().getWidth()+" ; "+objectA.getPosition());
                    // resolving collision between A and B+-
                    //System.out.println("A= "+objectA.getMass());
                    //System.out.println("B= "+objectB.getMass());
                    objectA.resolveCollision(objectB);
                    objectB.resolveCollision(objectA);
                }
            }
        }
    }

    // applying gravity to all objects
    private void applyGravity(double deltaTime) {
        
        for (PhysicalObject<T> object : physicalObjects) {
            // applying acceleration due to gravity
            object.applyForce(new Vector2D(0, GRAVITY_CONSTANT * object.getMass()));
        }
    }

    // applying friction to an object
    private void applyFriction(double frictionCoefficient) {
        
        for (PhysicalObject<T> object : physicalObjects) {
                Vector2D frictionForce = object.getSpeed().multiply(-1).normalize().multiply(frictionCoefficient);
                object.applyForce(frictionForce);
        }
    }


}
