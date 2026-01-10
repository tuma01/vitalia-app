package com.amachi.app.core.common.mapper;


import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

//@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface EntityDtoMapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    default List<D> toDTOs(Collection<E> entityList) {
        if (entityList == null) {
            return emptyList();
        }
        return entityList.stream().map(this::toDto).toList();
    }

    default List<E> toEntities(Collection<D> modelList) {
        if (modelList == null) {
            return emptyList();
        }
        return modelList.stream().map(this::toEntity).toList();
    }

    default Set<E> toEntitiesSet(Collection<D> modelSet) {
        if (modelSet == null) {
            return emptySet();
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
        if (ids == null) return null;
        return ids.stream().map(builderFn).toList();
    }

    // 🔹 Conversión genérica List<Entity> -> List<Long>
    default <T> List<Long> entitiesToIdsList(List<T> entities, Function<T, Long> idExtractor) {
        if (entities == null) return null;
        return entities.stream().map(idExtractor).toList();
    }
}
