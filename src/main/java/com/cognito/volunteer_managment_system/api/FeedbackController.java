package com.cognito.volunteer_managment_system.api;

import com.cognito.volunteer_managment_system.business.FeedbackService;
import com.cognito.volunteer_managment_system.core.common.PageResponse;
import com.cognito.volunteer_managment_system.dto.feedback.FeedbackResponse;
import com.cognito.volunteer_managment_system.dto.feedback.FeedbackTeamRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedback")
@Tag(name = "Feedback Controller")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/giveTeamFeedback")
    public ResponseEntity<FeedbackResponse> giveTeamFeedback(
            Authentication connectedUser,
            @RequestBody @Valid FeedbackTeamRequest teamRequest
    ) {
        return ResponseEntity.ok(feedbackService.giveTeamFeedback(connectedUser,teamRequest));
    }

    @GetMapping("/feedback/{team-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> findAllTeamFeedbacks(
            @PathVariable("team-id") Integer teamId,
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(feedbackService.findAllTeamFeedbacks(teamId,page,size,connectedUser));
    }
}
