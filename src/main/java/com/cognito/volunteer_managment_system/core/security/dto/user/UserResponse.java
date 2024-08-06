package com.cognito.volunteer_managment_system.core.security.dto.user;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        String firstname,
        String lastname,
        String email,
        String fullName,
        boolean isEnabled,
        String role,
        String permission,
        LocalDateTime registeredTime
) {
}
