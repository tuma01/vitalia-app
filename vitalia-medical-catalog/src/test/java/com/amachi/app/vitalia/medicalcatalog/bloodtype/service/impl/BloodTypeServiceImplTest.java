package com.amachi.app.vitalia.medicalcatalog.bloodtype.service.impl;

import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.dto.search.BloodTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.repository.BloodTypeRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BloodTypeServiceImplTest extends AbstractTestSupport {

    @Mock
    private BloodTypeRepository bloodTypeRepository;
    @InjectMocks
    private BloodTypeServiceImpl service;

    @Test
    void getById_ShouldReturnEntity() {
        BloodType entity = BloodType.builder().id(1L).code("O+").name("O Positivo").build();
        when(bloodTypeRepository.findById(1L)).thenReturn(Optional.of(entity));
        BloodType result = service.getById(1L);
        assertThat(result.getCode()).isEqualTo("O+");
    }

    @Test
    void getAll_WithFilters_ShouldReturnPage() {
        BloodTypeSearchDto searchDto = new BloodTypeSearchDto();
        BloodType entity = BloodType.builder().id(1L).code("O+").name("O Positivo").build();
        Page<BloodType> entityPage = new PageImpl<>(List.of(entity));

        when(bloodTypeRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(entityPage);

        Page<BloodType> result = service.getAll(searchDto, 0, 10);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
    }
}
