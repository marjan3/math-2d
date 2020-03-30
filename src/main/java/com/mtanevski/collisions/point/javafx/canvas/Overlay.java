package com.mtanevski.collisions.point.javafx.canvas;

import com.mtanevski.collisions.point.javafx.Constants;
import com.mtanevski.collisions.point.javafx.canvas.point2d.DrawablePoint2D;
import com.mtanevski.collisions.point.javafx.canvas.vector2d.Vector2DGraphic;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

import java.util.HashMap;
import java.util.Map;

public class Overlay {

    public static Pane pane;
    public static Map<String, Vector2DGraphic> vectors = new HashMap<>();

    public static void buildPane() {
        pane = new Pane();
        pane.setId("overlay");

        AnchorPane.setTopAnchor(pane, Constants.Canvas.PADDING);
        AnchorPane.setLeftAnchor(pane, Constants.Canvas.PADDING);
        AnchorPane.setRightAnchor(pane, Constants.Canvas.PADDING);
        AnchorPane.setBottomAnchor(pane, Constants.Canvas.PADDING);
    }

    public static void setOrigin(Constants.OriginType originType, double width, double height) {
        if (originType == Constants.OriginType.CENTER) {
            pane.getTransforms().clear();
            pane.getTransforms().add(new Translate(width / 2, height / 2));
        } else {
            pane.getTransforms().clear();
            pane.getTransforms().add(new Translate(0, 0));
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

    public static void add(Vector2DGraphic vector2DGraphic) {
        vectors.put(vector2DGraphic.label.getText(), vector2DGraphic);
    }

    public static void remove(Vector2DGraphic vector2DGraphic) {
        vectors.remove(vector2DGraphic.label.getText());
    }

}
