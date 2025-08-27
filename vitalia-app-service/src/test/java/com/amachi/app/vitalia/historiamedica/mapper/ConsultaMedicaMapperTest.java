package com.amachi.app.vitalia.historiamedica.mapper;

import com.amachi.app.vitalia.historiamedica.dto.ConsultaMedicaDto;
import com.amachi.app.vitalia.historiamedica.entity.ConsultaMedica;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaMedicaMapperTest {
    private ConsultaMedicaMapper consultaMedicaMapper;

    @BeforeEach
    void setUp() {
        consultaMedicaMapper = new ConsultaMedicaMapperImpl(Mappers.getMapper(TipoConsultaMapper.class));
    }
    @Test
    void shouldMapConsultaMedicaDtoToEntity() {

        ConsultaMedicaDto dto = Instancio.create(ConsultaMedicaDto.class);

        ConsultaMedica entity = consultaMedicaMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getEstadoConsulta(), entity.getEstadoConsulta());

        assertNotNull(entity.getTipoConsulta());
        assertEquals(dto.getTipoConsulta().getId(), entity.getTipoConsulta().getId());
        assertEquals(dto.getTipoConsulta().getDescripcion(), entity.getTipoConsulta().getDescripcion());
        assertEquals(dto.getTipoConsulta().getEspecialidad(), entity.getTipoConsulta().getEspecialidad());
        assertEquals(dto.getTipoConsulta().getNombre(), entity.getTipoConsulta().getNombre());

        assertEquals(dto.getFechaConsulta(), entity.getFechaConsulta());

        assertNotNull(entity.getDoctor());
        assertEquals(dto.getDoctorId(), entity.getDoctor().getId());

        assertEquals(dto.getMotivoConsulta(), entity.getMotivoConsulta());
        assertEquals(dto.getSintomas(), entity.getSintomas());
        assertEquals(dto.getDiagnostico(), entity.getDiagnostico());
        assertEquals(dto.getTratamiento(), entity.getTratamiento());

        assertNotNull(entity.getHistoriaMedica());
        assertEquals(dto.getHistoriaMedicaId(), entity.getHistoriaMedica().getId());
    }

    @Test
    void shouldMapConsultaMedicaToDto() {

        ConsultaMedica entity = Instancio.create(ConsultaMedica.class);

        ConsultaMedicaDto dto = consultaMedicaMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEstadoConsulta(), dto.getEstadoConsulta());
        assertNotNull(dto.getTipoConsulta());
        assertEquals(entity.getTipoConsulta().getId(), dto.getTipoConsulta().getId());
        assertEquals(entity.getTipoConsulta().getDescripcion(), dto.getTipoConsulta().getDescripcion());
        assertEquals(entity.getTipoConsulta().getEspecialidad(), dto.getTipoConsulta().getEspecialidad());
        assertEquals(entity.getTipoConsulta().getNombre(), dto.getTipoConsulta().getNombre());
        assertEquals(entity.getFechaConsulta(), dto.getFechaConsulta());
        assertNotNull(entity.getDoctor());
        assertEquals(entity.getDoctor().getId(), dto.getDoctorId());
        assertEquals(entity.getMotivoConsulta(), dto.getMotivoConsulta());
        assertEquals(entity.getSintomas(), dto.getSintomas());
        assertEquals(entity.getDiagnostico(), dto.getDiagnostico());
        assertEquals(entity.getTratamiento(), dto.getTratamiento());
        assertNotNull(entity.getHistoriaMedica());
        assertEquals(entity.getHistoriaMedica().getId(), dto.getHistoriaMedicaId());
    }
}