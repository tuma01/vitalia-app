package com.amachi.app.vitalia.doctorprofessionspeciality.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.search.DoctorProfessionSpecialitySearchDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import com.amachi.app.vitalia.doctorprofessionspeciality.repository.DoctorProfessionSpecialityRepository;
import com.amachi.app.vitalia.doctorprofessionspeciality.specification.DoctorProfessionSpecialitySpecification;
import lombok.AllArgsConstructor;
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


@AllArgsConstructor
@Service
public class DoctorProfessionSpecialityServiceImpl implements GenericService<DoctorProfessionSpeciality, DoctorProfessionSpecialitySearchDto> {

    DoctorProfessionSpecialityRepository doctorProfessionSpecialityRepository;

    @Override
    public List<DoctorProfessionSpeciality> getAll() {
        return doctorProfessionSpecialityRepository.findAll();
    }

    @Override
    public Page<DoctorProfessionSpeciality> getAll(DoctorProfessionSpecialitySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<DoctorProfessionSpeciality> specification = new DoctorProfessionSpecialitySpecification(searchDto);
        return doctorProfessionSpecialityRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<DoctorProfessionSpeciality> getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Optional<DoctorProfessionSpeciality> doctorProfessionSpecialityOptional = doctorProfessionSpecialityRepository.findById(id);
        if (doctorProfessionSpecialityOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return doctorProfessionSpecialityOptional;
    }

    @Override
    public DoctorProfessionSpeciality create(DoctorProfessionSpeciality entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        return doctorProfessionSpecialityRepository.save(entity);
    }

    @Override
    public DoctorProfessionSpeciality update(Long id, DoctorProfessionSpeciality entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL); // Ensure the entity itself isn't null
        if (!doctorProfessionSpecialityRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        entity.setId(id);
        return doctorProfessionSpecialityRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return doctorProfessionSpecialityRepository.findById(id)
                .map(municipio -> {
                    doctorProfessionSpecialityRepository.delete(municipio);
                    return true;
                })
                .orElse(false);
    }
}
