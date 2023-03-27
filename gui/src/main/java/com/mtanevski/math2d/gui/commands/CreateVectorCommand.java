package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import com.mtanevski.math2d.gui.dialogs.NewObjectDialog;
import javafx.application.Platform;

import static com.mtanevski.math2d.gui.utils.FxUtil.switchNode;

public class CreateVectorCommand implements Command {
    private DrawableVector drawableVector;
    private CreateRequest createRequest;

    public CreateVectorCommand(CreateRequest createRequest) {
        this.createRequest = createRequest;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            Overlay.deselectAll();
            if (this.createRequest == null) {
                var result = NewObjectDialog.showXYDialog(Constants.Labels.NEW_VECTOR_LABEL);
                createRequest = CreateRequest.fromDialogResult(result);
                drawableVector = new DrawableVector(createRequest.getName(), createRequest.getX(), createRequest.getY());
            } else if (createRequest.getName() == null) {
                var result =
                        NewObjectDialog.showXYDialog(Constants.Labels.NEW_VECTOR_LABEL, null, createRequest.getX(),
                                createRequest.getY());
                createRequest = CreateRequest.fromDialogResult(result);
                drawableVector = new DrawableVector(createRequest.getName(), createRequest.getX(), createRequest.getY());
            } else {
                drawableVector = new DrawableVector(createRequest.getName(), createRequest.getX(), createRequest.getY());
            }
            Overlay.drawVector(drawableVector);
            var editPropertiesPane = drawableVector.getEditPropertiesPane();
            drawableVector.onDrag(() -> switchNode(Overlay.getScene(), editPropertiesPane, Constants.Ids.PROPERTIES_PANE));
            switchNode(Overlay.getScene(), editPropertiesPane, Constants.Ids.PROPERTIES_PANE);
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
