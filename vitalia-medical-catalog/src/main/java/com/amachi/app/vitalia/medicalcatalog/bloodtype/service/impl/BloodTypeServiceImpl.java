package com.amachi.app.vitalia.medicalcatalog.bloodtype.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.dto.search.BloodTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.repository.BloodTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.specification.BloodTypeSpecification;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@Service
public class BloodTypeServiceImpl implements GenericService<BloodType, BloodTypeSearchDto> {

    BloodTypeRepository bloodTypeRepository;

    @Override
    public List<BloodType> getAll() {
        return bloodTypeRepository.findAll();
    }

    @Override
    public Page<BloodType> getAll(BloodTypeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        Specification<BloodType> specification = new BloodTypeSpecification(searchDto);
        return bloodTypeRepository.findAll(specification, pageable);
    }

    @Override
    public BloodType getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return bloodTypeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(BloodType.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public BloodType create(BloodType entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return bloodTypeRepository.save(entity);
    }

    @Override
    public BloodType update(Long id, BloodType entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        // Verificar que el BloodType exista
        bloodTypeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(BloodType.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return bloodTypeRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        BloodType bloodType = bloodTypeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(BloodType.class.getName(), "error.resource.not.found", id));
        bloodTypeRepository.delete(bloodType);
    }
}
