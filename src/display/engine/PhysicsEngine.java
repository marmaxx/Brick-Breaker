package display.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import display.engine.rules.PhysicalObject;
import display.engine.utils.*;

/**
 * The PhysicsEngine class represents a physics engine that simulates physical interactions between objects.
 */
public class PhysicsEngine implements Serializable{
    public static final long serialVersionUID = 8L;

    public static double GRAVITY_CONSTANT=0.5;
    public static double rebondForce = 100;
    public static double FRICTION_COEFFICIENT = 0.5;
    private List<PhysicalObject> physicalObjects;

    /**
     * Creates a new PhysicsEngine object.
     */
    public PhysicsEngine() {
        this.physicalObjects = new ArrayList<>();
    }

    /**
     * Sets the gravity constant.
     * 
     * @param scalar The scalar value to multiply the gravity constant by.
     */
    public static void setGravityConstant (double scalar){
        GRAVITY_CONSTANT*=scalar;
    }

    /**
     * Returns the list of physical objects in the physics engine.
     * 
     * @return The list of physical objects.
     */
    public List<PhysicalObject> getPhysicalObjects(){
        return this.physicalObjects;
    }

    /**
     * Adds a physical object to the physics engine.
     * 
     * @param object The object to add.
     */
    public void addPhysicalObject(PhysicalObject object) {
        physicalObjects.add(object);
    }

    /**
     * Updates the physics engine by applying various forces and handling collisions.
     * 
     * @param deltaTime The time elapsed since the last update.
     */
    public void update(double deltaTime) {
        applyGravity(deltaTime);
        applyGravitationalForces(deltaTime);
        handleCollisions(deltaTime);
        applyFriction(FRICTION_COEFFICIENT);
        
        // updating objects position relatively to the time spent
        for (PhysicalObject object : physicalObjects) {
            object.updateVelocity(deltaTime); 
            if (object.isActive() && object.isMovable()){
                object.updatePosition(deltaTime);
                object.getRepresentation().setPosX((int)object.getPosition().getX());
                object.getRepresentation().setPosY((int)object.getPosition().getY());
            }
        }
    }

    /**
     * Handles collisions between objects.
     * 
     * @param deltaTime The time elapsed since the last collision check.
     */
    private void handleCollisions(double deltaTime) {
        for (int i = 0; i < physicalObjects.size(); i++) {
            PhysicalObject objectA = physicalObjects.get(i);
            for (int j = i+1; j < physicalObjects.size(); j++) {
                PhysicalObject objectB = physicalObjects.get(j);

                if (objectA.isGoingToCollide(objectB, deltaTime) && objectA!=objectB && objectA.isActive() && objectB.isActive()) {
                    objectA.resolveCollision(objectB);
                    objectB.resolveCollision(objectA);

                    objectA.collided(objectB);
                    objectB.collided(objectA);
                }
            }
        }
    }

    /**
     * Applies gravity to all objects in a certain amount of time.
     * 
     * @param deltaTime The time elapsed since the last gravity application.
     */
    private void applyGravity(double deltaTime) {
        for (PhysicalObject object : physicalObjects) {
            // applying acceleration due to gravity
            if(object.isMovable() && object.isActive()) object.applyForce(new Vector2D(0, GRAVITY_CONSTANT * object.getMass()));
        }
    }

    /**
     * Applies friction to all objects depending on the friction coefficient.
     * 
     * @param frictionCoefficient The coefficient of friction to apply.
     */
    private void applyFriction(double frictionCoefficient) {
        for (PhysicalObject object : physicalObjects) {
            if(object.isMovable() && object.isActive()){
                Vector2D frictionForce = object.getSpeed().multiply(-1).normalize().multiply(frictionCoefficient);
                object.applyForce(frictionForce);
            }   
        }
    }

    /**
     * Applies gravitational field forces to all movable objects.
     * 
     * @param deltaTime The time elapsed since the last gravitational force application.
     */
    public void applyGravitationalForces(double deltaTime) {
        for (int i = 0; i < physicalObjects.size(); i++){
            PhysicalObject objectA = physicalObjects.get(i);
            for (int j = i+1; j < physicalObjects.size(); j++){
                PhysicalObject objectB = physicalObjects.get(j);
                if(objectA.isActive() && objectB.isActive()){
                    if (objectB.isMovable() && objectA.isAPlanet()) objectB.applyGravitationalForces(deltaTime, objectA);
                    else if (objectA.isMovable() && objectB.isAPlanet()) objectA.applyGravitationalForces(deltaTime, objectB);
                }
            }
        }
    }
}
