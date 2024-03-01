package physics;

import java.util.ArrayList;
import java.util.List;

import physics.utils.*;
//TODO: java docs
public class PhysicsEngine<T> {
    private static final double GRAVITY_CONSTANT=9.81; 
    private static final double FRICTION_COEFFICIENT = 0.5;
    private List<PhysicalObject<T>> physicalObjects;

    public PhysicsEngine() {
        this.physicalObjects = new ArrayList<>();
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
        applyFriction(FRICTION_COEFFICIENT);
       
            // updating objects position relatively to the time spent
         for (PhysicalObject<T> object : physicalObjects) {
            object.updateVelocity(deltaTime); System.out.println(object.getSpeed());
            object.updatePosition(deltaTime);
        }
    }

    // detecting and resolving collisions between objects
    private void handleCollisions() {
        for (int i = 0; i < physicalObjects.size(); i++) {
            PhysicalObject<T> objectA = physicalObjects.get(i);
            for (int j = i + 1; j < physicalObjects.size(); j++) {
                PhysicalObject<T> objectB = physicalObjects.get(j);
                if (objectA.collidesWith(objectB)) {
                    // resolving collision between A and B
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
            if (object.isMovable()){
                Vector2D frictionForce = object.getSpeed().multiply(-1).normalize().multiply(frictionCoefficient);
                object.applyForce(frictionForce);
            }
        }
    }


}
