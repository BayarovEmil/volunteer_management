package com.cognito.volunteer_managment_system.dto.feedback;

public record FeedbackUserRequest(
        Double rate,
        String comment,
        Integer userId,
        boolean isLeader
) {
}
