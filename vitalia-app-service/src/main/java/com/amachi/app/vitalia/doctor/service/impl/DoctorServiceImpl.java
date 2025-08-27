package com.amachi.app.vitalia.doctor.service.impl;


import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.doctor.dto.search.DoctorSearchDto;
import com.amachi.app.vitalia.doctor.entity.Doctor;
import com.amachi.app.vitalia.doctor.repository.DoctorRepository;
import com.amachi.app.vitalia.doctor.specification.DoctorSpecification;
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
public class DoctorServiceImpl implements GenericService<Doctor, DoctorSearchDto> {

    private final DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAll()  {
        return doctorRepository.findAll();
    }

    @Override
    public Page<Doctor> getAll(DoctorSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Doctor> specification = new DoctorSpecification(searchDto);
        return doctorRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Doctor> getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return doctorOptional;
    }

    @Override
    public Doctor create(Doctor entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        return doctorRepository.save(entity);
    }

    @Override
    public Doctor update(Long id, Doctor entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        entity.setId(id);
        return doctorRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctorRepository.delete(doctor);
                    return true;
                })
                .orElse(false);
    }
}
