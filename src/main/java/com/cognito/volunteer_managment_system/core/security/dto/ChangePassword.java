package com.cognito.volunteer_managment_system.core.security.dto;

public record ChangePassword(
        String currentPassword,
        String newPassword,
        String confirmationPassword
) {
}
