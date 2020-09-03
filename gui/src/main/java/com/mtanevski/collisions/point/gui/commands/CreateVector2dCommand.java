package com.mtanevski.collisions.point.gui.commands;

import com.mtanevski.collisions.point.gui.MainController;
import com.mtanevski.collisions.point.gui.canvas.Overlay;
import com.mtanevski.collisions.point.gui.canvas.vector2d.Vector2DGraphic;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class CreateVector2dCommand implements Command {
    private final MainController controller;

    public CreateVector2dCommand(MainController mainController) {
        this.controller = mainController;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            Vector2DGraphic vector2DGraphic = Overlay.drawVector2D();
            GridPane editPropertiesPane = vector2DGraphic.getEditPropertiesPane();
            vector2DGraphic.onDrag(() -> this.controller.switchPropertiesPane(editPropertiesPane));
            this.controller.switchPropertiesPane(editPropertiesPane);
        });
    }
}
