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
        canUndoProperty.setValue(canUndo());
        canRedoProperty.setValue(canRedo());
        history.push(command);
        command.execute();
    }

    public static void undo() {
        canUndoProperty.setValue(canUndo());
        canRedoProperty.setValue(canRedo());
        if(canUndo()) {
            var lastUndoCommand = history.pop();
            undoStack.push(lastUndoCommand);
            lastUndoCommand.undo();
        }
    }

    public static void redo() {
        canUndoProperty.setValue(canUndo());
        canRedoProperty.setValue(canRedo());
        if(canRedo()) {
            execute(undoStack.pop());
        }
    }

    public static boolean canUndo() {
       return !history.isEmpty();
    }

    public static boolean canRedo() {
        return !undoStack.isEmpty();
    }
}
