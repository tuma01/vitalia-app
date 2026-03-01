package com.amachi.app.vitalia.medicalcatalog.bloodtype.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.dto.BloodTypeDto;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.dto.search.BloodTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.mapper.BloodTypeMapper;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.service.impl.BloodTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mdm/blood-type")
@RequiredArgsConstructor
@Slf4j
public class BloodTypeController extends BaseController implements BloodTypeApi {

    private final BloodTypeServiceImpl service;
    private final BloodTypeMapper mapper;

    @Override
    public ResponseEntity<BloodTypeDto> getBloodTypeById(Long id) {
        BloodType entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<BloodTypeDto> createBloodType(BloodTypeDto dto) {
        BloodType entity = mapper.toEntity(dto);
        BloodType savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<BloodTypeDto> updateBloodType(Long id, BloodTypeDto dto) {
        BloodType existingEntity = service.getById(id);
        mapper.updateEntityFromDto(dto, existingEntity);
        BloodType updatedEntity = service.update(id, existingEntity);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteBloodType(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<BloodTypeDto>> getAllBloodTypes() {
        List<BloodType> entities = service.getAll();
        List<BloodTypeDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<BloodTypeDto>> getPaginatedBloodTypes(
            BloodTypeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<BloodType> page = service.getAll(searchDto, pageIndex, pageSize);
        List<BloodTypeDto> dtos = page.getContent()
                .stream()
                .map(bloodType -> mapper.toDto(bloodType)).toList();

        PageResponseDto<BloodTypeDto> response = PageResponseDto.<BloodTypeDto>builder()
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
