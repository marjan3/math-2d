package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.MainController;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import com.mtanevski.math2d.gui.dialogs.NewObjectDialog;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

public class CreateVectorCommand implements Command {
    private final MainController controller;
    private DrawableVector drawableVector;
    private CreateRequest createRequest;

    public CreateVectorCommand(MainController mainController, CreateRequest createRequest) {
        this.controller = mainController;
        this.createRequest = createRequest;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            Overlay.deselectAll();
            if(this.createRequest == null) {
                var result = NewObjectDialog.showNewVector2DDialog(Constants.Labels.NEW_VECTOR_LABEL);
                createRequest = CreateRequest.fromDialogResult(result);
                drawableVector = new DrawableVector(result.label, result.x, result.y, this.controller);
            } else {
                drawableVector = new DrawableVector(createRequest.getName(), createRequest.getX(), createRequest.getY(), this.controller);
            }
            Overlay.drawVector(drawableVector);
            VBox editPropertiesPane = drawableVector.getEditPropertiesPane();
            drawableVector.onDrag(() -> this.controller.switchPropertiesPane(editPropertiesPane));
            this.controller.switchPropertiesPane(editPropertiesPane);
        });
    }

    @Override
    public void undo() {
        Platform.runLater(() -> {
            Overlay.deselectAll();
            Overlay.remove(drawableVector);
        });
    }
}
