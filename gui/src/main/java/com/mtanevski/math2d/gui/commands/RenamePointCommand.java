package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.canvas.point.DrawablePoint;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class RenamePointCommand implements Command {
    private final DrawablePoint drawablePoint;
    private final String previousName;
    private final String nextName;

    @Override
    public void execute() {
        drawablePoint.setName(nextName);
    }

    @Override
    public void undo() {
        drawablePoint.setName(previousName);
    }
}
