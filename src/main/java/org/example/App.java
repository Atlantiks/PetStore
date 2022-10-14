package org.example;

import org.example.command.Command;
import org.example.command.pet.FindPetById;
import org.example.controller.PetStore;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        List<Command> commands = new ArrayList<>();

        commands.add(new FindPetById());

        PetStore petStore = new PetStore(commands);
        petStore.run();

    }
}
