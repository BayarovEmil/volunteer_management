package com.cognito.volunteer_managment_system.entity;

import com.cognito.volunteer_managment_system.core.common.BaseEntity;
import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "task")
public class Task extends BaseEntity {
    private String title;
    private String description;
    private boolean isDone;
    private boolean isActive;


    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team assignedTeam;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private User assignedLeader;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;
}
