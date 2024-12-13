package com.cognito.volunteer_managment_system.dto.task;

import lombok.Builder;

@Builder
public record TaskResponse(
        String title,
        String description,
        boolean isDone,
        String organizer,
        String assignedLeader
) {
}
