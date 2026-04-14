package com.amachi.app.core.geography.province.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.geography.province.dto.ProvinceDto;
import com.amachi.app.core.geography.province.dto.search.ProvinceSearchDto;
import com.amachi.app.core.geography.province.entity.Province;
import com.amachi.app.core.geography.province.mapper.ProvinceMapper;
import com.amachi.app.core.geography.province.service.impl.ProvinceServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provinces")
@RequiredArgsConstructor
@Slf4j
public class ProvinceController extends BaseController implements ProvinceApi {

    private final ProvinceServiceImpl service;
    private final ProvinceMapper mapper;

    @Override
    public ResponseEntity<ProvinceDto> getProvinceById(@NonNull @PathVariable Long id) {
        Province entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    public ResponseEntity<ProvinceDto> createProvince(@Valid @RequestBody @NonNull ProvinceDto dto) {
        Province entity = mapper.toEntity(dto);
        Province savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProvinceDto> updateProvince(@NonNull Long id, @Valid @RequestBody @NonNull ProvinceDto dto) {
        Province existingEntity = service.getById(id);
        mapper.updateEntityFromDto(dto, existingEntity);
        Province updatedEntity = service.update(id, existingEntity);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @Override
    public ResponseEntity<Void> deleteProvince(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ProvinceDto>> getAllProvinces() {
        List<Province> entities = service.getAll();
        List<ProvinceDto> dtos = entities.stream()
                .map(mapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<ProvinceDto>> getPaginatedProvinces(
            @NonNull ProvinceSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Province> page = service.getAll(searchDto, pageIndex, pageSize);
        List<ProvinceDto> dtos = page.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();

        PageResponseDto<ProvinceDto> response = PageResponseDto.<ProvinceDto>builder()
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
