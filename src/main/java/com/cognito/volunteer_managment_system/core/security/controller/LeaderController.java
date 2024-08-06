package com.cognito.volunteer_managment_system.core.security.controller;

import com.cognito.volunteer_managment_system.core.security.service.LeaderService;
import com.cognito.volunteer_managment_system.dto.team.TeamRequest;
import com.cognito.volunteer_managment_system.dto.team.TeamResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("leader")
@Tag(name = "Leader Controller")
@RequiredArgsConstructor
public class LeaderController {
    private final LeaderService leaderService;

    @GetMapping("/getTeamInformation")
    public ResponseEntity<TeamResponse> getTeamInformation(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(leaderService.getTeamInformation(connectedUser));
    }

    @PostMapping("/createTeam")
    public ResponseEntity<TeamResponse> createTeam(
            Authentication connectedUser,
            @RequestBody TeamRequest request
    ) {
        return ResponseEntity.ok(leaderService.createTeam(connectedUser,request));
    }

    @PatchMapping("defineSecondTeamLeader/{email}")
    public ResponseEntity<TeamResponse> defineSecondTeamLeader(
            Authentication connectedUser,
            @PathVariable("email") String email
    ) {
        return ResponseEntity.ok(leaderService.defineSecondTeamLeader(connectedUser,email));
    }

    @PostMapping("addNewMember")
    public ResponseEntity<TeamResponse> addNewMember(
            Authentication connectedUser,
            @RequestParam String email
    ) {
        return ResponseEntity.ok(leaderService.addNewMember(connectedUser,email));
    }

    @PatchMapping("/resign")
    public ResponseEntity<?> resign(
            Authentication connectedUser
    ) {
        leaderService.resign(connectedUser);
        return ResponseEntity.ok().build();
    }
}
