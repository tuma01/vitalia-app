package com.amachi.app.vitalia.medicalcatalog.kinship.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.dto.KinshipDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.dto.search.KinshipSearchDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import com.amachi.app.vitalia.medicalcatalog.kinship.mapper.KinshipMapper;
import com.amachi.app.vitalia.medicalcatalog.kinship.service.impl.KinshipServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/mdm/kinship")
@RequiredArgsConstructor
public class KinshipController extends BaseController implements KinshipApi {

    private final KinshipServiceImpl service;
    private final KinshipMapper mapper;

    @Override
    public ResponseEntity<KinshipDto> getKinshipById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<KinshipDto> createKinship(@NonNull KinshipDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<KinshipDto> updateKinship(@NonNull Long id, @NonNull KinshipDto dto) {
        Kinship existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteKinship(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<KinshipDto>> getAllKinships() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<KinshipDto>> getPaginatedKinships(@NonNull KinshipSearchDto searchDto,
            @NonNull Integer pageIndex, @NonNull Integer pageSize) {
        Page<Kinship> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<KinshipDto>builder()
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
