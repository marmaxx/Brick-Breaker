package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;
import display.engine.shapes.rules.Shape;
import java.awt.Image;

public class Rectangle extends Shape {

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
	 * @see display.engine.shapes.rules.Shape#paintComponent(java.awt.Graphics)
	 */
    @Override
	public void paintComponent(Graphics g){
		if (getImage() != null) {
			super.paintComponent(g);
			g.drawImage(this.getImage(), 0, 0 , this.getWidth(), this.getHeight(), null);
		} else {
			super.paintComponent(g);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}
}
