package com.cognito.volunteer_managment_system.dto.feedback;

import lombok.Builder;

@Builder
public record FeedbackResponse(
        Double rate,
        String comment,
        boolean ownComment,
        String owner
) {
}
