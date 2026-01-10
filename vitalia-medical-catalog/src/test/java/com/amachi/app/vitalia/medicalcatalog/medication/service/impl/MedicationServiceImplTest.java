package com.amachi.app.vitalia.medicalcatalog.medication.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import com.amachi.app.vitalia.medicalcatalog.medication.repository.MedicationRepository;
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
class MedicationServiceImplTest extends AbstractTestSupport {

    @Mock
    private MedicationRepository repository;

    @InjectMocks
    private MedicationServiceImpl service;

    @Test
    void getAll_ShouldReturnList() {
        Medication entity = loadJson("data/medication/medication-entity.json", Medication.class);
        when(repository.findAll()).thenReturn(List.of(entity));

        List<Medication> result = service.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGenericName()).isEqualTo("Paracetamol");
    }

    @Test
    void getAllPaginated_ShouldReturnPage() {
        Medication entity = loadJson("data/medication/medication-entity.json", Medication.class);
        MedicationSearchDto searchDto = loadJson("data/medication/medication-search-dto.json", MedicationSearchDto.class);
        Page<Medication> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<Medication> result = service.getAll(searchDto, 0, 10);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void getById_ShouldReturnEntity() {
        Medication entity = loadJson("data/medication/medication-entity.json", Medication.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Medication result = service.getById(1L);

        assertThat(result.getGenericName()).isEqualTo("Paracetamol");
    }

    @Test
    void create_ShouldSave() {
        Medication entity = loadJson("data/medication/medication-entity.json", Medication.class);
        when(repository.save(any())).thenReturn(entity);

        Medication result = service.create(entity);

        assertThat(result.getGenericName()).isEqualTo("Paracetamol");
    }

    @Test
    void update_ShouldSave() {
        Medication entity = loadJson("data/medication/medication-entity.json", Medication.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        Medication result = service.update(1L, entity);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void delete_ShouldCallRepo() {
        Medication entity = loadJson("data/medication/medication-entity.json", Medication.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository, times(1)).delete(entity);
    }
}
