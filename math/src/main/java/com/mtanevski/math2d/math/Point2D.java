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
}
