package display.engine.shapes.rules;

import java.awt.Color;
import java.awt.Image;

import display.engine.rules.GraphicalObject;

public abstract class Shape extends GraphicalObject {
	public static final long serialVersionUID = 3L;
	transient protected Image image;

	/**
	 * Instantiates a new Shape
	 * 
	 * @param color the color of the graphical object
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 */
    public Shape(Color color,
        int posX, int posY,
        int width, int height
    ) {
        super(color, posX, posY, width, height);
    }

	/**
	 * Instantiates a new Image
	 * 
	 * @param image the image to be displayed
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 */
    public Shape(Image image,
        int posX, int posY,
        int width, int height
    ) {
		super(image, posX, posY, width, height);
    }

	/**
	 * Gets the image to be displayed
	 * 
	 * @return The image to be displayed
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * Sets the image to be displayed
	 * 
	 * @param image The image to be displayed
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Paints the image on the screen
	 * 
	 * @param g The graphics object to be painted
	 */
	public void paintComponent(java.awt.Graphics g){
		super.paintComponent(g);
	}

}
