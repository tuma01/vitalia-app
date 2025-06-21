package com.amachi.app.vitalia.common.mapper;


import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@BaseMapper
public interface GenericMapper<E, D> {
//    @Mapping(target = "id", source = "id")
    E toEntity(D dto);

//    @Mapping(target = "id", source = "id")
    D toDto(E entity);

    default List<D> toDtoList(Collection<E> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    default List<E> toEntityList(Collection<D> dtos) {
        return dtos == null ? List.of() : dtos.stream().map(this::toEntity).toList();
    }

    default Set<E> toEntitySet(Collection<D> dtos) {
        return dtos == null ? Set.of() : dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
