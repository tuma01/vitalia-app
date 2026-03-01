package com.amachi.app.vitalia.medicalcatalog.diagnosis.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.search.Icd10SearchDto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.repository.Icd10Repository;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.specification.Icd10Specification;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@Service
public class Icd10ServiceImpl implements GenericService<Icd10, Icd10SearchDto> {

    Icd10Repository icd10Repository;

    @Override
    @Transactional(readOnly = true)
    public List<Icd10> getAll() {
        return icd10Repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Icd10> getAll(Icd10SearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<Icd10> specification = new Icd10Specification(searchDto);
        return icd10Repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Icd10 getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return icd10Repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Icd10.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Icd10 create(Icd10 entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(icd10Repository.save(entity));
    }

    @Override
    public Icd10 update(Long id, Icd10 entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        icd10Repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Icd10.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(icd10Repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        icd10Repository.delete(getById(id));
    }
}
