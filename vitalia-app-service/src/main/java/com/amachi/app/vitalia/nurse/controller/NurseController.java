package com.amachi.app.vitalia.nurse.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.nurse.dto.NurseDto;
import com.amachi.app.vitalia.nurse.dto.search.NurseSearchDto;
import com.amachi.app.vitalia.nurse.entity.Nurse;
import com.amachi.app.vitalia.nurse.mapper.NurseMapper;
import com.amachi.app.vitalia.nurse.service.impl.NurseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nurses")
@RequiredArgsConstructor
@Slf4j
public class NurseController extends BaseController implements NurseApi {

    private final NurseServiceImpl service;
    private final NurseMapper mapper;


    @Override
    public ResponseEntity<NurseDto> getNurseById(Long id) {
        Optional<Nurse> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<NurseDto> createNurse(NurseDto dto) {
        Nurse entity = mapper.toEntity(dto);
        Nurse savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<NurseDto> updateNurse(Long id, NurseDto dto) {
        Nurse entity = mapper.toEntity(dto);
        Nurse updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteNurse(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<NurseDto>> getAllNurses() {
        List<Nurse> entities = service.getAll();
        List<NurseDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<NurseDto>> getPaginatedNurses(NurseSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Nurse> page = service.getAll(searchDto, pageIndex, pageSize);
        List<NurseDto> dtos = page.getContent()
                .stream()
                .map(nurse -> mapper.toDto(nurse)).toList();

        PageResponseDto<NurseDto> response = PageResponseDto.<NurseDto>builder()
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
