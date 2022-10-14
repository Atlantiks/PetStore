package org.example.command;

public class Help implements Command {
    private static final String HELP = "help";

    @Override
    public boolean canBeExecuted(String input) {
        return HELP.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        System.out.println("===========HELP MENU===========");
        System.out.println(String.format("Type %s to see tasks for pets", "\033[0;93m" + PetMenu.PET_COMMANDS + "\033[0m"));
        System.out.println(String.format("Type %s to see tasks for store", "\033[0;93m" + StoreMenu.STORE_COMMANDS + "\033[0m"));

        System.out.println(String.format("\n .. or type %s to quit application","\033[0;93m" + "exit" + "\033[0m"));
        System.out.println("===============================");
    }
}
