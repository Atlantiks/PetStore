package org.example.command;

import org.example.command.store.FindOrderById;
import org.example.command.store.GetStoreInventories;
import org.example.command.store.PlaceOrder;

public class StoreMenu implements Command {
    public static final String STORE_COMMANDS = "store service";

    @Override
    public boolean canBeExecuted(String userInput) {
        return STORE_COMMANDS.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        System.out.println("===========STORE SERVICE MENU===========");
        System.out.println(String.format("Type %s to place an order for a pet", "\033[0;93m" + PlaceOrder.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to obtain pet inventories with status", "\033[0;93m" + GetStoreInventories.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to find purcahsed order by its id", "\033[0;93m" + FindOrderById.COMMAND + "\033[0m"));

        System.out.println();
    }
}
