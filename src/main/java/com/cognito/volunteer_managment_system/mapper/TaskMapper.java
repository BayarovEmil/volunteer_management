package com.cognito.volunteer_managment_system.mapper;

import com.cognito.volunteer_managment_system.core.security.dataAccess.UserRepository;
import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import com.cognito.volunteer_managment_system.dataAccess.TeamRepository;
import com.cognito.volunteer_managment_system.dto.task.TaskRequest;
import com.cognito.volunteer_managment_system.dto.task.TaskResponse;
import com.cognito.volunteer_managment_system.entity.Task;
import com.cognito.volunteer_managment_system.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMapper {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    public Task toTask(User user, TaskRequest request) {
        var team = Team.builder()
                .id(request.assignedTeam())
                .build();
//        var leader = userRepository.findByTeamId(team.getId())
//                .orElseThrow(()->new IllegalStateException("User not found by team id"));
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .isDone(false)
                 .isActive(true)
                .organizer(user)
//                 .assignedLeader(leader)
                .assignedTeam(team)
                .build();
    }

    public TaskResponse toTaskResponse(Task request) {
        return TaskResponse.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .isDone(request.isDone())
                .organizer(request.getOrganizer().getFirstname())
                .assignedLeader(request.getAssignedTeam().getTeamLeader())
                .build();
    }
}
