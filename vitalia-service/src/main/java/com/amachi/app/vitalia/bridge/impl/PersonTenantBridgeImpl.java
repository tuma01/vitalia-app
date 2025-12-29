package com.amachi.app.vitalia.bridge.impl;

import com.amachi.app.vitalia.authentication.bridge.PersonTenantBridge;
import com.amachi.app.vitalia.tenant.repository.TenantRepository;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.common.enums.RelationStatus;
import com.amachi.app.vitalia.common.enums.RoleContext;
import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.entity.PersonTenant;
import com.amachi.app.vitalia.person.repository.PersonRepository;
import com.amachi.app.vitalia.person.repository.PersonTenantRepository;
import com.amachi.app.vitalia.util.PersonTypeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonTenantBridgeImpl implements PersonTenantBridge {

    private final PersonRepository personRepository;
    private final TenantRepository tenantRepository;
    private final PersonTenantRepository personTenantRepository;

    @Override
    @Transactional
    public Long create(Long personId, String tenantCode, PersonType personType) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException(Person.class.getName(), "error.resource.not.found",
                        personId));

        // ✅ Validar usando instancia concreta
        if (!PersonTypeValidator.matches(person, personType)) {
            throw new IllegalStateException("El personType enviado no coincide con el tipo real de la entidad");
        }

        Tenant tenant = tenantRepository.findByCode(tenantCode)
                .orElseThrow(() -> new ResourceNotFoundException(Tenant.class.getName(), "error.resource.not.found",
                        tenantCode));

        RoleContext roleContext = mapPersonTypeToRoleContext(personType);

        // ❗ Validación de SUPER_ADMIN
        if (roleContext == RoleContext.SUPER_ADMIN && !tenantCode.equalsIgnoreCase("GLOBAL")) {
            throw new IllegalStateException("SUPER_ADMIN solo puede existir en el tenant GLOBAL");
        }

        // ❗ Solo un SUPER_ADMIN por tenant
        if (roleContext == RoleContext.SUPER_ADMIN &&
                personTenantRepository.existsByTenantAndRoleContext(tenant, RoleContext.SUPER_ADMIN)) {
            throw new IllegalStateException("Ya existe un SUPER_ADMIN registrado en este tenant");
        }

        // ❗ Evitar duplicados Person + Tenant + Rol
        personTenantRepository.findByPersonAndTenantAndRoleContext(person, tenant, roleContext)
                .ifPresent(p -> {
                    throw new IllegalStateException("La relación PersonTenant ya existe");
                });

        PersonTenant pt = PersonTenant.builder()
                .person(person)
                .tenant(tenant)
                .roleContext(roleContext)
                .relationStatus(RelationStatus.ACTIVE)
                .dateRegistered(LocalDateTime.now())
                .build();

        personTenantRepository.save(pt);

        return pt.getId();
    }

    private RoleContext mapPersonTypeToRoleContext(PersonType personType) {
        return switch (personType) {
            case SUPER_ADMIN -> RoleContext.SUPER_ADMIN;
            case ADMIN -> RoleContext.ADMIN;
            case DOCTOR -> RoleContext.DOCTOR;
            case NURSE -> RoleContext.NURSE;
            case PATIENT -> RoleContext.PATIENT;
            case EMPLOYEE -> RoleContext.EMPLOYEE;
            default -> throw new IllegalStateException(
                    "No existe RoleContext para PersonType: " + personType);
        };
    }
}
