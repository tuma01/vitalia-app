package com.amachi.app.core.common.service;

import com.amachi.app.core.common.dto.BaseSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface Core para servicios de la aplicación.
 * PUBLIC para ser visible desde todos los módulos (core-geography, etc).
 */
public interface GenericService<E, F extends BaseSearchDto> {

    List<E> getAll();

    Page<E> getAll(F searchDto, Integer pageIndex, Integer pageSize);

    E getById(Long id);

    E create(E entity);

    E update(Long id, E entity);

    void delete(Long id);
}
