package com.amachi.app.vitalia.common.utils;

import lombok.Getter;

@Getter
public enum PersonType {
    ADMIN("ROLE_ADMIN"),
    DOCTOR("ROLE_DOCTOR"),
    NURSE("ROLE_NURSE"),
    PATIENT("ROLE_PATIENT"),
    USER("ROLE_USER");

    public final String defaultRole;

    PersonType(String defaultRole) {
        this.defaultRole = defaultRole;
    }
}
