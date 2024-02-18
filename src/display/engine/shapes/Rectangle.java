package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;

import display.engine.shapes.rules.Shape;

public class Rectangle extends Shape {
	
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
        super(posX, posY, width, height, color);
    }

	/**
	 * @see display.engine.shapes.rules.Shape#paintComponent(java.awt.Graphics)
	 */


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }  
}
