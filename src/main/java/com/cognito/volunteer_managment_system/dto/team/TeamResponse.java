package com.cognito.volunteer_managment_system.dto.team;

import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import lombok.Builder;

import java.util.List;

@Builder
public record TeamResponse(
        String teamName,
        String leaderName,
        String secondLeaderName,
        String memberName,
        Integer memberCount,
        String bio,
        Double rating,
        List<User> allMembers
) {
}
