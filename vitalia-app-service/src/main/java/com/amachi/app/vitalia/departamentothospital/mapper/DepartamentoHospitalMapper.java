package com.amachi.app.vitalia.departamentothospital.mapper;

import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.GenericMapperConfig;
import com.amachi.app.vitalia.departamentothospital.dto.DepartamentoHospitalDto;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = GenericMapperConfig.class)
public interface DepartamentoHospitalMapper extends EntityDtoMapper<DepartamentoHospital, DepartamentoHospitalDto> {

    DepartamentoHospitalMapper INSTANCE = Mappers.getMapper(DepartamentoHospitalMapper.class);

    @Override
    DepartamentoHospital toEntity(DepartamentoHospitalDto dto);

    @Override
    DepartamentoHospitalDto toDto(DepartamentoHospital entity);
}