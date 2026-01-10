package com.amachi.app.core.common.enums;

import lombok.Getter;

@Getter
//@AllArgsConstructor
public enum EstadoCivilEnum {

    SOLTERO("Soltero/a"),
    CASADO("Casado/a"),
    UNION_LIBRE("Unión libre o unión de hecho"),
    SEPARADO("Separado/a"),
    DIVORCIADO("Divorciado/a"),
    VIUDO("Viudo/a");

    public final String label;

    EstadoCivilEnum(String label) {
        this.label = label;
    }

}
