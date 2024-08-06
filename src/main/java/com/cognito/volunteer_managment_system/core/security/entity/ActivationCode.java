package com.cognito.volunteer_managment_system.core.security.entity;

import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String code;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
