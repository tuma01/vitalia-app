package com.amachi.app.core.management.provider;

import com.amachi.app.core.common.enums.DomainContext;
import com.amachi.app.core.common.enums.TenantAdminLevel;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.person.service.DomainEntityProvider;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.management.superadmin.entity.SuperAdmin;
import com.amachi.app.core.management.superadmin.repository.SuperAdminRepository;
import com.amachi.app.core.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.core.management.tenantadmin.repository.TenantAdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Proveedor de entidades administrativas (SaaS Elite Tier).
 * Orquesta la creación de SuperAdmin y TenantAdmin vía composición.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManagementDomainEntityProvider implements DomainEntityProvider {

    private final SuperAdminRepository  superAdminRepository;
    private final TenantAdminRepository tenantAdminRepository;

    @Override
    public boolean supports(DomainContext context) {
        return context == DomainContext.ADMIN || context == DomainContext.SUPER_ADMIN;
    }

    @Override
    public void createEntity(Person person, Tenant tenant, DomainContext context) {
        log.info("[MGMT-PROVIDER] Creating administrative entity for context: {}", context);

        switch (context) {
            case SUPER_ADMIN -> createSuperAdmin(person, tenant);
            case ADMIN -> createTenantAdmin(person, tenant);
            default -> throw new IllegalArgumentException("Unsupported context: " + context);
        }
    }

    @Override
    public boolean exists(Person person, Tenant tenant, DomainContext context) {
        return switch (context) {
            case SUPER_ADMIN -> superAdminRepository.existsByPerson(person); // SuperAdmin is global
            case ADMIN -> tenantAdminRepository.existsByPersonAndTenant(person, tenant);
            default -> false;
        };
    }

    private void createSuperAdmin(Person person, Tenant tenant) {
        SuperAdmin superAdmin = SuperAdmin.builder()
                .person(person)
                .tenantId("SYSTEM")
                .globalAccess(true)
                .build();
        superAdminRepository.save(superAdmin);
    }

    private void createTenantAdmin(Person person, Tenant tenant) {
        TenantAdmin tenantAdmin = TenantAdmin.builder()
                .person(person)
                .tenant(tenant)
                .tenantId(tenant.getCode())
                .adminLevel(TenantAdminLevel.FULL)
                .build();
        tenantAdminRepository.save(tenantAdmin);
    }
}
