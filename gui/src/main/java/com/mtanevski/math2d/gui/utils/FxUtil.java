package com.mtanevski.math2d.gui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class FxUtil {

    public static Node loadUtilWindow(String location, Object controller, Window window) {
        var fxmlLoader = new FXMLLoader(FxUtil.class.getClassLoader().getResource(location));
        if(controller != null) {
            fxmlLoader.setController(controller);
        }

        try {
            final Parent load = fxmlLoader.load();
            var scene = new Scene(load);
            var stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(window);
            stage.setScene(scene);
            stage.show();

            return load;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
