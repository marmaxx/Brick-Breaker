package display.engine.shapes;

import java.awt.Color;
import java.awt.Graphics;

import display.engine.shapes.rules.Shape;

public class Circle extends Shape {
	
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
	 * @see display.engine.shapes.rules.Shape#paintComponent(java.awt.Graphics)
	 */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillOval(0, 0, this.getWidth(), this.getHeight());
    }  
}
