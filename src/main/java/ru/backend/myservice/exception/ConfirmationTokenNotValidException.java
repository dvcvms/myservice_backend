package ru.backend.myservice.exception;

public class ConfirmationTokenNotValidException extends RuntimeException {

    public ConfirmationTokenNotValidException(String message) {
        super(message);
    }

    public ConfirmationTokenNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
