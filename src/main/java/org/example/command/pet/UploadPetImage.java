package org.example.command.pet;

import org.example.command.Command;
import org.example.service.PetService;

public class UploadPetImage implements Command {
    public static final String COMMAND = "upload pet image";
    private static final PetService PET_SERVICE = PetService.getInstance();



    @Override
    public boolean canBeExecuted(String userInput) {
        return COMMAND.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        PET_SERVICE.uploadPetImage();
    }
}
