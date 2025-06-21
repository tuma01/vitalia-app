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
        requireNonNull(id, "ID must not be null");
        Optional<DoctorProfessionSpeciality> doctorProfessionSpecialityOptional = doctorProfessionSpecialityRepository.findById(id);
        if (doctorProfessionSpecialityOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return doctorProfessionSpecialityOptional;
    }

    @Override
    public DoctorProfessionSpeciality create(DoctorProfessionSpeciality entity) {
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null
        return doctorProfessionSpecialityRepository.save(entity);
    }

    @Override
    public DoctorProfessionSpeciality update(Long id, DoctorProfessionSpeciality entity) {
        requireNonNull(id, "ID must not be null");
        requireNonNull(entity, "Entity must not be null"); // Ensure the entity itself isn't null
        if (!doctorProfessionSpecialityRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        entity.setId(id);
        return doctorProfessionSpecialityRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        requireNonNull(id, "ID must not be null");
        return doctorProfessionSpecialityRepository.findById(id)
                .map(municipio -> {
                    doctorProfessionSpecialityRepository.delete(municipio);
                    return true;
                })
                .orElse(false);
    }

//    @Override
//    public DoctorProfessionSpeciality getDoctorProfessionSpeciality(Integer idDoctorProfessionSpeciality) {
//        checkNotNull(idDoctorProfessionSpeciality);
//        Optional<DoctorProfessionSpeciality> doctorProfessionSpecialityOptional = doctorProfessionSpecialityRepository.findById(idDoctorProfessionSpeciality);
//        if (doctorProfessionSpecialityOptional.isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, idDoctorProfessionSpeciality);
//        }
//        return doctorProfessionSpecialityOptional.get();
//    }
//
//    @Override
//    public DoctorProfessionSpeciality addDoctorProfessionSpeciality(DoctorProfessionSpeciality doctorProfessionSpeciality) {
//        checkNotNull(doctorProfessionSpeciality);
//        return doctorProfessionSpecialityRepository.save(doctorProfessionSpeciality);
//    }
//
//    @Override
//    public DoctorProfessionSpeciality updateDoctorProfessionSpeciality(Integer idDoctorProfessionSpeciality, DoctorProfessionSpeciality doctorProfessionSpeciality) {
//        checkNotNull(idDoctorProfessionSpeciality);
//        if (doctorProfessionSpecialityRepository.findById(idDoctorProfessionSpeciality).isEmpty()) {
//            throw new HospitalException(ErrorEnum.OBJECT_NOT_FOUND, idDoctorProfessionSpeciality);
//        }
//        doctorProfessionSpeciality.setIdDoctorProfessionSpeciality(idDoctorProfessionSpeciality);
//        return doctorProfessionSpecialityRepository.save(doctorProfessionSpeciality);
//    }
//
//    @Override
//    public void deleteDoctorProfessionSpeciality(Integer idDoctorProfessionSpeciality) {
//        checkNotNull(idDoctorProfessionSpeciality);
//        Optional<DoctorProfessionSpeciality> doctorProfessionSpecialityOptional = doctorProfessionSpecialityRepository.findById(idDoctorProfessionSpeciality);
//        if (doctorProfessionSpecialityOptional.isEmpty()) {
//            return;
//        }
//        doctorProfessionSpecialityRepository.delete(doctorProfessionSpecialityOptional.get());
//    }
//
//    @Override
//    public Page<DoctorProfessionSpeciality> getDoctorProfessionSpecialities(DoctorProfessionSpecialitySearchDto doctorProfessionSpecialitySearchDTO, Integer pageNumber, Integer pageSize, String sort) {
//        checkNotNull(doctorProfessionSpecialitySearchDTO);
//        var sortById = AppConstants.DEFAULT_SORT_BY + DoctorProfessionSpeciality.class.getSimpleName();
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, AppUtils.getSort(sort, sortById));
//        return doctorProfessionSpecialityRepository.getDoctorProfessionalActivities(doctorProfessionSpecialitySearchDTO, pageable);
//    }
//
//    @Override
//    public List<DoctorProfessionSpeciality> findAllDoctorProfessionSpecialities() {
//        return doctorProfessionSpecialityRepository.findAll();
//    }
}
