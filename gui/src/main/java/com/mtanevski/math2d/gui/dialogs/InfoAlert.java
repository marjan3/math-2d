package com.mtanevski.math2d.gui.dialogs;

import com.mtanevski.math2d.gui.Constants;
import javafx.scene.control.Alert;

public class InfoAlert extends Alert {

    private InfoAlert(String title, String information) {
        super(AlertType.INFORMATION);
        super.setTitle(Constants.Labels.INFORMATION_DIALOG);
        super.setHeaderText(null);
        super.setHeaderText(title);
        super.setContentText(information);
        super.showAndWait();
    }

    public static void alert(String title, String information) {
        new InfoAlert(title, information);
    }
}
