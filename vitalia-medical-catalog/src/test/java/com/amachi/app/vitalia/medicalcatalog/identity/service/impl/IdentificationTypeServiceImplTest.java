package com.amachi.app.vitalia.medicalcatalog.identity.service.impl;

import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import com.amachi.app.vitalia.medicalcatalog.identity.repository.IdentificationTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdentificationTypeServiceImplTest {

    @Mock
    private IdentificationTypeRepository repository;

    @InjectMocks
    private IdentificationTypeServiceImpl service;

    private IdentificationType entity;

    @BeforeEach
    void setUp() {
        entity = new IdentificationType();
        entity.setId(1L);
        entity.setCode("CC");
        entity.setName("Cédula de Ciudadanía");
        entity.setActive(true);
    }

    @Test
    void getAll_WithFilters_ShouldReturnPage() {
        IdentificationTypeSearchDto searchDto = new IdentificationTypeSearchDto();
        Page<IdentificationType> entityPage = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(entityPage);

        Page<IdentificationType> result = service.getAll(searchDto, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getById_ShouldReturnEntity() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        IdentificationType result = service.getById(1L);

        assertNotNull(result);
        assertEquals("CC", result.getCode());
    }

    @Test
    void create_ShouldReturnEntity() {
        when(repository.save(any(IdentificationType.class))).thenReturn(entity);

        IdentificationType result = service.create(entity);

        assertNotNull(result);
        assertEquals("CC", result.getCode());
        verify(repository).save(any());
    }

    @Test
    void delete_ShouldCallRepository() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);
        verify(repository).delete(entity);
    }
}
