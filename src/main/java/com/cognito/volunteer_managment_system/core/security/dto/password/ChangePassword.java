package com.cognito.volunteer_managment_system.core.security.dto.password;

public record ChangePassword(
        String currentPassword,
        String newPassword,
        String confirmationPassword
) {
}
