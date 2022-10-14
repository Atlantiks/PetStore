package org.example;

import org.example.command.*;
import org.example.command.pet.FindPetById;
import org.example.command.pet.FindPetByStatus;
import org.example.command.store.FindOrderById;
import org.example.command.store.GetStoreInventories;
import org.example.controller.PetStore;
import org.example.http.StoreRequests;
import org.example.service.PetService;
import org.example.service.StoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Command> commands = new ArrayList<>();

        commands.add(new Help());
        commands.add(new Exit());
        commands.add(new PetMenu());
        commands.add(new StoreMenu());
        commands.add(new FindPetById());
        commands.add(new FindPetByStatus());
        commands.add(new GetStoreInventories());
        commands.add(new FindOrderById());

        var petService = PetService.getInstance();
        petService.setScanner(sc);
        var storeService = StoreService.getInstance();
        storeService.setScanner(sc);

        PetStore petStore = new PetStore(commands);
        petStore.run();

    }
}
