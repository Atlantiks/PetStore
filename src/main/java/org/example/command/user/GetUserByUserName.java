package org.example.command.user;

import org.example.command.Command;
import org.example.service.UserService;

public class GetUserByUserName implements Command {
    public static final String COMMAND = "get user by name";
    private static final UserService USER_SERVICE = UserService.getInstance();

    @Override
    public boolean canBeExecuted(String userInput) {
        return COMMAND.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        USER_SERVICE.getUserByUserName();
    }
}
