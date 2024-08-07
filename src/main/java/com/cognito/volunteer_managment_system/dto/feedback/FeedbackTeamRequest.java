package com.cognito.volunteer_managment_system.dto.feedback;

import lombok.Builder;

@Builder
public record FeedbackTeamRequest(
        Double rate,
        String comment,
        Integer teamId
) {
}
