package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import javafx.application.Platform;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class DeleteVectorCommand implements Command {
    private final DrawableVector drawableVector;

    @Override
    public void execute() {
        Platform.runLater(() -> {
            Overlay.remove(drawableVector);
        });
    }

    @Override
    public void undo() {
        Platform.runLater(() -> {
            Overlay.drawVector(drawableVector);
        });
    }
}
