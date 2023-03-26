package com.mtanevski.math2d.gui.canvas.point;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.utils.FormatUtil;
import com.mtanevski.math2d.gui.utils.TextFormatters;
import com.mtanevski.math2d.math.Point2D;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;

public class PointProperties {
    @FXML
    public TextField xField;
    @FXML
    public TextField yField;

    private GridPane gridPane;
    private final ObjectProperty<Point2D> point2DProperty;

    public PointProperties(ObjectProperty<Point2D> point2DProperty) {
        this.point2DProperty = point2DProperty;
        try {
            var resource = PointProperties.class.getResource(Constants.Resources.POINT_2D_PROPERTIES_FXML);

            var loader = new FXMLLoader(resource);
            loader.setLocation(resource);
            loader.setController(this);
            gridPane = loader.load();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
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
                var point2D = point2DProperty.get();
                return Point2D.of(FormatUtil.parseDouble(s), point2D.y);
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
                var point2D = point2DProperty.get();
                return Point2D.of(point2D.x, FormatUtil.parseDouble(s));
            }
        });
    }

    public GridPane getPane() {
        return gridPane;
    }
}
