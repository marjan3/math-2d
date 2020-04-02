package com.mtanevski.collisions.point.gui.canvas;

import com.mtanevski.collisions.point.gui.Constants;
import com.mtanevski.collisions.point.gui.canvas.point2d.DrawablePoint2D;
import com.mtanevski.collisions.point.gui.canvas.vector2d.Vector2DGraphic;
import com.mtanevski.collisions.point.math.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.HashMap;
import java.util.Map;

public class Overlay {

    public static final String ID = "overlay";
    public static Pane pane;
    public static Map<String, Vector2DGraphic> vectors = new HashMap<>();
    private static Rectangle selectionRectangle;
    private static Translate translate;
    private static Point2D selectionStart;
    private static Point2D selectionEnd;
    public static boolean selectionEnabled = true;

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

    public static void setOrigin(Constants.OriginType originType, double width, double height) {
        if (originType == Constants.OriginType.CENTER) {
            pane.getTransforms().clear();
            translate = new Translate(width / 2, height / 2);
            pane.getTransforms().add(translate);
        } else {
            pane.getTransforms().clear();
            translate = new Translate(0, 0);
            pane.getTransforms().add(translate);
        }

    }

    public static Vector2DGraphic drawVector2D() {

        Vector2DGraphic vector2DGraphic = new Vector2DGraphic();
        Overlay.add(vector2DGraphic);
        pane.getChildren().addAll(vector2DGraphic.getChildren());
        return vector2DGraphic;
    }

    public static DrawablePoint2D drawPoint2D() {

        DrawablePoint2D drawablePoint2D = new DrawablePoint2D();
        pane.getChildren().addAll(drawablePoint2D.getChildren());
        return drawablePoint2D;
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

    public static void add(Vector2DGraphic vector2DGraphic) {
        vectors.put(vector2DGraphic.label.getText(), vector2DGraphic);
    }

    public static void remove(Vector2DGraphic vector2DGraphic) {
        vectors.remove(vector2DGraphic.label.getText());
    }

}
