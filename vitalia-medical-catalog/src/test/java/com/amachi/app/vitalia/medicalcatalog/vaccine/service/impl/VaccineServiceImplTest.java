package com.amachi.app.vitalia.medicalcatalog.vaccine.service.impl;

import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.search.VaccineSearchDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.entity.Vaccine;
import com.amachi.app.vitalia.medicalcatalog.vaccine.repository.VaccineRepository;
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
class VaccineServiceImplTest {

    @Mock
    private VaccineRepository repository;

    @InjectMocks
    private VaccineServiceImpl service;

    private Vaccine entity;

    @BeforeEach
    void setUp() {
        entity = new Vaccine();
        entity.setId(1L);
        entity.setCode("VAC-001");
        entity.setName("BCG");
        entity.setActive(true);
    }

    @Test
    void getAll_WithFilters_ShouldReturnPage() {
        VaccineSearchDto searchDto = new VaccineSearchDto();
        Page<Vaccine> entityPage = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(entityPage);

        Page<Vaccine> result = service.getAll(searchDto, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getById_ShouldReturnEntity() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Vaccine result = service.getById(1L);

        assertNotNull(result);
        assertEquals("VAC-001", result.getCode());
    }
}
