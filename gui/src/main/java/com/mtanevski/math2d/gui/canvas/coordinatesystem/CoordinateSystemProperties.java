package com.mtanevski.math2d.gui.canvas.coordinatesystem;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.MainEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class CoordinateSystemProperties {

    @FXML
    public ChoiceBox<Constants.OriginType> originType;

    private GridPane gridPane;

    public CoordinateSystemProperties() {
        try {
            var resource = MainEntry.class.getResource(Constants.Resources.COORDINATESYSTEM_PROPERTIES_FXML);
            var loader = new FXMLLoader(resource);
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
