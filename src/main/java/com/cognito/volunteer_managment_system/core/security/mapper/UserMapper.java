package com.cognito.volunteer_managment_system.core.security.mapper;

import com.cognito.volunteer_managment_system.core.security.dataAccess.UserRepository;
import com.cognito.volunteer_managment_system.core.security.dto.user.UserResponse;
import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final UserRepository userRepository;

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .fullName(user.getName())
                .isEnabled(user.isEnabled())
                .role(user.getRole().name())
                .permission(user.getAuthorities().toString())
                .registeredTime(user.getCreatedDate())
                .build();
    }
}
