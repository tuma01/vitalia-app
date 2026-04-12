package com.amachi.app.core.common.enums;

import lombok.Getter;

/**
 * Estado operativo y disponibilidad de una cama hospitalaria.
 */
@Getter
public enum BedStatusEnum implements I18nEnum {
    /**
     * Cama disponible para ingreso inmediato.
     */
    AVAILABLE("Disponible"),

    /**
     * Cama asignada a un paciente hospitalizado actualmente.
     */
    OCCUPIED("Ocupada"),

    /**
     * Cama en proceso de limpieza y desinfeccion.
     */
    DIRTY("Limpieza"),

    /**
     * Cama fuera de servicio por reparacion o mantenimiento preventivo.
     */
    MAINTENANCE("Mantenimiento"),

    /**
     * Cama bloqueada para un ingreso programado inminente.
     */
    RESERVED("Reservada");

    private final String label;

    BedStatusEnum(String label) {
        this.label = label;
    }
}
