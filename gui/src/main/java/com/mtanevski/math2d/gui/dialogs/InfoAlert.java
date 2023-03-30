package com.mtanevski.math2d.gui.dialogs;

import com.mtanevski.math2d.gui.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class InfoAlert extends Alert {

    private InfoAlert(String title, String information, String text) {
        super(AlertType.INFORMATION);
        super.setTitle(information);
        super.setHeaderText(null);
        super.setHeaderText(title);
        var pane = new StackPane();
        var textField = new TextField();
        textField.setStyle(Constants.Styles.ALERT_TEXT_FIELD_STYLE);
        textField.setEditable(false);
        textField.setText(text);
        pane.getChildren().add(textField);
        super.getDialogPane().setContent(pane);
        super.showAndWait();
    }

    public static void alert(String title, String information, String text) {
        new InfoAlert(title, information, text);
    }
}
