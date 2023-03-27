package com.mtanevski.math2d.gui.canvas;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.point.DrawablePoint;
import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import com.mtanevski.math2d.math.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Overlay {

    public static final String ID = "overlay";
    public static Pane pane;
    public static Map<String, DrawableVector> vectors = new HashMap<>();
    private static Map<String, DrawablePoint> points = new HashMap<>();
    private static Rectangle selectionRectangle;
    private static Translate translate;
    private static Point2D selectionStart;
    private static Point2D selectionEnd;

    public static void buildPane() {
        pane = new Pane();
        pane.setId(ID);

        AnchorPane.setTopAnchor(pane, Constants.Canvas.PADDING);
        AnchorPane.setLeftAnchor(pane, Constants.Canvas.PADDING);
        AnchorPane.setRightAnchor(pane, Constants.Canvas.PADDING);
        AnchorPane.setBottomAnchor(pane, Constants.Canvas.PADDING);

        pane.setOnMousePressed((MouseEvent event) -> {
            selectionStart = Point2D.of(event.getX() + translate.getX(), event.getY() + translate.getY());
        });

        pane.setOnMouseDragged((MouseEvent event) -> {
            selectionEnd = Point2D.of(event.getX() + translate.getX(), event.getY() + translate.getY());
            Overlay.redrawDragSquare(selectionStart, selectionEnd);
        });

        pane.setOnMouseReleased((MouseEvent event) -> {
            Overlay.removeDragSquare();
        });
    }

    public static void setOrigin(Constants.Origin origin, double width, double height) {
        if (origin == Constants.Origin.CENTER) {
            pane.getTransforms().clear();
            translate = new Translate(width / 2, height / 2);
            pane.getTransforms().add(translate);
        } else {
            pane.getTransforms().clear();
            translate = new Translate(0, 0);
            pane.getTransforms().add(translate);
        }

    }

    public static void redrawDragSquare(Point2D startPoint, Point2D endPoint) {
        removeDragSquare();

        selectionStart = startPoint;
        selectionEnd = endPoint;
        double width = endPoint.x - startPoint.x;
        double height = endPoint.y - startPoint.y;
        double startPointX = startPoint.x;
        double startPointY = startPoint.y;
        if(width < 0){
            startPointX = startPointX + width;
            width = Math.abs(width);
        }
        if(height < 0){
            startPointY = startPointY + height;
            height = Math.abs(height);
        }
        selectionRectangle = new Rectangle(startPointX, startPointY, width, height);
        selectionRectangle.setTranslateX(-translate.getX());
        selectionRectangle.setTranslateY(-translate.getY());
        selectionRectangle.setStrokeWidth(2.0);
        selectionRectangle.setStroke(Paint.valueOf("rgba(36, 108, 179, 1)"));
        selectionRectangle.setFill(Paint.valueOf("rgba(36, 108, 179, 0.51)"));
        pane.getChildren().addAll(selectionRectangle);
    }

    public static void removeDragSquare() {
        if(selectionRectangle != null) {
            pane.getChildren().removeAll(selectionRectangle);
        }
    }

    public static void deselectAll() {
        Stream.of(pane.getChildren()).forEach(n -> {
            n.forEach(node -> {
            if(node instanceof Polyline polyline) {
                polyline.setStroke(Constants.Colors.OBJECT);
            } else if(node instanceof Circle circle) {
                circle.setStroke(Constants.Colors.OBJECT);
            }
            });
        });
    }

    public static void drawPoint(DrawablePoint drawablePoint) {
        points.put(drawablePoint.label.getText(), drawablePoint);
        pane.getChildren().addAll(drawablePoint.getChildren());
    }

    public static void drawVector(DrawableVector drawableVector) {
        vectors.put(drawableVector.label.getText(), drawableVector);
        pane.getChildren().addAll(drawableVector.getChildren());
    }

    public static void remove(DrawableVector drawableVector) {
        vectors.remove(drawableVector.label.getText());
        pane.getChildren().removeAll(drawableVector.getChildren());
    }

    public static void remove(DrawablePoint drawablePoint) {
        points.remove(drawablePoint.label.getText());
        pane.getChildren().removeAll(drawablePoint.getChildren());
    }
    public static Scene getScene() {
        return pane.getScene();
    }


}
