package com.cognito.volunteer_managment_system.mapper;

import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import com.cognito.volunteer_managment_system.dto.TeamRequest;
import com.cognito.volunteer_managment_system.dto.TeamResponse;
import com.cognito.volunteer_managment_system.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamMapper {

    public Team toTeam(User user, TeamRequest request) {
        return Team.builder()
                .teamName(request.teamName())
                .bio(request.bio())
                .teamLeader(user.getFirstname())
                .build();
    }

    public TeamResponse toTeamResponse(Team team) {
        return TeamResponse.builder()
                .teamName(team.getTeamName())
                .leaderName(team.getTeamLeader())
                .memberName(team.getMemberName())
                .secondLeaderName(team.getSecondLeader())
                .bio(team.getBio())
                .rating(team.getRating())
                .memberCount(team.getMemberCount())
                .build();
    }
}
