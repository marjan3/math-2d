package com.mtanevski.math2d.gui.commands;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Stack;

public class CommandsManager {
    public final static Property<Boolean> canUndoProperty = new SimpleBooleanProperty();
    public final static Property<Boolean> canRedoProperty = new SimpleBooleanProperty();
    private final static Stack<Command> history = new Stack<>();
    private final static Stack<Command> undoStack = new Stack<>();

    public static void execute(Command command) {
        history.push(command);
        updateProperties();
        command.execute();
    }

    public static void undo() {
        if (!history.isEmpty()) {
            var lastUndoCommand = history.pop();
            undoStack.push(lastUndoCommand);
            lastUndoCommand.undo();
            updateProperties();
        }
    }

    public static void redo() {
        if (!undoStack.isEmpty()) {
            var command = undoStack.pop();
            history.push(command);
            updateProperties();
            command.execute();
        }
    }

    private static void updateProperties() {
        canUndoProperty.setValue(!history.isEmpty());
        canRedoProperty.setValue(!undoStack.isEmpty());
    }

}
