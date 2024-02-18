package display.engine.rules;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import display.engine.images.BallImage;
import display.engine.images.PaddleImage;
import display.engine.shapes.Circle;
import display.engine.shapes.Rectangle;
import display.engine.shapes.rules.Collisions;
import display.engine.shapes.rules.Shape;

public abstract class GraphicalObject extends JComponent {

    protected int posX, posY;
    protected int width, height;
	protected Color color;
    
	/**
	 * Instantiates a new GraphicalObject
	 * 
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 * @param color the color of the graphical object (ignored if the graphical object is represented by an image)
	 */
    public GraphicalObject (
        int posX, int posY,
        int width, int height,
		Color color
    ) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.setWidth(width);
		this.setHeight(height);
		this.setColor(color);

    }

	/**
	 * Gets the x position of the graphical object
	 * 
	 * @return The x position of the graphical object
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Sets the x position of the graphical object
	 * 
	 * @param posX The x position of the graphical object
	 */
    public void setPosX(int posX) {
        this.posX = posX;
    }

	/**
	 * Gets the y position of the graphical object
	 * 
	 * @return The y position of the graphical object
	 */
    public int getPosY() {
        return posY;
    }

	/**
	 * Sets the y position of the graphical object
	 * 
	 * @param posY The y position of the graphical object
	 */
    public void setPosY(int posY) {
        this.posY = posY;
    }

	/**
	 * Gets the width of the graphical object
	 * 
	 * @return The width of the graphical object
	 */
    public int getWidth() {
        return width;
    }

	/**
	 * Sets the width of the graphical object
	 * 
	 * @param width The width of the graphical object
	 */
    public void setWidth(int width) {
        this.width = width;
    }

	/**
	 * Gets the height of the graphical object
	 * 
	 * @return The height of the graphical object
	 */
    public int getHeight() {
        return height;
    }

	/**
	 * Sets the height of the graphical object
	 * 
	 * @param height The height of the graphical object
	 */
    public void setHeight(int height) {
        this.height = height;
    }

	/**
	 * Gets the color of the graphical object
	 * 
	 * @return The color of the graphical object
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the graphical object
	 * 
	 * @param color The color of the graphical object
	 */
	public void setColor(Color color) {
		this.color = color;
	}


	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        g.setColor(this.getColor());
		this.setLocation(posX, posY);
		this.setSize(width, height);
	}


}