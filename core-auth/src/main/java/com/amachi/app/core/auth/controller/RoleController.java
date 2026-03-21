package com.amachi.app.core.auth.controller;

import com.amachi.app.core.auth.dto.RoleDto;
import com.amachi.app.core.auth.dto.search.RoleSearchDto;
import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.mapper.RoleMapper;
import com.amachi.app.core.auth.service.impl.RoleServiceImpl;
import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController extends BaseController implements RoleApi {

    private final RoleServiceImpl service;
    private final RoleMapper mapper;

    @Override
    public ResponseEntity<RoleDto> getRoleById(Long id) {
        Role entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    public ResponseEntity<RoleDto> createRole(RoleDto dto) {
        Role entity = mapper.toEntity(dto);
        Role savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<RoleDto> updateRole(Long id, RoleDto dto) {
        Role existingEntity = service.getById(id);
        mapper.updateEntityFromDto(dto, existingEntity);
        Role updatedEntity = service.update(id, existingEntity);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @Override
    public ResponseEntity<Void> deleteRole(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<Role> entities = service.getAll();
        List<RoleDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<RoleDto>> getPaginatedRoles(RoleSearchDto roleSearchDto,
                                                                         Integer pageIndex, Integer pageSize) {
        Page<Role> page = service.getAll(roleSearchDto, pageIndex, pageSize);
        List<RoleDto> dtos = page.getContent()
                .stream()
                .map(role -> mapper.toDto(role)).toList();

        PageResponseDto<RoleDto> response = PageResponseDto.<RoleDto>builder()
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
