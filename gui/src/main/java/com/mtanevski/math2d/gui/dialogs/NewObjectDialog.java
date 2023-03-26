package com.mtanevski.math2d.gui.dialogs;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.EditablePropertiesPane;
import com.mtanevski.math2d.math.Vector2D;
import javafx.scene.control.*;

import java.util.*;
import java.util.stream.Collectors;

public class NewObjectDialog extends Dialog {

    private NewObjectDialog(String title, Map<String, Control> properties) {
        setTitle(title);
        // Set the button types.
        ButtonType okButtonType = new ButtonType(Constants.Labels.OK, ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the xField and yField labels and fields.
        var grid = new EditablePropertiesPane(properties);

        // Enable/Disable login button depending on whether a xField was entered.
        var okButton = this.getDialogPane().lookupButton(okButtonType);

        // Do some validation (using the Java 8 lambda syntax).
        properties.forEach((key, value) -> ((TextField) value).textProperty().addListener((observable, oldValue, newValue) -> okButton.setDisable(newValue.trim().isEmpty())));

        this.getDialogPane().setContent(grid);

//        // Request focus on the xField field by default.
//        Platform.runLater(firstTextField::requestFocus);

        // Convert the result to a xField-yField-pair when the login button is clicked.
        this.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return properties.values().stream().map(control -> ((TextField) control).getText())
                        .collect(Collectors.toList());
            }
            return new ArrayList<>();
        });
    }

    private List<String> showAndAwait() {
        Optional<List<String>> result = this.showAndWait();
        return result.orElse(new ArrayList<>());
    }

    public static NewObjectDialogResult showNewVector2DDialog(String title) {
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

        NewObjectDialog dialog = new NewObjectDialog(title, properties);
        List<String> result = dialog.showAndAwait();

        NewObjectDialogResult newDialogResult = new NewObjectDialogResult();
        newDialogResult.label = String.valueOf(result.get(0));
        newDialogResult.x = Double.parseDouble(result.get(1));
        newDialogResult.y = Double.parseDouble(result.get(2));
        return newDialogResult;
    }

    public static NewObjectDialogResult showResultingVector2DDialog(String title, String label, Vector2D vector2D) {
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

        NewObjectDialog dialog = new NewObjectDialog(title, properties);
        List<String> result = dialog.showAndAwait();

        NewObjectDialogResult newDialogResult = new NewObjectDialogResult();
        newDialogResult.label = String.valueOf(result.get(0));
        newDialogResult.x = Double.parseDouble(result.get(1));
        newDialogResult.y = Double.parseDouble(result.get(2));
        return newDialogResult;
    }

}
