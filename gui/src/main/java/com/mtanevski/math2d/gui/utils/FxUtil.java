package com.mtanevski.math2d.gui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@UtilityClass
public class FxUtil {

    public static Node loadUtilWindow(String location, Object controller, Window window) {
        var fxmlLoader = new FXMLLoader(FxUtil.class.getClassLoader().getResource(location));
        if (controller != null) {
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

    public static void switchNode(Scene scene, Node node, String selector) {
        var propertiesPane = (VBox) scene.lookup(selector);
        propertiesPane.getChildren().clear();
        propertiesPane.getChildren().addAll(node);
    }


    public static Image createImage(Object context, String resourceName) {
        var url = context.getClass().getClassLoader().getResource(resourceName);
        Objects.requireNonNull(url);
        return new Image(url.toExternalForm());
    }

    public static void debug(Node node) {
        node.setStyle("-fx-background-color: red");
    }
}
