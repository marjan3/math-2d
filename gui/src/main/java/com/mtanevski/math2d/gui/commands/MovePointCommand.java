package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.canvas.point.DrawablePoint;
import com.mtanevski.math2d.math.Point2D;
import javafx.application.Platform;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class MovePointCommand implements Command {
    private final DrawablePoint drawablePoint;
    private final Point2D previousLocation;
    private final Point2D currentLocation;

    @Override
    public void execute() {
        Platform.runLater(() -> drawablePoint.move(currentLocation));
    }

    @Override
    public void undo() {
        Platform.runLater(() -> drawablePoint.move(previousLocation));
    }
}
