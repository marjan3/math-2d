package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.canvas.point.DrawablePoint;
import com.mtanevski.math2d.math.Point2D;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class MovePointCommand implements Command {
    private final DrawablePoint drawablePoint;
    private final Point2D previousLocation;
    private final Point2D nextLocation;

    @Override
    public void execute() {
        drawablePoint.move(nextLocation);
    }

    @Override
    public void undo() {
        drawablePoint.move(previousLocation);
    }
}
