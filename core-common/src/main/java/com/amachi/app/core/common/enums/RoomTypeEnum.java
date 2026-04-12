package com.amachi.app.core.common.enums;

import lombok.Getter;

/**
 * Categorias de habitaciones hospitalarias segun su funcion y complejidad.
 */
@Getter
public enum RoomTypeEnum implements I18nEnum {
    /**
     * Habitacion estandar para hospitalizacion general.
     */
    ESTANDAR("Est├índar"),

    /**
     * Habitacion de lujo o privada con comodidades adicionales.
     */
    SUITE("Suite"),

    /**
     * Habitacion para cuidados intensivos (UCI).
     */
    UCI("Unidad de Cuidados Intensivos"),

    /**
     * Habitacion destinada a la recuperacion post-quirurgica.
     */
    RECOVERY("Recuperaci├│n"),

    /**
     * Habitacion o cubiculo para clasificacion inicial y triaje.
     */
    TRIAGE("Triaje"),

    /**
     * Habitacion exclusiva para atencion de urgencias.
     */
    EMERGENCY("Emergencias"),

    /**
     * Habitacion para preparacion pre-operatoria.
     */
    PRE_OPERATORY("Pre-operatorio");

    private final String label;

    RoomTypeEnum(String label) {
        this.label = label;
    }
}
