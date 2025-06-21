package com.amachi.app.vitalia.common.controller;

import com.amachi.app.vitalia.common.dto.search.BaseSearchDto;
import com.amachi.app.vitalia.common.entities.BaseEntity;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public  class BaseController2<E extends BaseEntity, D, M extends EntityDtoMapper<E, D>, S extends GenericService<E, F>, F extends BaseSearchDto> {

    public static final String ID = "/{id}";
    public static final String ALL = "/all";

    protected final S genericService;
    protected final M mapper;


    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<D>> getAllList() {
        List<E> entities = genericService.getAll();
        List<D> dtos =  entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> findById(@PathVariable Long id) {
        Optional<E> entity = genericService.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> create(@RequestBody D dto) {
        E entity = mapper.toEntity(dto);
        E savedEntity = genericService.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @PutMapping(value = ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> update(@PathVariable Long id, @RequestBody D dto) {
        E entity = mapper.toEntity(dto);
        E updatedEntity = genericService.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = genericService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}