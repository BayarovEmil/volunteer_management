package com.cognito.volunteer_managment_system.core.exception;

public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException() {
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }
}
