package com.amachi.app.vitalia.doctor.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.doctor.dto.DoctorDto;
import com.amachi.app.vitalia.doctor.dto.search.DoctorSearchDto;
import com.amachi.app.vitalia.doctor.entity.Doctor;
import com.amachi.app.vitalia.doctor.mapper.DoctorMapper;
import com.amachi.app.vitalia.doctor.service.impl.DoctorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorController extends BaseController implements DoctorApi {

    private final DoctorServiceImpl service;
    private final DoctorMapper mapper;

    @Override
    public ResponseEntity<DoctorDto> getDoctorById(Long id) {

        return service.getById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto dto) {
        Doctor saved = service.create(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(saved));

    }

    @Override
    public ResponseEntity<DoctorDto> updateDoctor(Long id, @Valid @RequestBody DoctorDto dto) {
        Doctor updated = service.update(id, mapper.toEntity(dto));
        return updated != null
                ? ResponseEntity.ok(mapper.toDto(updated))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> deleteDoctor(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> dtos = service.getAll().stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<DoctorDto>> getPaginatedDoctors(DoctorSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Doctor> page = service.getAll(searchDto, pageIndex, pageSize);

        List<DoctorDto> content = page.getContent().stream()
                .map(mapper::toDto)
                .toList();

        PageResponseDto<DoctorDto> response = PageResponseDto.<DoctorDto>builder()
                .content(content)
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();

        return ResponseEntity.ok(response);
    }
}
