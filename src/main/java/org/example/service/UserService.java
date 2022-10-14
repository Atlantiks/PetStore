package org.example.service;

import lombok.Setter;
import org.example.exception.BlancFieldException;
import org.example.exception.LoginFailureException;
import org.example.http.ApiResponse;
import org.example.http.UserRequests;

import java.util.Scanner;

public class UserService {
    private static final UserService USER_SERVICE = new UserService();
    private static final UserRequests USER_RQS = new UserRequests();
    @Setter
    private Scanner scanner;

    private UserService() {
    }

    public static UserService getInstance() {
        return USER_SERVICE;
    }

    public void login() {
        System.out.println("Please, enter your username");
        String userName = scanner.nextLine();
        System.out.println("Please, enter your password");
        String userPassword = scanner.nextLine();

        if (userName.isBlank() || userPassword.isBlank()) {
            throw new BlancFieldException("Can't pass blanc fields here");
        }

        var apiResponse = USER_RQS.login(userName,userPassword);
        if (apiResponse.getCode() == 200) {
            System.out.println("\033[0;92m" + "Login successful" + "\033[0m");
            System.out.println("Server message: " + apiResponse.getMessage());
        } else {
            throw new LoginFailureException("Failed to log in the system. Please check your credentials");
        }
    }

    public void logout() {
        ApiResponse apiResponse = USER_RQS.logout();
        if (apiResponse.getCode() == 200) {
            System.out.println("\033[0;92m" + "Logout successful" + "\033[0m");
            System.out.println("Server message: " + apiResponse.getMessage());
        }
    }


}
