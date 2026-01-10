package com.amachi.app.vitalia.medicalcatalog.demographic.service.impl;

import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.GenderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import com.amachi.app.vitalia.medicalcatalog.demographic.repository.GenderRepository;
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
class GenderServiceImplTest {

    @Mock
    private GenderRepository repository;

    @InjectMocks
    private GenderServiceImpl service;

    private Gender entity;

    @BeforeEach
    void setUp() {
        entity = new Gender();
        entity.setId(1L);
        entity.setCode("M");
        entity.setName("Masculino");
        entity.setActive(true);
    }

    @Test
    void getAll_WithFilters_ShouldReturnPage() {
        GenderSearchDto searchDto = new GenderSearchDto();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Gender> entityPage = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(entityPage);

        Page<Gender> result = service.getAll(searchDto, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getById_ShouldReturnEntity() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Gender result = service.getById(1L);

        assertNotNull(result);
        assertEquals("M", result.getCode());
    }
}
