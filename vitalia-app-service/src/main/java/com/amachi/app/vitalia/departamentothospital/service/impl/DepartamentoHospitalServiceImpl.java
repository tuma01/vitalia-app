package com.amachi.app.vitalia.departamentothospital.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.departamentothospital.dto.search.DepartamentoHospitalSearchDto;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import com.amachi.app.vitalia.departamentothospital.repository.DepartamentoHospitalRepository;
import com.amachi.app.vitalia.departamentothospital.specification.DepartamentoHospitalSpecification;
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
public class DepartamentoHospitalServiceImpl implements GenericService<DepartamentoHospital, DepartamentoHospitalSearchDto> {

    private final DepartamentoHospitalRepository departamentoHospitalRepository;

    @Override
    public List<DepartamentoHospital> getAll() {
        return departamentoHospitalRepository.findAll();
    }

    @Override
    public Page<DepartamentoHospital> getAll(DepartamentoHospitalSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<DepartamentoHospital> specification = new DepartamentoHospitalSpecification(searchDto);
        return departamentoHospitalRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<DepartamentoHospital> getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Optional<DepartamentoHospital> departamentoHospitalOptional = departamentoHospitalRepository.findById(id);
        if (departamentoHospitalOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return departamentoHospitalOptional;
    }

    @Override
    public DepartamentoHospital create(DepartamentoHospital entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        return departamentoHospitalRepository.save(entity);
    }

    @Override
    public DepartamentoHospital update(Long id, DepartamentoHospital entity) {
        requireNonNull(id, "ID must not be null");
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null

        //    We use existsById as we only need to know if it's there, not fetch the full object yet.
        if (!departamentoHospitalRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        entity.setId(id);
        return departamentoHospitalRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        requireNonNull(id, "ID must not be null");
        return departamentoHospitalRepository.findById(id)
                .map(municipio -> {
                    departamentoHospitalRepository.delete(municipio);
                    return true;
                })
                .orElse(false);
    }
}

