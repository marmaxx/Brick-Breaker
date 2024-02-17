package display.engine.shapes;

import java.awt.Color;
import javax.swing.ImageIcon;
// file.io
import java.io.File;

//import display.engine.images.rules.Image;
import display.engine.shapes.rules.Shape;

public class BallImage extends Shape {
	private static final String DEFAULT_IMAGE = System.getProperty("user.dir") + File.separator + "src" + File.separator 
												+ "game" + File.separator + "breakout" + File.separator + "assets" + File.separator 
												+ "images" + File.separator + "entities" + File.separator + "ball.png";
	
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
    public BallImage(String fileName,
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
	public BallImage(double posX, double posY, int width, int height, Color color) {
		this(DEFAULT_IMAGE, posX, posY, width, height, color);
	}
}
