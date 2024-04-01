package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import display.engine.shapes.rules.Shape;
import java.awt.Image;

public class Rectangle extends Shape {

	private int angle = 0;

	/**
	 * Instantiates a new Rectangle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int, int)
	 */
	public Rectangle(Color color,
		int posX, int posY,
		int width, int height,
		int speed
	) {
		super(color, posX, posY, width, height, speed);
	}

	/**
	 * Instantiates a new Rectangle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Image, int, int, int, int, int)
	 */
	public Rectangle(Image image,
		int posX, int posY,
		int width, int height,
		int speed
	) {
		super(image, posX, posY, width, height, speed);
	}

	/**
	 * Instantiates a new Rectangle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Image, int, int, int, int)
	 */
    public Rectangle(Color color,
        int posX, int posY,
        int width, int height
    ) {
        this(color, posX, posY, width, height, 0);
    }

	/**
	 * Instantiates a new Rectangle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int)
	 */
    public Rectangle(Image image,
        int posX, int posY,
        int width, int height
    ) {
		this(image, posX, posY, width, height, 0);
    }

	/**
 	* Sets the rotation angle.
 	* 
 	* @param a an integer representing the angle of rotation
 	*/
	public void setRotate(int a){
		this.angle = a;
	}
	
	
	/**
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
