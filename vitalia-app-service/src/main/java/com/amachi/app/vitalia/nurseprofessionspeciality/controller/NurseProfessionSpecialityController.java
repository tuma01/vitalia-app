package com.amachi.app.vitalia.nurseprofessionspeciality.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.dto.NurseProfessionSpecialityDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.dto.search.NurseProfessionSpecialitySearchDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.entity.NurseProfessionSpeciality;
import com.amachi.app.vitalia.nurseprofessionspeciality.mapper.NurseProfessionSpecialityMapper;
import com.amachi.app.vitalia.nurseprofessionspeciality.service.impl.NurseProfessionSpecialityServiceImpl;
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
@RequestMapping("/nurseprofessionspecialities")
@AllArgsConstructor
@Slf4j
public class NurseProfessionSpecialityController extends BaseController implements NurseProfessionSpecialityApi {

    private NurseProfessionSpecialityServiceImpl service;
    private NurseProfessionSpecialityMapper mapper;


    @Override
    public ResponseEntity<NurseProfessionSpecialityDto> getNurseProfessionSpecialityById(Long id) {
        Optional<NurseProfessionSpeciality> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<NurseProfessionSpecialityDto> createNurseProfessionSpeciality(NurseProfessionSpecialityDto dto) {
        NurseProfessionSpeciality entity = mapper.toEntity(dto);
        NurseProfessionSpeciality savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<NurseProfessionSpecialityDto> updateNurseProfessionSpeciality(Long id, NurseProfessionSpecialityDto dto) {
        NurseProfessionSpeciality entity = mapper.toEntity(dto);
        NurseProfessionSpeciality updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteNurseProfessionSpeciality(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<NurseProfessionSpecialityDto>> getAllNurseProfessionSpecialities() {
        List<NurseProfessionSpeciality> entities = service.getAll();
        List<NurseProfessionSpecialityDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<NurseProfessionSpecialityDto>> getPaginatedNurseProfessionSpecialities(NurseProfessionSpecialitySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<NurseProfessionSpeciality> page = service.getAll(searchDto, pageIndex, pageSize);
        List<NurseProfessionSpecialityDto> dtos = page.getContent()
                .stream()
                .map(nurseProfessionSpeciality -> mapper.toDto(nurseProfessionSpeciality)).toList();

        PageResponseDto<NurseProfessionSpecialityDto> response = PageResponseDto.<NurseProfessionSpecialityDto>builder()
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
