package org.example.command.store;

import org.example.command.Command;
import org.example.service.StoreService;

public class PlaceOrder implements Command {
    public static final String COMMAND = "place new order";
    private static final StoreService STORE_SERVICE = StoreService.getInstance();

    @Override
    public boolean canBeExecuted(String userInput) {
        return COMMAND.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        STORE_SERVICE.placeNewOrder();
    }
}
