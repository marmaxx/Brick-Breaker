package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import display.engine.shapes.rules.Shape;

public class Circle extends Shape {	

	/**
	 * Instantiates a new Circle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int, int)
	 */
	public Circle(Color color,
		int posX, int posY,
		int width, int height,
		int speed
	) {
		super(color, posX, posY, width, height, speed);
	}

	/**
	 * Instantiates a new Circle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int, int)
	 */
	public Circle(Image image,
		int posX, int posY,
		int width, int height,
		int speed
	) {
		super(image, posX, posY, width, height, speed);
	}

	/**
	 * Instantiates a new Circle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Image, int, int, int, int)
	 */
    public Circle(Color color,
        int posX, int posY,
        int width, int height
    ) {
        this(color, posX, posY, width, height, 0);
    }

	/**
	 * Instantiates a new Circle
	 * 
	 * @see display.engine.shapes.rules.Shape#Shape(Color, int, int, int, int)
	 */
    public Circle(Image image,
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
		} 
		else {
			super.paintComponent(g);
        	g.fillOval(0, 0, this.getWidth(), this.getHeight());
		}
	}
}
