package com.cognito.volunteer_managment_system.entity;

import com.cognito.volunteer_managment_system.core.common.BaseEntity;
import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity {

    private Double note;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
