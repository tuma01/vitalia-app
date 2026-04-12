package com.amachi.app.vitalia.medical.nurse.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.nurse.dto.NurseDto;
import com.amachi.app.vitalia.medical.nurse.dto.search.NurseSearchDto;
import com.amachi.app.vitalia.medical.nurse.entity.Nurse;
import com.amachi.app.vitalia.medical.nurse.mapper.NurseMapper;
import com.amachi.app.vitalia.medical.nurse.service.NurseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador homogeneo para enfermeria de Vitalia.
 */
@RestController
@RequestMapping("/personal/nurses")
@RequiredArgsConstructor
public class NurseController extends BaseController implements NurseApi {

    private final NurseService service;
    private final NurseMapper mapper;

    @Override
    public ResponseEntity<NurseDto> getNurseById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<NurseDto> createNurse(NurseDto dto) {
        Nurse entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<NurseDto> updateNurse(Long id, NurseDto dto) {
        Nurse existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    public ResponseEntity<Void> deleteNurse(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PageResponseDto<NurseDto>> getPaginatedNurses(NurseSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Nurse> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<NurseDto>builder()
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
