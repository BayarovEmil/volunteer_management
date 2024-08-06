package com.cognito.volunteer_managment_system.dataAccess;

import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import com.cognito.volunteer_managment_system.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Integer> {
    Optional<Team> findByTeamLeader(String leaderName);
    Optional<Team> findByMemberName(String memberName);
    Boolean existsByTeamLeader(String leaderName);
    Boolean existsByMemberName(String memberName);
}
