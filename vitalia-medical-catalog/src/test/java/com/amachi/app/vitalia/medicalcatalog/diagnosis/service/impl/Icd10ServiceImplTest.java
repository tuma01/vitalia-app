package com.amachi.app.vitalia.medicalcatalog.diagnosis.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.search.Icd10SearchDto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.repository.Icd10Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Icd10ServiceImplTest extends AbstractTestSupport {

    @Mock
    private Icd10Repository repository;

    @InjectMocks
    private Icd10ServiceImpl service;

    @Test
    void getAll_ShouldReturnList() {
        Icd10 entity = loadJson("data/diagnosis/icd10-entity.json", Icd10.class);
        when(repository.findAll()).thenReturn(List.of(entity));

        List<Icd10> result = service.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("A00.0");
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAllPaginated_ShouldReturnPage() {
        Icd10 entity = loadJson("data/diagnosis/icd10-entity.json", Icd10.class);
        Icd10SearchDto searchDto = loadJson("data/diagnosis/icd10-search-dto.json", Icd10SearchDto.class);
        Page<Icd10> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<Icd10> result = service.getAll(searchDto, 0, 10);

        assertThat(result.getContent()).hasSize(1);
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void getById_ShouldReturnEntity() {
        Icd10 entity = loadJson("data/diagnosis/icd10-entity.json", Icd10.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Icd10 result = service.getById(1L);

        assertThat(result.getCode()).isEqualTo("A00.0");
    }

    @Test
    void getById_WhenNotFound_ShouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_ShouldSaveEntity() {
        Icd10 entity = loadJson("data/diagnosis/icd10-entity.json", Icd10.class);
        when(repository.save(any(Icd10.class))).thenReturn(entity);

        Icd10 result = service.create(entity);

        assertThat(result.getCode()).isEqualTo("A00.0");
        verify(repository, times(1)).save(entity);
    }

    @Test
    void update_ShouldSaveEntity() {
        Icd10 entity = loadJson("data/diagnosis/icd10-entity.json", Icd10.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(Icd10.class))).thenReturn(entity);

        Icd10 result = service.update(1L, entity);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository, times(1)).save(entity);
    }

    @Test
    void delete_ShouldCallRepository() {
        Icd10 entity = loadJson("data/diagnosis/icd10-entity.json", Icd10.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository, times(1)).delete(entity);
    }
}
