package org.example.command;

import org.example.command.user.CreateUser;
import org.example.command.user.GetUserByUserName;
import org.example.command.user.Login;
import org.example.command.user.Logout;

public class UserMenu implements Command {
    public static final String USER_COMMANDS = "user service";

    @Override
    public boolean canBeExecuted(String userInput) {
        return USER_COMMANDS.equalsIgnoreCase(userInput);
    }

    @Override
    public void execute() {
        System.out.println("===========USER SERVICE MENU===========");
        System.out.println(String.format("Type %s to find user by his username", "\033[0;93m" + GetUserByUserName.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to create new user", "\033[0;93m" + CreateUser.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to login with your username and password", "\033[0;93m" + Login.COMMAND + "\033[0m"));
        System.out.println(String.format("Type %s to end your current user session", "\033[0;93m" + Logout.COMMAND + "\033[0m"));


        System.out.println();
    }
}
