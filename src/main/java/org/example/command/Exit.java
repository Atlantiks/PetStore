package org.example.command;

import org.example.exception.ExitException;

public class Exit implements Command {
    private static final String HELP = "exit";

    @Override
    public boolean canBeExecuted(String userInput) {
        return HELP.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        throw new ExitException();
    }
}
