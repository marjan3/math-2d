package com.mtanevski.collisions.point.gui.canvas;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.Map;

public class EditablePropertiesPane extends GridPane {

    public EditablePropertiesPane(Map<String, Control> properties) {
        ColumnConstraints firstColumnConstraints = new ColumnConstraints();
        firstColumnConstraints.setHgrow(Priority.SOMETIMES);
        firstColumnConstraints.setMinWidth(10.0);
        firstColumnConstraints.setPrefWidth(100.0);
        firstColumnConstraints.setPercentWidth(30.0);
        ColumnConstraints secondColumnnConstraints = new ColumnConstraints();
        secondColumnnConstraints.setHgrow(Priority.SOMETIMES);
        secondColumnnConstraints.setMinWidth(10.0);
        secondColumnnConstraints.setPrefWidth(100.0);
        secondColumnnConstraints.setPercentWidth(70.0);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(Priority.SOMETIMES);
        this.getColumnConstraints().addAll(firstColumnConstraints, secondColumnnConstraints);
        this.getRowConstraints().add(rowConstraints);
        Insets margins = new Insets(5.0, 0.0, 5.0, 0.0);

        int counter = 0;
        for (Map.Entry<String, Control> property : properties.entrySet()) {

            Label label = new Label(property.getKey());
            GridPane.setMargin(label, margins);
            GridPane.setMargin(property.getValue(), margins);
            this.add(label, 0, counter);
            this.add(property.getValue(), 1, counter);
            counter++;
        }
    }
}
