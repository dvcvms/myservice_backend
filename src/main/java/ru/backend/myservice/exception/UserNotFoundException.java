package ru.backend.myservice.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not found the user with id=" + id);
    }

    public UserNotFoundException(String message) {
        super("Could not found the user: " + message);
    }
}
