package display.engine.images;

import java.awt.Color;
import javax.swing.ImageIcon;

import display.engine.images.rules.Image;

public class BallImage extends Image {
	private static final String DEFAULT_IMAGE = System.getProperty("user.dir") + "/src/game/breakout/assets/images/entities/ball.png";
	
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
        int posX, int posY,
        int width, int height,
		Color color
    ) {
        super(new ImageIcon(fileName).getImage(), posX, posY, width, height, color);
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
	public BallImage(int posX, int posY, int width, int height, Color color) {
		this(DEFAULT_IMAGE, posX, posY, width, height, color);
	}
}
