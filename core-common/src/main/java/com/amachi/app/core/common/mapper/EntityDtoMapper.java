package com.amachi.app.core.common.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

//@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface EntityDtoMapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    void updateEntityFromDto(D dto, @org.mapstruct.MappingTarget E entity);

    default List<D> toDTOs(Collection<E> entityList) {
        if (entityList == null) {
            return new ArrayList<>();
        }
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<D> toDtoList(Collection<E> entityList) {
        return toDTOs(entityList);
    }

    default List<E> toEntities(Collection<D> modelList) {
        if (modelList == null) {
            return new ArrayList<>();
        }
        return modelList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    default Set<E> toEntitiesSet(Collection<D> modelSet) {
        if (modelSet == null) {
            return new HashSet<>();
        }
        return modelSet.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    // 🔹 Conversión genérica Set<Long> -> Set<Entity>
    default <T> Set<T> idsToEntities(Set<Long> ids, Function<Long, T> builderFn) {
        if (ids == null) {
            return null;
        }
        return ids.stream()
                .map(builderFn)
                .collect(Collectors.toSet());
    }

    // 🔹 Conversión genérica Set<Entity> -> Set<Long>
    default <T> Set<Long> entitiesToIds(Set<T> entities, Function<T, Long> idExtractor) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(idExtractor)
                .collect(Collectors.toSet());
    }

    // 🔹 Conversión genérica List<Long> -> List<Entity>
    default <T> List<T> idsToEntitiesList(List<Long> ids, Function<Long, T> builderFn) {
        if (ids == null)
            return null;
        return ids.stream().map(builderFn).toList();
    }

    // 🔹 Conversión genérica List<Entity> -> List<Long>
    default <T> List<Long> entitiesToIdsList(List<T> entities, Function<T, Long> idExtractor) {
        if (entities == null)
            return null;
        return entities.stream().map(idExtractor).toList();
    }
}
