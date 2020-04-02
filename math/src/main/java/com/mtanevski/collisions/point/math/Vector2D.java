package com.mtanevski.collisions.point.math;

import java.util.Objects;


/**
 * Vectors are useful for representing direction, speed, force, velocity or displacement
 */
public class Vector2D implements Cloneable {

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vector2D) {
        Objects.requireNonNull(vector2D);
        this.x = vector2D.x;
        this.y = vector2D.y;
    }

    public static Vector2D of(double x, double y) {
        return new Vector2D(x, y);
    }


    /**
     * Also known as magnitude which can represent any quantity: length, distance, movement, displacement, velocity, force, etc.
     * It is up to the user to define the unit of measure it will represent.
     *
     * @return the calculated length
     */
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }


    /**
     * Used as an alternative to avoid doing square root since square root is considered to take up resources
     */
    public double getSquaredLength() {
        return x * x + y * y;
    }

    /**
     * Also known as normalization of vector. Mostly used to denote a direction
     *
     * @return the unit vector
     */
    public Vector2D getUnit() {
        double length = getLength();
        return new Vector2D(x / length, y / length);
    }

    /**
     * Same as {@link Vector2D#getUnit} except it modifies this object x and y.
     * The process of converting a vector to a unit vector is called normalization
     */
    public void normalize() {
        double length = getLength();
        x /= length;
        y /= length;
    }

    /**
     * TODO
     * @param reductionLength
     */
    public void shortenLength(double reductionLength)
    {
        this.multiply(1 - reductionLength/this.getLength());
    }

    /**
     * @return vector that is 90 degrees counter clockwise of the given vector
     */
    public Vector2D getPerpendicularLeft() {
        return new Vector2D(-y, x);
    }

    /**
     * @return vector that is 90 degrees clockwise of the given vector
     */
    public Vector2D getPerpendicularRight() {
        return new Vector2D(y, -x);
    }

    /**
     * Adds two vectors
     *
     * @param a vector one
     * @param b vector 2
     * @return resulting vector
     */
    public static Vector2D add(Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    /**
     * Adds the given vector with this one
     *
     * @param vector2D the vector to add
     */
    public void add(Vector2D vector2D) {
        this.x += vector2D.x;
        this.y += vector2D.y;
    }

    /**
     * Adds the given x, y coordinates of other vector to this one
     *
     * @param x x coordinate of given vector
     * @param y y coordinate of given vector
     */
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Subtracts two vectors
     *
     * @param a vector one
     * @param b vector 2
     * @return resulting vector
     */
    public static Vector2D subtract(Vector2D a, Vector2D b) {
        return new Vector2D(a.x - b.x, a.y - b.y);
    }

    /**
     * Subtracts the given vector with this one
     *
     * @param vector2D the vector to subtract
     */
    public void subtract(Vector2D vector2D) {
        this.x -= vector2D.x;
        this.y -= vector2D.y;
    }

    /**
     * Subtracts the given x, y coordinates of other vector to this one
     *
     * @param x x coordinate of given vector
     * @param y y coordinate of given vector
     */
    public void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    /**
     * Negation of a vector
     *
     * @return the same vector with opposite direction
     */
    public Vector2D getNegative() {
        return new Vector2D(-x, -y);
    }

    /**
     * Negates a vector
     */
    public void negate() {
        x = -x;
        y = -y;
    }

    /**
     * Multiples a vector by a given scalar value
     *
     * @param vector2D the vector to multiply
     * @param scalar   the value that the vector will be multiplied by
     * @return the new multiplied vector
     */
    public static Vector2D multiply(Vector2D vector2D, double scalar) {
        return new Vector2D(vector2D.x * scalar, vector2D.y * scalar);
    }

    /**
     * Multiples this vector
     *
     * @param scalar the value this vector will be multiplied by
     */
    public void multiply(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    /**
     * Divides a vector by a given scalar value
     *
     * @param vector2D the vector to divide
     * @param scalar   the value that the vector will be divided by
     * @return the new divided vector
     */
    public static Vector2D divide(Vector2D vector2D, double scalar) {
        return new Vector2D(vector2D.x / scalar, vector2D.y / scalar);
    }

    /**
     * Divides this vector
     *
     * @param scalar the value this vector will be divided by
     */
    public void divide(double scalar) {
        x /= scalar;
        y /= scalar;
    }

    /**
     * If we assume a & b are unit vector the dot product can be calculated using the following method.
     * If not, the result will be multiplied by the lengths of the vectors
     * - If the dot product is positive, vector a is pointing in the same general direction as vector b
     * - If the dot product is negative, vector a is pointing in the opposite general direction of vector b
     * - If the dot product is equal to zero, vector a & b are perpendicular
     * - The dot product for vectors a & b where a is equal to be will be the squared length of the vector a or b
     * - The square root of the dot product of the same vector will result in the length of the vector
     * - The dot product of the vector a and a unit vector b results in a distance from the end of vector a to the line defined by the perpendicular unit vector of b
     * - The dot product of the vector a and a unit vector b results in the distance from the end of vector a to the line defined by the unit vector b
     *
     * @param a vector 1
     * @param b vector 2
     * @return the calculated dot product
     */
    public static double getDotProduct(Vector2D a, Vector2D b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * Calculates the perpendicular product of two vectors
     * - If the perpendicular product is positive, then the vector b is pointing left of the vector a
     * - If the perpendicular product is equal to zero, then the vector b is parallel to the vector a
     * - If the perpendicular product is negative, then the vector b is pointing right of the vector a
     * - The perpendicular product of the vector a (a unit vector), results in the distance (d) from the end of vector a to the line defined by the unit vector b.
     *
     * @param a vector 1
     * @param b vector 2
     * @return the calculated perpendicular product
     */
    public static double getPerpendicularProduct(Vector2D a, Vector2D b) {
        return a.x * b.y - a.y * b.x;
    }

    /**
     * Same as ${@link Vector2D#getPerpendicularProduct(Vector2D, Vector2D)}, except this method applies for this vector
     *
     * @param a vector 1
     * @return the calculated perpendicular product
     */
    public double getPerpendicularProduct(Vector2D a) {
        return a.y * x - a.x * y;
    }

    /**
     * The projection time is useful to determine if the projection point is behind, on or ahead of the vector b.
     * - If the projection time is less than zero, then the projection point will be behind the vector b.
     * - If the projection time is greater than one, then the projection point will be ahead of the vector b.
     * - Otherwise, the projection point will be on vector b
     *
     * @param a projection to vector
     * @param b the projected vector
     * @return the projection time (simplified when b is a unit vector
     */
    public static double getProjectionTime(Vector2D a, Vector2D b) {
        return (a.x * a.x + a.y * a.y) / (b.x * b.x + b.y * b.y);
    }

    /**
     * TODOVISUALLY
     * Get projection point based on the projection time
     *
     * @param p the point on which the projection is based
     * @param b the vector to project
     * @param t the projection time {@link Vector2D#getProjectionTime(Vector2D, Vector2D)}
     * @return the point of the projected vector
     */
    public static Point2D getProjectionPoint(Point2D p, Vector2D b, double t) {
        return new Point2D(p.x + t * b.x, p.y + t * b.y);
    }

    /**
     * TODOVISUALLY
     * Get projection point for this vector
     *
     * @param p the point on which the projection is based
     * @param t the projection time {@link Vector2D#getProjectionTime(Vector2D, Vector2D)}
     * @return the point of the projected vector
     */
    public Point2D getProjectionPoint(Point2D p, double t) {
        return new Point2D(p.x + t * x, p.y + t * y);
    }

    /**
     * Can be used to determinte the orientation of the vector
     * The slope (m) can be categorized into the following categories:
     * Positive (m>0),
     * Negative (m<0),
     * Horizontal (m=0),
     * and Vertical (m=nan).
     *
     * @return the slope
     */
    public double getSlope() {
        return y / x;
    }

    /**
     * TODOVISUALLY
     * Creates a vector from an angle based on trigonometry functions
     *
     * @param a the angle in radians
     * @return the resulting vector
     */
    public static Vector2D getVector(double a) {
        return new Vector2D(Math.cos(a), Math.sin(a));
    }


    /**
     * Calculates the angle of the vector
     *
     * @return the angle of the vector in radians
     */
    public double getAngle() {
        double length = this.getLength();
        return Math.acos(x / length);
    }


    /**
     * Returns the angle between this vector and vector a
     *
     * @param a the other vector
     * @return the angle in radians
     */
    public double getAngle(Vector2D a) {
        return Vector2D.getAngle(this, a);
    }

    /**
     * Returns the angle between two vectors
     *
     * @param a vector 1
     * @param b vector 2
     * @return the angle in radians
     */
    public static double getAngle(Vector2D a, Vector2D b) {
        double lv = a.getLength();
        double lx = b.getLength();
        return Math.acos((a.x * b.x + a.y * b.y) / (lv * lx));
    }


    /**
     * Creates a new rotated vector
     *
     * @param a the angle in radians
     * @return the rotated vector
     */
    public Vector2D getRotation(double a) {
        double s = Math.sin(a);
        double c = Math.cos(a);
        return new Vector2D(x * c - y * s, y * c + x * s);
    }

    /**
     * Rotates this vector by an angle
     *
     * @param a the angle in radians
     */
    public void rotate(double a) {
        double s = Math.sin(a);
        double c = Math.cos(a);
        x = x * c - y * s;
        y = y * c + x * s;
    }

    /**
     * Determines on which quadrant does the vector belongs to,
     * where P=positive, N=negative, Z=zero
     * @return the category
     */
    public String getCategory() {
        if (x < 0) {
            if (y < 0) return "NN";
            else if (y > 0) return "NP";
            else return "NZ";
        } else if (x > 0) {
            if (y < 0) return "PN";
            else if (y > 0) return "PP";
            else return "PZ";
        } else {
            if (y < 0) return "ZN";
            else if (y > 0) return "ZP";
            else return "ZZ";
        }
    }

    @Override
    public String toString() {
        return "Vector2D{" + "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public Vector2D clone() {
        return Vector2D.of(x, y);
    }
}
