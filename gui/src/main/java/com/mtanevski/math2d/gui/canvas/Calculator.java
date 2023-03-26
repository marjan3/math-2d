package com.mtanevski.math2d.gui.canvas;

import com.mtanevski.math2d.math.Vector2D;

public class Calculator {

    public static Double[] getArrow(double x, double y) {
        var vector2D = new Vector2D(x, y);

        // one side of the arrow
        var vector1 = vector2D.getRotation(Math.toRadians(-150))
                .normalize().multiply(10).add(vector2D);

        // other side of the arrow
        var vector2 = vector2D.getRotation(Math.toRadians(150))
                .normalize().multiply(10).add(vector2D);

        return new Double[]{
                0.0, 0.0,
                vector2D.x, vector2D.y, // go to the top of the arrow
                vector2.x, vector2.y, // go to the right side
                vector1.x, vector1.y, // go to the left side
                vector2D.x, vector2D.y, // go back to the top of the arrow
        };
    }
}
