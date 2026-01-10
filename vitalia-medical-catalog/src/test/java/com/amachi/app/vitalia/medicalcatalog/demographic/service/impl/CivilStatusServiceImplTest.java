package com.amachi.app.vitalia.medicalcatalog.demographic.service.impl;

import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.CivilStatusSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import com.amachi.app.vitalia.medicalcatalog.demographic.repository.CivilStatusRepository;
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
class CivilStatusServiceImplTest {

    @Mock
    private CivilStatusRepository repository;

    @InjectMocks
    private CivilStatusServiceImpl service;

    private CivilStatus entity;

    @BeforeEach
    void setUp() {
        entity = new CivilStatus();
        entity.setId(1L);
        entity.setCode("SINGLE");
        entity.setName("Soltero");
        entity.setActive(true);
    }

    @Test
    void getAll_WithFilters_ShouldReturnPage() {
        CivilStatusSearchDto searchDto = new CivilStatusSearchDto();
        Page<CivilStatus> entityPage = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(entityPage);

        Page<CivilStatus> result = service.getAll(searchDto, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getById_ShouldReturnEntity() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        CivilStatus result = service.getById(1L);

        assertNotNull(result);
        assertEquals("SINGLE", result.getCode());
    }
}
