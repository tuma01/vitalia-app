package com.amachi.app.core.common.enums;

import lombok.Getter;

@Getter
public enum BloodTypeEnum {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    UNKNOWN("Desconocido");

    private final String label;

    BloodTypeEnum(String label) {
        this.label = label;
    }
}
