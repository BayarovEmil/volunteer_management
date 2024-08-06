package com.cognito.volunteer_managment_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TeamRequest(
        @NotNull(message = "Team name is mandatory")
        @NotEmpty(message = "Team name is mandatory")
        @NotBlank(message = "Team name is mandatory")
        String teamName,
        String bio
) {
}
