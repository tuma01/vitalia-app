package com.amachi.app.core.auth.service.impl;

import com.amachi.app.core.auth.dto.search.UserTenantRoleSearchDto;
import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.entity.UserTenantRole;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.repository.UserTenantRoleRepository;
import com.amachi.app.core.auth.specification.UserTenantRoleSpecification;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTenantRoleServiceImpl implements UserTenantRoleService {

    private final RoleRepository roleRepository;
    private final UserTenantRoleRepository userTenantRoleRepository;
    @PersistenceContext
    private final EntityManager em;

    // @Override
    @Transactional
    public void assignRolesToUserAndTenant(Long userId, Long tenantId, List<String> roleNames) {

        if (roleNames == null || roleNames.isEmpty())
            return;

        // Obtener roles existentes por nombres
        List<Role> roles = roleRepository.findByNameIn(roleNames);
        if (roles.isEmpty())
            return;

        User userRef = em.getReference(User.class, userId);
        Tenant tenantRef = em.getReference(Tenant.class, tenantId);

        List<UserTenantRole> entities = roles.stream()
                .map(role -> UserTenantRole.builder()
                        .user(userRef)
                        .tenant(tenantRef)
                        .role(role)
                        .active(true)
                        .assignedAt(LocalDateTime.now()) // Added assignedAt
                        .build())
                .collect(Collectors.toList());
        userTenantRoleRepository.saveAll(entities);
    }

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
                log.debug("UserTenantRole ya existe user={}, tenant={}, role={}", user.getId(), tenant.getCode(),
                        role.getName());
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

    public Set<String> findRoleNamesByUserAndTenant(Long userId, Long tenantId) {
        if (userId == null || tenantId == null) {
            return Set.of();
        }
        // Assuming repo has a method or we use a custom query
        // Let's rely on JPA naming convention if possible, or stream results
        // Since we don't have a direct method in repo shown, we can use the repository
        // we have
        return new HashSet<>(userTenantRoleRepository.findActiveRoleNamesByUserAndTenant(userId, tenantId));
    }

    @Transactional
    public void unassignRolesFromUserAndTenant(Long userId, Long tenantId, List<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return;
        }

        List<Role> roles = roleRepository.findByNameIn(roleNames);
        if (roles.isEmpty()) {
            return;
        }

        User userRef = em.getReference(User.class, userId);
        Tenant tenantRef = em.getReference(Tenant.class, tenantId);

        List<UserTenantRole> userTenantRolesToDelete = userTenantRoleRepository
                .findByUserAndTenantAndRoleIn(userRef, tenantRef, roles);

        if (!userTenantRolesToDelete.isEmpty()) {
            userTenantRoleRepository.deleteAll(userTenantRolesToDelete);
        }
    }

    @Transactional
    @Override
    public void revokeRole(Long userId, Long tenantId, String roleName) {
        if (roleName == null || roleName.isBlank()) {
            return;
        }
        User userRef = em.getReference(User.class, userId);
        Tenant tenantRef = em.getReference(Tenant.class, tenantId);
        List<UserTenantRole> matches = userTenantRoleRepository.findAll((root, query, cb) -> cb.and(
                cb.equal(root.get("user"), userRef),
                cb.equal(root.get("tenant"), tenantRef),
                cb.equal(root.get("role").get("name"), roleName),
                cb.isTrue(root.get("active")),
                cb.isNull(root.get("revokedAt"))));
        for (UserTenantRole utr : matches) {
            utr.setActive(false);
            utr.setRevokedAt(LocalDateTime.now());
            userTenantRoleRepository.save(utr);
        }
    }

    @Override
    public List<UserTenantRole> getAll() {
        return userTenantRoleRepository.findAll();
    }

    @Override
    public Page<UserTenantRole> getAll(UserTenantRoleSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<UserTenantRole> specification = new UserTenantRoleSpecification(searchDto);
        return userTenantRoleRepository.findAll(specification, pageable);
    }

    @Override
    public UserTenantRole getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return userTenantRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UserTenantRole.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    public UserTenantRole create(UserTenantRole entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return userTenantRoleRepository.save(entity);
    }

    @Override
    public UserTenantRole update(Long id, UserTenantRole entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        // Verificar que el Country exista
        UserTenantRole existing = userTenantRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UserTenantRole.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return userTenantRoleRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        UserTenantRole userTenantRole = userTenantRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UserTenantRole.class.getName(),
                        "error.resource.not.found", id));
        userTenantRoleRepository.delete(userTenantRole);
    }
}