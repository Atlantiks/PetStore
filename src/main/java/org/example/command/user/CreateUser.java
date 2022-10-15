package org.example.command.user;

import org.example.command.Command;
import org.example.service.UserService;

public class CreateUser implements Command {
    public static final String COMMAND = "create user";
    private static final UserService USER_SERVICE = UserService.getInstance();

    @Override
    public boolean canBeExecuted(String userInput) {
        return COMMAND.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        USER_SERVICE.createUser();
    }
}
