package com.amachi.app.vitalia.medicalcatalog.procedure.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.search.MedicalProcedureSearchDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import com.amachi.app.vitalia.medicalcatalog.procedure.repository.MedicalProcedureRepository;
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
class MedicalProcedureServiceImplTest extends AbstractTestSupport {

    @Mock
    private MedicalProcedureRepository repository;

    @InjectMocks
    private MedicalProcedureServiceImpl service;

    @Test
    void getAll_ShouldReturnList() {
        MedicalProcedure entity = loadJson("data/procedure/procedure-entity.json", MedicalProcedure.class);
        when(repository.findAll()).thenReturn(List.of(entity));

        List<MedicalProcedure> result = service.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("90.3.8.01");
    }

    @Test
    void getAllPaginated_ShouldReturnPage() {
        MedicalProcedure entity = loadJson("data/procedure/procedure-entity.json", MedicalProcedure.class);
        MedicalProcedureSearchDto searchDto = loadJson("data/procedure/procedure-search-dto.json", MedicalProcedureSearchDto.class);
        Page<MedicalProcedure> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<MedicalProcedure> result = service.getAll(searchDto, 0, 10);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void getById_ShouldReturnEntity() {
        MedicalProcedure entity = loadJson("data/procedure/procedure-entity.json", MedicalProcedure.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        MedicalProcedure result = service.getById(1L);

        assertThat(result.getCode()).isEqualTo("90.3.8.01");
    }

    @Test
    void create_ShouldSave() {
        MedicalProcedure entity = loadJson("data/procedure/procedure-entity.json", MedicalProcedure.class);
        when(repository.save(any())).thenReturn(entity);

        MedicalProcedure result = service.create(entity);

        assertThat(result.getCode()).isEqualTo("90.3.8.01");
    }

    @Test
    void update_ShouldSave() {
        MedicalProcedure entity = loadJson("data/procedure/procedure-entity.json", MedicalProcedure.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        MedicalProcedure result = service.update(1L, entity);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void delete_ShouldCallRepo() {
        MedicalProcedure entity = loadJson("data/procedure/procedure-entity.json", MedicalProcedure.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository, times(1)).delete(entity);
    }
}
