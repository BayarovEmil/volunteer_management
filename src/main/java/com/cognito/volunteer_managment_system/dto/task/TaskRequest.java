package com.cognito.volunteer_managment_system.dto.task;

import lombok.Builder;

@Builder
public record TaskRequest(
        String title,
        String description,
        Integer assignedTeam
) {
}
