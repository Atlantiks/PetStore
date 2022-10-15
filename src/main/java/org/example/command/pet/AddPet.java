package org.example.command.pet;

import org.example.command.Command;
import org.example.service.PetService;

public class AddPet implements Command {
    public static final String COMMAND = "add pet";
    private static final PetService PET_SERVICE = PetService.getInstance();



    @Override
    public boolean canBeExecuted(String userInput) {
        return COMMAND.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        PET_SERVICE.addPet();
    }
}
