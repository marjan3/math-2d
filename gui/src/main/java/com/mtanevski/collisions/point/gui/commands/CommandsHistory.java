package com.mtanevski.collisions.point.gui.commands;

import java.util.List;
import java.util.Stack;

public class CommandsHistory {
    private final Stack<Command> history = new Stack<>();

    public void add(Command command) {
        history.push(command);
        command.execute();
    }

    public List<Command> getHistory() {
        return history.subList(0, history.size());
    }
}
