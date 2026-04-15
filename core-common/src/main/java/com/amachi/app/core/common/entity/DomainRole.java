package com.amachi.app.core.common.entity;

import com.amachi.app.core.domain.entity.Person;

/**
 * Contrato base para todas las entidades que representan un rol de dominio (SaaS Elite Tier).
 * Habilita el polimorfismo y la gestión unificada de identidades vinculadas a un tenant.
 */
public interface DomainRole {

    /**
     * @return La identidad global (Persona) vinculada a este rol.
     */
    Person getPerson();

    /**
     * @return El identificador del tenant al que pertenece este rol operativo.
     */
    String getTenantId();

    /**
     * Helper para obtener el ID de la persona de forma segura.
     * @return ID de la persona o null si no está vinculada.
     */
    default Long getPersonId() {
        return getPerson() != null ? getPerson().getId() : null;
    }
}
