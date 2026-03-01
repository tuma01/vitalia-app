package com.amachi.app.vitalia.medicalcatalog.demographic.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.GenderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import com.amachi.app.vitalia.medicalcatalog.demographic.repository.GenderRepository;
import com.amachi.app.vitalia.medicalcatalog.demographic.specification.GenderSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Service
public class GenderServiceImpl implements GenericService<Gender, GenderSearchDto> {

    GenderRepository genderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Gender> getAll() {
        return genderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Gender> getAll(GenderSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        return genderRepository.findAll(new GenderSpecification(searchDto), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Gender getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return genderRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Gender.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Gender create(Gender entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(genderRepository.save(entity));
    }

    @Override
    public Gender update(Long id, Gender entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        genderRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Gender.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(genderRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        genderRepository.delete(getById(id));
    }
}
