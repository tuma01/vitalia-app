package com.amachi.app.core.common.service;

import com.amachi.app.core.common.dto.BaseSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import java.util.List;

public interface GenericService<E, F extends BaseSearchDto> {

    @NonNull
    List<E> getAll();

    @NonNull
    Page<E> getAll(@NonNull F searchDto, @NonNull Integer pageIndex, @NonNull Integer pageSize);

    @NonNull
    E getById(@NonNull Long id);

    @NonNull
    E create(@NonNull E entity);

    @NonNull
    E update(@NonNull Long id, @NonNull E entity);

    void delete(@NonNull Long id);
}
