package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.repository.HealthcareProviderRepository;
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
class HealthcareProviderServiceImplTest extends AbstractTestSupport {

    @Mock
    private HealthcareProviderRepository repository;

    @InjectMocks
    private HealthcareProviderServiceImpl service;

    @Test
    void getAll_ShouldReturnList() {
        HealthcareProvider entity = loadJson("data/provider/provider-entity.json", HealthcareProvider.class);
        when(repository.findAll()).thenReturn(List.of(entity));

        List<HealthcareProvider> result = service.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("SURA EPS");
    }

    @Test
    void getAllPaginated_ShouldReturnPage() {
        HealthcareProvider entity = loadJson("data/provider/provider-entity.json", HealthcareProvider.class);
        HealthcareProviderSearchDto searchDto = loadJson("data/provider/provider-search-dto.json", HealthcareProviderSearchDto.class);
        Page<HealthcareProvider> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<HealthcareProvider> result = service.getAll(searchDto, 0, 10);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void getById_ShouldReturnEntity() {
        HealthcareProvider entity = loadJson("data/provider/provider-entity.json", HealthcareProvider.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        HealthcareProvider result = service.getById(1L);

        assertThat(result.getName()).isEqualTo("SURA EPS");
    }

    @Test
    void create_ShouldSave() {
        HealthcareProvider entity = loadJson("data/provider/provider-entity.json", HealthcareProvider.class);
        when(repository.save(any())).thenReturn(entity);

        HealthcareProvider result = service.create(entity);

        assertThat(result.getName()).isEqualTo("SURA EPS");
    }

    @Test
    void update_ShouldSave() {
        HealthcareProvider entity = loadJson("data/provider/provider-entity.json", HealthcareProvider.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        HealthcareProvider result = service.update(1L, entity);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void delete_ShouldCallRepo() {
        HealthcareProvider entity = loadJson("data/provider/provider-entity.json", HealthcareProvider.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository, times(1)).delete(entity);
    }
}
