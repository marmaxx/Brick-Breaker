package display.engine.shapes.rules;

import display.engine.shapes.BallImage;
import display.engine.shapes.PaddleImage;
import display.engine.rules.GraphicalObject;
import display.engine.shapes.Circle;
import display.engine.shapes.Rectangle;


public interface Collisions {




	/**
	 * checks for collisions between 2 rectangles a and b
	 * 
	 * @param tw width of a
	 * @param th height of a
     * @param rw widht of b
     * @param rh height of b
     * 
     * @param tx x of a
     * @param ty y of a
     * @param rx x of b
     * @param ry y of b
     * 
     * @return true if a and b intersect, false otherwise
	 */
    public static boolean checkCollisions(Rectangle a, Rectangle b){

        int tw = a.getWidth();
        int th = a.getHeight();
        int rw = b.getWidth();
        int rh = b.getHeight();
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = a.getX();
        int ty = a.getY();
        int rx = b.getX();
        int ry = b.getY();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
	}

    /**
	 * checks for collisions between a BallImage and a rectangle
	 * @param tw width of a
	 * @param th height of a
     * @param rw widht of b
     * @param rh height of b
     * 
     * @param tx x of a
     * @param ty y of a
     * @param rx x of b
     * @param ry y of b
     * 
     * @return true if a and b intersect, false otherwise
	 */
    public static boolean checkCollisions(BallImage a, PaddleImage b){   
        // Find the closest point to the circle within the rectangle
        int closestX = clamp(a.getX(), b.getX(), b.getX() + b.getWidth());
        int closestY = clamp(a.getY(), b.getY(), b.getY() + b.getHeight());

        // Calculate the distance between the circle's center and this closest point
        int distanceX = a.getX() - closestX;
        int distanceY = a.getY() - closestY;

        // If the distance is less than the circle's radius, an intersection occurs
        int distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < (a.getWidth()/2 * a.getWidth()/2);
    }

    /**
	 * checks for collisions between a circle and a rectangle
	 * 
	 * @param tw width of a
	 * @param th height of a
     * @param rw widht of b
     * @param rh height of b
     * 
     * @param tx x of a
     * @param ty y of a
     * @param rx x of b
     * @param ry y of b
     * 
     * @return true if a and b intersect, false otherwise
	 */
    public static boolean checkCollisions(Circle a, Rectangle b){   
        // Find the closest point to the circle within the rectangle
        int closestX = clamp(a.getX(), b.getX(), b.getX() + b.getWidth());
        int closestY = clamp(a.getY(), b.getY(), b.getY() + b.getHeight());

        // Calculate the distance between the circle's center and this closest point
        int distanceX = a.getX() - closestX;
        int distanceY = a.getY() - closestY;

        // If the distance is less than the circle's radius, an intersection occurs
        int distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < (a.getWidth()/2 * a.getWidth()/2);
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
