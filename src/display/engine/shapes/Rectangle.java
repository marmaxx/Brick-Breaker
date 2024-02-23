package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import java.io.File;


import display.engine.shapes.rules.Shape;

public class Rectangle extends Shape {
	private static final String DEFAULT_IMAGE = System.getProperty("user.dir") + File.separator + "src" + File.separator 
												+ "game" + File.separator + "breakout" + File.separator + "assets" + File.separator 
												+ "images" + File.separator + "entities" + File.separator + "paddle.png";

	/**
	 * Instantiates a new Rectangle
	 * 
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 * @param color the color of the graphical object (ignored if the graphical object is represented by an image)
	 */
    public Rectangle(
        int posX, int posY,
        int width, int height,
		Color color
    ) {
        super(posX, posY, width, height, color); // The top-left corner is at (posX,
    }

	/**
	 * Instantiates a new PaddleImage
	 * 
	 * @param fileName The path to the image file
	 * @param posX The initial x position of the graphical object
	 * @param posY The initial y position of the graphical object
	 * @param width The width of the graphical object
	 * @param height The height of the graphical object
	 * @param color The color of the graphical object (ignored but used to call the super constructor)
	 */
    public Rectangle(String fileName,
        int posX, int posY,
        int width, int height,
		Color color
    ) {
        super(new ImageIcon(fileName).getImage(), posX, posY, width, height, color);
    }

	/**
	 * Instantiates a new Rectangle
	 * 
	 * @param posX The initial x position of the graphical object
	 * @param posY The initial y position of the graphical object
	 * @param width The width of the graphical object
	 * @param height The height of the graphical object
	 * @param color The color of the graphical object (ignored but used to call the super constructor)
	 */
	public Rectangle(int picture, int posX, int posY, int width, int height, Color color) {
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
		} else {
			super.paintComponent(g);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
