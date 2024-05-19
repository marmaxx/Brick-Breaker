package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import display.engine.shapes.rules.Shape;

public class Oval extends Shape {	
	public static final long serialVersionUID = 5L;
	
	/**
	 * Instantiates a new Oval with the specified color, position, width, and height.
	 * 
	 * @param color the color of the oval
	 * @param posX the x-coordinate of the oval's position
	 * @param posY the y-coordinate of the oval's position
	 * @param width the width of the oval
	 * @param height the height of the oval
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int)
	 */
	public Oval(Color color,
		int posX, int posY,
		int width, int height
	) {
		super(color, posX, posY, width, height);
	}

	/**
	 * Instantiates a new Oval with the specified image, position, width, and height.
	 * 
	 * @param image the image of the oval
	 * @param posX the x-coordinate of the oval's position
	 * @param posY the y-coordinate of the oval's position
	 * @param width the width of the oval
	 * @param height the height of the oval
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int)
	 */
	public Oval(Image image,
		int posX, int posY,
		int width, int height
	) {
		super(image, posX, posY, width, height);
	}


    /**
	 * Instantiates a new Oval with the specified color, position, and width.
	 * The height of the oval is set to half of the width.
	 * 
	 * @param color the color of the oval
	 * @param posX the x-coordinate of the oval's position
	 * @param posY the y-coordinate of the oval's position
	 * @param width the width of the oval
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Image, int, int, int, int)
	 */
    public Oval(Color color,
        int posX, int posY,
        int width
    ) {
        this(color, posX, posY, width, width/2);
    }

	/**
	 * Instantiates a new Oval with the specified image, position, and width.
	 * The height of the oval is set to half of the width.
	 * 
	 * @param image the image of the oval
	 * @param posX the x-coordinate of the oval's position
	 * @param posY the y-coordinate of the oval's position
	 * @param width the width of the oval
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int)
	 */
    public Oval(Image image,
        int posX, int posY,
        int width
    ) {
		this(image, posX, posY, width, width/2);
    }

	/**
	 * Overrides the paintComponent method of the parent class.
	 * Draws the oval on the graphics context.
	 * If an image is set, the image is drawn instead of the oval shape.
	 * 
	 * @param g the graphics context
	 * 
	 * @see display.engine.shapes.rules.Shape#paintComponent(java.awt.Graphics)
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
