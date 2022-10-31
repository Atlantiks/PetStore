package org.example.exception;

public class BlancFieldException extends RuntimeException {
    public BlancFieldException(String message) {
        super("\033[0;91m" + message + "\033[0m");
    }

}
