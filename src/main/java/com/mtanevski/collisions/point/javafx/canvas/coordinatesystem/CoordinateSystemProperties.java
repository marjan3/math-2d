package com.mtanevski.collisions.point.javafx.canvas.coordinatesystem;

import com.mtanevski.collisions.point.javafx.Constants;
import com.mtanevski.collisions.point.javafx.canvas.point2d.DrawablePoint2DProperties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

public class CoordinateSystemProperties {

    private GridPane gridPane;

    @FXML
    private ChoiceBox<Constants.OriginType> originType;

    public CoordinateSystemProperties() {
        try {
            URL resource = DrawablePoint2DProperties.class.getClassLoader().getResource("coordinatesystem/properties.fxml");

            FXMLLoader loader = new FXMLLoader(resource);
            loader.setController(this);
            gridPane = loader.load();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // initialize choicebox
        originType.getItems().addAll(Constants.OriginType.values());
        originType.getSelectionModel().select(Constants.OriginType.CENTER);
    }

    public Constants.OriginType getSelectedItem(){
        return originType.getSelectionModel().getSelectedItem();
    }

    public GridPane getPane() {
        return gridPane;
    }

    public void setOnAction(Runnable runnable) {
        originType.setOnAction(event -> runnable.run());
    }
}
