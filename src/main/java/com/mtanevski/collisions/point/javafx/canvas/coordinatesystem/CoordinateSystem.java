package com.mtanevski.collisions.point.javafx.canvas.coordinatesystem;

import com.mtanevski.collisions.point.javafx.Constants;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;

public class CoordinateSystem {

    private final Canvas canvas;
    private static final double LINE_WIDTH = 0.2;
    private static final Point2D START = new Point2D(0, 0);
    private final CoordinateSystemProperties coordinateSystemProperties;

    public CoordinateSystem(Canvas canvas, Runnable onOriginChange){
        this.canvas = canvas;
        coordinateSystemProperties = new CoordinateSystemProperties();
        coordinateSystemProperties.setOnAction(onOriginChange);
    }

    public void draw() {
        Constants.OriginType selectedItem = this.getOriginType();
        this.setOrigin(selectedItem);
        drawCoordinateSystem(Constants.Coordinates.POINT_INCREMENT, Constants.Coordinates.NUMBER_INCREMENT);
    }

    public GridPane getEditPropertiesPane() {
        return coordinateSystemProperties.getPane();
    }


    private void drawCoordinateSystem(double pointIncrement, double numberIncrement) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        // draw x
        for (double y = START.getY(); y < height; y+=pointIncrement) {
            // quarter 3
            drawCoordinateLine(START.getX(), y, width, y);

            // quarter 1
            drawCoordinateLine(START.getX(), -y, -width, -y);

            // quarter 4
            drawCoordinateLine(START.getX(), -y, width, -y);

            // quarter 2
            drawCoordinateLine(START.getX(), y, -width, y);
        }

        // draw y
        for (double x = START.getX(); x < width; x+=pointIncrement) {
            // quarter 3
            drawCoordinateLine(x, START.getY(), x, height);

            // quarter 1
            drawCoordinateLine(-x, START.getY(), -x, -height);

            // quarter 4
            drawCoordinateLine(-x, START.getY(), -x, height);

            // quarter 2
            drawCoordinateLine(x, START.getY(), x, -height);
        }

        //  draw  coordinates on X
        for (double x = START.getX(); x < width; x+= numberIncrement) {
            drawNumberPoint(x, START.getY(), String.format("%d", (int) x));
            drawNumberPoint(-x, START.getY(), String.format("%d", (int) -x));
        }

        //  draw  coordinates on Y
        for (double y = START.getY(); y < height; y+= numberIncrement) {
            drawNumberPoint(START.getX(), y, String.format("%d", (int) y));
            drawNumberPoint(START.getY(), -y, String.format("%d", (int) -y));
        }
    }

    private void drawCoordinateLine(double fromX, double fromY, double toX, double toY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.beginPath();
        gc.setLineWidth(LINE_WIDTH);
        gc.moveTo(fromX, fromY);
        gc.lineTo(toX, toY);
        gc.stroke();
    }

    private void drawNumberPoint(double x, double y, String text) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.beginPath();
        int radius = 2;
        int diameter = radius * 2;

        gc.fillOval(x - radius, y - radius, diameter, diameter);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                text,
                x,
                y  + 15
        );
    }


    public void setOrigin(Constants.OriginType originType) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        if(originType == Constants.OriginType.CENTER) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Affine affine = new Affine();
            affine.appendTranslation(width * .5, height * .5);
            gc.setTransform(affine);
        }
    }

    public boolean isOriginCenter(){
        return coordinateSystemProperties.getSelectedItem() == Constants.OriginType.CENTER;
    }

    public Constants.OriginType getOriginType(){
        return coordinateSystemProperties.getSelectedItem();
    }
}
