package org.example;

import org.example.command.Command;
import org.example.command.Exit;
import org.example.command.Help;
import org.example.command.PetMenu;
import org.example.command.pet.FindPetById;
import org.example.command.pet.FindPetByStatus;
import org.example.controller.PetStore;
import org.example.service.PetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main( String[] args ) {
        Scanner sc = new Scanner(System.in);
        List<Command> commands = new ArrayList<>();

        commands.add(new Help());
        commands.add(new Exit());
        commands.add(new PetMenu());
        commands.add(new FindPetById());
        commands.add(new FindPetByStatus());

        var petService = PetService.getInstance();
        petService.setScanner(sc);

        PetStore petStore = new PetStore(commands);
        petStore.run();

    }
}
