package com.amachi.app.core.domain.hospital.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.domain.hospital.dto.search.HospitalSearchDto;
import com.amachi.app.core.domain.hospital.entity.Hospital;
import com.amachi.app.core.domain.hospital.repository.HospitalRepository;
import com.amachi.app.core.domain.hospital.service.HospitalService;
import com.amachi.app.core.domain.hospital.specification.HospitalSpecification;
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
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Override
    public List<Hospital> getAll() {
        return hospitalRepository.findAll();
    }

    @Override
    public Page<Hospital> getAll(HospitalSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<Hospital> specification = new HospitalSpecification(searchDto);
        return hospitalRepository.findAll(specification, pageable);
    }

    @Override
    public Hospital getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Hospital.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Hospital create(Hospital entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return hospitalRepository.save(entity);
    }

    @Override
    public Hospital update(Long id, Hospital entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        
        hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Hospital.class.getName(), "error.resource.not.found", id));
        
        entity.setId(id);
        return hospitalRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Hospital.class.getName(), "error.resource.not.found", id));
        hospitalRepository.delete(hospital);
    }
}
