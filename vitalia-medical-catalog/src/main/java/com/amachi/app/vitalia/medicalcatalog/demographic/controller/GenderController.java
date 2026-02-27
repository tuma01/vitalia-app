package com.amachi.app.vitalia.medicalcatalog.demographic.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.GenderDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.GenderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import com.amachi.app.vitalia.medicalcatalog.demographic.mapper.GenderMapper;
import com.amachi.app.vitalia.medicalcatalog.demographic.service.impl.GenderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/mdm/demographic/gender")
@RequiredArgsConstructor
public class GenderController extends BaseController implements GenderApi {

    private final GenderServiceImpl service;
    private final GenderMapper mapper;

    @Override
    public ResponseEntity<GenderDto> getGenderById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<GenderDto> createGender(@NonNull GenderDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<GenderDto> updateGender(@NonNull Long id, @NonNull GenderDto dto) {
        Gender existing = service.getById(id);
        mapper.updateEntity(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteGender(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<GenderDto>> getAllGenders() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<GenderDto>> getPaginatedGenders(@NonNull GenderSearchDto searchDto,
            @NonNull Integer pageIndex, @NonNull Integer pageSize) {
        Page<Gender> page = service.getAll(searchDto, pageIndex, pageSize);

        return ResponseEntity.ok(PageResponseDto.<GenderDto>builder()
                .content(page.getContent().stream().map(mapper::toDto).toList())
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
