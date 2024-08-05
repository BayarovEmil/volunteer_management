package com.cognito.volunteer_managment_system.core.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BusinessErrorCode {

    NO_CODE(0,HttpStatus.NOT_IMPLEMENTED,"No code"),
    ACCOUNT_LOCKED(300,HttpStatus.FORBIDDEN,"Account locked"),
    ACCOUNT_DISABLED(301,HttpStatus.FORBIDDEN,"Account disabled"),
    BAD_CREDENTIALS(303,HttpStatus.FORBIDDEN,"Login or password is wrong"),
    INCORRECT_PASSWORD(304,HttpStatus.BAD_REQUEST,""),
    NEW_PASSWORD_DOES_NOT_MATCH(305,HttpStatus.BAD_REQUEST,"")
    ;

    @Getter
    private final Integer code;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description;
}
