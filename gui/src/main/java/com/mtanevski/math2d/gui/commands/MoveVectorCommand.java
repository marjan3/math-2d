package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import com.mtanevski.math2d.math.Vector2D;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class MoveVectorCommand implements Command {
    private final DrawableVector drawableVector;
    private final Vector2D previousLocation;
    private final Vector2D nextLocation;

    @Override
    public void execute() {
        drawableVector.move(nextLocation);
    }

    @Override
    public void undo() {
        drawableVector.move(previousLocation);
    }
}
