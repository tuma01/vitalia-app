package com.amachi.app.vitalia.common.service;

import com.amachi.app.vitalia.common.dto.BaseSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface GenericService<E, F extends BaseSearchDto>{

    List<E> getAll();

    Page<E> getAll(F searchDto, Integer pageIndex, Integer pageSize);

    E getById(Long id);

    E create(E entity);

    E update(Long id, E entity);

    void delete(Long id);
}
