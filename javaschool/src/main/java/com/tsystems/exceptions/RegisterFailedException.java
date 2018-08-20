package com.tsystems.exceptions;

/**
 * Exceptions occurs when something goes wrong while registration
 */
public class RegisterFailedException extends RuntimeException {


    public RegisterFailedException() {
    }

    public RegisterFailedException(String message) {
        super(message);
    }

    public RegisterFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
