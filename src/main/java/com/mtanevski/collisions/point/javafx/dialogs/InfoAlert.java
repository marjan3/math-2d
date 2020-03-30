package com.mtanevski.collisions.point.javafx.dialogs;

import javafx.scene.control.Alert;

public class InfoAlert extends Alert {

    private InfoAlert(String title, String information) {
        super(AlertType.INFORMATION);
        super.setTitle("Information Dialog");
        super.setHeaderText(null);
        super.setHeaderText(title);
        super.setContentText(information);

        super.showAndWait();
    }

    public static void alert(String title, String information) {
        new InfoAlert(title, information);
    }
}
