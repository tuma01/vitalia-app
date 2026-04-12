package com.amachi.app.vitalia.medical.infrastructure.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.RoomDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.RoomSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Room;
import com.amachi.app.vitalia.medical.infrastructure.mapper.RoomMapper;
import com.amachi.app.vitalia.medical.infrastructure.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infrastructure/rooms")
@RequiredArgsConstructor
public class RoomController extends BaseController implements RoomApi {

    private final RoomService service;
    private final RoomMapper mapper;

    @Override
    public ResponseEntity<RoomDto> getRoomById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<RoomDto> createRoom(RoomDto dto) {
        Room entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<RoomDto> updateRoom(Long id, RoomDto dto) {
        Room entity = service.getById(id);
        mapper.updateEntityFromDto(dto, entity);
        return ResponseEntity.ok(mapper.toDto(service.update(id, entity)));
    }

    @Override
    public ResponseEntity<Void> deleteRoom(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PageResponseDto<RoomDto>> getPaginatedRooms(RoomSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Room> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<RoomDto>builder()
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
