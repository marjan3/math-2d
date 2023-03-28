package com.mtanevski.math2d.gui.dialogs;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.EditablePropertiesPane;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

import static com.mtanevski.math2d.gui.Constants.Resources.ARROW_DOWN_RIGHT_PNG;
import static com.mtanevski.math2d.gui.Constants.Resources.SMALL_CIRCLE_PNG;
import static com.mtanevski.math2d.gui.utils.FxUtil.createImage;

public class SimpleDialog extends Dialog<List<String>> {

    public static NewObjectDialogResult showXYDialog(String title) {
        return SimpleDialog.showXYDialog(title, null, null, null);
    }

    public static NewObjectDialogResult showNameDialog(String title) {
        var properties = new LinkedHashMap<String, Control>();
        var nameField = new TextField();
        nameField.setText(Constants.Placeholders.DEFAULT_NAME);
        properties.put(Constants.Labels.NAME, nameField);

        var dialog = new SimpleDialog(title, properties);
        var result = dialog.showAndAwait();

        var newDialogResult = new NewObjectDialogResult();
        if(result.isEmpty()){
            throw new IllegalArgumentException("User cancelled the XY dialog");
        }
        newDialogResult.name = String.valueOf(result.get(0));
        return newDialogResult;
    }

    public static NewObjectDialogResult showXYDialog(String title, String name, Double x, Double y) {
        var properties = new LinkedHashMap<String, Control>();
        var nameField = new TextField();
        nameField.setText((Optional.ofNullable(name).orElse(Constants.Placeholders.DEFAULT_NAME)));
        properties.put(Constants.Labels.NAME, nameField);
        var xField = new TextField();
        xField.setText(Optional.ofNullable(x).map(String::valueOf).orElse(Constants.Placeholders.DEFAULT_X));
        properties.put(Constants.Labels.X, xField);
        var yField = new TextField();
        yField.setText(Constants.Placeholders.DEFAULT_Y);
        yField.setText(Optional.ofNullable(y).map(String::valueOf).orElse(Constants.Placeholders.DEFAULT_Y));
        properties.put(Constants.Labels.Y, yField);

        var dialog = new SimpleDialog(title, properties);

        var stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(createImage(dialog, title.contains("Point") ? SMALL_CIRCLE_PNG : ARROW_DOWN_RIGHT_PNG));
        var result = dialog.showAndAwait();

        var newDialogResult = new NewObjectDialogResult();
        if(result.isEmpty()){
            throw new IllegalArgumentException("User cancelled the XY dialog");
        }
        newDialogResult.name = String.valueOf(result.get(0));
        newDialogResult.x = Double.parseDouble(result.get(1));
        newDialogResult.y = Double.parseDouble(result.get(2));
        return newDialogResult;
    }

    private SimpleDialog(String title, Map<String, Control> properties) {
        setTitle(title);
        // Set the button types.
        ButtonType okButtonType = new ButtonType(Constants.Labels.OK, ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the xField and yField labels and fields.
        var grid = new EditablePropertiesPane(properties);

        // Enable/Disable login button depending on whether a xField was entered.
        var okButton = this.getDialogPane().lookupButton(okButtonType);

        // Do some validation (using the Java 8 lambda syntax).
        properties.forEach((key, value) ->
                ((TextField) value).textProperty().addListener((observable, oldValue, newValue) ->
                        okButton.setDisable(newValue.trim().isEmpty())));

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

}
