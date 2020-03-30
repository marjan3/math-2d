package com.mtanevski.collisions.point.javafx.canvas;

import com.mtanevski.collisions.point.lib.Vector2D;

public class Calculator {

    public static Double[] getArrow(double x, double y) {

        Vector2D vector2D = new Vector2D(x, y);
        Vector2D rotatedVector1 = vector2D.getRotation(Math.toRadians(150));
        rotatedVector1.normalize();
        rotatedVector1.multiply(10);
        rotatedVector1.add(vector2D);
        /*
        Or depending on how your code is arranged,
        you can combine the normalization and lengthening into one step.
        First, getVector2D the current length of the vector,
        just as you would when attempting to normalize it.
        Then instead of dividing each component by that value, take your desired length, and divide that value by the current length.
        Take this new value and multiply it by each component of the vector. Your vector should now be your desired length, while still in the same direction.
         */

        Vector2D rotatedVector2 = vector2D.getRotation(Math.toRadians(-150));
        rotatedVector2.normalize();
        rotatedVector2.multiply(10);
        rotatedVector2.add(vector2D);
        return new Double[]{
                0.0, 0.0,
                vector2D.x, vector2D.y,
                rotatedVector2.x, rotatedVector2.y,
                vector2D.x, vector2D.y,
                rotatedVector1.x, rotatedVector1.y,
                vector2D.x, vector2D.y,
        };
    }
}
