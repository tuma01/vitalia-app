package com.amachi.app.core.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CivilStatus {

    SINGLE("Single"),
    MARRIED("Married"),
    DOMESTIC_PARTNERSHIP("Domestic Partnership"),
    SEPARATED("Separated"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed");

    public final String label;
}
