package org.example.service;

import lombok.Setter;
import org.example.entity.User;
import org.example.exception.BlancFieldException;
import org.example.exception.LoginFailureException;
import org.example.exception.OperationFailedException;
import org.example.http.ApiResponse;
import org.example.http.UserRequests;

import java.util.ArrayList;
import java.util.List;
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

    public void getUserByUserName() {
        System.out.println("Please, enter your username");
        String userName = scanner.nextLine();

        var user = USER_RQS.getUserByName(userName).orElseThrow(() -> new OperationFailedException("User not found"));
        System.out.println(user);
    }

    public void login() {
        System.out.println("Please, enter your username");
        String userName = scanner.nextLine();
        System.out.println("Please, enter your password");
        String userPassword = scanner.nextLine();

        if (userName.isBlank() || userPassword.isBlank()) {
            throw new BlancFieldException("Can't pass blanc fields here");
        }

        var apiResponse = USER_RQS.login(userName, userPassword);
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

    public void createUser() {
        var apiResponse = USER_RQS.saveUser(createNewUser());

        if (apiResponse.getCode() == 200) {
            System.out.println("\033[0;92m" + "User successfully created" + "\033[0m");
        } else {
            System.out.println(apiResponse.getCode());
            throw new OperationFailedException("Couldn't create new user with entered credentials");
        }
    }

    public void createArrayOfUsers() {
        List<User> newUsers = new ArrayList<>();
        boolean addNextUser = true;
        String userInput;

        while (addNextUser) {
            if (newUsers.size() == 0) {
                System.out.println("User array is now empty.");
                System.out.println("Wanna add first user?");
            } else {
                System.out.printf("User array now contains %d user(s).\n", newUsers.size());
                System.out.println("Wanna add another user?");
            }
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("Y")) {
                newUsers.add(createNewUser());
            } else {
                addNextUser = false;
            }
        }

        ApiResponse apiResponse;

        if (newUsers.size() > 0) {
            apiResponse = USER_RQS.saveUserArray(newUsers.toArray(new User[0]));
        } else {
            throw new LoginFailureException("You should have added at least one user!");
        }

        if (apiResponse.getCode() == 200) {
            System.out.println("\033[0;92m" + "All users from entered array were successfully created" + "\033[0m");
        } else {
            System.out.println(apiResponse.getCode());
            throw new OperationFailedException("Couldn't upload user array with entered credentials");
        }
    }

    private User createNewUser() {
        String firstName, lastName, email, password, phone;

        System.out.println("Please, enter new user's first name");
        firstName = scanner.nextLine();
        System.out.println("Please, enter new user's last name");
        lastName = scanner.nextLine();
        System.out.println("Please, enter new user's email");
        email = scanner.nextLine();
        System.out.println("Please, enter new user's password");
        password = scanner.nextLine();
        System.out.println("Please, enter new user's phone number");
        phone = scanner.nextLine();

        return User.builder()
                .id(0)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(0)
                .build();
    }
}
