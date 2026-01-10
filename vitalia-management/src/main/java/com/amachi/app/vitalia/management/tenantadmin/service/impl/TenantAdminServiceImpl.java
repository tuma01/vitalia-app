package com.amachi.app.vitalia.management.tenantadmin.service.impl;

import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.entity.UserTenantRole;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.common.utils.AppConstants;
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
    private final com.amachi.app.core.domain.tenant.repository.TenantRepository tenantRepository;

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

        // Handle existing Tenant attachment to avoid "detached entity passed to
        // persist"
        if (entity.getTenant() != null && entity.getTenant().getId() != null) {
            Tenant detachedTenant = entity.getTenant();
            Tenant existingTenant = tenantRepository.findById(detachedTenant.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Tenant.class.getName(),
                            "error.resource.not.found", detachedTenant.getId()));

            // Link Address if it was set on the detached entity (e.g. by
            // handleTenantAddress)
            if (detachedTenant.getAddressId() != null) {
                existingTenant.setAddressId(detachedTenant.getAddressId());
            }

            entity.setTenant(existingTenant);
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
        tenantAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantAdmin.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return tenantAdminRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        TenantAdmin tenantAdmin = tenantAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantAdmin.class.getName(),
                        "error.resource.not.found", id));

        // 🛡️ Last Man Standing Protection (Tenant Scoped)
        if (tenantAdmin.getTenant() != null) {
            long adminCount = tenantAdminRepository.countByTenantId(tenantAdmin.getTenant().getId());
            if (adminCount <= 1) {
                throw new IllegalStateException(
                        String.format(
                                "Cannot delete the last administrator for Tenant '%s'. Assign a new administrator before deleting this one.",
                                tenantAdmin.getTenant().getName()));
            }
        }

        tenantAdminRepository.delete(tenantAdmin);
    }
}
