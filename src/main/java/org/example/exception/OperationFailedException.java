package org.example.exception;

public class OperationFailedException extends RuntimeException {
    public OperationFailedException(String message) {
        super("\033[0;91m" + message + "\033[0m");
    }
}
