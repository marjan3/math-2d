package com.mtanevski.collisions.point.gui.canvas.coordinatesystem;

import com.mtanevski.collisions.point.gui.Constants;
import com.mtanevski.collisions.point.gui.MainEntry;
import com.mtanevski.collisions.point.gui.canvas.point2d.DrawablePoint2DProperties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

public class CoordinateSystemProperties {

    @FXML
    public ChoiceBox<Constants.OriginType> originType;

    private GridPane gridPane;

    public CoordinateSystemProperties() {
        try {
            URL resource = MainEntry.class.getResource("/coordinatesystem/properties.fxml");

            FXMLLoader loader = new FXMLLoader(resource);
            loader.setController(this);
            gridPane = loader.load();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
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
