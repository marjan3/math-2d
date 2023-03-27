package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.canvas.point.DrawablePoint;
import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import com.mtanevski.math2d.math.Point2D;
import com.mtanevski.math2d.math.Vector2D;
import javafx.application.Platform;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class MoveVectorCommand implements Command {
    private final DrawableVector drawableVector;
    private final Vector2D previousLocation;
    private final Vector2D currentLocation;

    @Override
    public void execute() {
        Platform.runLater(() -> drawableVector.move(currentLocation));
    }

    @Override
    public void undo() {
        Platform.runLater(() -> drawableVector.move(previousLocation));
    }
}
