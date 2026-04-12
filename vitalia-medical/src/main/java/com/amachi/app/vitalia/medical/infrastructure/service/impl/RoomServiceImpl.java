package com.amachi.app.vitalia.medical.infrastructure.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.RoomSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Room;
import com.amachi.app.vitalia.medical.infrastructure.repository.RoomRepository;
import com.amachi.app.vitalia.medical.infrastructure.service.RoomService;
import com.amachi.app.vitalia.medical.infrastructure.specification.RoomSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * Implementacion Gold Standard del servicio de Habitaciones.
 */
@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    RoomRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> getAll(RoomSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "roomNumber"));
        Specification<Room> specification = new RoomSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Room getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Room.class.getName(), "error.resource.not.found", id));
    }

    @Override
    @Transactional
    public Room create(Room entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(repository.save(entity));
    }

    @Override
    @Transactional
    public Room update(Long id, Room entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Room.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Room room = getById(id);
        repository.delete(room);
    }
}
