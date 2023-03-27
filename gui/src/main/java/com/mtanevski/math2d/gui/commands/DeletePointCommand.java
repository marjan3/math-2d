package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.point.DrawablePoint;
import javafx.application.Platform;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class DeletePointCommand implements Command {
    private final DrawablePoint drawablePoint;

    @Override
    public void execute() {
        Platform.runLater(() -> {
            Overlay.remove(drawablePoint);
        });
    }

    @Override
    public void undo() {
        Platform.runLater(() -> {
            Overlay.drawPoint(drawablePoint);
        });
    }
}
