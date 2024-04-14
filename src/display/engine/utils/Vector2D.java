package display.engine.utils;

/**
 * This class represents a two-dimensional vector.
 * It stores the x and the y coordinates of a point in space.
 */

public class Vector2D {
    private double x; // x-coordinate of the vector
    private double y; // y-coordinate of the vector

    /**
     * Initialize a two-dimensional vector.
     * @param x represent x-coordinate of the vector.
     * @param y represent y-coordinate of the vector. 
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the abcissa of the vector.
     */
    public double getX() {
        return x;
    }

    /**
     * @return the ordinate of the vector.
     */
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x=x;
    }

    public void setY(double y) {
        this.y=y;
    }
    
    /**
     * @return the magnitude of the vector.
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Method to normalize the vector.
     * @return the normalized vector.
     */
    public Vector2D normalize() {
        double magnitude = magnitude();
        if (magnitude!=0) return new Vector2D(x / magnitude, y / magnitude);
        else return this;
    }

    /**
     * @param other represents another vector.
     * @return a new vector which represents the addition of the two previous one.
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.getX(), this.y + other.getY());
    }

    /**
     * @param other represents another vector. 
     * @return a new vector which represents the substraction of the two previous one.
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.getX(), this.y - other.getY());
    }

    /**
     * @param scalar represents a scalar with which we multiply the vector. 
     * @return the multiplied vector.
     */
    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * @param other represents another vector.
     * @return the dot product between these two vectors.
     */
    public double dotProduct(Vector2D other) {
        return this.x * other.getX() + this.y * other.getY();
    }

    /**
     * @param other represents another vector.
     * @return the angle between these two vectors.
     */
    public double angleBetween(Vector2D other) {
        double dotProduct = dotProduct(other);
        double magnitudeProduct = magnitude() * other.magnitude();
        return Math.acos(dotProduct / magnitudeProduct);
    }

        /**
     * @param other represents another vector.
     * @return the angle in radians from the this vector to the 'other' vector.
     */
    public double angleFromTo(Vector2D other) {
        double dotProduct = this.x * other.getX() + this.y * other.getY();
        double determinant = this.x * other.getY() - this.y * other.getX();
        double angle = Math.atan2(determinant, dotProduct);
    
        return angle;
    }

    

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}

