package com.cognito.volunteer_managment_system.core.security.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    LEADER(
            Set.of(
                    Permission.LEADER_CREATE,
                    Permission.LEADER_READ,
                    Permission.LEADER_UPDATE,
                    Permission.LEADER_DELETE
            )
    ),
    ORGANIZER(
            Set.of(
                    Permission.ORGANIZER_CREATE,
                    Permission.ORGANIZER_READ,
                    Permission.ORGANIZER_UPDATE,
                    Permission.ORGANIZER_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.LEADER_CREATE,
                    Permission.LEADER_READ,
                    Permission.LEADER_UPDATE,
                    Permission.LEADER_DELETE,
                    Permission.ORGANIZER_CREATE,
                    Permission.ORGANIZER_READ,
                    Permission.ORGANIZER_UPDATE,
                    Permission.ORGANIZER_DELETE
            )
    )
    ;


    @Getter
    private final Set<Permission> permissions;


    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
