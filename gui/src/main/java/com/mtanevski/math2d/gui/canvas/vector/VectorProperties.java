package com.mtanevski.math2d.gui.canvas.vector;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.utils.FormatUtil;
import com.mtanevski.math2d.gui.utils.ReadOnlyStringConverter;
import com.mtanevski.math2d.gui.utils.TextFormatters;
import com.mtanevski.math2d.math.Vector2D;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;

public class VectorProperties {

    @FXML
    public Label nameLabel;
    @FXML
    public TextField xField;
    @FXML
    public TextField yField;
    @FXML
    public TextField squaredLengthField;
    @FXML
    public TextField unitVectorField;
    @FXML
    public TextField categoryField;
    @FXML
    public TextField slopeField;
    @FXML
    public TextField angleField;
    @FXML
    public TextField multiplyFactorField;
    @FXML
    public TextField divisionFactorField;
    @FXML
    public TextField addXField;
    @FXML
    public TextField addYField;
    @FXML
    public TextField subtractXField;
    @FXML
    public TextField subtractYField;
    @FXML
    public TextField lengthField;
    @FXML
    public TextField rotationAngleField;

    private final VBox root;
    private final ObjectProperty<Vector2D> vector2DProperty;
    private final StringProperty nameProperty;

    public VectorProperties(ObjectProperty<Vector2D> vector2DProperty, StringProperty nameProperty) {
        this.vector2DProperty = vector2DProperty;
        this.nameProperty = nameProperty;
        try {
            var resource = VectorProperties.class.getResource(Constants.Resources.VECTOR_2D_PROPERTIES_FXML);

            var loader = new FXMLLoader(resource);
            loader.setController(this);
            root = loader.load();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void setVector2DProperty(Vector2D vector2D) {
        this.vector2DProperty.set(vector2D);
    }

    @FXML
    public void initialize() {
        // title
        Bindings.bindBidirectional(nameLabel.textProperty(), nameProperty);
        // x
        xField.setText(String.valueOf(vector2DProperty.get().x));
        xField.setTextFormatter(TextFormatters.newDoubleFormatter());
        Bindings.bindBidirectional(xField.textProperty(), vector2DProperty, new StringConverter<>() {
            @Override
            public String toString(Vector2D vector2D) {
                return String.valueOf(vector2D.x);
            }

            @Override
            public Vector2D fromString(String s) {
                var vector2D = vector2DProperty.get();
                return Vector2D.of(FormatUtil.parseDouble(s), vector2D.y);
            }
        });

        // y
        yField.setTextFormatter(TextFormatters.newDoubleFormatter());
        yField.setText(String.valueOf(vector2DProperty.get().y));
        Bindings.bindBidirectional(yField.textProperty(), vector2DProperty, new StringConverter<>() {
            @Override
            public String toString(Vector2D vector2D) {
                return String.valueOf(vector2D.y);
            }

            @Override
            public Vector2D fromString(String s) {
                var vector2D = vector2DProperty.get();
                return Vector2D.of(vector2D.x, FormatUtil.parseDouble(s));
            }
        });

        // Length
        lengthField.setText(String.valueOf(vector2DProperty.get().getLength()));
        lengthField.textProperty().bindBidirectional(vector2DProperty, new ReadOnlyStringConverter<>() {
            @Override
            public String toString(Vector2D vector2D) {
                return String.valueOf(vector2D.getLength());
            }
        });


        // Squared Length
        squaredLengthField.setText(String.valueOf(vector2DProperty.get().getSquaredLength()));
        squaredLengthField.textProperty().bindBidirectional(vector2DProperty, new ReadOnlyStringConverter<>() {
            @Override
            public String toString(Vector2D vector2D) {
                return String.valueOf(vector2D.getSquaredLength());
            }
        });


        // Unit
        unitVectorField.setText(vector2DProperty.get().getUnit().x + "," + vector2DProperty.get().getUnit().y);
        unitVectorField.textProperty().bindBidirectional(vector2DProperty, new ReadOnlyStringConverter<>() {
            @Override
            public String toString(Vector2D vector2D) {
                Vector2D u = vector2D.getUnit();
                return u.x + "," + u.y;
            }
        });

        // category
        categoryField.setText(vector2DProperty.get().getCategory());
        categoryField.textProperty().bindBidirectional(vector2DProperty, new ReadOnlyStringConverter<>() {
            @Override
            public String toString(Vector2D vector2D) {
                return vector2D.getCategory();
            }
        });

        // slope
        slopeField.setText(String.valueOf(vector2DProperty.get().getSlope()));
        slopeField.textProperty().bindBidirectional(vector2DProperty, new ReadOnlyStringConverter<>() {
            @Override
            public String toString(Vector2D vector2D) {
                return String.valueOf(vector2D.getSlope());
            }
        });

        //angle
        angleField.setText(String.valueOf(Math.toDegrees(vector2DProperty.get().getAngle())));
        angleField.textProperty().bindBidirectional(vector2DProperty, new ReadOnlyStringConverter<>() {
            @Override
            public String toString(Vector2D vector2D) {
                return String.valueOf(Math.toDegrees(vector2D.getAngle()));
            }
        });

        // multiply
        multiplyFactorField.setTextFormatter(TextFormatters.newDoubleFormatter());

        // divide
        divisionFactorField.setTextFormatter(TextFormatters.newDoubleFormatter());

        // add
        this.addXField.setTextFormatter(TextFormatters.newDoubleFormatter());
        this.addYField.setTextFormatter(TextFormatters.newDoubleFormatter());

        // subtract
        this.subtractXField.setTextFormatter(TextFormatters.newDoubleFormatter());
        this.subtractYField.setTextFormatter(TextFormatters.newDoubleFormatter());
    }

    public void negate(ActionEvent actionEvent) {
        var vector2D = this.vector2DProperty.get().clone();
        vector2D.negate();
        this.vector2DProperty.set(vector2D);
    }

    public void multiply(ActionEvent actionEvent) {
        var vector2D = this.vector2DProperty.get().clone();
        double scalar = Double.parseDouble(this.multiplyFactorField.getText());
        vector2D.multiply(scalar);
        this.vector2DProperty.set(vector2D);
    }

    public void divide(ActionEvent actionEvent) {
        var vector2D = this.vector2DProperty.get().clone();
        double scalar = Double.parseDouble(this.divisionFactorField.getText());
        vector2D.divide(scalar);
        this.vector2DProperty.set(vector2D);

    }

    public void add(ActionEvent actionEvent) {
        double x = Double.parseDouble(this.addXField.getText());
        double y = Double.parseDouble(this.addYField.getText());
        var clone = this.vector2DProperty.get().clone();
        clone.add(x, y);
        this.vector2DProperty.set(clone);

    }

    public void subtract(ActionEvent actionEvent) {
        double x = Double.parseDouble(this.subtractXField.getText());
        double y = Double.parseDouble(this.subtractYField.getText());
        var clone = this.vector2DProperty.get().clone();
        clone.subtract(x, y);
        this.vector2DProperty.set(clone);

    }

    public void normalize(ActionEvent actionEvent) {
        this.vector2DProperty.set(this.vector2DProperty.get().getUnit());
    }

    public void rotate(ActionEvent actionEvent) {
        String text = this.rotationAngleField.getText();
        double radians = Math.toRadians(Double.parseDouble(text));
        var rotatedVector = this.vector2DProperty.get().getRotation(radians);
        this.vector2DProperty.set(rotatedVector);
    }

    public VBox getPane() {
        return root;
    }
}
