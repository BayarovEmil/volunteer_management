package com.cognito.volunteer_managment_system.mapper;

import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import com.cognito.volunteer_managment_system.dto.feedback.FeedbackResponse;
import com.cognito.volunteer_managment_system.dto.feedback.FeedbackTeamRequest;
import com.cognito.volunteer_managment_system.dto.feedback.FeedbackUserRequest;
import com.cognito.volunteer_managment_system.entity.Feedback;
import com.cognito.volunteer_managment_system.entity.Team;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {

    public Feedback toFeedback(User user, FeedbackTeamRequest teamRequest) {
        return Feedback.builder()
                .note(teamRequest.rate())
                .comment(teamRequest.comment())
                .user(user)
                .team(Team.builder()
                        .id(teamRequest.teamId())
                        .build())
                .build();
    }

    public FeedbackResponse toFeedbackResponse(User user,Feedback feedback) {
        return FeedbackResponse.builder()
                .rate(feedback.getNote())
                .comment(feedback.getComment())
                .owner(feedback.getUser().getFirstname())
                .ownComment(Objects.equals(feedback.getUser().getId(),user.getId()))
                .build();
    }
}
