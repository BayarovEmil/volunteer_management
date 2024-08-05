package com.cognito.volunteer_managment_system.core.security.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String refreshToken,
        String accessToken
) {
}
