package org.example.command;

import org.example.command.pet.*;

public class PetMenu implements Command {
    public static final String PET_COMMANDS = "pet service";

    @Override
    public boolean canBeExecuted(String userInput) {
        return PET_COMMANDS.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        System.out.println("===========PET SERVICE MENU===========");
        System.out.println(String.format("Type %s to add new pet to the store", "\033[0;93m" + AddPet.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to update existing pet in the store", "\033[0;93m" + UpdatePet.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to replace existing pet in the store with new one", "\033[0;93m" + ReplacePet.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to delete existing pet in the store", "\033[0;93m" + DeletePet.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to obtain pet with entered id", "\033[0;93m" + FindPetById.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to obtain pets with entered status", "\033[0;93m" + FindPetByStatus.COMMAND + "\033[0m"));
        System.out.println();
    }
}
