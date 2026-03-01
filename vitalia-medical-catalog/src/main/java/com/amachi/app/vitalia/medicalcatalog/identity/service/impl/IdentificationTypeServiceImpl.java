package com.amachi.app.vitalia.medicalcatalog.identity.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import com.amachi.app.vitalia.medicalcatalog.identity.repository.IdentificationTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.identity.specification.IdentificationTypeSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Service
public class IdentificationTypeServiceImpl implements GenericService<IdentificationType, IdentificationTypeSearchDto> {

    IdentificationTypeRepository identificationTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<IdentificationType> getAll() {
        return identificationTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IdentificationType> getAll(IdentificationTypeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        return identificationTypeRepository.findAll(
                new IdentificationTypeSpecification(searchDto),
                PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name")));
    }

    @Override
    @Transactional(readOnly = true)
    public IdentificationType getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return identificationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IdentificationType.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    public IdentificationType create(IdentificationType entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(identificationTypeRepository.save(entity));
    }

    @Override
    public IdentificationType update(Long id, IdentificationType entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        identificationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IdentificationType.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(identificationTypeRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        identificationTypeRepository.delete(getById(id));
    }
}
