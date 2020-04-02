package com.mtanevski.collisions.point.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainEntry extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // identify main component
        // construct it
        FXMLLoader loader = new FXMLLoader(MainEntry.class.getClassLoader().getResource("index.fxml"));
        loader.setController(new MainController());
        Pane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Canvas");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}