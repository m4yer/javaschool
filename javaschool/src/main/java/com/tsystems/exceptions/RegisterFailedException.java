package com.tsystems.exceptions;

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
