package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import java.io.File;


import display.engine.shapes.rules.Shape;

public class Circle extends Shape {
	private static final String DEFAULT_IMAGE = System.getProperty("user.dir") + File.separator + "src" + File.separator 
												+ "game" + File.separator + "breakout" + File.separator + "assets" + File.separator 
												+ "images" + File.separator + "entities" + File.separator + "ball.png";
	
	/**
	 * Instantiates a new Circle
	 * 
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param size the size of the graphical object
	 * @param color the color of the graphical object (ignored if the graphical object is represented by an image)
	 */
    public Circle(
        int posX, int posY,
        int size,
		Color color
    ) {
        super(posX, posY, size, size, color);
    }

	/**
	 * Instantiates a new BallImage
	 * 
	 * @param fileName The path to the image file
	 * @param posX The initial x position of the graphical object
	 * @param posY The initial y position of the graphical object
	 * @param width The width of the graphical object
	 * @param height The height of the graphical object
	 * @param color The color of the graphical object (ignored but used to call the super constructor)
	 */
    public Circle(String fileName,
        double posX, double posY,
        int width, int height,
		Color color
    ) {
        super(new ImageIcon(fileName).getImage(), (int)posX, (int)posY, width, height, color);
    }

	/**
	 * Instantiates a new BallImage
	 * 
	 * @param posX The initial x position of the graphical object
	 * @param posY The initial y position of the graphical object
	 * @param width The width of the graphical object
	 * @param height The height of the graphical object
	 * @param color The color of the graphical object (ignored but used to call the super constructor)
	 */
	public Circle(int picture, double posX, double posY, int width, int height, Color color) {
		this(DEFAULT_IMAGE, posX, posY, width, height, color);
	}
	
	/**
	 * @see display.engine.shapes.rules.Shape#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g){
		if (getImage() != null) {
			super.paintComponent(g);
			g.drawImage(this.getImage(), 0, 0 , null);
		} 
		else {
			super.paintComponent(g);
        	g.fillOval(0, 0, this.getWidth(), this.getHeight());
		}
	}
}
