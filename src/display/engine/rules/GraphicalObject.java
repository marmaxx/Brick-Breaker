package display.engine.rules;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


public abstract class GraphicalObject extends JComponent {

    protected int posX, posY;
    protected int width, height;
	protected Color color;

	// Used to define the boundaries of the object (i.e collision detection)
	public static enum Boundary {
		MIN_X, MAX_X, MIN_Y, MAX_Y
	}
    
	/**
	 * Instantiates a new GraphicalObject
	 * 
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 * @param color the color of the graphical object (ignored if the graphical object is represented by an image)
	 */
    public GraphicalObject(
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
	 * Get the coordinates of the boundaries from this object
	 * 
	 * @return The coordinates of the boundaries
	 */
	public float[] getBoundaries() {
		float[] centerPosition = { this.getPosX(), this.getPosY()};
		float[] size = { this.getWidth(), this.getHeight()};

		float[] boundaryBox = new float[Boundary.values().length];

		boundaryBox[Boundary.MAX_Y.ordinal()] = centerPosition[1] + size[1] / 2;
		boundaryBox[Boundary.MIN_Y.ordinal()] = centerPosition[1] - size[1] / 2;
		boundaryBox[Boundary.MIN_X.ordinal()] = centerPosition[0] - size[0] / 2;
		boundaryBox[Boundary.MAX_X.ordinal()] = centerPosition[0] + size[0] / 2;

		return boundaryBox;
	}
	
	/**	
	 * Get the coordinate of a boundary from this graphical object
	 * 
	 * @param boundary The boundary to retrieve
	 */
	public float getBoundary(Boundary boundary) {
		return this.getBoundaries()[boundary.ordinal()];
	}

	/**
	 * Check if this graphical object is colliding with another graphical object
	 * 
	 * @param object The graphical object to check collision with
	 * 
	 * @return true if the objects are colliding, false otherwise
	 */
	public boolean isColliding(GraphicalObject object) {
		float[] thisBoundingBox = this.getBoundaries();
		float[] objectBoundingBox = object.getBoundaries();

		// If the top of this object is higher than the bottom of the other object
		boolean isColliding = (thisBoundingBox[Boundary.MAX_Y.ordinal()] >= objectBoundingBox[Boundary.MIN_Y.ordinal()]
				// If the bottom of this object is lower than the top of the other object
				&& thisBoundingBox[Boundary.MIN_Y.ordinal()] <= objectBoundingBox[Boundary.MAX_Y.ordinal()]
				// If the left of this object is more to the left than the right of the other object
				&& thisBoundingBox[Boundary.MIN_X.ordinal()] <= objectBoundingBox[Boundary.MAX_X.ordinal()]
				// If the right of this object is more to the right than the left of the other object
				&& thisBoundingBox[Boundary.MAX_X.ordinal()] >= objectBoundingBox[Boundary.MIN_X.ordinal()]);

		return isColliding;
	}

	public String toString() {
		return "GraphicalObject: " + "\n"
				+ "\tType: " + this.getClass().getSimpleName() + "\n"
				+ "\tPosition: X: " + this.getPosX() + ", Y: " + this.getPosY() + ", Z: " + "\n"
				+ "\tScale: X: " + this.getWidth() + ", Y: " + this.getHeight() + ", Z: " + "\n"
				+ "\tBoundingBox: " + "\n"
				+ "\t\tTop (MAX_Y): " + this.getBoundary(Boundary.MAX_Y) + "\n"
				+ "\t\tLeft: (MIN_X): " + this.getBoundary(Boundary.MIN_X) + "\n"
				+ "\t\tRight: (MAX_X): " + this.getBoundary(Boundary.MAX_X) + "\n"
				+ "\t\tBottom: (MIN_Y): " + this.getBoundary(Boundary.MIN_Y) + "\n";
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