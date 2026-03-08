package com.amachi.app.vitalia.management.tenantadmin.service.impl;

import com.amachi.app.core.common.enums.TenantAdminLevel;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import com.amachi.app.vitalia.management.tenantadmin.dto.search.TenantAdminSearchDto;
import com.amachi.app.vitalia.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.vitalia.management.tenantadmin.repository.TenantAdminRepository;
import com.amachi.app.vitalia.management.tenantadmin.specification.TenantAdminSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.*;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantAdminServiceImpl implements GenericService<TenantAdmin, TenantAdminSearchDto> {

    private final TenantAdminRepository tenantAdminRepository;
    private final TenantAdminDomainServiceImpl tenantAdminDomainService;
    private final TenantRepository tenantRepository;

    @Override
    public List<TenantAdmin> getAll() {
        return tenantAdminRepository.findAll(Sort.by(Sort.Direction.ASC, "tenantName"));
    }

    @Override
    public Page<TenantAdmin> getAll(TenantAdminSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<TenantAdmin> specification = new TenantAdminSpecification(searchDto);
        return tenantAdminRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public TenantAdmin getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return tenantAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantAdmin.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    @Transactional
    public TenantAdmin create(TenantAdmin entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);

        // Handle existing Tenant attachment to avoid "detached entity passed to persist"
        if (entity.getTenant() != null && entity.getTenant().getId() != null) {
            Tenant detachedTenant = entity.getTenant();
            Tenant existingTenant = tenantRepository.findById(detachedTenant.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Tenant.class.getName(),
                            "error.resource.not.found", detachedTenant.getId()));

            // Link Address if it was set on the detached entity (e.g. by handleTenantAddress)
            if (detachedTenant.getAddressId() != null) {
                existingTenant.setAddressId(detachedTenant.getAddressId());
            }

            entity.setTenant(existingTenant);

            // 🛡️ Business Rule: The first administrator must be LEVEL_1
            long adminCount = tenantAdminRepository.countByTenantIdAndDeletedFalse(existingTenant.getId());
            if (adminCount == 0) {
                log.info("Setting first administrator for tenant '{}' to LEVEL_1", existingTenant.getName());
                entity.setAdminLevel(TenantAdminLevel.LEVEL_1);
            }
        }

        TenantAdmin savedEntity = tenantAdminRepository.save(entity);
        tenantAdminDomainService.completeAccountSetup(savedEntity);
        return savedEntity;
    }

    @Override
    @Transactional
    public TenantAdmin update(Long id, TenantAdmin entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);

        TenantAdmin existingAdmin = tenantAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantAdmin.class.getName(),
                        "error.resource.not.found", id));

        // 🛡️ Business Rule: Cannot downgrade the last LEVEL_1 administrator
        if (existingAdmin.getAdminLevel() == TenantAdminLevel.LEVEL_1 &&
            entity.getAdminLevel() != TenantAdminLevel.LEVEL_1) {

            long level1Count = tenantAdminRepository.countByTenantIdAndAdminLevelAndDeletedFalse(existingAdmin.getTenant().getId(),
                    TenantAdminLevel.LEVEL_1);

            if (level1Count <= 1) {
                throw new IllegalStateException(String.format(
                        "Cannot downgrade the last LEVEL_1 administrator for Tenant '%s'.",
                        existingAdmin.getTenant().getName()));
            }
        }

        entity.setId(id);
        return tenantAdminRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        TenantAdmin tenantAdmin = tenantAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantAdmin.class.getName(),
                        "error.resource.not.found", id));

        // 🛡️ Last Man Standing Protection (Ensuring at least one LEVEL_1 remains)
        if (tenantAdmin.getTenant() != null &&
            tenantAdmin.getAdminLevel() == TenantAdminLevel.LEVEL_1) {

            long level1Count = tenantAdminRepository.countByTenantIdAndAdminLevelAndDeletedFalse(tenantAdmin.getTenant().getId(),
                    TenantAdminLevel.LEVEL_1);

            if (level1Count <= 1) {
                throw new IllegalStateException(
                        String.format(
                                "Cannot delete the last LEVEL_1 administrator for Tenant '%s'. Assign a new LEVEL_1 administrator before deleting this one.",
                                tenantAdmin.getTenant().getName()));
            }
        }

        // 🛡️ Logical Deletion
        tenantAdmin.setDeleted(true);

        // Disable linked user account
        if (tenantAdmin.getUser() != null) {
            tenantAdmin.getUser().setEnabled(false);
        }

        tenantAdminRepository.save(tenantAdmin);
        log.info("Logically deleted TenantAdmin with ID: {}", id);
    }
}
