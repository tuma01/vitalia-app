package com.amachi.app.vitalia.management.tenant.service.impl;

import com.amachi.app.core.auth.repository.*;
import com.amachi.app.core.domain.tenant.dto.search.TenantSearchDto;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.geography.address.repository.AddressRepository;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import com.amachi.app.vitalia.management.tenant.specification.TenantSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantServiceImpl implements GenericService<Tenant, TenantSearchDto> {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final UserTenantRoleRepository userTenantRoleRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Tenant> getAll() {
        return tenantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Page<Tenant> getAll(TenantSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Tenant> specification = new TenantSpecification(searchDto);
        return tenantRepository.findAll(specification, pageable);
    }

    @Override
    public Tenant getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Tenant.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    public Tenant create(Tenant entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        requireNonNull(entity.getAddressId(), ENTITY_MUST_NOT_BE_NULL);
        return tenantRepository.save(entity);
    }

    @Override
    @Transactional
    public Tenant update(Long id, Tenant entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        requireNonNull(entity.getAddressId(), ENTITY_MUST_NOT_BE_NULL);

        // Verificar que el Country exista
        Tenant existing = tenantRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Tenant.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return tenantRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Implements Soft Delete pattern by delegating to disableTenant.
        // This ensures code consistency and centralization of the "deactivation" logic.
        // The data remains physically in the database (Historical Integrity),
        // but the Tenant is marked as inactive, preventing any user login (Security).
        disableTenant(id);
    }

    // =====================================================================
    // ACTIVAR / DESACTIVAR TENANT
    // =====================================================================
    @Transactional
    public void enableTenant(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Tenant t = tenantRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Tenant.class.getName(), "error.resource.not.found", id));
        t.setIsActive(true);
        tenantRepository.save(t);
    }

    @Transactional
    public void disableTenant(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Tenant t = tenantRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Tenant.class.getName(), "error.resource.not.found", id));
        t.setIsActive(false);
        tenantRepository.save(t);
    }

    // =====================================================================
    // RESET PASSWORD DE ADMIN DEL TENANT
    // =====================================================================
    // @Transactional
    // public void resetAdminPassword(Long tenantId, String newPassword) {
    // requireNonNull(tenantId, ID_MUST_NOT_BE_NULL);
    // Tenant entity = tenantRepository.findById(tenantId)
    // .orElseThrow(() -> new ResourceNotFoundException(Tenant.class.getName(),
    // "error.resource.not.found", id));
    //
    // entity.getCode();
    //
    // // Buscar el rol administrador
    // Role adminRole = roleRepository.findByName(AppConstants.Roles.ROLE_ADMIN)
    // .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));
    //
    // // Buscar en UserTenantRole
    // UserTenantRole utr = userTenantRoleRepository
    // .findFirstByTenantIdAndRoleId(tenantId, adminRole.getId())
    // .orElseThrow(() -> new ResourceNotFoundException(Tenant.class.getName(),
    // "error.resource.not.found", tenantId));
    //
    // User user = utr.getUser();
    // user.setPassword(passwordEncoder.encode(newPassword));
    // userRepository.save(user);
    // }

}
