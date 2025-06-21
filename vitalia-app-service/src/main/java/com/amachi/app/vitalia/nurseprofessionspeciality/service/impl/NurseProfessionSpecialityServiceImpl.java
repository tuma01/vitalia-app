package com.amachi.app.vitalia.nurseprofessionspeciality.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.nurseprofessionspeciality.dto.search.NurseProfessionSpecialitySearchDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.entity.NurseProfessionSpeciality;
import com.amachi.app.vitalia.nurseprofessionspeciality.repository.NurseProfessionSpecialityRepository;
import com.amachi.app.vitalia.nurseprofessionspeciality.specification.NurseProfessionSpecialitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class NurseProfessionSpecialityServiceImpl implements GenericService<NurseProfessionSpeciality, NurseProfessionSpecialitySearchDto> {

    private final NurseProfessionSpecialityRepository nurseProfessionSpecialityRepository;

    @Override
    public List<NurseProfessionSpeciality> getAll() {
        return nurseProfessionSpecialityRepository.findAll();
    }

    @Override
    public Page<NurseProfessionSpeciality> getAll(NurseProfessionSpecialitySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<NurseProfessionSpeciality> specification = new NurseProfessionSpecialitySpecification(searchDto);
        return nurseProfessionSpecialityRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<NurseProfessionSpeciality> getById(Long id) {
        requireNonNull(id, "ID must not be null");
        Optional<NurseProfessionSpeciality> nurseProfessionSpecialityOptional = nurseProfessionSpecialityRepository.findById(id);
        if (nurseProfessionSpecialityOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return nurseProfessionSpecialityOptional;
    }

    @Override
    public NurseProfessionSpeciality create(NurseProfessionSpeciality entity) {
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null
        return nurseProfessionSpecialityRepository.save(entity);
    }

    @Override
    public NurseProfessionSpeciality update(Long id, NurseProfessionSpeciality entity) {
        requireNonNull(id, "ID must not be null");
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null
        if (!nurseProfessionSpecialityRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        entity.setId(id);
        return nurseProfessionSpecialityRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        requireNonNull(id, "ID must not be null");
        return nurseProfessionSpecialityRepository.findById(id)
                .map(municipio -> {
                    nurseProfessionSpecialityRepository.delete(municipio);
                    return true;
                })
                .orElse(false);
    }
}
