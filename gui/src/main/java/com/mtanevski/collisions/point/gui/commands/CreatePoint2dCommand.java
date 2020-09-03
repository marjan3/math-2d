package com.mtanevski.collisions.point.gui.commands;

import com.mtanevski.collisions.point.gui.MainController;
import com.mtanevski.collisions.point.gui.canvas.Overlay;
import com.mtanevski.collisions.point.gui.canvas.point2d.DrawablePoint2D;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class CreatePoint2dCommand implements Command {
    private final MainController controller;

    public CreatePoint2dCommand(MainController mainController) {
        this.controller = mainController;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            DrawablePoint2D drawablePoint2D = Overlay.drawPoint2D();
            GridPane editPropertiesPane = drawablePoint2D.getEditPropertiesPane();
            drawablePoint2D.onDrag(() -> controller.switchPropertiesPane(editPropertiesPane));
            controller.switchPropertiesPane(editPropertiesPane);
        });
    }
}
