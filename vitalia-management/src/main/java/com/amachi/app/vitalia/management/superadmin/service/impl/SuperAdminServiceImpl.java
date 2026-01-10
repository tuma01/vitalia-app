package com.amachi.app.vitalia.management.superadmin.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.management.superadmin.dto.search.SuperAdminSearchDto;
import com.amachi.app.vitalia.management.superadmin.entity.SuperAdmin;
import com.amachi.app.vitalia.management.superadmin.repository.SuperAdminRepository;
import com.amachi.app.vitalia.management.superadmin.specification.SuperAdminSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class SuperAdminServiceImpl implements GenericService<SuperAdmin, SuperAdminSearchDto> {

    private final SuperAdminRepository superAdminRepository;
    private final SuperAdminDomainServiceImpl superAdminDomainService;

    @Override
    public List<SuperAdmin> getAll() {
        return superAdminRepository.findAll();
    }

    @Override
    public Page<SuperAdmin> getAll(SuperAdminSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<SuperAdmin> specification = new SuperAdminSpecification(searchDto);
        return superAdminRepository.findAll(specification, pageable);
    }

    @Override
    public SuperAdmin getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return superAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SuperAdmin.class.getName(), "error.resource.not.found",
                        id));
    }

    @Override
    public SuperAdmin create(SuperAdmin entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        superAdminDomainService.encodePasswordIfNeeded(entity);
        SuperAdmin savedEntity = superAdminRepository.save(entity);
        superAdminDomainService.completeAccountSetup(savedEntity);
        return savedEntity;
    }

    @Override
    public SuperAdmin update(Long id, SuperAdmin entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        // Verificar que el SuperAdmin exista
        superAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SuperAdmin.class.getName(), "error.resource.not.found",
                        id));
        entity.setId(id);
        return superAdminRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        SuperAdmin superAdmin = superAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SuperAdmin.class.getName(), "error.resource.not.found",
                        id));

        // 🛡️ Last Man Standing Protection
        long count = superAdminRepository.count();
        if (count <= 1) {
            throw new IllegalStateException("Cannot delete the only remaining Super Admin. Create another one first.");
        }

        superAdminRepository.delete(superAdmin);
    }
}
