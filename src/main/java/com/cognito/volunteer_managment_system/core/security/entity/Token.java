package com.cognito.volunteer_managment_system.core.security.entity;

import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String token;
    private String refreshToken;
    private boolean expired;
    private boolean revoked;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
