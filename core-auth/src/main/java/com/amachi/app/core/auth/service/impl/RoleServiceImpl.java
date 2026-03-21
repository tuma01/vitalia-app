package com.amachi.app.core.auth.service.impl;

import com.amachi.app.core.auth.dto.search.RoleSearchDto;
import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.specification.RoleSpecification;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.amachi.app.core.common.utils.AppConstants.Roles.ROLE_SUPER_ADMIN;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements GenericService<Role, RoleSearchDto> {

    RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        boolean isSuperAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_SUPER_ADMIN));

        if (!isSuperAdmin) {
            return roles.stream()
                    .filter(role -> !role.getName().equals(ROLE_SUPER_ADMIN))
                    .collect(Collectors.toList());
        }
        return roles;
    }

    @Override
    public Page<Role> getAll(RoleSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<Role> specification = new RoleSpecification(searchDto);
        return roleRepository.findAll(specification, pageable);
    }

    @Override
    public Role getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Role create(Role entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return roleRepository.save(entity);
    }

    @Override
    public Role update(Long id, Role entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        // Verificar que el Role exista
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return roleRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getName(), "error.resource.not.found", id));
        roleRepository.delete(role);
    }

    // Helper methods for internal use (found in Repo)
    public List<Role> findByNames(List<String> names) {
        return roleRepository.findByNameIn(names);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getName(), "error.resource.not.found.by.name", name));
    }
}
