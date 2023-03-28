package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import com.mtanevski.math2d.gui.dialogs.SimpleDialog;
import javafx.application.Platform;
import lombok.ToString;

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
            if(drawableVector == null) {
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
            Overlay.drawVector(drawableVector);
        });
    }

    @Override
    public void undo() {
        Overlay.remove(drawableVector);
        Overlay.deselectAll();
    }
}
