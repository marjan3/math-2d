package com.mtanevski.math2d.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class MainEntry extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // identify main component
        // construct it
        URL resource = MainEntry.class.getClassLoader().getResource(Constants.Resources.INDEX_FXML);
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setController(new MainController());
        Pane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(Constants.Labels.TITLE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}