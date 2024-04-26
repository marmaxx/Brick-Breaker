package display.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import display.engine.rules.PhysicalObject;
import display.engine.utils.*;

/**
 * PhysicsEngine
 */
public class PhysicsEngine implements Serializable{
    public static final long serialVersionUID = 8L;

    public static double GRAVITY_CONSTANT=0.5;
    public static final double rebondForce = 100;
    private static final double FRICTION_COEFFICIENT = 0.5;
    private List<PhysicalObject> physicalObjects;

    /**
     * Create a new PhysicsEngine
     */
    public PhysicsEngine() {
        this.physicalObjects = new ArrayList<>();
    }

    /**
     * Set the gravity constant
     */
    public static void setGravityConstant (double scalar){
        GRAVITY_CONSTANT*=scalar;
    }

    /**
     * Get the gravity constant
     */
    public List<PhysicalObject> getPhysicalObjects(){
        return this.physicalObjects;
    }

    /**
     * Add a physical object to the physics engine
     * 
     * @param object The object to add
     */
    public void addPhysicalObject(PhysicalObject object) {
        physicalObjects.add(object);
    }

    /**
     * update the physics engine
     * 
     * @param deltaTime
     */
    public void update(double deltaTime) {
        applyGravity(deltaTime);
        handleCollisions(deltaTime);
        applyFriction(FRICTION_COEFFICIENT);
       
            // updating objects position relatively to the time spent
         for (PhysicalObject object : physicalObjects) {
            //if (object instanceof Ball) System.out.println(object.getAcceleration());
            object.updateVelocity(deltaTime); 
            if (object.isActive() && object.isMovable()){
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

    

    /**
     * Handle collisions between objects
     * 
     * @param deltaTime
     */
    private void handleCollisions(double deltaTime) {
        for (int i = 0; i < physicalObjects.size(); i++) {
            PhysicalObject objectA = physicalObjects.get(i);
            for (int j = i+1; j < physicalObjects.size(); j++) {
                PhysicalObject objectB = physicalObjects.get(j);
                if (objectA.isGoingToCollide(objectB, deltaTime) && objectA!=objectB && objectA.isActive() &&objectB.isActive()) {
                   //System.out.println("COLLISION");
                    //System.out.println(objectB.getPosition());
                    //if (objectA.getObject() instanceof Wall) System.out.println(objectA.getRepresentation().getWidth()+" ; "+objectA.getPosition());
                    // resolving collision between A and B+-
                    //System.out.println("A= "+objectA.getMass());
                    //System.out.println("B= "+objectB.getMass());
                    objectA.resolveCollision(objectB);
                    objectB.resolveCollision(objectA);

                    objectA.collided(objectB);
                    objectB.collided(objectA);
                }
            }
        }
    }


    /**
     * Apply gravity to all objects in a certain amount of time
     * 
     * @param deltaTime
     */
    private void applyGravity(double deltaTime) {
        
        for (PhysicalObject object : physicalObjects) {
            // applying acceleration due to gravity
            if(object.isMovable() &&object.isActive()) object.applyForce(new Vector2D(0, GRAVITY_CONSTANT * object.getMass()));
        }
    }

    /**
     * Apply friction to all objects depending on the friction coefficient
     * 
     * @param frictionCoefficient
     */
    private void applyFriction(double frictionCoefficient) {
        
        for (PhysicalObject object : physicalObjects) {
            if(object.isMovable() &&object.isActive()){
                Vector2D frictionForce = object.getSpeed().multiply(-1).normalize().multiply(frictionCoefficient);
                object.applyForce(frictionForce);
            }   
        }
    }


}
