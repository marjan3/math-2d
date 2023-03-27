package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.point.DrawablePoint;
import com.mtanevski.math2d.gui.dialogs.NewObjectDialog;
import javafx.application.Platform;
import lombok.ToString;

import static com.mtanevski.math2d.gui.utils.FxUtil.switchNode;

@ToString
public class CreatePointCommand implements Command {
    private DrawablePoint drawablePoint;
    private CreateRequest createRequest;

    public CreatePointCommand(CreateRequest createRequest) {
        this.createRequest = createRequest;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            Overlay.deselectAll();
            if (createRequest == null) {
                var result = NewObjectDialog.showXYDialog(Constants.Labels.NEW_POINT_LABEL);
                createRequest = CreateRequest.fromDialogResult(result);
                drawablePoint = new DrawablePoint(createRequest.getName(), createRequest.getX(), createRequest.getY());
            } else if (createRequest.getName() == null) {
                var result =
                        NewObjectDialog.showXYDialog(Constants.Labels.NEW_POINT_LABEL, null, createRequest.getX(),
                                createRequest.getY());
                createRequest = CreateRequest.fromDialogResult(result);
                drawablePoint = new DrawablePoint(createRequest.getName(), createRequest.getX(), createRequest.getY());
            } else {
                drawablePoint = new DrawablePoint(createRequest.getName(), createRequest.getX(), createRequest.getY());
            }
            Overlay.drawPoint(drawablePoint);
            var editPropertiesPane = drawablePoint.getEditPropertiesPane();
            drawablePoint.onDrag(() -> switchNode(Overlay.getScene(), editPropertiesPane, Constants.Ids.PROPERTIES_PANE));
            switchNode(Overlay.getScene(), editPropertiesPane, Constants.Ids.PROPERTIES_PANE);
        });
    }

    @Override
    public void undo() {
        Platform.runLater(() -> {
            Overlay.deselectAll();
            Overlay.remove(drawablePoint);
        });
    }

}
