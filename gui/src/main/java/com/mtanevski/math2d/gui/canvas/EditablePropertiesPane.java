package com.mtanevski.math2d.gui.canvas;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.Map;

public class EditablePropertiesPane extends GridPane {

    private static final double WIDTH = 90.0;
    private static final double MIN_WIDTH = 10.0;
    private static final double MIN_HEIGHT = 10.0;
    private static final double HEIGHT = 30.0;
    private static final int VGAP = 5;
    private static final Insets PADDING = new Insets(10, 10, 10, 10);
    private static final Insets MARGINS = new Insets(10.0, 10.0, 10.0, 10.0);

    public EditablePropertiesPane(Map<String, Control> properties) {
        var firstColumnConstraints = new ColumnConstraints();
        firstColumnConstraints.setHgrow(Priority.SOMETIMES);
        firstColumnConstraints.setMinWidth(MIN_WIDTH);
        firstColumnConstraints.setPrefWidth(WIDTH);
        var secondColumnnConstraints = new ColumnConstraints();
        secondColumnnConstraints.setHgrow(Priority.SOMETIMES);
        secondColumnnConstraints.setMinWidth(MIN_WIDTH);
        secondColumnnConstraints.setPrefWidth(WIDTH);

        var rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(MIN_HEIGHT);
        rowConstraints.setPrefHeight(HEIGHT);
        rowConstraints.setVgrow(Priority.SOMETIMES);

        this.setVgap(VGAP);
        this.setPadding(PADDING);
        this.getColumnConstraints().addAll(firstColumnConstraints, secondColumnnConstraints);
        this.getRowConstraints().add(rowConstraints);

        int counter = 0;
        for (Map.Entry<String, Control> property : properties.entrySet()) {
            var label = new Label(property.getKey());
            GridPane.setMargin(label, MARGINS);
            GridPane.setMargin(property.getValue(), MARGINS);
            this.add(label, 0, counter);
            this.add(property.getValue(), 1, counter);
            counter++;
        }
    }
}
