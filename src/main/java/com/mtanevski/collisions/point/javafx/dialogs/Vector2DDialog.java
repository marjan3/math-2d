package com.mtanevski.collisions.point.javafx.dialogs;

import com.mtanevski.collisions.point.javafx.canvas.EditablePropertiesPane;
import com.mtanevski.collisions.point.lib.Vector2D;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.*;
import java.util.stream.Collectors;

public class Vector2DDialog extends Dialog {

    private Vector2DDialog(String title, Map<String, Control> properties) {
        setTitle(title);
        // Set the button types.
        ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the xField and yField labels and fields.
        EditablePropertiesPane grid = new EditablePropertiesPane(properties);

        // Enable/Disable login button depending on whether a xField was entered.
        Node okButton = this.getDialogPane().lookupButton(okButtonType);

        // Do some validation (using the Java 8 lambda syntax).
        properties.forEach((key, value) -> ((TextField) value).textProperty().addListener((observable, oldValue, newValue) -> okButton.setDisable(newValue.trim().isEmpty())));

        this.getDialogPane().setContent(grid);

//        // Request focus on the xField field by default.
//        Platform.runLater(firstTextField::requestFocus);

        // Convert the result to a xField-yField-pair when the login button is clicked.
        this.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return properties.entrySet().stream().map(entry -> ((TextField) entry.getValue()).getText()).collect(Collectors.toList());
            }
            return new ArrayList<>();
        });
    }

    private List<String> showAndAwait() {
        Optional<List<String>> result = this.showAndWait();
        return result.orElse(new ArrayList<>());
    }

    public static NewDialogResult showNewVector2DDialog(String title) {
        Map<String, Control> properties = new LinkedHashMap<>();
        TextField labelField = new TextField();
        labelField.setText("A");
        properties.put("Label", labelField);
        TextField xField = new TextField();
        xField.setText("100");
        properties.put("x", xField);
        TextField yField = new TextField();
        yField.setText("100");
        properties.put("y", yField);

        Vector2DDialog dialog = new Vector2DDialog(title, properties);
        List<String> result = dialog.showAndAwait();

        NewDialogResult newDialogResult = new NewDialogResult();
        newDialogResult.label = String.valueOf(result.get(0));
        newDialogResult.x = Double.valueOf(result.get(1));
        newDialogResult.y = Double.valueOf(result.get(2));
        return newDialogResult;
    }

    public static NewDialogResult showResultingVector2DDialog(String title, String label, Vector2D vector2D) {
        Map<String, Control> properties = new LinkedHashMap<>();
        TextField labelField = new TextField();
        labelField.setText(label);
        properties.put("Label", labelField);
        TextField xField = new TextField();
        xField.setText(String.valueOf(vector2D.x));
        xField.setDisable(true);
        properties.put("x", xField);
        TextField yField = new TextField();
        yField.setDisable(true);
        yField.setText(String.valueOf(vector2D.y));
        properties.put("y", yField);

        Vector2DDialog dialog = new Vector2DDialog(title, properties);
        List<String> result = dialog.showAndAwait();

        NewDialogResult newDialogResult = new NewDialogResult();
        newDialogResult.label = String.valueOf(result.get(0));
        newDialogResult.x = Double.valueOf(result.get(1));
        newDialogResult.y = Double.valueOf(result.get(2));
        return newDialogResult;
    }

}
