package com.amachi.app.core.management.email.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum of Thymeleaf HTML email templates available in the system.
 *
 * <p>Each constant maps to a {@code .html} file located in
 * {@code resources/templates/} (Spring/Thymeleaf classpath).
 */
@Getter
@RequiredArgsConstructor
public enum EmailTemplateName {

    /**
     * Template used when a user self-registers and needs to activate their account.
     * File: {@code resources/templates/activation_account.html}
     */
    ACTIVATION_ACCOUNT("activation_account"),

    /**
     * Template used when a TenantAdmin invites a staff member to the hospital portal.
     * File: {@code resources/templates/invitation.html}
     */
    INVITATION("invitation");

    private final String name;
}
