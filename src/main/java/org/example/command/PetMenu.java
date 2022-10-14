package org.example.command;

import org.example.command.pet.FindPetById;
import org.example.command.pet.FindPetByStatus;

public class PetMenu implements Command {
    public static final String PET_COMMANDS = "pet service";

    @Override
    public boolean canBeExecuted(String userInput) {
        return PET_COMMANDS.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        System.out.println("===========PET SERVICE MENU===========");
        System.out.println(String.format("Type %s to obtain pet with entered id", "\033[0;93m" + FindPetById.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to obtain pets with entered status", "\033[0;93m" + FindPetByStatus.COMMAND + "\033[0m"));
    }
}
