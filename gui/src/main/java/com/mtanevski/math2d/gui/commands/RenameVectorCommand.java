package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.canvas.vector.DrawableVector;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class RenameVectorCommand implements Command {
    private final DrawableVector drawableVector;
    private final String previousName;
    private final String nextName;

    @Override
    public void execute() {
        drawableVector.setName(nextName);
    }

    @Override
    public void undo() {
        drawableVector.setName(previousName);
    }
}
