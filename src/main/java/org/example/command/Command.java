package org.example.command;

public interface Command {
    boolean canBeExecuted(String userInput);
    void execute();
}
