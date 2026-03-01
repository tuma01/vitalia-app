package com.amachi.app.vitalia.medicalcatalog.procedure.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.search.MedicalProcedureSearchDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import com.amachi.app.vitalia.medicalcatalog.procedure.repository.MedicalProcedureRepository;
import com.amachi.app.vitalia.medicalcatalog.procedure.specification.MedicalProcedureSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Service
public class MedicalProcedureServiceImpl implements GenericService<MedicalProcedure, MedicalProcedureSearchDto> {

    MedicalProcedureRepository medicalProcedureRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MedicalProcedure> getAll() {
        return medicalProcedureRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalProcedure> getAll(MedicalProcedureSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<MedicalProcedure> specification = new MedicalProcedureSpecification(searchDto);
        return medicalProcedureRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalProcedure getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return medicalProcedureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalProcedure.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    public MedicalProcedure create(MedicalProcedure entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(medicalProcedureRepository.save(entity));
    }

    @Override
    public MedicalProcedure update(Long id, MedicalProcedure entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        medicalProcedureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalProcedure.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(medicalProcedureRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        medicalProcedureRepository.delete(getById(id));
    }
}
