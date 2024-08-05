package com.cognito.volunteer_managment_system.core.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotNull(message = "Firstname is mandatory")
        @NotEmpty(message = "Firstname is mandatory")
        String firstname,
        @NotNull(message = "Lastname is mandatory")
        @NotEmpty(message = "Lastname is mandatory")
        String lastname,
        @NotNull(message = "Email is mandatory")
        @NotEmpty(message = "Email is mandatory")
        @Email(message = "Email format is wrong")
        String email,
        @NotNull(message = "Password is mandatory")
        @NotEmpty(message = "Password is mandatory")
        @Size(message = "Password length must be longer than 4 elements")
        String password
) {
}
