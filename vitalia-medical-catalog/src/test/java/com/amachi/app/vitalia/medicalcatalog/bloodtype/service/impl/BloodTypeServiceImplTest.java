package com.amachi.app.vitalia.medicalcatalog.bloodtype.service.impl;

import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.repository.BloodTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BloodTypeServiceImplTest extends AbstractTestSupport {
    @Mock private BloodTypeRepository repository;
    @InjectMocks private BloodTypeServiceImpl service;

    @Test
    void getById_ShouldReturnEntity() {
        BloodType entity = BloodType.builder().id(1L).code("O+").name("O Positivo").build();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        BloodType result = service.getById(1L);
        assertThat(result.getCode()).isEqualTo("O+");
    }
}
