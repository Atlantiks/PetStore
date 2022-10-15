package org.example.exception;

public class WrongUserInputException extends RuntimeException {
    public WrongUserInputException(String message) {
        super("\033[0;91m" + message + "\033[0m");
    }
}
