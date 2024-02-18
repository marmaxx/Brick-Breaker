package display.engine.shapes.rules;

import java.awt.Color;

import display.engine.images.BallImage;
import display.engine.rules.GraphicalObject;
import display.engine.shapes.Circle;
import display.engine.shapes.Rectangle;

public abstract class Shape extends GraphicalObject  implements Collisions{

	/**
	 * Instantiates a new Shape
	 * 
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 * @param color the color of the graphical object (ignored if the graphical object is represented by an image)
	 */
    public Shape(
        int posX, int posY,
        int width, int height,
        Color color
    ) {
        super(posX, posY, width, height, color);
    }

	/**
     * takes into argument two graphical objects and calls collision detection methods based on their type
     * @param a graphical object 
     * @param b graphical object
     * @return boolean that is set to true if there is a collision, false otherwise or if there is a type missmatch
     */
    public boolean checkCollisions(Shape b){ 
		if (this instanceof BallImage ball && b instanceof Rectangle rect){
            return Collisions.checkCollisions(ball, rect);
        }
        if (this instanceof Circle ball && b instanceof Rectangle rect){
            return Collisions.checkCollisions(ball, rect);
        }
        if (this instanceof Rectangle rect1&& b instanceof Rectangle rect2){
            return Collisions.checkCollisions(rect1, rect2);
        }
        return false;
    }


}
