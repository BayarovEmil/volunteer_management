package com.cognito.volunteer_managment_system.core.security.controller;

import com.cognito.volunteer_managment_system.core.common.PageResponse;
import com.cognito.volunteer_managment_system.core.security.service.OrganizerService;
import com.cognito.volunteer_managment_system.dto.task.TaskRequest;
import com.cognito.volunteer_managment_system.dto.task.TaskResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("organizer")
@Tag(name = "Organizer Controller")
@RequiredArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;

    @GetMapping("/getAllTasks")
    public ResponseEntity<PageResponse<TaskResponse>> getAllTasks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(organizerService.getAllTasks(page,size,connectedUser));
    }

    @GetMapping("/getAllCompletedTasks")
    public ResponseEntity<PageResponse<TaskResponse>> getAllCompletedTasks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(organizerService.getAllCompletedTasks(page,size,connectedUser));
    }

    @GetMapping("/getAllUnCompletedTasks")
    public ResponseEntity<PageResponse<TaskResponse>> getAllUnCompletedTasks(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(organizerService.getAllUnCompletedTasks(page,size,connectedUser));
    }

    @GetMapping("/getAllTasksByTeamId/{team-id}")
    public ResponseEntity<PageResponse<TaskResponse>> getAllTasksByTeamId(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            @PathVariable("team-id") Integer teamId
    ) {
        return ResponseEntity.ok(organizerService.getAllTasksByTeamId(page,size,teamId));
    }

    @PostMapping("/giveTaskToTeam")
    public ResponseEntity<TaskResponse> giveTaskToTeam(
            Authentication connectedUser,
            @RequestBody TaskRequest request
    ) {
        return ResponseEntity.ok(organizerService.giveTaskToLeader(connectedUser,request));
    }

    @PutMapping("/updateGivenTaskToTeam/{task-id}")
    public ResponseEntity<TaskResponse> updateGivenTaskToTeam(
            Authentication connectedUser,
            @PathVariable("task-id") Integer taskId,
            @RequestBody TaskRequest request
    ) {
        return ResponseEntity.ok(organizerService.updateGivenTaskToTeam(connectedUser,taskId,request));
    }

    @PatchMapping("/deactivateTask/{task-id}")
    public ResponseEntity<TaskResponse> deactivateTask(
            @PathVariable("task-id") Integer taskId
    ) {
        return ResponseEntity.ok(organizerService.deactivateTask(taskId));
    }
}
