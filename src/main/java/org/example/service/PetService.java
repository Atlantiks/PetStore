package org.example.service;

import lombok.Setter;
import org.example.entity.Pet;
import org.example.exception.NotFoundException;
import org.example.exception.WrongUserInputException;
import org.example.http.PetRequests;

import java.util.Arrays;
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

        Pet returnedPet = PET_RQS.findPetById(userInput).orElseThrow(() -> new NotFoundException("Pet not found"));
        System.out.println(returnedPet);
    }

    public void findPetByStatus() {
        String userInput;
        Pet.PetStatus petStatus;
        System.out.println("Please, enter pet status");
        System.out.println("Available values : available, pending, sold");

        userInput = scanner.nextLine();
        try {
            petStatus = Pet.PetStatus.valueOf(userInput);
        } catch (IllegalArgumentException e) {
            throw new WrongUserInputException("Couldn't map your input to existing values");
        }

        Pet[] returnedPets = PET_RQS.findPetByStatus(petStatus.name()).orElseThrow(() -> new NotFoundException("Pet not found"));
        Arrays.stream(returnedPets).forEach(System.out::println);
    }
}
