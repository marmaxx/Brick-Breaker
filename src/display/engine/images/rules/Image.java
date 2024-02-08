package display.engine.images.rules;

import java.awt.Color;

import display.engine.rules.GraphicalObject;

public abstract class Image extends GraphicalObject {
	protected java.awt.Image image;

	/**
	 * Instantiates a new Image
	 * 
	 * @param image the image to be displayed
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 * @param color The color of the graphical object (ignored but used to call the super constructor)
	 */
    public Image(java.awt.Image image,
        int posX, int posY,
        int width, int height,
        Color color
    ) {
        super(posX, posY, width, height, color);
		this.setImage(image);
    }

	/**
	 * Gets the image to be displayed
	 * 
	 * @return The image to be displayed
	 */
	public java.awt.Image getImage() {
		return this.image;
	}

	/**
	 * Sets the image to be displayed
	 * 
	 * @param image The image to be displayed
	 */
	public void setImage(java.awt.Image image) {
		this.image = image;
	}

	/**
	 * Paints the image on the screen
	 * 
	 * @param g The graphics object to be painted
	 */
	public void paintComponent(java.awt.Graphics g){
		super.paintComponent(g);
		g.drawImage(this.getImage(), 0, 0, null);
	}

}
