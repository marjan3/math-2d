package com.mtanevski.math2d.math;

public class Point2D {

    public double x;
    public double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public static Point2D of(double x, double y) {
        return new Point2D(x, y);
    }

    @Override
    public Point2D clone() {
        return Point2D.of(x, y);
    }

    @Override
    public String toString() {
        return "Point2D{" + "x=" + x +
                ", y=" + y +
                '}';
    }

}
