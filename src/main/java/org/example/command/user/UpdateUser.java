package org.example.command.user;

import org.example.command.Command;
import org.example.service.UserService;

public class UpdateUser implements Command {
    public static final String COMMAND = "update user";
    private static final UserService USER_SERVICE = UserService.getInstance();

    @Override
    public boolean canBeExecuted(String userInput) {
        return COMMAND.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        USER_SERVICE.updateUser();
    }
}
