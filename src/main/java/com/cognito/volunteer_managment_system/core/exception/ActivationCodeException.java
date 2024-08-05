package com.cognito.volunteer_managment_system.core.exception;

public class ActivationCodeException extends RuntimeException{
    public ActivationCodeException() {
    }

    public ActivationCodeException(String message) {
        super(message);
    }
}
