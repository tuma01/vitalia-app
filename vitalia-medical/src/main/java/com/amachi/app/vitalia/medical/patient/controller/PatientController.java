package com.amachi.app.vitalia.medical.patient.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.patient.dto.PatientDto;
import com.amachi.app.vitalia.medical.patient.dto.search.PatientSearchDto;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import com.amachi.app.vitalia.medical.patient.mapper.PatientMapper;
import com.amachi.app.vitalia.medical.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementacion del controlador de pacientes siguiendo el estandar homogeneo vitalia.
 */
@RestController
@RequestMapping("/clinical/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController extends BaseController implements PatientApi {

    private final PatientService service;
    private final PatientMapper mapper;

    @Override
    public ResponseEntity<PatientDto> getPatientById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<PatientDto> createPatient(PatientDto dto) {
        Patient entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PatientDto> updatePatient(Long id, PatientDto dto) {
        Patient existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    public ResponseEntity<Void> deletePatient(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PageResponseDto<PatientDto>> getPaginatedPatients(PatientSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Patient> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<PatientDto>builder()
                .content(mapper.toDTOs(page.getContent()))
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .numberOfElements(page.getNumberOfElements())
                .build());
    }
}
