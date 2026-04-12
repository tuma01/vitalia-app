package com.amachi.app.core.management.tenantadmin.service.impl;

import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.repository.UserRepository;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.common.enums.RelationStatus;
import com.amachi.app.core.common.enums.RoleContext;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.core.domain.entity.PersonTenant;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import com.amachi.app.core.geography.address.service.impl.AddressServiceImpl;
import com.amachi.app.core.management.tenantadmin.dto.TenantAdminDto;
import com.amachi.app.core.management.tenantadmin.entity.TenantAdmin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantAdminDomainServiceImpl {

    private final AddressServiceImpl addressService;
    private final AddressMapper addressMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserTenantRoleService userTenantRoleService;
    private final RoleRepository roleRepository;

    @Transactional
    public void handleTenantAddress(TenantAdmin entity, TenantAdminDto dto) {
        if (dto.getTenant() == null || dto.getTenant().getAddress() == null) {
            return;
        }

        AddressDto addressDto = dto.getTenant().getAddress();
        Tenant tenant = entity.getTenant();

        if (addressDto.getId() == null) {
            Address address = addressMapper.toEntity(addressDto);
            Address savedAddress = addressService.create(address);
            tenant.setAddressId(savedAddress.getId());
        } else {
            // Security Check (Elite Standard): Confirm the address belongs to this tenant
            if (tenant.getAddressId() != null && !tenant.getAddressId().equals(addressDto.getId())) {
                log.error("Security violation: Tenant {} attempted to access/update unauthorized address ID: {}. Current address ID: {}",
                        tenant.getCode(), addressDto.getId(), tenant.getAddressId());
                throw new IllegalStateException("Unauthorized address modification attempted");
            }

            Address existingAddress = addressService.getById(addressDto.getId());
            addressMapper.updateEntityFromDto(addressDto, existingAddress);
            addressService.update(addressDto.getId(), existingAddress);
            tenant.setAddressId(existingAddress.getId());
        }
    }

    public TenantAdminDto enrichTenantAdminDto(TenantAdmin entity, TenantAdminDto dto) {
        if (entity.getTenant() != null && entity.getTenant().getAddressId() != null) {
            Address address = addressService.getById(entity.getTenant().getAddressId());
            if (dto.getTenant() != null) {
                dto.getTenant().setAddress(addressMapper.toDto(address));
            }
        }
        return dto;
    }

    public void encodePasswordIfNeeded(TenantAdmin entity) {
        if (entity.getUser() != null && entity.getUser().getPassword() != null) {
            entity.getUser().setPassword(passwordEncoder.encode(entity.getUser().getPassword()));
        }
    }

    public void encodePasswordIfPresent(TenantAdmin entity, TenantAdminDto dto) {
        if (dto.getUser() != null && dto.getUser().getPassword() != null && !dto.getUser().getPassword().isBlank()) {
            if (entity.getUser() != null) {
                entity.getUser().setPassword(passwordEncoder.encode(dto.getUser().getPassword()));
            }
        }
    }

    public void resetAdminPassword(TenantAdmin entity, String password) {
        if (entity.getUser() != null) {
            entity.getUser().setPassword(passwordEncoder.encode(password));
            userRepository.save(entity.getUser());
        }
    }

    @Transactional
    public void completeAccountSetup(TenantAdmin savedEntity) {
        log.info("Configurando cuenta para el administrador: {}", savedEntity.getEmail());

        User user = savedEntity.getUser();
        user.setPerson(savedEntity);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Vinculación Persona-Tenant
        PersonTenant pt = PersonTenant.builder()
                .person(savedEntity)
                .tenant(savedEntity.getTenant())
                .roleContext(RoleContext.ADMIN)
                .relationStatus(RelationStatus.ACTIVE)
                .dateRegistered(LocalDateTime.now())
                .build();

        if (savedEntity.getPersonTenants() == null) {
            savedEntity.setPersonTenants(new HashSet<>());
        }
        savedEntity.getPersonTenants().add(pt);

        // Registro de Usuario
        userRepository.save(user);

        // Asignación de Roles (ADMIN por defecto)
        Role adminRole = roleRepository.findByName(AppConstants.Roles.ROLE_TENANT_ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "error.role.not_found", AppConstants.Roles.ROLE_TENANT_ADMIN));

        userTenantRoleService.assignRolesToUserAndTenant(
                user,
                savedEntity.getTenant(),
                new HashSet<>(Collections.singletonList(adminRole))
        );
    }
}
