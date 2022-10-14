package org.example.service;

import lombok.Setter;
import org.example.entity.Pet;
import org.example.exception.NotFoundException;
import org.example.exception.WrongUserInputException;
import org.example.http.PetRequests;

import java.util.Scanner;

public class PetService {
    private static final PetService PET_SERVICE = new PetService();
    private static final PetRequests PET_RQS = new PetRequests();
    @Setter
    private Scanner scanner;

    private PetService() {
    }

    public static PetService getInstance() {
        return PET_SERVICE;
    }

    public void findPetById() {
        int userInput;
        System.out.println("Please, enter ID of pet to return");
        try {
            userInput = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new WrongUserInputException("Incorrect input. Only integer numbers are allowed here");
        }

        Pet a = PET_RQS.findPetById(userInput).orElseThrow(() -> new NotFoundException("Pet not found"));

        System.out.println(a);
    }
}
