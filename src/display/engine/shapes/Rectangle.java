package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import display.engine.shapes.rules.Shape;
import java.awt.Image;

public class Rectangle extends Shape {

	public static final long serialVersionUID = 6L;

	private int angle = 0;

	/**
	 * Instantiates a new Rectangle.
	 * 
	 * @param color the color of the rectangle
	 * @param posX the x-coordinate of the top-left corner of the rectangle
	 * @param posY the y-coordinate of the top-left corner of the rectangle
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int)
	 */
	public Rectangle(Color color,
		int posX, int posY,
		int width, int height
	) {
		super(color, posX, posY, width, height);
	}

	/**
	 * Instantiates a new Rectangle.
	 * 
	 * @param image the image to be displayed as the rectangle
	 * @param posX the x-coordinate of the top-left corner of the rectangle
	 * @param posY the y-coordinate of the top-left corner of the rectangle
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Image, int, int, int, int)
	 */
	public Rectangle(Image image,
		int posX, int posY,
		int width, int height
	) {
		super(image, posX, posY, width, height);
	}

	/**
	 * Sets the rotation angle of the rectangle.
	 * 
	 * @param a an integer representing the angle of rotation
	 */
	public void setRotate(int a){
		this.angle = a;
	}
	
	
	/**
	 * Overrides the paintComponent method to draw the rectangle on the screen.
	 * 
	 * @param g the Graphics object used for drawing
	 * 
	 * @see display.engine.shapes.rules.Shape#paintComponent(java.awt.Graphics)
	 */
    @Override
	public void paintComponent(Graphics g){
		if (getImage() != null) {
			if(this.angle != 0){
				Graphics2D g2D = (Graphics2D)g;
				g2D.rotate(Math.toRadians(this.angle));
				super.paintComponent(g2D);
				g2D.drawImage(this.getImage(), 0, 0 , this.getWidth(), this.getHeight(), null);
			} else {
				super.paintComponent(g);
				g.drawImage(this.getImage(), 0, 0 , this.getWidth(), this.getHeight(), null);
			}
		} else {
			if (this.angle != 0){
				Graphics2D g2D = (Graphics2D)g;
				g2D.rotate(Math.toRadians(this.angle));
				super.paintComponent(g2D);
				g2D.fillRect(0, 0 , this.getWidth(), this.getHeight());
			} else {
				super.paintComponent(g);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}	
		}
	}
}
