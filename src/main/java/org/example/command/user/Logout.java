package org.example.command.user;

import org.example.command.Command;
import org.example.service.UserService;

public class Logout implements Command {
    public static final String COMMAND = "logout";
    private static final UserService USER_SERVICE = UserService.getInstance();

    @Override
    public boolean canBeExecuted(String userInput) {
        return COMMAND.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        USER_SERVICE.logout();
    }
}
