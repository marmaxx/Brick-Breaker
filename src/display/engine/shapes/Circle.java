package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import display.engine.shapes.rules.Shape;

public class Circle extends Shape {	
	public static final long serialVersionUID = 4L;

	/**
	 * Instantiates a new Circle with the specified color, position, width, and height.
	 * 
	 * @param color the color of the circle
	 * @param posX the x-coordinate of the top-left corner of the circle
	 * @param posY the y-coordinate of the top-left corner of the circle
	 * @param width the width of the circle
	 * @param height the height of the circle
	 */
	public Circle(Color color,
		int posX, int posY,
		int width, int height
	) {
		super(color, posX, posY, width, height);
	}

	/**
	 * Instantiates a new Circle with the specified image, position, width, and height.
	 * 
	 * @param image the image to be displayed as the circle
	 * @param posX the x-coordinate of the top-left corner of the circle
	 * @param posY the y-coordinate of the top-left corner of the circle
	 * @param width the width of the circle
	 * @param height the height of the circle
	 */
	public Circle(Image image,
		int posX, int posY,
		int width, int height
	) {
		super(image, posX, posY, width, height);
	}

	/**
	 * Overrides the paintComponent method to draw the circle on the graphics object.
	 * If an image is set, it is drawn using the specified width and height.
	 * If no image is set, a filled oval is drawn using the specified width and height.
	 * 
	 * @param g the graphics object to draw on
	 */
	@Override
	public void paintComponent(Graphics g){
		if (getImage() != null) {
			super.paintComponent(g);
			g.drawImage(this.getImage(), 0, 0 , this.getWidth(), this.getHeight(), null);
		} 
		else {
			super.paintComponent(g);
        	g.fillOval(0, 0, this.getWidth(), this.getHeight());
		}
	}
}
