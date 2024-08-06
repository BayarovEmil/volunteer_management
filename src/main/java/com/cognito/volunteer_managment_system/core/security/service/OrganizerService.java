package com.cognito.volunteer_managment_system.core.security.service;

import com.cognito.volunteer_managment_system.core.common.PageResponse;
import com.cognito.volunteer_managment_system.core.security.dataAccess.UserRepository;
import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import com.cognito.volunteer_managment_system.dataAccess.TaskRepository;
import com.cognito.volunteer_managment_system.dataAccess.TeamRepository;
import com.cognito.volunteer_managment_system.dto.task.TaskRequest;
import com.cognito.volunteer_managment_system.dto.task.TaskResponse;
import com.cognito.volunteer_managment_system.entity.Task;
import com.cognito.volunteer_managment_system.entity.Team;
import com.cognito.volunteer_managment_system.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizerService {
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public PageResponse<TaskResponse> getAllTasks(int page,int size,Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Task> tasks = taskRepository.findAll(pageable);
        List<TaskResponse> taskResponses =tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponses,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }


    public PageResponse<TaskResponse> getAllCompletedTasks(int page,int size,Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<Task> tasks = taskRepository.findAllByDone(true,pageable);
        List<TaskResponse> taskResponses = tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponses,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }

    public PageResponse<TaskResponse> getAllUnCompletedTasks(int page,int size,Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<Task> tasks = taskRepository.findAllByDone(false,pageable);
        List<TaskResponse> taskResponses = tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponses,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }

    public PageResponse<TaskResponse> getAllTasksByTeamId(int page, int size, Integer teamId) {
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<Task> tasks = taskRepository.findAllById(teamId,pageable);
        List<TaskResponse> taskResponses = tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponses,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }

    public TaskResponse giveTaskToLeader(
            Authentication connectedUser,
            TaskRequest request
    ) {
        User user = (User) connectedUser.getPrincipal();
        var team = teamRepository.findById(request.assignedTeam())
                .orElseThrow(()->new IllegalStateException("Team not found by id"));
        var task = taskMapper.toTask(user,request);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    public TaskResponse updateGivenTaskToTeam(Authentication connectedUser, Integer taskId, TaskRequest request) {
        User user = (User) connectedUser.getPrincipal();
        var task = taskRepository.findById(taskId)
                .orElseThrow(()->new IllegalStateException("Task not found by id"));
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setOrganizer(user);
        task.setAssignedTeam(Team.builder().id(request.assignedTeam()).build());

        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    public TaskResponse deactivateTask(Integer taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(()->new IllegalStateException("Task not found by id"));
        task.setActive(false);
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }
}
