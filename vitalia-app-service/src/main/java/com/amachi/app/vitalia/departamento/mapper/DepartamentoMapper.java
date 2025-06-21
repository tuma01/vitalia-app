package com.amachi.app.vitalia.departamento.mapper;

import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.GenericMapperConfig;
import com.amachi.app.vitalia.departamento.dto.DepartamentoDto;
import com.amachi.app.vitalia.departamento.entity.Departamento;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = GenericMapperConfig.class)
public interface DepartamentoMapper extends EntityDtoMapper<Departamento, DepartamentoDto> {


//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "createdDate", ignore = true)
//    @Mapping(target = "lastModifiedBy", ignore = true)
//    @Mapping(target = "lastModifiedDate", ignore = true)
//    Departamento toEntity(DepartamentoDto dto);
//
////        @Mapping(target = "createdBy", ignore = true)
////    @Mapping(target = "createdDate", ignore = true)
////    @Mapping(target = "lastModifiedBy", ignore = true)
////    @Mapping(target = "lastModifiedDate", ignore = true)
//    DepartamentoDto toDto(Departamento entity);
}