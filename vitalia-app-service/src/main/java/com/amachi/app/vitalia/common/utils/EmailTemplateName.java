package com.amachi.app.vitalia.common.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplateName {

    ACTIVATION_ACCOUNT("activation_account");

    private final String name;
}
