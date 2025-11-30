package com.amachi.app.vitalia.authentication.service.impl;

import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserTenantRole;
import com.amachi.app.vitalia.authentication.repository.RoleRepository;
import com.amachi.app.vitalia.authentication.repository.UserTenantRoleRepository;
import com.amachi.app.vitalia.authentication.service.UserTenantRoleService;
import com.amachi.app.vitalia.common.entity.Tenant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTenantRoleServiceImpl implements UserTenantRoleService {

    private final UserTenantRoleRepository repo;
    private final RoleRepository roleRepository;
    private final UserTenantRoleRepository userTenantRoleRepository;
    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional
    public void assignRolesToUserAndTenant(Long userId, Long tenantId, List<String> roleNames) {

        if (roleNames == null || roleNames.isEmpty()) return;

        // Obtener roles existentes por nombres
        List<Role> roles = roleRepository.findByNameIn(roleNames);
        if (roles.isEmpty()) return;

        User userRef = em.getReference(User.class, userId);
        Tenant tenantRef = em.getReference(Tenant.class, tenantId);

        List<UserTenantRole> entities = roles.stream()
                .map(role -> UserTenantRole.builder()
                        .user(userRef)
                        .tenant(tenantRef)
                        .role(role)
                        .active(true)
                        .build()
                )
                .collect(Collectors.toList());
        repo.saveAll(entities);
    }

    @Override
    @Transactional
    public Set<UserTenantRole> assignRolesToUserAndTenant(User user, Tenant tenant, Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Set.of();
        }

        // usar referencias para evitar cargar entidades completas si es necesario
        User userRef = em.getReference(User.class, user.getId());
        Tenant tenantRef = em.getReference(Tenant.class, tenant.getId());

        Set<UserTenantRole> toPersist = new HashSet<>();

        for (Role role : roles) {
            // evitar duplicado exacto (user+tenant+role)
            boolean exists = userTenantRoleRepository.existsByUserAndTenantAndRole(userRef, tenantRef, role);
            if (exists) {
                log.debug("UserTenantRole ya existe user={}, tenant={}, role={}", user.getId(), tenant.getCode(), role.getName());
                continue;
            }

            UserTenantRole utr = UserTenantRole.builder()
                    .user(userRef)
                    .tenant(tenantRef)
                    .role(role)
                    .active(true)
                    .assignedAt(LocalDateTime.now())
                    .build();

            toPersist.add(utr);
        }

        if (!toPersist.isEmpty()) {
            var saved = userTenantRoleRepository.saveAll(toPersist);
            return saved.stream().collect(Collectors.toSet());
        }
        return Set.of();
    }
}