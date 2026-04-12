package com.amachi.app.vitalia.medical.doctor.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.doctor.dto.DoctorDto;
import com.amachi.app.vitalia.medical.doctor.dto.search.DoctorSearchDto;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.doctor.mapper.DoctorMapper;
import com.amachi.app.vitalia.medical.doctor.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador homogeneo para el personal medico de Vitalia.
 */
@RestController
@RequestMapping("/personal/doctors")
@RequiredArgsConstructor
public class DoctorController extends BaseController implements DoctorApi {

    private final DoctorService service;
    private final DoctorMapper mapper;

    @Override
    public ResponseEntity<DoctorDto> getDoctorById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<DoctorDto> createDoctor(DoctorDto dto) {
        Doctor entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DoctorDto> updateDoctor(Long id, DoctorDto dto) {
        Doctor existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    public ResponseEntity<Void> deleteDoctor(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PageResponseDto<DoctorDto>> getPaginatedDoctors(DoctorSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Doctor> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<DoctorDto>builder()
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
