package com.amachi.app.vitalia.medicalcatalog.kinship.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.kinship.dto.search.KinshipSearchDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import com.amachi.app.vitalia.medicalcatalog.kinship.repository.KinshipRepository;
import com.amachi.app.vitalia.medicalcatalog.kinship.specification.KinshipSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
@Transactional
public class KinshipServiceImpl implements GenericService<Kinship, KinshipSearchDto> {
    private final KinshipRepository repository;

    @Override @Transactional(readOnly = true) public List<Kinship> getAll() { return repository.findAll(); }

    @Override @Transactional(readOnly = true) public Page<Kinship> getAll(KinshipSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        Specification<Kinship> specification = new KinshipSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override @Transactional(readOnly = true) public Kinship getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Kinship.class.getName(), "error.resource.not.found", id));
    }

    @Override public Kinship create(Kinship entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override public Kinship update(Long id, Kinship entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Kinship.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return repository.save(entity);
    }

    @Override public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Kinship entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Kinship.class.getName(), "error.resource.not.found", id));
        repository.delete(entity);
    }
}
