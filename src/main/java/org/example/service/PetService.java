package org.example.service;

import lombok.Setter;
import org.example.entity.Category;
import org.example.entity.Pet;
import org.example.entity.Tag;
import org.example.exception.BlancFieldException;
import org.example.exception.OperationFailedException;
import org.example.exception.WrongUserInputException;
import org.example.http.ApiResponse;
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

    public void addPet() {
        long userIdInput;
        String userPetNameInput;
        Pet.PetStatus userPetStatusInput;

        System.out.println("Please, enter ID of the pet to store");
        try {
            userIdInput = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new WrongUserInputException("Incorrect input. Only integer numbers are allowed here");
        }

        System.out.println("Please, enter name of the pet to store");
        userPetNameInput = scanner.nextLine();
        if (userPetNameInput.isBlank()) throw new BlancFieldException("Empty fields are not allowed here");


        System.out.println("Please, enter pet status");
        System.out.println("Available values : available, pending, sold");

        try {
            userPetStatusInput = Pet.PetStatus.valueOf(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            throw new WrongUserInputException("Couldn't map your input to existing values");
        }

        Pet pet = new Pet();

        pet.setId(userIdInput);
        pet.setName(userPetNameInput);
        pet.setStatus(userPetStatusInput);

        // hardcoded other inputs...
        pet.setCategory(new Category(1L, "string"));
        pet.setPhotoUrls(new String[]{"some text here"});
        pet.setTags(new Tag[]{new Tag(1L, "tagName")});

        pet = PET_RQS.save(pet).orElseThrow(() -> new WrongUserInputException("Invalid input"));
        System.out.println(pet);
    }

    public void updatePet() {
        long userIdInput;
        String userPetNameInput;
        Pet.PetStatus userPetStatusInput;

        System.out.println("Please, enter ID of pet that needs to be updated");
        try {
            userIdInput = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new WrongUserInputException("Incorrect input. Only integer numbers are allowed here");
        }

        System.out.println("Please, enter updated name of the pet");
        userPetNameInput = scanner.nextLine();
        if (userPetNameInput.isBlank()) throw new BlancFieldException("Empty fields are not allowed here");


        System.out.println("Please, updated status of the pet");
        System.out.println("Available values : available, pending, sold");

        try {
            userPetStatusInput = Pet.PetStatus.valueOf(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            throw new WrongUserInputException("Couldn't map your input to existing values");
        }

        ApiResponse serverResponse = PET_RQS.update(userIdInput, userPetNameInput, userPetStatusInput.name());
        System.out.println(serverResponse.getMessage());

    }

    public void findPetById() {
        int userInput;
        System.out.println("Please, enter ID of pet to return");
        try {
            userInput = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new WrongUserInputException("Incorrect input. Only integer numbers are allowed here");
        }

        Pet returnedPet = PET_RQS.findPetById(userInput).orElseThrow(() -> new OperationFailedException("Pet not found"));
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

        Pet[] returnedPets = PET_RQS.findPetByStatus(petStatus.name()).orElseThrow(() -> new OperationFailedException("Pet not found"));
        Arrays.stream(returnedPets).forEach(System.out::println);
    }
}
