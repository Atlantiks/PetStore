package org.example.command.pet;

import org.example.command.Command;
import org.example.service.PetService;

public class FindPetById implements Command {
    public static final String COMMAND = "find pet by id";
    private static final PetService PET_SERVICE = PetService.getInstance();



    @Override
    public boolean canBeExecuted(String userInput) {
        return COMMAND.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        PET_SERVICE.findPetById();
    }
}
