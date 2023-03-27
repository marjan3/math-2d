package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.MainController;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import com.mtanevski.math2d.gui.dialogs.NewObjectDialog;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

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
            if(this.createRequest == null) {
                var result = NewObjectDialog.showNewVector2DDialog(Constants.Labels.NEW_VECTOR_LABEL);
                createRequest = CreateRequest.fromDialogResult(result);
                drawableVector = new DrawableVector(result.label, result.x, result.y);
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
