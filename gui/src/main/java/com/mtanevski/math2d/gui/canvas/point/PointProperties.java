package com.mtanevski.math2d.gui.canvas.point;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.vector.VectorProperties;
import com.mtanevski.math2d.gui.commands.CommandsManager;
import com.mtanevski.math2d.gui.commands.MovePointCommand;
import com.mtanevski.math2d.gui.commands.MoveVectorCommand;
import com.mtanevski.math2d.gui.utils.FormatUtil;
import com.mtanevski.math2d.gui.utils.TextFormatters;
import com.mtanevski.math2d.math.Point2D;
import com.mtanevski.math2d.math.Vector2D;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;

public class PointProperties {
    private final VBox pane;
    private final ObjectProperty<Point2D> point2DProperty;
    private final StringProperty nameProperty;
    private final DrawablePoint drawablePoint;

    @FXML
    public TextField xField;
    @FXML
    public TextField yField;
    @FXML
    public Label nameLabel;

    public PointProperties(DrawablePoint drawablePoint, ObjectProperty<Point2D> point2DProperty, StringProperty nameProperty) {
        this.drawablePoint = drawablePoint;
        this.point2DProperty = point2DProperty;
        this.nameProperty = nameProperty;
        try {
            var resource = PointProperties.class.getResource(Constants.Resources.POINT_2D_PROPERTIES_FXML);
            var loader = new FXMLLoader(resource);
            loader.setLocation(resource);
            loader.setController(this);
            pane = loader.load();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    public void initialize() {
        // title
        Bindings.bindBidirectional(nameLabel.textProperty(), nameProperty);
        // x
        xField.setText(String.valueOf(point2DProperty.get().x));
        xField.setTextFormatter(TextFormatters.newDoubleFormatter());
        Bindings.bindBidirectional(xField.textProperty(), point2DProperty, new StringConverter<>() {
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
        xField.focusedProperty().addListener(new FocusPropertyChangeListener());

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
        yField.focusedProperty().addListener(new FocusPropertyChangeListener());
    }

    public VBox getPane() {
        return pane;
    }

    private class FocusPropertyChangeListener implements ChangeListener<Boolean> {
        Point2D previousLocation;
        @Override
        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
        {
            if (newPropertyValue) {
                previousLocation = point2DProperty.get();
            } else {
                CommandsManager.execute(new MovePointCommand(drawablePoint, previousLocation, point2DProperty.get()));
            }
        }
    }
}
