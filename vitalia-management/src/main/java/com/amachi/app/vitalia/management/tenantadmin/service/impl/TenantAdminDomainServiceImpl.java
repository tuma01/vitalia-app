package com.amachi.app.vitalia.management.tenantadmin.service.impl;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.repository.UserRepository;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import com.amachi.app.core.geography.address.service.impl.AddressServiceImpl;
import com.amachi.app.vitalia.management.tenantadmin.dto.TenantAdminDto;
import com.amachi.app.vitalia.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.UserAccount;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.core.domain.entity.PersonTenant;
import com.amachi.app.core.common.enums.RoleContext;
import com.amachi.app.core.common.enums.RelationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TenantAdminDomainServiceImpl {
    private final AddressServiceImpl addressService;
    private final AddressMapper addressMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserTenantRoleService userTenantRoleService;
    private final RoleRepository roleRepository;

    // .. existing methods omitted for brevity as they are unchanged ..

    public void handleTenantAddress(TenantAdmin entity, TenantAdminDto dto) {

        if (dto.getTenant() == null || dto.getTenant().getAddress() == null) {
            return;
        }

        AddressDto addressDto = dto.getTenant().getAddress();
        Long addressId = addressDto.getId();
        Long existingAddressId = entity.getTenant().getAddressId();

        if (addressId != null) {
            // Caso 1: Update explícito (ID viene en el DTO)

            // 🔒 Security Validation: Prevent cross-tenant address updates (IDOR)
            if (existingAddressId != null && !existingAddressId.equals(addressId)) {
                throw new IllegalStateException(
                        "Security violation: Attempting to modify an address that does not belong to this Tenant.");
            }

            Address address = addressService.getById(addressId);
            addressMapper.updateEntityFromDto(addressDto, address);
            addressService.update(addressId, address);
            entity.getTenant().setAddressId(addressId);

        } else if (existingAddressId != null) {
            // Caso 2: Update implícito (No ID en DTO, pero Tenant ya tiene dirección)
            Address address = addressService.getById(existingAddressId);
            addressMapper.updateEntityFromDto(addressDto, address);
            addressService.update(existingAddressId, address);
            // No cambiamos el ID en el tenant porque es el mismo

        } else {
            // Caso 3: Creación (No ID en DTO, Tenant sin dirección)
            Address address = addressService.create(addressMapper.toEntity(addressDto));
            entity.getTenant().setAddressId(address.getId());
        }
    }

    public void encodePasswordIfPresent(TenantAdmin entity, TenantAdminDto dto) {
        if (dto.getUser() == null || entity.getUser() == null) {
            return;
        }

        String rawPassword = dto.getUser().getPassword();

        if (rawPassword == null || rawPassword.isBlank()) {
            return;
        }

        entity.getUser().setPassword(passwordEncoder.encode(rawPassword));
    }

    public void encodePasswordIfNeeded(TenantAdmin entity) {
        User user = entity.getUser();
        if (user == null) {
            return;
        }

        // Solo si es nuevo o viene password plano
        if (user.getId() == null && user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    public TenantAdminDto enrichTenantAdminDto(TenantAdmin entity, TenantAdminDto dto) {
        if (entity.getTenant() != null && entity.getTenant().getAddressId() != null) {
            AddressDto addressDto = addressMapper.toDto(
                    addressService.getById(entity.getTenant().getAddressId()));
            dto.getTenant().setAddress(addressDto);
        }

        // Enrich with Roles (Context: TenantAdmin's User in TenantAdmin's Tenant)
        if (entity.getUser() != null && entity.getTenant() != null) {
            java.util.Set<String> roles = userTenantRoleService.findRoleNamesByUserAndTenant(
                    entity.getUser().getId(),
                    entity.getTenant().getId());
            dto.getUser().setRoles(roles);
        }

        return dto;
    }

    public void resetAdminPassword(TenantAdmin tenantAdmin, String newPassword) {
        User user = tenantAdmin.getUser();
        if (user == null) {
            throw new ResourceNotFoundException(User.class.getName(),
                    "error.resource.not.found.for.tenant.admin", user.getId());
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Professional setup of the UserAccount and Roles after the TenantAdmin is
     * persisted.
     * Use "Split Persistence" strategy to handle the personId constraint.
     */
    public void completeAccountSetup(TenantAdmin savedEntity) {
        // 1. Validation
        if (savedEntity.getId() == null) {
            throw new IllegalStateException("TenantAdmin must be saved before setting up account.");
        }

        // 2. Fix User person (JPA relation)
        savedEntity.getUser().setPerson(savedEntity);

        // 3. Create UserAccount (if not exists)
        UserAccount account = UserAccount.builder()
                .user(savedEntity.getUser())
                .tenant(savedEntity.getTenant())
                .person(savedEntity) // 🟢 Valid person object
                .build();
        savedEntity.getUser().getUserAccounts().add(account);

        // 4. Update User with new Account and personId
        userRepository.save(savedEntity.getUser());

        // 5. Create PersonTenant (Contextual Data for Visibility)
        PersonTenant pt = PersonTenant.builder()
                .person(savedEntity)
                .tenant(savedEntity.getTenant())
                .roleContext(RoleContext.ADMIN)
                .relationStatus(RelationStatus.ACTIVE)
                .dateRegistered(LocalDateTime.now())
                .build();

        savedEntity.getPersonTenants().add(pt);

        // 6. Assign Roles
        assignAdminRole(savedEntity.getUser(), savedEntity.getTenant());

        log.info("✔ Account setup completed for TenantAdmin ID: {}", savedEntity.getId());
    }

    private void assignAdminRole(User user, com.amachi.app.core.domain.tenant.entity.Tenant tenant) {
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(AppConstants.Roles.ROLE_ADMIN).ifPresent(roles::add);
        userTenantRoleService.assignRolesToUserAndTenant(user, tenant, roles);
    }
}
