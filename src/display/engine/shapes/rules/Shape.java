package display.engine.shapes.rules;

import java.awt.Color;
import java.awt.Image;

import display.engine.rules.GraphicalObject;
import display.engine.shapes.Circle;
import display.engine.shapes.Rectangle;

public abstract class Shape extends GraphicalObject implements Collisions {
	protected Image image;

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
	 * Instantiates a new Image
	 * 
	 * @param image the image to be displayed
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 * @param color The color of the graphical object (ignored but used to call the super constructor)
	 */
    public Shape(Image image,
        int posX, int posY,
        int width, int height,
        Color color
    ) {
        super(posX, posY, width, height, color);
		Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		this.setImage(scaledImage);
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
     * takes into argument two graphical objects and calls collision detection methods based on their type
     * @param a graphical object 
     * @param b graphical object
     * @return boolean that is set to true if there is a collision, false otherwise or if there is a type missmatch
     */
	public boolean checkCollisions(Shape b){ 
		/* 
		if (this instanceof BallImage && b instanceof PaddleImage){
			BallImage ball = (BallImage) this;
			PaddleImage paddle = (PaddleImage) b;
			return Collisions.checkCollisions(ball, paddle);
		}
		*/
		if (this instanceof Circle && b instanceof Rectangle){
			Circle ball = (Circle) this;
			Rectangle rect = (Rectangle) b;
			return Collisions.checkCollisions(ball, rect);
		}
		if (this instanceof Rectangle && b instanceof Rectangle){
			Rectangle rect1 = (Rectangle) this;
			Rectangle rect2 = (Rectangle) b;
			return Collisions.checkCollisions(rect1, rect2);
		}
		return false;
	}


	/**
	 * Paints the image on the screen
	 * 
	 * @param g The graphics object to be painted
	 */
	public void paintComponent(java.awt.Graphics g){
		super.paintComponent(g);
		g.drawImage(this.getImage(), 0, 0 , null);
	}

}
