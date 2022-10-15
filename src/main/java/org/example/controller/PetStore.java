package org.example.controller;

import org.example.command.Command;
import org.example.exception.*;

import java.util.List;
import java.util.Scanner;

public class PetStore {
    Scanner sc = new Scanner(System.in);
    List<Command> commands;

    public PetStore(List<Command> commands) {
        this.commands = commands;
    }


    public void run() {
        System.out.println("Hello, please enter help to see all commands");
        try {
            execute();
        } catch (ExitException e) {
            System.out.println("Thanks for your visit! See you next time...");
        }
    }
    public void execute() {
        boolean commandExists;
        while (true) {
            commandExists = false;
            System.out.println("Enter new command:");
            var userInput = sc.nextLine();
            for (Command command : commands) {
                if (command.canBeExecuted(userInput)) {
                    commandExists = true;
                    try {
                        command.execute();
                    } catch (OperationFailedException | WrongUserInputException | LoginFailureException |
                             BlancFieldException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
            if (!commandExists) System.out.println("Bad command.");
        }
    }
}
