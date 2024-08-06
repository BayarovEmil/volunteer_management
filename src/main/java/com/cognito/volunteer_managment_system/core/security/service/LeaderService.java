package com.cognito.volunteer_managment_system.core.security.service;

import com.cognito.volunteer_managment_system.core.exception.OperationNotPermittedException;
import com.cognito.volunteer_managment_system.core.security.dataAccess.UserRepository;
import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import com.cognito.volunteer_managment_system.dataAccess.TeamRepository;
import com.cognito.volunteer_managment_system.dto.team.TeamRequest;
import com.cognito.volunteer_managment_system.dto.team.TeamResponse;
import com.cognito.volunteer_managment_system.mapper.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaderService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamResponse getTeamInformation(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        var team = teamRepository.findByTeamLeader(user.getFirstname())
                .orElseThrow(()->new OperationNotPermittedException("You are not in the team"));
        return teamMapper.toTeamResponse(team);
    }

    public TeamResponse createTeam(
            Authentication connectedUser,
            TeamRequest request
    ) {
        User user = (User) connectedUser.getPrincipal();
        if (teamRepository.existsByTeamLeader(user.getFirstname())) {
            throw new OperationNotPermittedException("You are team leader in other team");
        }
        var team = teamMapper.toTeam(user,request);
        teamRepository.save(team);
        user.setTeam(team);
        userRepository.save(user);
        return teamMapper.toTeamResponse(team);
    }

    public TeamResponse defineSecondTeamLeader(
            Authentication connectedUser,
            String email
    ) {
        User user =(User) connectedUser.getPrincipal();
        var userInfo = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User email not found"));
        var team = teamRepository.findByTeamLeader(user.getFirstname())
                .orElseThrow(()->new OperationNotPermittedException("You are not team leader"));
//        var teamMember = teamRepository.findByMemberName(userInfo.getFirstname())
//                .orElseThrow(()->new OperationNotPermittedException("User is not in your group"));
        team.setSecondLeader(userInfo.getFirstname());
        return teamMapper.toTeamResponse(teamRepository.save(team));
    }

    public TeamResponse addNewMember(
            Authentication connectedUser,
            String email
    ) {
        User user =(User) connectedUser.getPrincipal();
        var userInfo = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User email not found"));
        var team = teamRepository.findByTeamLeader(user.getFirstname())
                .orElseThrow(()->new OperationNotPermittedException("You are not team leader"));
        userInfo.setTeam(team);
        team.getUsers().add(userInfo);
        team.setMemberCount(team.getUsers().size());
        userRepository.save(userInfo);
        return teamMapper.toTeamResponse(teamRepository.save(team));
    }

    public void resign(Authentication connectedUser) {
        User user =(User) connectedUser.getPrincipal();
        var team = teamRepository.findByTeamLeader(user.getFirstname())
                .orElseThrow(()->new OperationNotPermittedException("You are not team leader"));
        team.setTeamLeader(team.getSecondLeader());
        teamRepository.save(team);
    }
}
