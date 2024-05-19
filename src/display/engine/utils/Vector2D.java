package display.engine.utils;

import java.io.Serializable;

/**
 * This class represents a two-dimensional vector.
 * It stores the x and the y coordinates of a point in space.
 */
public class Vector2D implements Serializable{
    public static final long serialVersionUID = 7L;

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

    /**
     * Set the x-coordinate of the vector.
     * @param x the new x-coordinate value.
     */
    public void setX(double x) {
        this.x=x;
    }

    /**
     * Set the y-coordinate of the vector.
     * @param y the new y-coordinate value.
     */
    public void setY(double y) {
        this.y=y;
    }
    
    /**
     * Calculate the magnitude of the vector.
     * @return the magnitude of the vector.
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Normalize the vector.
     * @return the normalized vector.
     */
    public Vector2D normalize() {
        double magnitude = magnitude();
        if (magnitude!=0) return new Vector2D(x / magnitude, y / magnitude);
        else return this;
    }

    /**
     * Add another vector to this vector.
     * @param other the vector to be added.
     * @return a new vector which represents the addition of the two vectors.
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.getX(), this.y + other.getY());
    }

    /**
     * Subtract another vector from this vector.
     * @param other the vector to be subtracted.
     * @return a new vector which represents the subtraction of the two vectors.
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.getX(), this.y - other.getY());
    }

    /**
     * Multiply the vector by a scalar value.
     * @param scalar the scalar value to multiply the vector by.
     * @return the multiplied vector.
     */
    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Calculate the dot product between this vector and another vector.
     * @param other the other vector.
     * @return the dot product between the two vectors.
     */
    public double dotProduct(Vector2D other) {
        return this.x * other.getX() + this.y * other.getY();
    }

    /**
     * Calculate the angle between this vector and another vector.
     * @param other the other vector.
     * @return the angle between the two vectors.
     */
    public double angleBetween(Vector2D other) {
        double dotProduct = dotProduct(other);
        double magnitudeProduct = magnitude() * other.magnitude();
        return Math.acos(dotProduct / magnitudeProduct);
    }

    /**
     * Calculate the angle in radians from this vector to the 'other' vector.
     * @param other the other vector.
     * @return the angle in radians from this vector to the 'other' vector.
     */
    public double angleFromTo(Vector2D other) {
        double dotProduct = this.x * other.getX() + this.y * other.getY();
        double determinant = this.x * other.getY() - this.y * other.getX();
        double angle = Math.atan2(determinant, dotProduct);
    
        return angle;
    }

    /**
     * Get a string representation of the vector.
     * @return a string representation of the vector.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}

