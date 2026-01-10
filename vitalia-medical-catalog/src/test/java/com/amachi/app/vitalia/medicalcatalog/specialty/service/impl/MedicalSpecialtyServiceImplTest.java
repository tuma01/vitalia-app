package com.amachi.app.vitalia.medicalcatalog.specialty.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.search.MedicalSpecialtySearchDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import com.amachi.app.vitalia.medicalcatalog.specialty.repository.MedicalSpecialtyRepository;
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
class MedicalSpecialtyServiceImplTest extends AbstractTestSupport {

    @Mock
    private MedicalSpecialtyRepository repository;

    @InjectMocks
    private MedicalSpecialtyServiceImpl service;

    @Test
    void getAll_ShouldReturnList() {
        MedicalSpecialty entity = loadJson("data/specialty/specialty-entity.json", MedicalSpecialty.class);
        when(repository.findAll()).thenReturn(List.of(entity));

        List<MedicalSpecialty> result = service.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Cardiología");
    }

    @Test
    void getAllPaginated_ShouldReturnPage() {
        MedicalSpecialty entity = loadJson("data/specialty/specialty-entity.json", MedicalSpecialty.class);
        MedicalSpecialtySearchDto searchDto = loadJson("data/specialty/specialty-search-dto.json", MedicalSpecialtySearchDto.class);
        Page<MedicalSpecialty> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<MedicalSpecialty> result = service.getAll(searchDto, 0, 10);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void getById_ShouldReturnEntity() {
        MedicalSpecialty entity = loadJson("data/specialty/specialty-entity.json", MedicalSpecialty.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        MedicalSpecialty result = service.getById(1L);

        assertThat(result.getName()).isEqualTo("Cardiología");
    }

    @Test
    void create_ShouldSave() {
        MedicalSpecialty entity = loadJson("data/specialty/specialty-entity.json", MedicalSpecialty.class);
        when(repository.save(any())).thenReturn(entity);

        MedicalSpecialty result = service.create(entity);

        assertThat(result.getName()).isEqualTo("Cardiología");
    }

    @Test
    void update_ShouldSave() {
        MedicalSpecialty entity = loadJson("data/specialty/specialty-entity.json", MedicalSpecialty.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        MedicalSpecialty result = service.update(1L, entity);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void delete_ShouldCallRepo() {
        MedicalSpecialty entity = loadJson("data/specialty/specialty-entity.json", MedicalSpecialty.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository, times(1)).delete(entity);
    }
}
