package com.amachi.app.vitalia.doctorprofessionspeciality.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.DoctorProfessionSpecialityDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.search.DoctorProfessionSpecialitySearchDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import com.amachi.app.vitalia.doctorprofessionspeciality.mapper.DoctorProfessionSpecialityMapper;
import com.amachi.app.vitalia.doctorprofessionspeciality.service.impl.DoctorProfessionSpecialityServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/doctorprofessionspecialities")
public class DoctorProfessionSpecialityController extends BaseController implements DoctorProfessionSpecialityApi {

    private DoctorProfessionSpecialityServiceImpl service;
    private DoctorProfessionSpecialityMapper mapper;

    @Override
    public ResponseEntity<DoctorProfessionSpecialityDto> getDoctorProfessionSpecialityById(Long id) {
        Optional<DoctorProfessionSpeciality> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<DoctorProfessionSpecialityDto> createDoctorProfessionSpeciality(DoctorProfessionSpecialityDto dto) {
        DoctorProfessionSpeciality entity = mapper.toEntity(dto);
        DoctorProfessionSpeciality savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DoctorProfessionSpecialityDto> updateDoctorProfessionSpeciality(Long id, DoctorProfessionSpecialityDto dto) {
        DoctorProfessionSpeciality entity = mapper.toEntity(dto);
        DoctorProfessionSpeciality updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteDoctorProfessionSpeciality(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<List<DoctorProfessionSpecialityDto>> getAllDoctorProfessionSpecialities() {
        List<DoctorProfessionSpeciality> entities = service.getAll();
        List<DoctorProfessionSpecialityDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<DoctorProfessionSpecialityDto>> getPaginatedDoctorProfessionSpecialities(DoctorProfessionSpecialitySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<DoctorProfessionSpeciality> page = service.getAll(searchDto, pageIndex, pageSize);
        List<DoctorProfessionSpecialityDto> dtos = page.getContent()
                .stream()
                .map(doctorProfessionSpeciality -> mapper.toDto(doctorProfessionSpeciality)).toList();

        PageResponseDto<DoctorProfessionSpecialityDto> response = PageResponseDto.<DoctorProfessionSpecialityDto>builder()
                .content(dtos)
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .numberOfElements(page.getNumberOfElements())
                .build();

        return ResponseEntity.ok(response);
    }
}
