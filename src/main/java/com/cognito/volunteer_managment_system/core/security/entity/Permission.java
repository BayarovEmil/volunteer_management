package com.cognito.volunteer_managment_system.core.security.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_CREATE("admin::create"),
    ADMIN_READ("admin::read"),
    ADMIN_UPDATE("admin::update"),
    ADMIN_DELETE("admin::delete"),
    LEADER_CREATE("LEADER::create"),
    LEADER_READ("LEADER::read"),
    LEADER_UPDATE("LEADER::update"),
    LEADER_DELETE("LEADER::delete"),
    ORGANIZER_CREATE("ORGANIZER::create"),
    ORGANIZER_READ("ORGANIZER::read"),
    ORGANIZER_UPDATE("ORGANIZER::update"),
    ORGANIZER_DELETE("ORGANIZER::delete")
    ;

    @Getter
    private final String permissions;
}
