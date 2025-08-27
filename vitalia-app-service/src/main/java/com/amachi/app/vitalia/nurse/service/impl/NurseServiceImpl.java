package com.amachi.app.vitalia.nurse.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.nurse.dto.search.NurseSearchDto;
import com.amachi.app.vitalia.nurse.entity.Nurse;
import com.amachi.app.vitalia.nurse.repository.NurseRepository;
import com.amachi.app.vitalia.nurse.specification.NurseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.amachi.app.vitalia.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.vitalia.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class NurseServiceImpl  implements GenericService<Nurse, NurseSearchDto> {

    private final NurseRepository nurseRepository;

    @Override
    public List<Nurse> getAll() {
        return nurseRepository.findAll();
    }

    @Override
    public Page<Nurse> getAll(NurseSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Nurse> specification = new NurseSpecification(searchDto);
        return nurseRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Nurse> getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Optional<Nurse> nurseOptional = nurseRepository.findById(id);
        if (nurseOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return nurseOptional;
    }

    @Override
    public Nurse create(Nurse entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        return nurseRepository.save(entity);
    }

    @Override
    public Nurse update(Long id, Nurse entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        if (!nurseRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        entity.setId(id);
        return nurseRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return nurseRepository.findById(id)
                .map(municipio -> {
                    nurseRepository.delete(municipio);
                    return true;
                })
                .orElse(false);
    }
}
