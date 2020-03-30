package com.mtanevski.collisions.point.javafx.canvas.point2d;

import com.mtanevski.collisions.point.javafx.utils.TextFormatters;
import com.mtanevski.collisions.point.lib.Point2D;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;

public class DrawablePoint2DProperties {
    private final ObjectProperty<Point2D> point2DProperty;
    @FXML
    public TextField xField;
    @FXML
    public TextField yField;
    private GridPane gridPane;

    public DrawablePoint2DProperties(ObjectProperty<Point2D> point2DProperty) {
        this.point2DProperty = point2DProperty;
        try {
            URL resource = DrawablePoint2DProperties.class.getClassLoader().getResource("point2d/properties.fxml");

            FXMLLoader loader = new FXMLLoader(resource);
            loader.setController(this);
            gridPane = loader.load();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void setProperty(Point2D point2D) {
        this.point2DProperty.set(point2D);
    }

    @FXML
    public void initialize() {
        // x
        xField.setText(String.valueOf(point2DProperty.get().x));
        xField.setTextFormatter(TextFormatters.newDoubleFormatter());
        Bindings.bindBidirectional(xField.textProperty(), point2DProperty, new StringConverter<Point2D>() {
            @Override
            public String toString(Point2D point2D) {
                return String.valueOf(point2D.x);
            }

            @Override
            public Point2D fromString(String s) {
                Point2D point2D = point2DProperty.get();
                double x = Double.parseDouble(s);
                return Point2D.of(x, point2D.y);
            }
        });

        // y
        yField.setTextFormatter(TextFormatters.newDoubleFormatter());
        yField.setText(String.valueOf(point2DProperty.get().y));
        Bindings.bindBidirectional(yField.textProperty(), point2DProperty, new StringConverter<>() {
            @Override
            public String toString(Point2D point2D) {
                return String.valueOf(point2D.y);
            }

            @Override
            public Point2D fromString(String s) {
                Point2D point2D = point2DProperty.get();
                double y = Double.parseDouble(s);
                return Point2D.of(point2D.x, y);
            }
        });
    }

    public GridPane getPane() {
        return gridPane;
    }
}
