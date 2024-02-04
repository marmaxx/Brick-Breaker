package display.engine.shapes.rules;

import java.awt.Color;

import display.engine.rules.GraphicalObject;

public abstract class Shape extends GraphicalObject {

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

}
