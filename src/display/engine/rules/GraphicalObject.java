package display.engine.rules;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.JComponent;

import display.engine.utils.Vector2D;
import display.view.GamePanel;


public abstract class GraphicalObject extends JComponent {
	public static final long serialVersionUID = 1L;

    protected int posX, posY;
    protected int width, height;
	protected Color color;
	transient protected Image image;

	// Used to define the boundaries of the object (i.e collision detection)
	public static enum Boundary {
		MIN_X, MAX_X, MIN_Y, MAX_Y
	}
    
	/**
	 * Instantiates a new GraphicalObject
	 * 
	 * @param image The image file representing the graphical object
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 */
    public GraphicalObject(Image image,
        int posX, int posY,
        int width, int height
    ) {
		this.setColor(null);
		this.setImage(image);
		this.setPosX(posX);
		this.setPosY(posY);
		this.setWidth(width);
		this.setHeight(height);
		
    }
    
	/**
	 * Instantiates a new GraphicalObject
	 * 
	 * @param color The color of the graphical object
	 * @param posX the initial x position of the graphical object
	 * @param posY the initial y position of the graphical object
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 */
    public GraphicalObject(Color color, 
		int posX, int posY,
        int width, int height
    ) {
		this.setColor(color);
		this.setImage(null);
		this.setPosX(posX);
		this.setPosY(posY);
		this.setWidth(width);
		this.setHeight(height);
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
	 * Gets the x position of the center of the graphical object
	 * 
	 * @return The x position of the center of the graphical object
	 */
	public int getCenterX() {
		return (posX+(width/2));
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
	 * Gets the y position of the graphical object
	 * 
	 * @return The y position of the graphical object
	 */
    public int getCenterY() {
        return (posY+(height/2));
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
	 * Sets the image representing the graphical object
	 * 
	 * @param image The image representing the graphical object
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Gets the image representing the graphical object
	 * 
	 * @return The image representing the graphical object
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Get the coordinates of the boundaries from this object
	 * 
	 * @return The coordinates of the boundaries
	 */
	public int[] getBoundaries() {
		int[] boundaryBox = new int[Boundary.values().length];

		boundaryBox[Boundary.MAX_Y.ordinal()] = this.getPosY() + this.getHeight();
		boundaryBox[Boundary.MIN_Y.ordinal()] = this.getPosY();
		boundaryBox[Boundary.MIN_X.ordinal()] = this.getPosX();
		boundaryBox[Boundary.MAX_X.ordinal()] = this.getPosX() + this.getWidth();

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
	 * Get the next coordinates of the boundaries from this object
	 * 
	 * @return The coordinates of the boundaries
	 */
	public int[] getNextBoundaries(int[] nextPos) {
		int[] boundaryBox = new int[Boundary.values().length];

		boundaryBox[Boundary.MAX_Y.ordinal()] = nextPos[1] + this.getHeight();
		boundaryBox[Boundary.MIN_Y.ordinal()] = nextPos[1];
		boundaryBox[Boundary.MIN_X.ordinal()] = nextPos[0];
		boundaryBox[Boundary.MAX_X.ordinal()] = nextPos[0] + this.getWidth();

		return boundaryBox;
	}
	
	/**	
	 * Get the next coordinates of a boundary from this graphical object
	 * 
	 * @param boundary The boundary to retrieve
	 */
	public float getNextBoundary(Boundary boundary,int[] nextPos) {
		return this.getNextBoundaries(nextPos)[boundary.ordinal()];
	}



	/**
	 * Checks if the object is within its panel boundaries
	 * 
	 * @param x the x position to check
	 * @param y the y position to check
	 * @param panel the panel in which the object is rendered
	 * 
	 * @return true if the object is within the panel boundaries, false otherwise
	 */
	public static boolean isOnScreen(int x, int y, GamePanel panel) {
		return (x >= 0 && x <= panel.getWidth() && y >= 0 && y <= panel.getHeight());
	}


	/**
	 * Check if this graphical object is colliding with another graphical object
	 * 
	 * @param object The graphical object to check collision with
	 * 
	 * @return true if the objects are colliding, false otherwise
	 */
	public boolean isColliding(GraphicalObject object) {
		int[] thisBoundingBox = this.getBoundaries();
		int[] objectBoundingBox = object.getBoundaries();

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


	/**
	 * check if this is going to collide with an object on the next tick
	 * @param object the object we want to check collisions with
	 * @param myNextPos my next coordinates 
	 * @param objectNextPos their next coordinates
	 * @return true if we are going to collide, false otherwise
	 */
	public boolean isGoingToCollide(GraphicalObject object, int[] myNextPos, int[]objectNextPos) {
		int[] thisBoundingBox = this.getNextBoundaries(myNextPos);
		int[] objectBoundingBox = object.getNextBoundaries(objectNextPos);

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

	/**
	 *  
	 * @param object the object we want our vector to point to
	 * @return a vector that goes from this object's center to object in argument
	 */
	public Vector2D vectorFromCenterToCenter(GraphicalObject object){
		Vector2D rep = new Vector2D(0, 0);
		rep.setX(object.getCenterX() - this.getCenterX());
		rep.setY(-(object.getCenterY()- this.getCenterY()));  // negated because of how y coordinates are handled by java
		return rep;
	}

	/**
	 * 
	 * @param x 
	 * @param y
	 * @return a vector from this object's center to (x,y)
	 */
	public Vector2D vectorCenterToCoordinates(double x, double y){
		Vector2D rep = new Vector2D(0, 0);
		rep.setX(x - this.getCenterX());
		rep.setY((y-this.getCenterY()));  // negated because of how y coordinates are handled by java
		return rep;
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
	 * removes the graphical object from the view
	 */
	public void destroy() {
        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.repaint(); // Ensure that the container repaints after removal
        }
    }


	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (this.getImage() != null) {
			g.drawImage(this.getImage(), 0, 0 , this.getWidth(), this.getHeight(), null);
		}
        g.setColor(this.getColor());
		this.setLocation(posX, posY);
		this.setSize(width, height);
	}
}