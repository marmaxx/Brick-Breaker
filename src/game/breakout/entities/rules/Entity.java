package game.breakout.entities.rules;

import display.engine.rules.GraphicalObject;

public abstract class Entity {
    protected GraphicalObject representation;
    
	/**
	 * Instantiates a new Entity
	 * 
	 * @param representation the graphical representation of the entity
	 */
    public Entity(GraphicalObject representation) {
		this.setRepresentation(representation);       
    }

	/**
	 * Gets the graphical representation of the entity
	 * 
	 * @return the graphical representation of the entity
	 */
    public GraphicalObject getRepresentation() {
        return this.representation;
    }

	/**
	 * Sets the graphical representation of the entity
	 * 
	 * @param representation the new graphical representation of the entity
	 */
    public void setRepresentation(GraphicalObject representation) {
        this.representation = representation;
    }
}
