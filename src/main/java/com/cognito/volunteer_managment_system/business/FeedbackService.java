package com.cognito.volunteer_managment_system.business;

import com.cognito.volunteer_managment_system.core.common.PageResponse;
import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import com.cognito.volunteer_managment_system.dataAccess.FeedbackRepository;
import com.cognito.volunteer_managment_system.dataAccess.TeamRepository;
import com.cognito.volunteer_managment_system.dto.feedback.FeedbackResponse;
import com.cognito.volunteer_managment_system.dto.feedback.FeedbackTeamRequest;
import com.cognito.volunteer_managment_system.dto.feedback.FeedbackUserRequest;
import com.cognito.volunteer_managment_system.entity.Feedback;
import com.cognito.volunteer_managment_system.entity.Team;
import com.cognito.volunteer_managment_system.mapper.FeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final TeamRepository teamRepository;
    public FeedbackResponse giveTeamFeedback(
            Authentication connectedUser,
            FeedbackTeamRequest teamRequest
    ) {
        var team = teamRepository.findById(teamRequest.teamId())
                .orElseThrow(()->new IllegalStateException("Team not found by id"));
        User user = (User) connectedUser.getPrincipal();
        var feedback= feedbackMapper.toFeedback(user,teamRequest);
        team.setRating(feedback.getNote());
        feedbackRepository.save(feedback);
        teamRepository.save(team);
        return feedbackMapper.toFeedbackResponse(user,feedback);
    }

    public PageResponse<FeedbackResponse> findAllTeamFeedbacks(Integer teamId, int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Feedback> feedbacks = feedbackRepository.findAllFeedbacksByTeamId(teamId,pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(f-> feedbackMapper.toFeedbackResponse(user,f))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbackResponses.size(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}
