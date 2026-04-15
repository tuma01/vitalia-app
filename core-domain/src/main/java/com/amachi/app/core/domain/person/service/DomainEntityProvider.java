package com.amachi.app.core.domain.person.service;

import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.DomainContext;

/**
 * Proveedor de creación de entidades de dominio específicas (SaaS Elite Tier).
 * Permite que módulos externos (como vitalia-medical) registren la creación
 * de sus propias entidades (Doctor, Patient) sin acoplar core-domain.
 */
public interface DomainEntityProvider {
    
    /**
     * Indica si este proveedor maneja el contexto de dominio dado.
     */
    boolean supports(DomainContext context);

    /**
     * Crea la entidad de dominio específica vinculada a la persona y el tenant.
     */
    void createEntity(Person person, Tenant tenant, DomainContext context);

    /**
     * Verifica si la entidad de dominio ya existe para evitar duplicados.
     */
    boolean exists(Person person, Tenant tenant, DomainContext context);
}
