package com.amachi.app.vitalia.medicalcatalog.vaccine.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.search.VaccineSearchDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.entity.Vaccine;
import com.amachi.app.vitalia.medicalcatalog.vaccine.repository.VaccineRepository;
import com.amachi.app.vitalia.medicalcatalog.vaccine.specification.VaccineSpecification;
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
public class VaccineServiceImpl implements GenericService<Vaccine, VaccineSearchDto> {

    VaccineRepository vaccineRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Vaccine> getAll() {
        return vaccineRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vaccine> getAll(VaccineSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        return vaccineRepository.findAll(
                new VaccineSpecification(searchDto),
                PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name")));
    }

    @Override
    @Transactional(readOnly = true)
    public Vaccine getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return vaccineRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Vaccine.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Vaccine create(Vaccine entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(vaccineRepository.save(entity));
    }

    @Override
    public Vaccine update(Long id, Vaccine entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        vaccineRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Vaccine.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(vaccineRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        vaccineRepository.delete(getById(id));
    }
}
