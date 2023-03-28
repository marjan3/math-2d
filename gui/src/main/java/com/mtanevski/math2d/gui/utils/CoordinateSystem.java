package com.mtanevski.math2d.gui.utils;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.Constants.Origin;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CoordinateSystem {

    private static final Point2D START = new Point2D(0, 0);

    public static void draw(Canvas canvas, Constants.Origin origin) {
        setOrigin(canvas, origin);
        drawCoordinateSystem(canvas, Constants.Coordinates.POINT_INCREMENT, Constants.Coordinates.NUMBER_INCREMENT);
    }

    private static void drawCoordinateSystem(Canvas canvas, double pointIncrement, double numberIncrement) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        // draw x
        for (double y = START.getY(); y < height; y += pointIncrement) {
            // quarter 3
            drawCoordinateLine(canvas, START.getX(), y, width, y);

            // quarter 1
            drawCoordinateLine(canvas, START.getX(), -y, -width, -y);

            // quarter 4
            drawCoordinateLine(canvas, START.getX(), -y, width, -y);

            // quarter 2
            drawCoordinateLine(canvas, START.getX(), y, -width, y);
        }

        // draw y
        for (double x = START.getX(); x < width; x += pointIncrement) {
            // quarter 3
            drawCoordinateLine(canvas, x, START.getY(), x, height);

            // quarter 1
            drawCoordinateLine(canvas, -x, START.getY(), -x, -height);

            // quarter 4
            drawCoordinateLine(canvas, -x, START.getY(), -x, height);

            // quarter 2
            drawCoordinateLine(canvas, x, START.getY(), x, -height);
        }

        //  draw  coordinates on X
        for (double x = START.getX(); x < width; x += numberIncrement) {
            drawNumberPoint(canvas, x, START.getY(), String.format("%d", (int) x));
            drawNumberPoint(canvas, -x, START.getY(), String.format("%d", (int) -x));
        }

        //  draw  coordinates on Y
        for (double y = START.getY(); y < height; y += numberIncrement) {
            drawNumberPoint(canvas, START.getX(), y, String.format("%d", (int) y));
            drawNumberPoint(canvas, START.getY(), -y, String.format("%d", (int) -y));
        }
    }

    private static void drawCoordinateLine(Canvas canvas, double fromX, double fromY, double toX, double toY) {
        var gc = canvas.getGraphicsContext2D();
        gc.beginPath();
        gc.setLineWidth(Constants.Widths.COORDINATE);
        gc.moveTo(fromX, fromY);
        gc.lineTo(toX, toY);
        gc.stroke();
    }

    private static void drawNumberPoint(Canvas canvas, double x, double y, String text) {
        var gc = canvas.getGraphicsContext2D();
        gc.beginPath();
        int radius = 2;
        int diameter = radius * 2;

        gc.fillOval(x - radius, y - radius, diameter, diameter);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                text,
                x,
                y + 15
        );
    }

    public static void setOrigin(Canvas canvas, Origin origin) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        if (origin == Constants.Origin.CENTER) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Affine affine = new Affine();
            affine.appendTranslation(width * .5, height * .5);
            gc.setTransform(affine);
        }
    }

}
