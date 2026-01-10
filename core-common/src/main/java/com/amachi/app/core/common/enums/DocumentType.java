package com.amachi.app.core.common.enums;

import lombok.Getter;

@Getter
public enum DocumentType {
    DNI("Documento Nacional de Identidad / Cédula"),
    PASSPORT("Pasaporte"),
    FOREIGN_ID("Cédula de Extranjería / Residencia"),
    HEALTH_CARD("Carnet de Seguro de Salud"),
    DRIVER_LICENSE("Licencia de Conducir"),
    SOCIAL_SECURITY("Número de Seguridad Social"),
    OTHER("Otro");

    private final String description;

    DocumentType(String description) {
        this.description = description;
    }
}
