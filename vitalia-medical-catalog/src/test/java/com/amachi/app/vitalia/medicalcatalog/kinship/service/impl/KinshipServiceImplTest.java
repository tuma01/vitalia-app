package com.amachi.app.vitalia.medicalcatalog.kinship.service.impl;

import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import com.amachi.app.vitalia.medicalcatalog.kinship.repository.KinshipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KinshipServiceImplTest extends AbstractTestSupport {
    @Mock private KinshipRepository repository;
    @InjectMocks private KinshipServiceImpl service;

    @Test
    void getById_ShouldReturnEntity() {
        Kinship entity = Kinship.builder().id(1L).code("FATHER").name("Padre").build();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        Kinship result = service.getById(1L);
        assertThat(result.getCode()).isEqualTo("FATHER");
    }
}
