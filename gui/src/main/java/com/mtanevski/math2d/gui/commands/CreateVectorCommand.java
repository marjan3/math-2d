package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import com.mtanevski.math2d.gui.dialogs.SimpleDialog;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import lombok.ToString;

import static com.mtanevski.math2d.gui.utils.FxUtil.switchNode;

@ToString
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
            if (drawableVector == null) {
                if (this.createRequest == null) {
                    var result = SimpleDialog.showXYDialog(Constants.Labels.NEW_VECTOR_LABEL);
                    createRequest = CreateRequest.fromDialogResult(result);
                    drawableVector = new DrawableVector(createRequest.getName(), createRequest.getX(), createRequest.getY());
                } else if (createRequest.getName() == null) {
                    var result =
                            SimpleDialog.showXYDialog(Constants.Labels.NEW_VECTOR_LABEL, null, createRequest.getX(),
                                    createRequest.getY());
                    createRequest = CreateRequest.fromDialogResult(result);
                    drawableVector = new DrawableVector(createRequest.getName(), createRequest.getX(), createRequest.getY());
                } else {
                    drawableVector = new DrawableVector(createRequest.getName(), createRequest.getX(), createRequest.getY());
                }
            }

            switchNode(Overlay.getScene(), drawableVector.getEditablePropertiesPane(), Constants.Ids.PROPERTIES_PANE);
            Overlay.drawVector(drawableVector);
        });
    }

    @Override
    public void undo() {
        switchNode(drawableVector.getChildren().get(0).getScene(), new Pane(), Constants.Ids.PROPERTIES_PANE);
        Overlay.remove(drawableVector);
        Overlay.deselectAll();
    }
}
