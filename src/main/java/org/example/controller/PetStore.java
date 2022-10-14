package org.example.controller;

import org.example.command.Command;

import java.util.List;
import java.util.Scanner;

public class PetStore {
    Scanner sc = new Scanner(System.in);
    List<Command> commands;

    public PetStore(List<Command> commands) {
        this.commands = commands;
    }

    public void run() {
        boolean commandExists;
        while (true) {
            commandExists = false;
            System.out.println("Enter new command:");
            var x = sc.nextLine();
            for (Command command : commands) {
                if (command.canBeExecuted(x)) {
                    commandExists = true;
                    command.execute();
                    break;
                }
            }
            if (!commandExists) System.out.println("Bad command.");
        }
    }
}
